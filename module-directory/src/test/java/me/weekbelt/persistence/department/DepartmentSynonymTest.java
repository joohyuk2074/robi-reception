package me.weekbelt.persistence.department;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DepartmentSynonymTest {

    @Test
    @DisplayName("DepartmentSynonym에서 synonym, branchId의 필드가 같을경우 같은 값으로 취급")
    public void same_value() {
        // given & when
        DepartmentSynonym departmentSynonym1 = DepartmentSynonym.builder()
            .id(UUID.randomUUID().toString())
            .synonym("최상위부서")
            .branchId("test")
            .build();
        DepartmentSynonym departmentSynonym2 = DepartmentSynonym.builder()
            .id(UUID.randomUUID().toString())
            .synonym("최상위부서")
            .branchId("test")
            .build();

        // then
        assertThat(departmentSynonym1).isEqualTo(departmentSynonym2);
    }

    @Test
    @DisplayName("DepartmentSynonym에서 synonym, branchId의 필드가 다를경우 다른 값으로 취급")
    public void different_value() {
        // given & when
        DepartmentSynonym departmentSynonym1 = DepartmentSynonym.builder()
            .id(UUID.randomUUID().toString())
            .synonym("최상위부서")
            .branchId("test")
            .build();
        DepartmentSynonym departmentSynonym2 = DepartmentSynonym.builder()
            .id(UUID.randomUUID().toString())
            .synonym("최상위부서")
            .branchId("test1")
            .build();

        // then
        assertThat(departmentSynonym1).isNotEqualTo(departmentSynonym2);
    }
}