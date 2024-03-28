package com.postgresql.PostGresSecurity.DTO;

import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AffectedPermissionDTO {
    private String role;
    private List<String> permissions;
}
