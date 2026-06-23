package com.ecommercetech;

import com.ecommercetech.product.dto.ProductRequestDTO;
import com.ecommercetech.product.dto.ProductResponseDTO;
import com.ecommercetech.product.mapper.Mapper;
import com.ecommercetech.product.model.Product;
import com.ecommercetech.product.repository.ProductRepository;
import com.ecommercetech.product.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

/*
* Utilizamos la extension de Mockito en esta clase, habilitando todas las funcionalidades
* de Mockito dentro de las pruebas unitarias, es decir, reconoce las anotaciones:
* @Mock, @InjectMocks, etc
*/
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository; //simulado repositorio, crea el objeto ficticio
    private Mapper mapper;

    @InjectMocks
    private ProductServiceImpl productService; // Inyectamos el mock, el servicio que vamos  a probar

    @Test
    void testCreateProduct(){
        //Arrange(preparamos datos)
        ProductRequestDTO request = new ProductRequestDTO();
        request.setName("Laptop");
        request.setCategory("Electronic");
        request.setDescription("Laptop gamer");
        request.setPrice(1500.0);
        request.setStock(10);


        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("Laptop");
        savedProduct.setCategory("Electronic");
        savedProduct.setDescription("Laptop gamer");
        savedProduct.setPrice(1500.0);
        savedProduct.setStock(10);

        // Simulamos que el repositorio guarda y devuelve el producto
        Mockito.when(productRepository.save(Mockito.any(Product.class)))
                .thenReturn(savedProduct);

        // Act (ejecutar el método)
        ProductResponseDTO result = productService.createProduct(request);

        // Assert (verificar resultado)
        assertEquals(1L, result.getId());
        assertEquals("Laptop", result.getName());
        assertEquals("Electronic", result.getCategory());
        assertEquals(1500.0, result.getPrice());
        assertEquals(10, result.getStock());
    }

    @Test
    void testGetAllProducts(){
        // Arrange(Preparamosd datos)
        //Creamos una lista de productos que simula estar en la base de datos
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Laptop");
        product1.setPrice(1500.0);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Mouse");
        product2.setPrice(50.0);

        List<Product> mockProductsList = List.of(product1, product2);

        // Le decimos a Mockito: "Cuando el servicio llame a findAll(), devuelve esta lista"
        Mockito.when(productRepository.findAll()).thenReturn(mockProductsList);

        // Act
        List<ProductResponseDTO> result = productService.getAllProducts();

        // Assert(comprobar resultados)
        assertEquals(2, result.size()); // Devuelve el numero de elementos que hay en la lista, semejante a .length
        assertEquals("Laptop", result.get(0).getName()); // El primero es Laptop
        assertEquals("Mouse", result.get(1).getName()); // El segundo es Mouse
    }

    @Test
    void testGetUserById(){

        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("PC GAMER");
        product.setPrice(1500.00);

        // findById devuelve Optional.of()
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        ProductResponseDTO response = productService.getProductById(1L);
        // tambien ProductResponseDTO response = productService.getProductById(product.getId());

        // Assert
        assertEquals(1, response.getId());
        assertEquals("PC GAMER", response.getName());
        assertEquals(1500.00, response.getPrice());
    }

    @Test
    void testUpdateProduct(){

        // Arrange(preparamos datos existentes en la BD simulada)
        Product product = Product.builder()
                .id(1L)
                .name("Laptop")
                .category("Electronic")
                .description("Laptop gamer")
                .price(1500.0)
                .stock(10)
                .build();

        // Datos nuevos que llegan en el request
        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
                .name("Laptop Pro")
                .category("Electronic")
                .description("Laptop gamer avanzada")
                .price(2000.0)
                .stock(5)
                .build();

        // Simulamos que el repositorio "busca", encuentra y devuelve el producto por id
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Simulamos que al "guardar" devuelve el producto actualizado
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenAnswer(invocation -> {
            Product update = invocation.getArgument(0);
            update.setId(1L);
            return update;
        });

        //Act
        ProductResponseDTO response = productService.updateProduct(1L, productRequestDTO);

        // Assert (solo verificamos el precio)
        assertEquals(1L, response.getId());
        assertEquals(2000.0, response.getPrice()); // Precio actualizado
        assertEquals("Laptop Pro", response.getName()); // Nombre actualizado
    }



}

/*

 JUnit :
*   - Framework de pruebas unitarias
*   - Permite escribir metodos anotados con @Test que verfica el comportamiento del codigo
*   - Usa "asserts(assertEquals, assertTrue, etc) para comprobar que el resultado esperado
*     coincida con el resultado final
*   - Necesita usar Mockito si la clase depende de otras clases, de lo contrario, ncesitaria una
*     base de datos real

*/

/*

  Mockito:
*   - Framework de mocking
    - Sirve para dependencias externas(como repositorios, servicios, APIs, etc) en las pruebas
    - Asi no necesitamos una base de datos real ni levantar todo el servidor
    - Con @Mock creamos un objeto simulado y con "when(...).thenReturn(...) definimos que debemos devolver
*
*/

/* Seguimos el patron de analisis:
*   1. Arrange -> preparar datos y definir comportamiento de los mocks
*   2. Act -> ejecutar el metodo del Service que queremos probar
*   3. Assert -> verificamos el metodo del Service que queremos probar*/


/*
    Combinacion profesional:
        Controller  -> MockMvc
        Service     -> JUnit + Mockito
        Repository  -> @DataJpaTest
        API completa -> @SpringBootTest
*/

// Probarlo con : mvn -f ecommerce-system/pom.xml test -Dtest=ProductServiceTest