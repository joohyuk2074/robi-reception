package me.weekbelt.persistence.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import me.weekbelt.persistence.PhoneType;
import me.weekbelt.persistence.department.Department;
import me.weekbelt.apiserver.department.dto.DepartmentCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DepartmentMapperTest {

    @Test
    @DisplayName("DepartmentCreateRequest -> Department")
    public void testcase() {
        // given
        DepartmentCreateRequest departmentCreateRequest = DepartmentCreateRequest.builder()
            .name("수지구청")
            .number("1234")
            .phoneType(PhoneType.INWARD_DIALING)
            .build();

        // when
        Department department = DepartmentMapper.toDepartment(departmentCreateRequest);

        // then
        assertThat(department.getId()).isNotEmpty();
        assertThat(department.getName()).isEqualTo("수지구청");
        assertThat(department.getPhone().getNumber()).isEqualTo("1234");
        assertThat(department.getPhone().getPhoneType()).isEqualTo(PhoneType.INWARD_DIALING);
    }

}