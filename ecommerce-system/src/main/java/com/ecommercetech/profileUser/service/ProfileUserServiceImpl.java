package com.ecommercetech.profileUser.service;

import com.ecommercetech.profileUser.model.ProfileUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ecommercetech.profileUser.repository.ProfileUserRepository;
import com.ecommercetech.profileUser.dto.ProfileUserRequestDTO;
import com.ecommercetech.profileUser.dto.ProfileUserResponseDTO;
import com.ecommercetech.profileUser.mapper.ProfileUserMapper;
import com.ecommercetech.user.model.User;
import com.ecommercetech.user.repository.UserRepository;
import com.ecommercetech.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileUserServiceImpl implements ProfileUserService {

    private final ProfileUserRepository profileUserRepository;
    private final UserRepository userRepository;

    @Override
    public ProfileUserResponseDTO createProfileUser(ProfileUserRequestDTO profileUserRequestDTO) {

        User user = userRepository.findById(profileUserRequestDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id " + profileUserRequestDTO.getUserId()));

        ProfileUser profileUser = ProfileUserMapper.toModel(profileUserRequestDTO, user);

        return ProfileUserMapper.toResponseDTO(profileUserRepository.save(profileUser));
    }


    @Override
    public List<ProfileUserResponseDTO> getAllProfileUser(){

        return profileUserRepository.findAll()
                .stream()
                .map(ProfileUserMapper::toResponseDTO)
                .toList();
    }

    @Override
    public ProfileUserResponseDTO getProfileUserById(Long id){

        ProfileUser profileUser = profileUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Perfil de usuario no encontrado con id " + id));

        return ProfileUserMapper.toResponseDTO(profileUser);
    }

    @Override
    public ProfileUserResponseDTO updateProfileUser(ProfileUserRequestDTO profileUserRequestDTO, Long id){

        ProfileUser profileUser = profileUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Perfil de usuario no encontrado con id " + id));

        User user = userRepository.findById(profileUserRequestDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id " + profileUserRequestDTO.getUserId()));

        profileUser.setUser(user);
        profileUser.setName(profileUserRequestDTO.getName());
        profileUser.setAddress(profileUserRequestDTO.getAddress());
        profileUser.setPhone(profileUserRequestDTO.getPhone());
        profileUser.setProfilePhoto(profileUserRequestDTO.getProfilePhoto());

        return ProfileUserMapper.toResponseDTO(profileUserRepository.save(profileUser));
    }

    @Override
    public void deleteProfileUser(Long id) {
        if (!profileUserRepository.existsById(id)) {
            throw new NotFoundException("Perfil de usuario no encontrado con id " + id);
        }

        profileUserRepository.deleteById(id);
    }
}
