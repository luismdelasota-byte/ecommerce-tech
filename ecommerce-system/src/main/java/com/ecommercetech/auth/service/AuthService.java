package com.ecommercetech.auth.service;

import com.ecommercetech.auth.dto.AuthResponseDTO;
import com.ecommercetech.auth.dto.LoginRequestDTO;
import com.ecommercetech.auth.dto.RegisterRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ecommercetech.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.ecommercetech.user.model.User;
import com.ecommercetech.security.jwt.JwtUtil;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    // Long
    public AuthResponseDTO login(LoginRequestDTO requestDTO){

        // Consulta base de datos con el username que envio el cliente
        User user = userRepository.findByUsername(requestDTO.getUsername())
                // Si no existe lanza una excepcion(evitamos que alguien intente loguearse con un username que no existe)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con username " + requestDTO.getUsername()));

        /*
        * Validamos la contraseña que envio el cliente con la que tenemos en la base de datos
        * Comparamos usamos -> matches() de passwordEncoder para comparar la contraseña encriptada con la que envio el cliente
        * requestDTO.getPassword() -> contraseña que envio el cliente
        * user.getPassword() -> contraseña que tenemos en la base de datos
        * Si no coinciden lanzamos una excepcion
        * lanza una excepcion si la contraseña no coincide, evitando que alguien intente loguearse con una contraseña incorrecta
        */
        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }


        /*
        * Se crea un JWT que contiene informacion del usuario y se devuelve al cliente para que lo use en futuras solicitudes
        * El JWT contiene informacion del usuario como username, email, id, etc. y se firma con una clave secreta para evitar que alguien lo modifique
        * Este token se envia al cliente y este lo guarda en el localStorage o en una cookie para usarlo en futuras solicitudes
        * Sin token el cliente no podra acceder a los endpoints protegidos de la aplicacion

        El usuario sería la persona.
        El token sería el carnet de identificación.
        Dentro del carnet hay datos importantes, por ejemplo:
        ID del usuario.
        Nombre de usuario.
        Rol (ADMIN, USER, etc.).
        Fecha de expiración.

        Luego, cuando el usuario quiere acceder a una ruta protegida, envía ese token:

        Authorization: Bearer eyJhbGciOiJIUzI1Ni...

        * Recordar : no guarda toda la información del usuario,
        * como la contraseña o todos los datos de la base de datos.
        * Generalmente solo contiene algunos datos básicos llamados claims.

        */
        String token = jwtUtil.generateToken(user);


        // Contruimos respuesta
        /*
        * Se devuelve un objeto AuthRespondeDTO con:
        * token -> el JWT que el cliente usara en cada request
        * userId -> el ID del usuario en BD
        * username -> nombre del usuario
        * email -> correo del usuario
        */
        return AuthResponseDTO.builder()
                .token(token)
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    // Registro
    public AuthResponseDTO register(RegisterRequestDTO registerRequestDTO){
        // Verificamos que exista un usuario con ese username en la base de datos, evita duplicados
        if(userRepository.existsByUsername(registerRequestDTO.getUsername())){
            throw new RuntimeException("El usuario ya existe");
        }

        // Construimos un nuevo objeto User
        User user = User.builder()
                .username(registerRequestDTO.getUsername())
                .email(registerRequestDTO.getEmail())
                // Encripta la contraseña antes de gaurdarla
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .role("USER") // Por defecto USER
                .build();

        // Guardamos el nuevo usuario en la base de datos
        userRepository.save(user);

        /*
        Genera un token JWT para el nuevo usuario, asi el usuario creado ya puede autenticarse
        Sin necesidad de hacer login inmediatamanete
        Esto genera token en el registro y manda directamente al dashboard, sin esto en el registro
        se tendria primero que logears para generar el token.
        */
        String token = jwtUtil.generateToken(user);

        // Construimos respuesta
        return AuthResponseDTO.builder()
                .token(token) // Para el Headers
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

}
