package com.postgresql.PostGresSecurity.Controllers;


import com.postgresql.PostGresSecurity.DTO.AffectedPermissionDTO;
import com.postgresql.PostGresSecurity.Services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
public class RoleController {
    private final RoleService roleService;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/user/affectPermissions")
    public void affcetPermission(@RequestBody AffectedPermissionDTO affectedPermission){
        roleService.affectPermissionToRole(affectedPermission);

    }
}
