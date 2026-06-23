package com.ecommercetech;

import com.ecommercetech.product.model.Product;
import com.ecommercetech.user.dto.UserRequestDTO;
import com.ecommercetech.user.dto.UserResponseDTO;
import com.ecommercetech.user.repository.UserRepository;
import com.ecommercetech.user.service.UserService;
import com.ecommercetech.user.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.ecommercetech.user.model.User;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks // Siempre usarlo en la implentacion mas no en la interfaz
    private UserServiceImpl userServiceImp;

    @Test
    void createUser(){

        // Arrange(datos)
        UserRequestDTO request = new UserRequestDTO();
        request.setUsername("luisdelasota");
        request.setEmail("luis.mdelasota@gmail.com");
        request.setPassword("LS76DA91");
        request.setRole("admin");

        User user = User.builder()
                .id(1L) //Asignamos id
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .build();

        // Simulamos el comportamiento del repositorio("Mockito.when(...).thenReturn(...)")
        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(user);

        // Act(Ejecutamos el metodo del servicio)
        UserResponseDTO result = userServiceImp.createUser(request);

        // Assert (verificar resultado)
        assertEquals(1L, result.getId());
        assertEquals("luisdelasota", result.getUsername());
        assertEquals("luis.mdelasota@gmail.com", result.getEmail());
        assertEquals("admin", result.getRole());
    }

    @Test
    void testGetAllUser() {

        //Arrange(creamos  los objetos)
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("accesoluis");
        user1.setEmail("accesoluis@gmail.com");
        user1.setRole("ADMIN");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("accesoraul");
        user2.setEmail("accesoraul@gmail.com");
        user2.setRole("ADMIN");

        List<User> mockUserList = new ArrayList<>();
        mockUserList.add(user1);
        mockUserList.add(user2);

        //Simulamos guardar en el repositorio
        Mockito.when(userRepository.findAll()).thenReturn(mockUserList);

        //Act(ejectumos el metodo para mostrar)
        List<UserResponseDTO> response = userServiceImp.getAllUsers();

        //Assert(comparamos para que no nos bote null)
        assertEquals(2, response.size());
        assertEquals("accesoluis", response.get(0).getUsername());
        assertEquals("accesoraul", response.get(1).getUsername());

    }

}
