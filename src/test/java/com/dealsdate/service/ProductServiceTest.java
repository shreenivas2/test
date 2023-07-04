package com.dealsdate.service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dealsdate.entity.Order;
import com.dealsdate.entity.Product;
import com.dealsdate.exception.ProductAlreadyExistsException;
import com.dealsdate.exception.ProductNotFoundException;
import com.dealsdate.repositories.ProductRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

class ProductServiceTest {

    @Mock
    private ProductRepository prodRepo;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProduct_ProductNotExists_Success() throws ProductAlreadyExistsException {
        // Mock repository method
        when(prodRepo.findByProductName("Test Product")).thenReturn(null);
        when(prodRepo.save(any())).thenReturn(new Product());

        // Create a test product
        Product testProduct = createTestProduct();

        // Call the service method
        Product result = productService.addProduct(testProduct);

        // Verify the result
        assertNotNull(result);
        verify(prodRepo, times(1)).findByProductName("Test Product");
        verify(prodRepo, times(1)).save(any());
    }

    @Test
    void testAddProduct_ProductExists_ExceptionThrown() {
        // Mock repository method
        when(prodRepo.findByProductName("Existing Product")).thenReturn(new Product());

        // Create a test product
        Product testProduct = createTestProduct();
        testProduct.setProductName("Existing Product");

        // Call the service method and verify the exception
        Assertions.assertThrows(ProductAlreadyExistsException.class, () -> productService.addProduct(testProduct));

        verify(prodRepo, times(1)).findByProductName("Existing Product");
        verify(prodRepo, never()).save(any());
    }

    @Test
    void testUpdateProduct_ProductExists_Success() throws ProductNotFoundException {
        // Mock repository method
        when(prodRepo.existsById(1)).thenReturn(true);
        when(prodRepo.save(any())).thenReturn(new Product());

        // Create a test product
        Product testProduct = createTestProduct();
        testProduct.setProductId(1);

        // Call the service method
        Product result = productService.updateProduct(testProduct);

        // Verify the result
        assertNotNull(result);
        verify(prodRepo, times(1)).existsById(1);
        verify(prodRepo, times(1)).save(any());
    }

    @Test
    void testUpdateProduct_ProductNotExists_ExceptionThrown() {
        // Mock repository method
        when(prodRepo.existsById(1)).thenReturn(false);

        // Create a test product
        Product testProduct = createTestProduct();
        testProduct.setProductId(1);

        // Call the service method and verify the exception
        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(testProduct));

