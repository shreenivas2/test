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

import com.dealsdate.entity.Cart;
import com.dealsdate.entity.Customer;
import com.dealsdate.entity.Order;
import com.dealsdate.entity.Product;
import com.dealsdate.exception.OrderNotFoundException;

import com.dealsdate.repositories.CartRepository;
import com.dealsdate.repositories.OrderRepository;


public class OrderServiceTest {

    @Mock
    private CartService cartService;

    @Mock
    private CartRepository cartRepo;

    @Mock
    private OrderRepository orderRepo;

    @InjectMocks
    private OrderService orderService = new OrderServiceImpl();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddOrder() {
        // Create mock objects
        Cart cart = new Cart();
        Customer customer = new Customer();
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        Product product2 = new Product();

        // Set up cart and product objects as needed

        cart.setCustomer(customer);
        cart.setProducts(products);
        // ...

        // Set up the mock behavior
        when(cartService.viewCartById(any(Integer.class))).thenReturn(cart);
        when(orderRepo.save(any(Order.class))).thenReturn(new Order());

        // Call the service method
        Order result = orderService.addOrder(1, 100);

        // Verify the result
        assertNotNull(result);
        // Add additional assertions as needed

        // Verify that the mock methods were called
        verify(cartService, times(1)).viewCartById(1);
        verify(orderRepo, times(1)).save(any(Order.class));
        // ...
    }

//    @Test
//    public void testCancelOrder_OrderExists() throws OrderNotFoundException {
//        // Create a mock order
//        Order order = new Order();
//
//        // Set up the mock behavior
//        when(orderRepo.existsById(any(Integer.class))).thenReturn(true);
//
//        // Call the service method
//        String result = orderService.cancelOrder(order);
//
//        // Verify the result
//        assertEquals("Order deleted", result);
//
//        // Verify that the mock method was called
//        verify(orderRepo, times(1)).delete(order);
//    }

    @Test
    public void testCancelOrder_OrderNotFound() {
        // Create a mock order
        Order order = new Order();

        // Set up the mock behavior
        when(orderRepo.existsById(any(Integer.class))).thenReturn(false);

        // Call the service method and assert the exception
        assertThrows(OrderNotFoundException.class, () -> orderService.cancelOrder(order));

        // Verify that the mock method was called
        verify(orderRepo, never()).delete(order);
    }

//    @Test
//    public void testCancelAProduct_OrderExists_ProductExists() throws OrderNotFoundException, ProductNotFoundException {
//        // Create a mock order and product
//        Order order = new Order();
//        Product product1 = new Product();
//        Product product2 = new Product();
//        List<Product> products = new ArrayList<>();
//        products.add(product1);
//        products.add(product2);
//        order.setProducts(products);
//
//        // Set up the mock behavior
//        when(orderRepo.existsById(any(Integer.class))).thenReturn(true);
//        when(orderRepo.findById(any(Integer.class))).thenReturn(Optional.of(order));
//
//        // Call the service method
//        String result = orderService.cancelAProduct(1, 1);
//
//        // Verify the result
//        assertEquals("Product deleted", result);
//        assertEquals(1, order.getProducts().size());
//
//        // Verify that the mock methods were called
//        verify(orderRepo, times(1)).existsById(1);
//        verify(orderRepo, times(1)).findById(1);
//        verify(orderRepo, times(1)).save(order);
//    }

//    @Test
//    public void testCancelAProduct_OrderExists_ProductNotFound() throws OrderNotFoundException {
//        // Create a mock order
//        Order order = new Order();
//
//        // Set up the mock behavior
//        when(orderRepo.existsById(any(Integer.class))).thenReturn(true);
//        when(orderRepo.findById(any(Integer.class))).thenReturn(Optional.of(order));
//
//        // Call the service method and assert the exception
//        assertThrows(ProductNotFoundException.class, () -> orderService.cancelAProduct(1, 1));
//
//        // Verify that the mock methods were called
//        verify(orderRepo, times(1)).existsById(1);
//        verify(orderRepo, times(1)).findById(1);
//        verify(orderRepo, never()).save(order);
//    }

