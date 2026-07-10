package com.ecommercetech.profileUser.service;

import com.ecommercetech.profileUser.dto.ProfileUserResponseDTO;
import com.ecommercetech.profileUser.dto.ProfileUserRequestDTO;

import java.util.List;

public interface ProfileUserService {
    ProfileUserResponseDTO createProfileUser(ProfileUserRequestDTO profileUserRequestDTO);
    List<ProfileUserResponseDTO> getAllProfileUser();
    ProfileUserResponseDTO getProfileUserById(Long id);
    ProfileUserResponseDTO updateProfileUser(ProfileUserRequestDTO profileUserRequestDTO, Long id);
    void deleteProfileUser(Long id);
}

