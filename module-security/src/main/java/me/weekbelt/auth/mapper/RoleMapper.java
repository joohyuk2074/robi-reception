package me.weekbelt.auth.mapper;

import me.weekbelt.apiserver.auth.dto.RoleResponse;
import me.weekbelt.auth.entity.Role;

public class RoleMapper {

    public static RoleResponse toRoleResponse(Role role) {
        return RoleResponse.builder()
            .id(role.getId())
            .role(role.getRoleName())
            .build();
    }
}
