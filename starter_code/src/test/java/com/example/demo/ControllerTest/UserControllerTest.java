package com.example.demo.ControllerTest;

import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserController userController;


    @Test
    public void testFindById() {
        User user = new User();
        user.setId(1L);
        user.setUsername("Dung");
        user.setPassword("12345678");
        user.setCart(new Cart());
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        Mockito.when(userRepository.findByUsername("Dung")).thenReturn(user);
        ResponseEntity<User> response = userController.findById(1L);
        assertEquals(200, response.getStatusCodeValue());
    }


    @Test
    public void testFindByUserName() {
        User user = new User();
        user.setId(1L);
        user.setUsername("Dung");
        user.setPassword("12345678");
        user.setCart(new Cart());
        Mockito.when(userRepository.findByUsername("Dung")).thenReturn(user);
        ResponseEntity<User> response = userController.findByUserName("Dung");
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testFindByUserName_EmptyUser() {
        Mockito.when(userRepository.findByUsername("zane")).thenReturn(null);
        ResponseEntity<User> response = userController.findByUserName("zane");
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testCreateUser_invalidPassword() {
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername("user");
        userRequest.setPassword("pass");
        userRequest.setConfirmPassword("pass");
        ResponseEntity<User> response = userController.createUser(userRequest);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void testCreateUser() {
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername("username");
        userRequest.setPassword("123456789");
        userRequest.setConfirmPassword("123456789");
        ResponseEntity<User> response = userController.createUser(userRequest);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testCreateUser_NotMatchPassword() {
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername("user");
        userRequest.setPassword("password");
        userRequest.setConfirmPassword("password1");
        ResponseEntity<User> response = userController.createUser(userRequest);
        assertEquals(400, response.getStatusCodeValue());
    }

}
