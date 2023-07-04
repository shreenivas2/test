package com.dealsdate.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dealsdate.entity.Address;
import com.dealsdate.exception.AddressNotFoundException;
import com.dealsdate.repositories.AddressRepository;

class AddressServiceTest {
    @Mock
    private AddressRepository addrRepo;

    @InjectMocks
    private AddressServiceImpl addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddAddress() {
        Address address = new Address();
        when(addrRepo.save(address)).thenReturn(address);

        Address result = addressService.addAddress(address);

        verify(addrRepo).save(address);
        assertEquals(address, result);
    }

    @Test
    void testUpdateAddress() throws AddressNotFoundException {
        Address address = new Address();
        when(addrRepo.existsById(address.getAddressId())).thenReturn(true);
        when(addrRepo.save(address)).thenReturn(address);

        Address result = addressService.updateAddress(address);

        verify(addrRepo).existsById(address.getAddressId());
        verify(addrRepo).save(address);
        assertEquals(address, result);
    }

    @Test
    void testUpdateAddress_ThrowException() {
        Address address = new Address();
        when(addrRepo.existsById(address.getAddressId())).thenReturn(false);

        assertThrows(AddressNotFoundException.class, () -> addressService.updateAddress(address));

        verify(addrRepo).existsById(address.getAddressId());
        verify(addrRepo, never()).save(address);
    }

    @Test
    void testDeleteAddressById() throws AddressNotFoundException {
        Integer addressId = 1;
        when(addrRepo.existsById(addressId)).thenReturn(true);

        String result = addressService.deleteAddressById(addressId);

        verify(addrRepo).existsById(addressId);
        verify(addrRepo).deleteById(addressId);
        assertEquals("Address deleted successfully", result);
    }

    @Test
    void testDeleteAddressById_ThrowException() {
        Integer addressId = 1;
        when(addrRepo.existsById(addressId)).thenReturn(false);

        assertThrows(AddressNotFoundException.class, () -> addressService.deleteAddressById(addressId));

        verify(addrRepo).existsById(addressId);
        verify(addrRepo, never()).deleteById(addressId);
    }

    @Test
    void testGetAddressById() throws AddressNotFoundException {
        Integer addressId = 1;
        Address address = new Address();
        when(addrRepo.existsById(addressId)).thenReturn(true);
        when(addrRepo.findById(addressId)).thenReturn(Optional.of(address));

        Address result = addressService.getAddressById(addressId);

        verify(addrRepo).existsById(addressId);
        verify(addrRepo).findById(addressId);
        assertEquals(address, result);
    }

    @Test
    void testGetAddressById_ThrowException() {
        Integer addressId = 1;
        when(addrRepo.existsById(addressId)).thenReturn(false);
        assertThrows(AddressNotFoundException.class, () -> addressService.getAddressById(addressId));
        verify(addrRepo).existsById(addressId);
        verify(addrRepo, never()).findById(addressId);
    }
}
