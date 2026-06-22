package com.ecommercetech.user.service;

import com.ecommercetech.user.dto.UserRequestDTO;
import com.ecommercetech.user.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long id);
    UserResponseDTO updateUser(Long id,UserRequestDTO userRequestDTO);
    void deleteUser(Long id);
}
