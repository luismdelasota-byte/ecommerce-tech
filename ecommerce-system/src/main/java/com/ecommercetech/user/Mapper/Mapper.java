package com.ecommercetech.user.Mapper;

import com.ecommercetech.user.dto.UserRequestDTO;
import com.ecommercetech.user.dto.UserResponseDTO;
import com.ecommercetech.user.model.User;

public class Mapper {

    public static User toModel(UserRequestDTO dto){
        if(dto == null) return null;

        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();
    }


    public static UserResponseDTO toResponseDTO(User user){
        if(user == null) return null;

        return UserResponseDTO.builder()
                .id(user.getId()) // Reconmendale devolver siempre el id
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}

/* User.builder() -> invoca el builder de la clase User. Este builder es generado automaticamente por Lomnok
*  cuando usamos la anotacion @Builder en la entidad User, asi mismo tambien la usar
*  .username(dto.getUsername) -> Toma el valor dle campo username que viene en el UserRequestDTO
*  y lo asignamos al atributo username del objeto User que estamos contruyendo*/