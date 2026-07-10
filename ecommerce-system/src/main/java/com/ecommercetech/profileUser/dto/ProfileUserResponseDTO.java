package com.ecommercetech.profileUser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileUserResponseDTO {
    private Long id;
    private Long userId;
    private String name;
    private String address;
    private String phone;
    private String profilePhoto;
}
