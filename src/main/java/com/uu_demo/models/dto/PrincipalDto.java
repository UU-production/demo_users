package com.uu_demo.models.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrincipalDto {

    private Long id;
    private String email;
    private String fullName;
    private String avatar;
    private String role;
}
