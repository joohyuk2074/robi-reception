package me.weekbelt.apiserver.department.service;

import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import me.weekbelt.apiserver.department.dto.DepartmentCreateRequest;
import me.weekbelt.apiserver.department.dto.DepartmentResponse;
import me.weekbelt.apiserver.department.dto.DepartmentUpdateRequest;
import me.weekbelt.apiserver.exception.DirectoryException;
import me.weekbelt.persistence.Phone;
import me.weekbelt.persistence.PhoneType;
import me.weekbelt.persistence.department.Department;
import me.weekbelt.persistence.department.DepartmentSynonym;
import me.weekbelt.persistence.department.DepartmentTree;
import me.weekbelt.persistence.department.repository.DepartmentRepository;
import me.weekbelt.persistence.department.repository.DepartmentSynonymRepository;
import me.weekbelt.persistence.department.repository.DepartmentTreeRepository;
import me.weekbelt.persistence.department.service.DepartmentDataService;
import me.weekbelt.persistence.department.service.DepartmentSynonymDataService;
import me.weekbelt.persistence.department.service.DepartmentTreeDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
class ClientDepartmentCommandServiceTest {


    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentTreeRepository departmentTreeRepository;

    @Autowired
    private DepartmentSynonymRepository departmentSynonymRepository;

    private DepartmentDataService departmentDataService;

    @SpyBean
    private DepartmentTreeDataService departmentTreeDataService;

    private DepartmentSynonymDataService departmentSynonymDataService;

    private ClientDepartmentCommandService departmentCommandService;

    @BeforeEach
    public void initClientDepartmentService() {
        departmentDataService = new DepartmentDataService(departmentRepository);
        departmentSynonymDataService = new DepartmentSynonymDataService(departmentSynonymRepository);
        departmentCommandService = new ClientDepartmentCommandService(departmentDataService, departmentTreeDataService, departmentSynonymDataService);
    }

    @Test
    @DisplayName("최상위 계층 부서 생성 성공")
    public void create_root_department_success() {
        // given
        DepartmentCreateRequest createRequest = DepartmentCreateRequest.builder()
            .name("최상위부서")
            .number("1234")
            .phoneType(PhoneType.INWARD_DIALING)
            .branchId("test")
            .build();

        // when
        DepartmentResponse departmentResponse = departmentCommandService.save(createRequest);

        // then
        assertThat(departmentResponse.getId()).isNotEmpty();

        List<DepartmentTree> departmentTrees = departmentTreeDataService.getByDescendant(departmentResponse.getId());
        assertThat(departmentTrees.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("최상위 계층을 부모로 하는 1 계층 부서 생성 성공")
    public void create_depth_1_department_success() {
        // given
        Department rootDepartment = createRootDepartment(EMPTY_LIST);
        DepartmentCreateRequest createRequest = DepartmentCreateRequest.builder()
            .name("1계층부서")
            .number("4321")
            .phoneType(PhoneType.INWARD_DIALING)
            .branchId("test")
            .parentId(rootDepartment.getId())
            .build();

        // when
        DepartmentResponse departmentResponse = departmentCommandService.save(createRequest);

        // then
        assertThat(departmentResponse.getId()).isNotEmpty();

        List<DepartmentTree> departmentTrees = departmentTreeDataService.getByDescendant(departmentResponse.getId());
        assertThat(departmentTrees.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("최상위 부서 정보를 수정")
    public void update_department_info() {
        // given
        Department rootDepartment = createRootDepartment(EMPTY_LIST);
        DepartmentUpdateRequest updateRequest = DepartmentUpdateRequest.builder()
            .name("수정된부서")
            .phoneType(PhoneType.GROUP_DIALING)
            .number("3001")
            .build();

        // when
        DepartmentResponse updatedDepartment = departmentCommandService.update(rootDepartment.getId(), updateRequest);

        // then
        assertThat(updatedDepartment.getName()).isEqualTo("수정된부서");
        assertThat(updatedDepartment.getNumber()).isEqualTo("3001");
        assertThat(updatedDepartment.getPhoneType()).isEqualTo(PhoneType.GROUP_DIALING);
    }

    @Test
    @DisplayName("부서 삭제를 성공한다")
    public void delete_department_success() {
        // given
        Department rootDepartment = createRootDepartment(EMPTY_LIST);

        // when
        departmentCommandService.delete(rootDepartment.getId());

        // then
        Throwable throwable = catchThrowable(() -> departmentDataService.getById(rootDepartment.getId()));
        assertThat(throwable).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("하위 부서 혹은 직원이 존재하기때문에 부서 삭제를 실패한다")
    public void delete_department_fail_sub_directory_exists() {
        // given
        Department rootDepartment = createRootDepartment(List.of("최상, 최상위 부서"));

        DepartmentTree departmentTree = DepartmentTree.builder()
            .ancestor(rootDepartment.getId())
            .descendant("123")
            .depth(1)
            .build();
        given(departmentTreeDataService.getByAncestor(rootDepartment.getId())).willReturn(List.of(departmentTree));

        // when
        Throwable thrown = catchThrowable(() -> departmentCommandService.delete(rootDepartment.getId()));

        // then
        assertThat(thrown).isInstanceOf(DirectoryException.class);
    }

    @Test
    @DisplayName("Department에 동의어를 추가한다")
    public void add_department_synonyms() {
        // given
        Department rootDepartment = createRootDepartment(EMPTY_LIST);
        List<String> synonyms = List.of("최상위", "최상위", "최상");

        // when
        DepartmentResponse departmentResponse = departmentCommandService.addSynonyms(rootDepartment.getId(), synonyms);

        // then
        assertThat(departmentResponse.getSynonyms().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Department에 동의어를 삭제한다")
    public void remove_department_synonyms() {
        // given
        Department rootDepartment = createRootDepartment(List.of("최상위", "최상위", "최상"));
        Set<DepartmentSynonym> departmentSynonyms = rootDepartment.getDepartmentSynonyms();
        List<String> departmentSynonymIds = departmentSynonyms.stream()
            .map(DepartmentSynonym::getId)
            .toList();

        // when
        DepartmentResponse departmentResponse = departmentCommandService.deleteSynonym(rootDepartment.getId(), departmentSynonymIds.get(0));

        // then
        assertThat(departmentResponse.getSynonyms().size()).isEqualTo(1);
    }

    private Department createRootDepartment(List<String> synonyms) {
        Phone rootPhone = Phone.builder()
            .number("1234")
            .phoneType(PhoneType.INWARD_DIALING)
            .build();
        Department rootDepartment = Department.builder()
            .id(UUID.randomUUID().toString())
            .name("최상위부서")
            .phone(rootPhone)
            .branchId("test")
            .build();
        rootDepartment.addSynonyms(synonyms);
        departmentRepository.save(rootDepartment);

        DepartmentTree departmentTree = DepartmentTree.builder()
            .id(UUID.randomUUID().toString())
            .ancestor(rootDepartment.getId())
            .descendant(rootDepartment.getId())
            .depth(0)
            .branchId("test")
            .build();
        departmentTreeRepository.save(departmentTree);

        return rootDepartment;
    }
}