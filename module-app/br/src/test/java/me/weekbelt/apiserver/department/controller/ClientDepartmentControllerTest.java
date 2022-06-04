package me.weekbelt.apiserver.department.controller;

import static java.util.Collections.EMPTY_LIST;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import me.weekbelt.apiserver.department.dto.DepartmentCreateRequest;
import me.weekbelt.apiserver.department.dto.DepartmentResponse;
import me.weekbelt.apiserver.department.dto.DepartmentUpdateRequest;
import me.weekbelt.apiserver.department.service.ClientDepartmentCommandService;
import me.weekbelt.persistence.PhoneType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@WebMvcTest(ClientDepartmentController.class)
@ExtendWith({RestDocumentationExtension.class})
class ClientDepartmentControllerTest {

    private static final String DEPARTMENT_BASE_URL = "/admin/v1/departments";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientDepartmentCommandService departmentCommandService;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void before(WebApplicationContext ctx, RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
            .apply(documentationConfiguration(restDocumentationContextProvider))
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
    }

    @Test
    @DisplayName("Department를 생성한다")
    public void create_department_success() throws Exception {
        // given
        DepartmentCreateRequest departmentCreateRequest = DepartmentCreateRequest.builder()
            .name("수지구청")
            .number("1234")
            .phoneType(PhoneType.INWARD_DIALING)
            .parentId("parentDepartmentId")
            .branchId("test")
            .build();

        DepartmentResponse departmentResponse = DepartmentResponse.builder()
            .id(UUID.randomUUID().toString())
            .name("수지구청")
            .number("1234")
            .phoneType(PhoneType.INWARD_DIALING)
            .branchId("test")
            .synonyms(EMPTY_LIST)
            .build();

        given(departmentCommandService.save(any())).willReturn(departmentResponse);

        // when
        ResultActions resultActions = mockMvc.perform(post(DEPARTMENT_BASE_URL)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(departmentCreateRequest)));

        // then
        resultActions
            .andDo(getCreateDepartmentApiDocument())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("id").isNotEmpty())
            .andExpect(jsonPath("name").value("수지구청"))
            .andExpect(jsonPath("number").value("1234"))
            .andExpect(jsonPath("phoneType").value("INWARD_DIALING"))
            .andExpect(jsonPath("branchId").value("test"));
    }

    private RestDocumentationResultHandler getCreateDepartmentApiDocument() {
        return document("department",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestFields(
                fieldWithPath("name").description("부서명"),
                fieldWithPath("number").description("부서 번호"),
                fieldWithPath("phoneType").description("부서 번호 타입(내선, 외선, 그룹)"),
                fieldWithPath("parentId").description("상위 부서 식별자 ID"),
                fieldWithPath("branchId").description("브랜치 ID")
            ),
            responseFields(
                fieldWithPath("id").description("부서의 식별 id"),
                fieldWithPath("name").description("부서명"),
                fieldWithPath("number").description("부서 번호"),
                fieldWithPath("phoneType").description("부서 번호 타입(내선, 외선, 그룹)"),
                fieldWithPath("branchId").description("브랜치 ID"),
                fieldWithPath("synonyms").description("동의어 리스트")
            ));
    }

    @Test
    @DisplayName("Department를 수정한다")
    public void update_department_success() throws Exception {
        // given
        DepartmentResponse departmentResponse = DepartmentResponse.builder()
            .id(UUID.randomUUID().toString())
            .name("수지구청")
            .number("1234")
            .phoneType(PhoneType.INWARD_DIALING)
            .branchId("test")
            .build();

        given(departmentCommandService.update(anyString(), any(DepartmentUpdateRequest.class))).willReturn(departmentResponse);

        DepartmentUpdateRequest departmentUpdateRequest = DepartmentUpdateRequest.builder()
            .name("수지구청")
            .number("1234")
            .phoneType(PhoneType.INWARD_DIALING)
            .build();

        // when
        ResultActions resultActions = mockMvc.perform(patch(DEPARTMENT_BASE_URL + "/{id}", UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(departmentUpdateRequest)));

        // then
        resultActions
            .andDo(getUpdateDepartmentApiDocument())
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").isNotEmpty())
            .andExpect(jsonPath("name").value("수지구청"))
            .andExpect(jsonPath("number").value("1234"))
            .andExpect(jsonPath("phoneType").value("INWARD_DIALING"))
            .andExpect(jsonPath("branchId").value("test"));
    }

    private RestDocumentationResultHandler getUpdateDepartmentApiDocument() {
        return document("department",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestFields(
                fieldWithPath("name").description("부서명"),
                fieldWithPath("number").description("부서 번호"),
                fieldWithPath("phoneType").description("부서 번호 타입(내선, 외선, 그룹)")
            ),
            responseFields(
                fieldWithPath("id").description("부서의 식별 id"),
                fieldWithPath("name").description("부서명"),
                fieldWithPath("number").description("부서 번호"),
                fieldWithPath("phoneType").description("부서 번호 타입(내선, 외선, 그룹)"),
                fieldWithPath("branchId").description("브랜치 ID"),
                fieldWithPath("synonyms").description("동의어 리스트")
            ));
    }

    @Test
    @DisplayName("Department생성을 실패한다")
    public void create_department_fail() throws Exception {
        // given
        DepartmentCreateRequest departmentCreateRequest = DepartmentCreateRequest.builder()
            .number("1234")
            .phoneType(PhoneType.INWARD_DIALING)
            .build();

        // when
        ResultActions resultActions = mockMvc.perform(post("/admin/v1/departments")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(departmentCreateRequest)));

        // then
        resultActions
            .andExpect(status().is4xxClientError());
    }
}