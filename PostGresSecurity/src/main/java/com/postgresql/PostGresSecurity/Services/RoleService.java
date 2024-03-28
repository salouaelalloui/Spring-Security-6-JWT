package com.postgresql.PostGresSecurity.Services;

import com.postgresql.PostGresSecurity.DTO.AffectedPermissionDTO;
import com.postgresql.PostGresSecurity.Entities.Role;
import com.postgresql.PostGresSecurity.Enum.Permission;

import java.util.Set;

public interface RoleService {

    public Role saveRole(Role role);
    public Role saveRoleWithPermissions(Role role, Set<Permission> permissions );
    public void affectPermissionToRole(AffectedPermissionDTO affectedPermission);

}
