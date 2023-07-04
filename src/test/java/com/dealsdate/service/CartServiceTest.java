package com.dealsdate.service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dealsdate.entity.Product;
import com.dealsdate.entity.Cart;
import com.dealsdate.exception.ProductNotFoundException;
import com.dealsdate.repositories.CartRepository;
import com.dealsdate.repositories.ProductRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepo;

    @Mock
    private ProductRepository prodRepo;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    void testViewCartById() {
        // Mock data
        Cart cart = new Cart();
        when(cartRepo.findById(1)).thenReturn(Optional.of(cart));

        // Test
        Cart result = cartService.viewCartById(1);

        // Verify
        verify(cartRepo).findById(1);
        assertEquals(cart, result);
    }

   

    @Test
    void testAddProductToCart_ThrowsProductNotFoundException() {
        // Mock data
        when(prodRepo.findById(1)).thenReturn(Optional.empty());

        // Test and verify
        assertThrows(ProductNotFoundException.class, () -> cartService.addProductToCart(1, 1));
        verify(prodRepo).findById(1);
        verify(cartRepo, never()).findById(anyInt());
        verify(cartRepo, never()).save(any(Cart.class));
    }


    @Test
    void testDeleteProductFromCart_ThrowsProductNotFoundException() {
        // Mock data
        when(prodRepo.findById(1)).thenReturn(Optional.empty());

        // Test and verify
        assertThrows(ProductNotFoundException.class, () -> cartService.deleteProductFromCart(1, 1));
        verify(prodRepo).findById(1);
        verify(cartRepo, never()).findById(anyInt());
        verify(cartRepo, never()).save(any(Cart.class));
    }

    @Test
    void testGetAllCartProducts() {
        // Mock data
        Product product1 = new Product();
        Product product2 = new Product();
        Cart cart = new Cart();
        cart.setProducts(Arrays.asList(product1, product2));
        when(cartRepo.findById(1)).thenReturn(Optional.of(cart));

        // Test
        List<Product> result = cartService.getAllCartProducts(1);

        // Verify
        verify(cartRepo).findById(1);
        assertEquals(Arrays.asList(product1, product2), result);
    }
}
