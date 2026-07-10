package com.ecommercetech.profileUser.mapper;

import com.ecommercetech.profileUser.dto.ProfileUserRequestDTO;
import com.ecommercetech.profileUser.dto.ProfileUserResponseDTO;
import com.ecommercetech.profileUser.model.ProfileUser;
import com.ecommercetech.user.model.User;

public class ProfileUserMapper {

    // Convertir el DTO en Model
    public static ProfileUser toModel(ProfileUserRequestDTO profileUserRequestDTO, User user){

        if(profileUserRequestDTO == null) return null;

        return ProfileUser.builder()
                .id(profileUserRequestDTO.getId()) // opcional, si lo usas en update
                .user(user)
                .name(profileUserRequestDTO.getName())
                .address(profileUserRequestDTO.getAddress())
                .phone(profileUserRequestDTO.getPhone())
                .profilePhoto(profileUserRequestDTO.getProfilePhoto())
                .build();
    }


    // Convertir el Model en DTO para enviar como respuesta
    public static ProfileUserResponseDTO toResponseDTO(ProfileUser profileUser){
        if (profileUser == null) return null;

        return ProfileUserResponseDTO.builder()
                .id(profileUser.getId())
                .userId(profileUser.getUser().getId())
                .name(profileUser.getName())
                .address(profileUser.getAddress())
                .phone(profileUser.getPhone())
                .profilePhoto(profileUser.getProfilePhoto())
                .build();
    }
}
