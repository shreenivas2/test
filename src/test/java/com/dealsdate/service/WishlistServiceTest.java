package com.dealsdate.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.dealsdate.entity.Product;
import com.dealsdate.entity.Wishlist;
import com.dealsdate.exception.ProductAlreadyExistsException;
import com.dealsdate.exception.ProductNotFoundException;
import com.dealsdate.repositories.ProductRepository;
import com.dealsdate.repositories.WishlistRepository;

@SpringBootTest
public class WishlistServiceTest {

    @Mock
    private WishlistRepository wishRepo;

    @Mock
    private ProductRepository prodRepo;

    @InjectMocks
    private WishlistServiceImpl wishlistService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testViewWishlist() {
        // Mock data
        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistId(1);
        Optional<Wishlist> optionalWishlist = Optional.of(wishlist);

        // Mocking repository behavior
        when(wishRepo.findById(1)).thenReturn(optionalWishlist);

        // Call the method under test
        Wishlist result = wishlistService.viewWishlist(1);

        // Assertion
        assertNotNull(result);
        assertEquals(1, result.getWishlistId());
    }

    @Test
    public void testAddProductToWishlist() throws ProductNotFoundException, ProductAlreadyExistsException {
        // Mock data
        Product product = new Product();
        product.setProductId(1);
        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistId(1);
        List<Product> products = new ArrayList<>();
        wishlist.setProducts(products);

        Optional<Product> optionalProduct = Optional.of(product);
        Optional<Wishlist> optionalWishlist = Optional.of(wishlist);

        // Mocking repository behavior
        when(prodRepo.findById(1)).thenReturn(optionalProduct);
        when(wishRepo.findById(1)).thenReturn(optionalWishlist);
        when(wishRepo.save(any(Wishlist.class))).thenReturn(wishlist);

        // Call the method under test
        List<Product> result = wishlistService.addProductToWishlist(1, 1);

        // Assertion
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getProductId());
    }

    @Test
    public void testDeleteProductFromWishlist() throws ProductNotFoundException {
        // Mock data
        Product product = new Product();
        product.setProductId(1);
        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistId(1);
        List<Product> products = new ArrayList<>();
        products.add(product);
        wishlist.setProducts(products);

        Optional<Product> optionalProduct = Optional.of(product);
        Optional<Wishlist> optionalWishlist = Optional.of(wishlist);

        // Mocking repository behavior
        when(prodRepo.findById(1)).thenReturn(optionalProduct);
        when(wishRepo.findById(1)).thenReturn(optionalWishlist);
        when(wishRepo.save(any(Wishlist.class))).thenReturn(wishlist);

        // Call the method under test
        List<Product> result = wishlistService.deleteProductFromWishlist(1, 1);

        // Assertion
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testGetAllWishProducts() {
        // Mock data
        Product product = new Product();
        product.setProductId(1);
        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistId(1);
        List<Product> products = new ArrayList<>();
        products.add(product);
        wishlist.setProducts(products);

        Optional<Wishlist> optionalWishlist = Optional.of(wishlist);

        // Mocking repository behavior
        when(wishRepo.findById(1)).thenReturn(optionalWishlist);

        // Call the method under test
        List<Product> result = wishlistService.getAllWishProducts(1);

        // Assertion
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getProductId());
    }
}

