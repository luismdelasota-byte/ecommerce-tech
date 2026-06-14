package com.ecommercetech.product.controller;

import com.ecommercetech.product.dto.ProductRequestDTO;
import com.ecommercetech.product.dto.ProductResponseDTO;
import com.ecommercetech.product.service.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    // POST : Crear productos,  @RequestBody: Usamos para recibir el objeto completo enviado desde frontend, en el cuerpo de  request(JSON)
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequestDTO){
        return ResponseEntity.ok(productService.createProduct(productRequestDTO));
    }

    // GET : Treaer productos
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // GET con id : Traer productos con id, @PathVarible: Usamos cuando el dato viene en la URL no en el cuero(JSON)
    @GetMapping("/{id}") // Segmento dinamico de la URL, la URL completa seria GET/products/{id} = GET /products/5
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // PUT : actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO){
        return ResponseEntity.ok(productService.updateProduct(id, productRequestDTO));
    }

    //DELE : Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        // Buscamos el producto po id
        productService.deleteProduct(id);
        // ResponseEntity : respuesta  HTTP
        return ResponseEntity.noContent().build();

        /* ResponseEntity : respuesta  HTTP
         *  noContent() : construye una respuesta con codigo 204 No Content, el estandar correcto para un DELETE exitoso
         *  build() : Termina de armar la respuesta HTTP que Spring enviara al cliente con ->
         *       Codigo de estado 204(No Content)
         *       Cuerpo vacio (Void)
         *       Headers : los basicos de la respuesta (Metadatos, no forman parte del contenido, Date, Content-Length Connection, etc)
         *  ResponseEntity<Void> : El cuerpo de la respuesta esta vacio(Void) porque no hay nada que devolver*/

        /* noContent() : ya devuelve un ResponseEntity<Void> con estado 204, pero usar build() para cerrer la respueta
         * es mas consistente si luego queremos añadir headers o configuraciones adicionales*/


    }



}
