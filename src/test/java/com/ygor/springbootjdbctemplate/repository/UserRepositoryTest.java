package com.ygor.springbootjdbctemplate.repository;

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
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getUserById() {
        User user = userRepository.getById(1);

        assertNotNull(user);

        printUser(user);
    }

    @Test
    public void getAllUsers() {
        List<User> users = userRepository.getAll();

        assertNotNull(users);

        for (User user : users) {
            printUser(user);
            System.out.println("----------------------------");
        }
    }

    @Test
    public void createUser() {
        User user = new User();

        user.setName("Ygor");
        user.setEmail("ygoralcantara@gmail.com");
        user.setCellphone("(83) 99999-8888");

        User saveduser = userRepository.create(user);
        User newUser = userRepository.getById(saveduser.getId());

        assertNotNull(newUser);
        assertEquals("Ygor", newUser.getName());
        assertEquals("ygoralcantara@gmail.com", newUser.getEmail());
        assertEquals("(83) 99999-8888", newUser.getCellphone());

        printUser(newUser);
    }

    @Test
    public void updateUser() {
        int id = 1;

        User updateUser = userRepository.getById(id);

        updateUser.setName("Ygor");
        updateUser.setEmail("ygor@gmail.com");

        userRepository.update(id, updateUser);

        User newUser = userRepository.getById(id);

        assertNotNull(newUser);
        assertEquals("Ygor", newUser.getName());
        assertEquals("ygor@gmail.com", newUser.getEmail());

        printUser(newUser);
    }

    @Test
    public void removeUser() {
        int id = 2;

        userRepository.remove(id);

        User user = userRepository.getById(id);

        assertNull(user);
    }

    private void printUser(User user) {
        System.out.println("ID: " + user.getId());
        System.out.println("NAME: " + user.getName());
        System.out.println("EMAIL: " + user.getEmail());
        System.out.println("CELLPHONE: " + user.getCellphone());
    }
}
