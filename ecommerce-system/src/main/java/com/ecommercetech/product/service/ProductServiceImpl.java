package com.ecommercetech.product.service;

import com.ecommercetech.product.dto.ProductRequestDTO;
import com.ecommercetech.product.dto.ProductResponseDTO;
import com.ecommercetech.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ecommercetech.product.mapper.Mapper;
import com.ecommercetech.product.model.Product;

import com.ecommercetech.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    /* En lugar de poner @Autowired en cada atributo o constructor, Lombok
    * genera automaticamente el constructor con todos los campos final usando @RequiredArgsConstructor.
    *  Mas limpio, mas seguro ya que maneja campos "final" y no pueden cambiarse despues*/

    private final ProductRepository productRepository;

    //Creamos el producto a partir del DTO recibido, lo g DTOuardamos en la base de datos y devolvemos el producto guardado convertido a
    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO){

        // Otra menera de transformar directamente el DTO recibido a model
        //Product productModel = Mapper.toModel(productRequestDTO);

        //Creamos el objeto de tipo Product "Product.builder()..."
        Product product = Product.builder()
                .name(productRequestDTO.getName())
                .description(productRequestDTO.getDescription())
                .category(productRequestDTO.getCategory())
                .price(productRequestDTO.getPrice())
                .stock(productRequestDTO.getStock())
                .build();

        return Mapper.toResponseDTO(productRepository.save(product));
    }

    // Leemos el producto a partir del id, lo convertimos a DTO y lo devolvemos
    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(Mapper::toResponseDTO)
                .toList();
    }

    // Leemos el producto por id, lo convertimos a DTO y lo devolvemos
    @Override
    public ProductResponseDTO getProductById(Long id){

        //findById devuelve Optional<Product>, puede contener un producto si existe o estar vacio si no existe.
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con id: " + id ));

        return Mapper.toResponseDTO(product);

        /*Otra manera:
        * return productRepository.finById(id).
        *         .map(Mapper::toResponseDTO)
        *         .orElse(null);*/
    }

    // Actualizacion de producto
    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Producto no encontrado con id: " + id));

        // 1. Traemos los valores que vienen en el productRequestDTO y asignamos a la entidad product
        product.setName(productRequestDTO.getName());
        product.setCategory(productRequestDTO.getCategory());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setStock(productRequestDTO.getStock());

        // 2. Guardamos los cambios en la BD "productRepository.save(product)"
        // 3. Transformamos la entidad actualizada en un DTO para enviarlo al frontend "Mapper.toResponseDTO(productRepository.save(product));"
        return Mapper.toResponseDTO(productRepository.save(product));
    }

    // Eliminar producto
    @Override
    public void deleteProduct(Long id){
        if(!productRepository.existsById(id)){
            throw new NotFoundException("Producto no encontrado con id: " + id);
        }

        productRepository.deleteById(id);
    }


    /*Recordar:
    * existById -> Solo sabe si existe o no, lanza true o false, uno mismo decide que hacer, lanzar exception o retornar algo
    * findById(...).orElseThrow(...) -> Obtenemos la entidad directamente, si no existe, lanza exception sin necesidad de un if*/

}
