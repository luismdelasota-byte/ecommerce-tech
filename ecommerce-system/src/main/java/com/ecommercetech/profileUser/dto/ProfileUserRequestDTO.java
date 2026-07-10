package com.ecommercetech.profileUser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileUserRequestDTO {

    private Long id;
    private Long userId;
    private String name;
    private String address;
    private String phone;
    private String profilePhoto;
}
