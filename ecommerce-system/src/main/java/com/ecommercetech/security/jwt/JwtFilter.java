package com.ecommercetech.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.ecommercetech.user.model.User;
import com.ecommercetech.user.repository.UserRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

import java.io.IOException;

@Component // Spring lo detecta automaticamente como un bean y lo registra
@RequiredArgsConstructor // Lombok : Genera el constructor con los atributos final(JwtUtil, UserRepository) asi Spring puede inyectarlas
// Extiende OncePerRequestFilter -> Este filtro se ejecuta una sola vez por cada request
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    // Metodo
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Extraer el header Authorization
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // 2. Verificar que empiece con "Bearer "
        // Si el header existe y empieza con "Bearer" se extrae el token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // quitar "Bearer "
            // Con jwtUtil.getUsernameFromToken(token) se obtiene el username que esta dentor de JWT
            username = jwtUtil.getUsernameFromToken(token);
        }

        // 3. Validar token y cargar usuario en el contexto de seguridad
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtil.validateToken(token)) {
                // Carga usuario manualmente, no es necesario usar la implementacion de otra clase "UserDetailsService"
                User user = userRepository.findByUsername(username).orElse(null);

                if (user != null) {
                    List<SimpleGrantedAuthority> authorities = user.getRole() != null ?
                            List.of(new SimpleGrantedAuthority(user.getRole())) : List.of();
                    // Si el username no es nulo, se crea el objeto de autenticacion
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    user, null, authorities
                            );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Guardar autenticación en el contexto de Spring
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        // 4. Continuar con el filtro
        filterChain.doFilter(request, response);
    }
}



/*

JwtUtil : Su unico trabajo es crear y validar tokens
-> En el login o registro, genera el JWT con datos basicos del usuario(username, role, expiracion)
-> Tambien sabe leer el token(getUsernameFromToken) y comprobar si esta expirado(validateToken)

JwtFilter : Es el portero que intercepta cada request(INTERCEPTA REQUEST Y VERIFICA TOKEN)
-> Extrae el token del header "Authorization : Bearer <token>
-> Llama a JwtUtil para vlaidar que el carnet es autentico y vigente
-> Si es valid, carga el usuario en el contexto de Sring Security(Es decir : "Este carnet es real, dejalo pasar")

SecurityConfig : Es la muralla que define que rutas son publicas y cuales requieren carnet
-> Ejm : /api/auth/** es publico (login, registro)
-> /api/products/** requiere token
> Ademas puede usar el rol del token para decidir permisos:
USER -> puede ver productos
ADMIN -> puede crear/eliminar productos
-> Es quin aplica las reglas dea cceso segun el rol que viene en el token.

Flujo completo :

 Login -> AuthService (Valida credenciales) -> JwtUtil genera token
 Frontend guarda token y lo envia en cada request
 JwtFilter intercepta la peticion -> valida token con JwtUtil
 Security aplica reglas segun rol del token
 El controlador CRUD recibe la peticion ya autenticada y con permisos correctos

*/
