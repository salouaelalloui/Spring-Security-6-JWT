package com.postgresql.PostGresSecurity.Services;

import com.postgresql.PostGresSecurity.DTO.AffectedPermissionDTO;
import com.postgresql.PostGresSecurity.Entities.Role;
import com.postgresql.PostGresSecurity.Enum.Permission;
import com.postgresql.PostGresSecurity.Repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    public Role saveRole(Role role){
        return roleRepository.save(role);
    }
    public Role saveRoleWithPermissions(Role role, Set<Permission> permissions ){
        role.getPermissions().addAll(permissions);
        return roleRepository.save(role);

    }

    public void affectPermissionToRole(AffectedPermissionDTO affectedPermission){
        Role role = roleRepository.findByRole(affectedPermission.getRole()).orElseThrow(()->new RuntimeException("there is no role for this id : "+affectedPermission.getRole()));
       Set<Permission> permissions = new HashSet<>();
        for (String permissionName : affectedPermission.getPermissions()) {

            try {
                Permission permission = Permission.valueOf(permissionName);
                permissions.add(permission);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Permission '" + permissionName + "' is not valid.");
            }
        }


        role.getPermissions().addAll(permissions);
        roleRepository.save(role);
    }


}