        verify(prodRepo, times(1)).existsById(1);
        verify(prodRepo, never()).save(any());
    }

    private Product createTestProduct() {
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Test Product");
        // Set other fields accordingly
        return product;
    }
    
    @Test
    public void testDeleteProductById_ProductExists() throws ProductNotFoundException {
        // Mock repository method
        when(prodRepo.existsById(1)).thenReturn(true);
        when(prodRepo.findById(1)).thenReturn(Optional.of(createProductWithOrders()));

        // Call the service method
        String result = productService.deleteProductById(1);

        // Verify the result
        assertEquals("Product deleted successfully.", result);

        // Verify that the repository methods were called
        verify(prodRepo, times(1)).existsById(1);
        verify(prodRepo, times(1)).findById(1);
        verify(prodRepo, times(1)).delete(any(Product.class));
    }

    private Product createProductWithOrders() {
        Product product = new Product();
        product.setProductId(1);
        // Set other fields as needed

        // Create a list of orders
        List<Order> orders = new ArrayList<>();
        Order order1 = new Order();
        Order order2 = new Order();
        // Set other fields for orders as needed

        // Add the orders to the product
        product.setOrders(orders);

        return product;
    }

    @Test
    void testDeleteProductById_ProductNotFound() {
        // Mock repository method
        when(prodRepo.existsById(1)).thenReturn(false);

        // Call the service method and verify the exception
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProductById(1));

        // Verify that the repository method was called
        verify(prodRepo, times(1)).existsById(1);
        verify(prodRepo, never()).findById(anyInt());
        verify(prodRepo, never()).delete(any(Product.class));
    }

    @Test
    void testSearchByProductName_ProductExists() throws ProductNotFoundException {
        // Mock repository method
        when(prodRepo.findByProductName("TestProduct")).thenReturn(new Product());

        // Call the service method
        Product result = productService.searchByProductName("TestProduct");

        // Verify the result
        assertNotNull(result);

        // Verify that the repository method was called
        verify(prodRepo, times(1)).findByProductName("TestProduct");
    }

    @Test
    void testSearchByProductName_ProductNotFound() {
        // Mock repository method
        when(prodRepo.findByProductName("TestProduct")).thenReturn(null);

        // Call the service method and verify the exception
        assertThrows(ProductNotFoundException.class, () -> productService.searchByProductName("TestProduct"));

        // Verify that the repository method was called
        verify(prodRepo, times(1)).findByProductName("TestProduct");
    }

    @Test
    void testSearchByColour() {
        // Mock repository method
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        when(prodRepo.findAllByColour("Red")).thenReturn(productList);

        // Call the service method
        List<Product> result = productService.searchByColour("Red");

        // Verify the result
        assertNotNull(result);
        assertEquals(1, result.size());

        // Verify that the repository method was called
        verify(prodRepo, times(1)).findAllByColour("Red");
    }
    
    @Test
    public void testSearchByDimension() {
        // Prepare test data
        String dimension = "50x50x50";
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product(/* Set the required fields */));

        // Mock repository method
        when(prodRepo.findAllByDimension(dimension)).thenReturn(expectedProducts);

        // Call the service method
        List<Product> products = productService.searchByDimension(dimension);

        // Verify the result
        assertNotNull(products);
        assertEquals(expectedProducts.size(), products.size());
        assertEquals(expectedProducts.get(0), products.get(0));

        // Verify that the repository method was called
        verify(prodRepo, times(1)).findAllByDimension(dimension);
    }

    @Test
    public void testFilterByBrand() {
        // Prepare test data
        String brand = "YourBrand";
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product(/* Set the required fields */));

        // Mock repository method
        when(prodRepo.findAllByBrand(brand)).thenReturn(expectedProducts);

        // Call the service method
        List<Product> products = productService.filterByBrand(brand);

        // Verify the result
        assertNotNull(products);
        assertEquals(expectedProducts.size(), products.size());
        assertEquals(expectedProducts.get(0), products.get(0));

        // Verify that the repository method was called
        verify(prodRepo, times(1)).findAllByBrand(brand);
    }

    @Test
    public void testSortByPrice() {
        // Prepare test data
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product(/* Set the required fields */));

        // Mock repository method
        when(prodRepo.findAllSortedByPrice()).thenReturn(expectedProducts);

        // Call the service method
        List<Product> products = productService.sortByPrice();

        // Verify the result
        assertNotNull(products);
        assertEquals(expectedProducts.size(), products.size());
        assertEquals(expectedProducts.get(0), products.get(0));

        // Verify that the repository method was called
        verify(prodRepo, times(1)).findAllSortedByPrice();
    }

    @Test
    public void testGetAllProducts() {
        // Prepare test data
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product(/* Set the required fields */));

        // Mock repository method
        when(prodRepo.findAll()).thenReturn(expectedProducts);

        // Call the service method
        List<Product> products = productService.getAllProducts();

        // Verify the result
        assertNotNull(products);
        assertEquals(expectedProducts.size(), products.size());
        assertEquals(expectedProducts.get(0), products.get(0));

        // Verify that the repository method was called
        verify(prodRepo, times(1)).findAll();
    }

    @Test
    public void testGetProductById_ProductExists() throws ProductNotFoundException {
        // Prepare test data
        Integer id = 1;
        Product expectedProduct = new Product(/* Set the required fields */);

        // Mock repository method
        when(prodRepo.findById(id)).thenReturn(Optional.of(expectedProduct));

        // Call the service method
        Product product = productService.getProductById(id);

        // Verify the result
        assertNotNull(product);
        assertEquals(expectedProduct, product);

        // Verify that the repository method was called
        verify(prodRepo, times(1)).findById(id);
    }

    @Test
    public void testGetProductById_ProductNotFound() {
        // Prepare test data
        Integer id = 1;

        // Mock repository method
        when(prodRepo.findById(id)).thenReturn(Optional.empty());

        // Call the service method and verify the exception
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(id));

        // Verify that the repository method was called
        verify(prodRepo, times(1)).findById(id);
    }
}
