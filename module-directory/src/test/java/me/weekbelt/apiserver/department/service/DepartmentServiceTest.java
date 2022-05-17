package me.weekbelt.apiserver.department.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import me.weekbelt.persistence.Phone;
import me.weekbelt.persistence.PhoneType;
import me.weekbelt.persistence.department.Department;
import me.weekbelt.persistence.department.DepartmentTree;
import me.weekbelt.persistence.department.dto.DepartmentCreateRequest;
import me.weekbelt.persistence.department.dto.DepartmentResponse;
import me.weekbelt.persistence.department.repository.DepartmentRepository;
import me.weekbelt.persistence.department.repository.DepartmentTreeRepository;
import me.weekbelt.persistence.department.service.DepartmentDataService;
import me.weekbelt.persistence.department.service.DepartmentTreeDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class DepartmentServiceTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentTreeRepository departmentTreeRepository;

    private DepartmentDataService departmentDataService;

    private DepartmentTreeDataService departmentTreeDataService;

    private DepartmentService departmentService;

    @BeforeEach
    public void initClientDepartmentService() {
        departmentDataService = new DepartmentDataService(departmentRepository);
        departmentTreeDataService = new DepartmentTreeDataService(departmentTreeRepository);
        departmentService = new DepartmentService(departmentDataService, departmentTreeDataService);
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
        DepartmentResponse departmentResponse = departmentService.save(createRequest);

        // then
        assertThat(departmentResponse.getId()).isNotEmpty();

        List<DepartmentTree> departmentTrees = departmentTreeDataService.getByDescendant(departmentResponse.getId());
        assertThat(departmentTrees.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("최상위 계층을 부모로 하는 1 계층 부서 생성 성공")
    public void create_depth_1_department_success() {
        // given
        Department rootDepartment = createRootDepartment();
        DepartmentCreateRequest createRequest = DepartmentCreateRequest.builder()
            .name("1계층부서")
            .number("4321")
            .phoneType(PhoneType.INWARD_DIALING)
            .branchId("test")
            .parentId(rootDepartment.getId())
            .build();

        // when
        DepartmentResponse departmentResponse = departmentService.save(createRequest);

        // then
        assertThat(departmentResponse.getId()).isNotEmpty();

        List<DepartmentTree> departmentTrees = departmentTreeDataService.getByDescendant(departmentResponse.getId());
        assertThat(departmentTrees.size()).isEqualTo(2);
    }

    private Department createRootDepartment() {
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