    @Test
    public void testCancelAProduct_OrderNotFound() {
        // Set up the mock behavior
        when(orderRepo.existsById(any(Integer.class))).thenReturn(false);

        // Call the service method and assert the exception
        assertThrows(OrderNotFoundException.class, () -> orderService.cancelAProduct(1, 1));

        // Verify that the mock methods were called
        verify(orderRepo, times(1)).existsById(1);
        verify(orderRepo, never()).findById(1);
        verify(orderRepo, never()).save(any(Order.class));
    }

//    @Test
//    public void testUpdateOrder_OrderExists() throws OrderNotFoundException {
//        // Create a mock order
//        Order order = new Order();
//
//        // Set up the mock behavior
//        when(orderRepo.existsById(any(Integer.class))).thenReturn(true);
//        when(orderRepo.save(any(Order.class))).thenReturn(order);
//
//        // Call the service method
//        Order result = orderService.updateOrder(order);
//
//        // Verify the result
//        assertNotNull(result);
//        // Add additional assertions as needed
//
//        // Verify that the mock methods were called
//        verify(orderRepo, times(1)).existsById(order.getOrderId());
//        verify(orderRepo, times(1)).save(order);
//    }

    @Test
    public void testUpdateOrder_OrderNotFound() {
        // Create a mock order
        Order order = new Order();

        // Set up the mock behavior
        when(orderRepo.existsById(any(Integer.class))).thenReturn(false);

        // Call the service method and assert the exception
        assertThrows(OrderNotFoundException.class, () -> orderService.updateOrder(order));

        // Verify that the mock methods were called
        verify(orderRepo, times(1)).existsById(order.getOrderId());
        verify(orderRepo, never()).save(order);
    }

    @Test
    public void testGetOrderById_OrderExists() throws OrderNotFoundException {
        // Create a mock order
        Order order = new Order();

        // Set up the mock behavior
        when(orderRepo.existsById(any(Integer.class))).thenReturn(true);
        when(orderRepo.findById(any(Integer.class))).thenReturn(Optional.of(order));

        // Call the service method
        Order result = orderService.getOrderById(1);

        // Verify the result
        assertNotNull(result);
        // Add additional assertions as needed

        // Verify that the mock methods were called
        verify(orderRepo, times(1)).existsById(1);
        verify(orderRepo, times(1)).findById(1);
    }

    @Test
    public void testGetOrderById_OrderNotFound() {
        // Set up the mock behavior
        when(orderRepo.existsById(any(Integer.class))).thenReturn(false);

        // Call the service method and assert the exception
        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(1));

        // Verify that the mock methods were called
        verify(orderRepo, times(1)).existsById(1);
        verify(orderRepo, never()).findById(1);
    }

    @Test
    public void testCancelOrderById_OrderExists() throws OrderNotFoundException {
        // Create a mock order
        Order order = new Order();

        // Set up the mock behavior
        when(orderRepo.existsById(any(Integer.class))).thenReturn(true);
        when(orderRepo.findById(any(Integer.class))).thenReturn(Optional.of(order));

        // Call the service method
        String result = orderService.cancelOrderById(1);

        // Verify the result
        assertEquals("Order deleted by ID", result);

        // Verify that the mock methods were called
        verify(orderRepo, times(1)).deleteById(1);
    }

    @Test
    public void testCancelOrderById_OrderNotFound() {
        // Set up the mock behavior
        when(orderRepo.existsById(any(Integer.class))).thenReturn(false);

        // Call the service method and assert the exception
        assertThrows(OrderNotFoundException.class, () -> orderService.cancelOrderById(1));

        // Verify that the mock methods were called
        verify(orderRepo, times(1)).existsById(1);
        verify(orderRepo, never()).deleteById(1);
    }


}
