package com.dealsdate.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.dealsdate.dto.LoginRequestDto;
import com.dealsdate.entity.Customer;
import com.dealsdate.entity.User;
import com.dealsdate.exception.UserNotFoundException;
import com.dealsdate.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private LoginRequestDto testLoginRequest;
    private List<User> testUsers;

    @BeforeEach
    public void setUp() {
        // Initialize test data
        testUser = new User();
        testUser.setUserId(1);
        testUser.setUserName("testUser");
        testUser.setUserPassword("password");

        testLoginRequest = new LoginRequestDto();
        testLoginRequest.setUserName("testUser");
        testLoginRequest.setPassword("password");

        testUsers = new ArrayList<>();
        testUsers.add(testUser);
    }

    @Test
    public void testAddUser() {
        // Mock repository method
        when(userRepo.save(any(User.class))).thenReturn(testUser);

        // Call the service method
        User addedUser = userService.addUser(testUser);

        // Verify the result
        assertNotNull(addedUser);
        assertEquals(testUser, addedUser);

        // Verify that the repository method was called
        verify(userRepo, times(1)).save(testUser);
    }

    @Test
    public void testLoginUser_Successful() throws UserNotFoundException {
        // Mock repository method
        when(userRepo.findByUserName("testUser")).thenReturn(Optional.of(testUser));

        // Call the service method
        User loggedInUser = userService.loginUser(testLoginRequest);

        // Verify the result
        assertNotNull(loggedInUser);
        assertEquals(testUser, loggedInUser);

        // Verify that the repository method was called
        verify(userRepo, times(1)).findByUserName("testUser");
    }

    @Test
    public void testLoginUser_WrongPassword() {
        // Mock repository method
        when(userRepo.findByUserName("testUser")).thenReturn(Optional.of(testUser));

        // Update the testLoginRequest with wrong password
        testLoginRequest.setPassword("wrongPassword");

        // Call the service method and verify that it throws UserNotFoundException
        assertThrows(UserNotFoundException.class, () -> userService.loginUser(testLoginRequest));

        // Verify that the repository method was called
        verify(userRepo, times(1)).findByUserName("testUser");
    }

    @Test
    public void testLoginUser_UserNotFound() {
        // Mock repository method
        when(userRepo.findByUserName("testUser")).thenReturn(Optional.empty());

        // Call the service method and verify that it throws UserNotFoundException
        assertThrows(UserNotFoundException.class, () -> userService.loginUser(testLoginRequest));

        // Verify that the repository method was called
        verify(userRepo, times(1)).findByUserName("testUser");
    }

    @Test
    public void testGetAllUsers() {
        // Mock repository method
        when(userRepo.findAll()).thenReturn(testUsers);

        // Call the service method
        List<User> allUsers = userService.getAllUsers();

        // Verify the result
        assertNotNull(allUsers);
        assertEquals(testUsers, allUsers);

        // Verify that the repository method was called
        verify(userRepo, times(1)).findAll();
    }

    @Test
    public void testGetCustomerByUserId_UserExists() {
        // Mock repository method
        when(userRepo.findById(1)).thenReturn(Optional.of(testUser));

        // Set up the Customer object associated with the User
        Customer testCustomer = new Customer();
        testCustomer.setCustomerId(1);
        testUser.setCustomer(testCustomer);

        // Call the service method
        Customer customer = userService.getCustomerByUserId(1);

        // Verify the result
        assertNotNull(customer);
        assertEquals(testCustomer, customer);

        // Verify that the repository method was called
        verify(userRepo, times(1)).findById(1);
    }


    @Test
    public void testGetCustomerByUserId_UserNotFound() {
        // Mock repository method
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        // Call the service method
        Customer customer = userService.getCustomerByUserId(1);

        // Verify the result
        assertNull(customer);

        // Verify that the repository method was called
        verify(userRepo, times(1)).findById(1);
    }
}
