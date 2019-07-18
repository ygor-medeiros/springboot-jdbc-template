package com.ygor.springbootjdbctemplate.repository;

import com.ygor.springbootjdbctemplate.model.Address;
import com.ygor.springbootjdbctemplate.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getAddressById() {
        int id = 1;

        User user = userRepository.getById(id);

        Address address = addressRepository.getById(user.getId());

        assertNotNull(address);

        System.out.println("NAME: " + user.getName());

        printAddress(address);
    }

    @Test
    public void getAllAddresses() {
        List<Address> addresses = addressRepository.getAll();

        assertNotNull(addresses);

        for (Address address : addresses) {
            printAddress(address);
            System.out.println("--------------------------------");
        }
    }

    @Test
    public void createAddress() {
        Address address = new Address(4, "Rua Brasília 64", "João Pessoa",
                "PB", "Brasil", "58078-458");

        Address savedAddress = addressRepository.create(address);
        Address newAddress = addressRepository.getById(savedAddress.getUserId());

        assertNotNull(newAddress);
        assertEquals("Rua Brasília 64", newAddress.getStreet());
        assertEquals("João Pessoa", newAddress.getCity());
        assertEquals("PB", newAddress.getState());
        assertEquals("Brasil", newAddress.getCountry());
        assertEquals("58078-458", newAddress.getZipCode());

        printAddress(newAddress);
    }

    @Test
    public void updateAddress() {
        int user_id = 1;

        Address updateAddress = addressRepository.getById(user_id);

        updateAddress.setCountry("United States");

        addressRepository.update(user_id, updateAddress);

        Address newAddress = addressRepository.getById(user_id);

        assertNotNull(newAddress);
        assertEquals(updateAddress.getCountry(), newAddress.getCountry());

        printAddress(newAddress);
    }

    @Test
    public void removeAddress() {
        int user_id = 2;

        addressRepository.remove(user_id);

        Address address = addressRepository.getById(user_id);

        assertNull(address);
    }

    private void printAddress(Address address) {
        System.out.println("STREET: " + address.getStreet());
        System.out.println("CITY: " + address.getCity());
        System.out.println("STATE: " + address.getState());
        System.out.println("COUNTRY: " + address.getCountry());
        System.out.println("ZIP CODE: " + address.getZipCode());
    }
}
