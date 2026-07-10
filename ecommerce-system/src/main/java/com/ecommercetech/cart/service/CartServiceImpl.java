package com.ecommercetech.cart.service;

import com.ecommercetech.cart.dto.CartRequestDTO;
import com.ecommercetech.cart.dto.CartResponseDTO;
import com.ecommercetech.cart.mapper.CartMapper;
import com.ecommercetech.cart.model.Cart;
import com.ecommercetech.exception.NotFoundException;
import com.ecommercetech.product.model.Product;
import com.ecommercetech.product.repository.ProductRepository;
import com.ecommercetech.user.model.User;
import com.ecommercetech.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ecommercetech.cart.repository.CartRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public CartResponseDTO createCart(CartRequestDTO cartRequestDTO){

        User user = userRepository.findById(cartRequestDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id " + cartRequestDTO.getUserId()));

        Product product  = productRepository.findById(cartRequestDTO.getProductId())
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con id " + cartRequestDTO.getProductId()));
        Cart cart = CartMapper.toModel(cartRequestDTO, user, product);

        return CartMapper.toResponseDTO(cartRepository.save(cart));
    }

    @Override
    public List<CartResponseDTO> getAllCarts(){
        return cartRepository.findAll()
                .stream()
                .map(CartMapper::toResponseDTO)
                .toList();
    }

    @Override
    public CartResponseDTO getCartById(Long id){

        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Carrito no encontrado con id " + id));

        return CartMapper.toResponseDTO(cart);
    }

    @Override
    public CartResponseDTO updateCart(Long id, CartRequestDTO cartRequestDTO) {

        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Carrito no encontrado con id " + id));

        User user = userRepository.findById(cartRequestDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id " + cartRequestDTO.getUserId()));

        Product product = productRepository.findById(cartRequestDTO.getProductId())
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con id " + cartRequestDTO.getProductId()));

        cart.setId(cart.getId());
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(cartRequestDTO.getQuantity());
        cart.setUnitPrice(cartRequestDTO.getUnitPrice());
        cart.setSubtotal(cartRequestDTO.getQuantity() * cartRequestDTO.getUnitPrice());

        return CartMapper.toResponseDTO(cartRepository.save(cart));
    }

    @Override
    public void deleteCart(Long id) {

        if(!cartRepository.existsById(id)) {
            throw new NotFoundException("Carrito no encontrado con id " + id);
        }

        cartRepository.deleteById(id);
    }
}
