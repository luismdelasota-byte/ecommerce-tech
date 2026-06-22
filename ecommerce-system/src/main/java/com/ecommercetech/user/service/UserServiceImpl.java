package com.ecommercetech.user.service;

import com.ecommercetech.exception.NotFoundException;
import com.ecommercetech.user.Mapper.Mapper;
import com.ecommercetech.user.dto.UserRequestDTO;
import com.ecommercetech.user.dto.UserResponseDTO;
import com.ecommercetech.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommercetech.user.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){

        User user = User.builder()
                .username(userRequestDTO.getUsername())
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .role(userRequestDTO.getRole())
                .build();

        return Mapper.toResponseDTO(userRepository.save(user));
    }

    @Override
    public List<UserResponseDTO> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(Mapper::toResponseDTO)
                .toList();
    }

    @Override
    public UserResponseDTO getUserById(Long id){
         User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado " + id));

         return Mapper.toResponseDTO(user);
    }


    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO){

        //Buscamos usuario
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado " + id));

        //Actualizamo usuario
        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        user.setRole(userRequestDTO.getRole());

        return Mapper.toResponseDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new NotFoundException("Usuario no encontrado con " + id);
        }

        userRepository.deleteById(id);
    }


}
