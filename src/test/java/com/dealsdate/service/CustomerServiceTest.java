package com.dealsdate.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.dealsdate.entity.Customer;
import com.dealsdate.entity.Order;
import com.dealsdate.entity.Cart;
import com.dealsdate.entity.User;
import com.dealsdate.entity.Wishlist;
import com.dealsdate.exception.CustomerAlreadyExistsException;
import com.dealsdate.exception.CustomerNotFoundException;
import com.dealsdate.repositories.CustomerRepository;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository custRepo;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addCustomer_WhenCustomerDoesNotExist_ShouldReturnAddedCustomer() throws CustomerAlreadyExistsException {
        // Arrange
        Customer customer = new Customer();
        customer.setMobileNo("1234567890");

        User user = new User(); 
        user.setUserType("Customer"); 
        customer.setUser(user); 

        when(custRepo.findByMobileNo(any(String.class))).thenReturn(null);
        when(custRepo.save(any(Customer.class))).thenReturn(customer);

        // Act
        Customer result = customerService.addCustomer(customer);

        // Assert
        Assertions.assertEquals(customer, result);
        verify(custRepo, times(1)).findByMobileNo(any(String.class));
        verify(custRepo, times(1)).save(any(Customer.class));
    }


    @Test
    void addCustomer_WhenCustomerAlreadyExists_ShouldThrowCustomerAlreadyExistsException() {
        // Arrange
        Customer customer = new Customer();
        customer.setMobileNo("1234567890");

        when(custRepo.findByMobileNo(any(String.class))).thenReturn(customer);

        // Act and Assert
        Assertions.assertThrows(CustomerAlreadyExistsException.class, () -> customerService.addCustomer(customer));
        verify(custRepo, times(1)).findByMobileNo(any(String.class));
        verify(custRepo, never()).save(any(Customer.class));
    }

    @Test
    void testGetCustomerById() throws CustomerNotFoundException {
        // Prepare test data
        Customer customer = new Customer();
        customer.setCustomerId(1);

        // Mock behavior
        when(custRepo.existsById(anyInt())).thenReturn(true);
        when(custRepo.findById(anyInt())).thenReturn(Optional.of(customer));

        // Perform the test
        Customer result = customerService.getCustomerById(1);

        // Verify the result
        Assertions.assertEquals(customer, result);
        Mockito.verify(custRepo, Mockito.times(1)).existsById(anyInt());
        Mockito.verify(custRepo, Mockito.times(1)).findById(anyInt());
    }

    @Test
    void testGetCustomerById_CustomerNotFoundException() {
        // Mock behavior
        when(custRepo.existsById(anyInt())).thenReturn(false);

        // Perform the test and verify the exception
        Assertions.assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getCustomerById(1);
        });

        Mockito.verify(custRepo, Mockito.times(1)).existsById(anyInt());
        Mockito.verify(custRepo, Mockito.times(0)).findById(anyInt());
    }
    
    @Test
    void testDeleteCustomerById() throws CustomerNotFoundException {
        // Mock behavior
        when(custRepo.existsById(anyInt())).thenReturn(true);

        // Perform the test
        String result = customerService.deleteCustomerById(1);

        // Verify the result
        Assertions.assertEquals("Deleted Customer by ID", result);
        Mockito.verify(custRepo, Mockito.times(1)).existsById(anyInt());
        Mockito.verify(custRepo, Mockito.times(1)).deleteById(anyInt());
    }

    @Test
    void testDeleteCustomerById_CustomerNotFoundException() {
        // Mock behavior
        when(custRepo.existsById(anyInt())).thenReturn(false);

        // Perform the test and verify the exception
        Assertions.assertThrows(CustomerNotFoundException.class, () -> {
            customerService.deleteCustomerById(1);
        });

        Mockito.verify(custRepo, Mockito.times(1)).existsById(anyInt());
        Mockito.verify(custRepo, Mockito.times(0)).deleteById(anyInt());
    }
    
    @Test
    void testUpdateCustomer() throws CustomerNotFoundException {
        // Create a sample customer
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setCustomerName("John Doe");

        // Mock the behavior of the customer repository
        when(custRepo.existsById(1)).thenReturn(true);
        when(custRepo.findById(1)).thenReturn(Optional.of(customer));
        when(custRepo.save(any(Customer.class))).thenReturn(customer);

        // Update the customer
        Customer updatedCustomer = new Customer();
        updatedCustomer.setCustomerId(1);
        updatedCustomer.setCustomerName("John Doe Updated");
        Customer result = customerService.updateCustomer(updatedCustomer);

        // Verify the repository method invocations
        verify(custRepo).existsById(1);
        verify(custRepo).findById(1);
        verify(custRepo).save(any(Customer.class));

        // Assertions
        assertEquals(updatedCustomer.getCustomerName(), result.getCustomerName());
    }

    @Test
    void testUpdateCustomer_ThrowsCustomerNotFoundException() {
        // Mock the behavior of the customer repository
        when(custRepo.existsById(1)).thenReturn(false);

        // Update the customer
        Customer customer = new Customer();
        customer.setCustomerId(1);
        assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(customer));

        // Verify the repository method invocations
        verify(custRepo).existsById(1);
        verify(custRepo, never()).findById(anyInt());
        verify(custRepo, never()).save(any(Customer.class));
    }

    @Test
    void testDeleteCustomer() throws CustomerNotFoundException {
        // Create a sample customer
        Customer customer = new Customer();
        customer.setCustomerId(1);

        // Mock the behavior of the customer repository
        when(custRepo.existsById(1)).thenReturn(true);

        // Delete the customer
        String result = customerService.deleteCustomer(customer);

        // Verify the repository method invocations
        verify(custRepo).existsById(1);
        verify(custRepo).delete(customer);

        // Assertion
        assertEquals("Customer Deleted", result);
    }

    @Test
    void testDeleteCustomer_ThrowsCustomerNotFoundException() {
        // Mock the behavior of the customer repository
        when(custRepo.existsById(1)).thenReturn(false);

        // Delete the customer
        Customer customer = new Customer();
        customer.setCustomerId(1);
        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(customer));

        // Verify the repository method invocations
        verify(custRepo).existsById(1);
        verify(custRepo, never()).delete(any(Customer.class));
    }
    @Test
    void testGetOrdersByCustomerId() throws CustomerNotFoundException {
        // Create a sample customer
        Customer customerMock = mock(Customer.class);
        customerMock.setCustomerId(1);

        // Create a list of orders
        List<Order> orders = Arrays.asList(new Order(), new Order());

        // Mock the behavior of the customer repository
        when(custRepo.existsById(1)).thenReturn(true);

        // Mock the behavior of findById(1) to return Optional.of(customerMock)
        when(custRepo.findById(1)).thenReturn(Optional.of(customerMock));

        // Mock the behavior of customerMock.getOrders() to return orders
        when(customerMock.getOrders()).thenReturn(orders);

        // Retrieve the orders
        List<Order> result = customerService.getOrdersByCustomerId(1);

        // Verify the repository method invocations
        verify(custRepo).existsById(1);

        // Verify that findById(1) is called twice
        verify(custRepo, times(2)).findById(1);

        // Verify that customerMock.getOrders() is called
        verify(customerMock).getOrders();

        // Assertion
        assertEquals(orders, result);
    }



    @Test
    void testGetOrdersByCustomerId_ThrowsCustomerNotFoundException() {
        // Mock the behavior of the customer repository
        when(custRepo.existsById(1)).thenReturn(false);

        // Retrieve the orders
        assertThrows(CustomerNotFoundException.class, () -> customerService.getOrdersByCustomerId(1));

        // Verify the repository method invocations
        verify(custRepo).existsById(1);
        verify(custRepo, never()).findById(anyInt());
    }

    @Test
    void testGetCartByCustId() throws CustomerNotFoundException {
        // Create a sample customer
        Customer customer = new Customer();
        customer.setCustomerId(1);
        Cart cart = new Cart();
        customer.setCart(cart);

        // Mock the behavior of the customer repository
        when(custRepo.existsById(1)).thenReturn(true);
        when(custRepo.findById(1)).thenReturn(Optional.of(customer));

        // Retrieve the cart
        Cart result = customerService.getCartByCustId(1);

        // Verify the repository method invocations
        verify(custRepo).existsById(1);
        verify(custRepo).findById(1);

        // Assertion
        assertEquals(cart, result);
    }

    @Test
    void testGetCartByCustId_ThrowsCustomerNotFoundException() {
        // Mock the behavior of the customer repository
        when(custRepo.existsById(1)).thenReturn(false);

        // Retrieve the cart
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCartByCustId(1));

        // Verify the repository method invocations
        verify(custRepo).existsById(1);
        verify(custRepo, never()).findById(anyInt());
    }

    @Test
    void testGetWishListByCustId() throws CustomerNotFoundException {
        // Create a sample customer
        Customer customer = new Customer();
        customer.setCustomerId(1);
        Wishlist wishlist = new Wishlist();
        customer.setWishlist(wishlist);

        // Mock the behavior of the customer repository
        when(custRepo.existsById(1)).thenReturn(true);
        when(custRepo.findById(1)).thenReturn(Optional.of(customer));

        // Retrieve the wishlist
        Wishlist result = customerService.getWishListByCustId(1);

        // Verify the repository method invocations
        verify(custRepo).existsById(1);
        verify(custRepo).findById(1);

        // Assertion
        assertEquals(wishlist, result);
    }

    @Test
    void testGetWishListByCustId_ThrowsCustomerNotFoundException() {
        // Mock the behavior of the customer repository
        when(custRepo.existsById(1)).thenReturn(false);

        // Retrieve the wishlist
        assertThrows(CustomerNotFoundException.class, () -> customerService.getWishListByCustId(1));

        // Verify the repository method invocations
        verify(custRepo).existsById(1);
        verify(custRepo, never()).findById(anyInt());
    }
}

