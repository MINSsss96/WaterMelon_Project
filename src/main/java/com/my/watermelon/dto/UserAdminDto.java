package com.my.watermelon.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAdminDto {
    private Long id;
    private String username;
    private String name;
    private String email;
    private boolean enabled;
}
