package com.example.demo.ControllerTest;

import com.example.demo.controllers.OrderController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderController orderController;

    UserOrder userOrder;

    @Before
    public void setUp(){

    }

    @Test
    public void testSubmit() {
        Mockito.when(userRepository.findByUsername("Dung")).thenReturn(testUser());
        ResponseEntity<UserOrder> response = orderController.submit("Dung");

        assertEquals(200, response.getStatusCodeValue());
        UserOrder userOrder = response.getBody();
        assertNotNull(userOrder);
        assertEquals(2, userOrder.getItems().size());
    }


    @Test
    public void testSubmit_userNotFound() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(null);
        ResponseEntity<UserOrder> response = orderController.submit("Dung");

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testGetOrdersForUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("Dung");
        user.setPassword("12345678");
        Cart cart = new Cart();
        Item item = new Item();
        item.setId(1L);
        item.setName("item1");
        item.setDescription("Description");
        item.setPrice(BigDecimal.valueOf(100));
        cart.addItem(item);
        user.setCart(cart);
        userOrder = UserOrder.createFromCart(user.getCart());
        Mockito.when(userRepository.findByUsername("Dung")).thenReturn(user);
        Mockito.when(orderRepository.findByUser(user)).thenReturn(Arrays.asList(userOrder));
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("Dung");
        assertNotNull(response);
        List<UserOrder> orders = response.getBody();
        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetOrdersForUser_EmptyUser() throws Exception {
        Mockito.when(userRepository.findByUsername("Dung")).thenReturn(null);
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("Dung");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    public User testUser(){
        User user = new User();
        user.setId(1L);
        user.setUsername("Dung");
        user.setPassword("12345678");
        Cart cart = new Cart();
        Item item1= new Item();
        item1.setId(1L);
        item1.setName("item1");
        item1.setDescription("Description");
        item1.setPrice(BigDecimal.valueOf(100));
        Item item2= new Item();
        item2.setId(2L);
        item2.setName("item2");
        item2.setDescription("Description");
        item2.setPrice(BigDecimal.valueOf(50));
        cart.setUser(user);
        cart.addItem(item1);
        cart.addItem(item2);
        user.setCart(cart);
        return user;
    }

}
