package com.ecommercetech.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

    public NotFoundException(String msg){
        super(msg);
    }
}
/* Usamos @ResponseStatus para mayor especificacion de "recurso no encontrado" ya que
* Si no lo usamos, por defecto nos estaria monstrando 500 "El servidor fallo al procesa la peticion"
* Esto causaria confusion porque no indica con exactitud que realmente esta fallando "*/