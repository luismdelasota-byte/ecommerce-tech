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
        assertEquals(2, result.size()); // Devuelve el numero de elementos que hay en la lista, semejanete a .length
        assertEquals("Laptop", result.get(0).getName()); // El primero es Laptop
        assertEquals("Mouse", result.get(1).getName()); // El segundo es Mouse
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