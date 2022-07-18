package com.example.demo.ControllerTest;

import com.example.demo.controllers.CartController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private CartController cartController;


    @Test
    public void addToCartSuccess() {
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
        user.setCart(cart);
        ModifyCartRequest req = new ModifyCartRequest();
        req.setUsername("Dung");
        req.setItemId(1L);
        req.setQuantity(3);
        Mockito.when(userRepository.findByUsername("Dung")).thenReturn(user);
        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(item1));
        ResponseEntity<Cart> response = cartController.addTocart(req);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Cart cart1 = response.getBody();
        assertNotNull(cart);
        assertEquals(3, cart1.getItems().size());
    }

    @Test
    public void addToCartSuccess_userNotFound(){
        ModifyCartRequest req = new ModifyCartRequest();
        req.setUsername("Dung");
        req.setItemId(1L);
        req.setQuantity(3);
        Mockito.when(userRepository.findByUsername("Dung")).thenReturn(null);
        ResponseEntity<Cart> response = cartController.addTocart(req);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void removeFromCartSuccess() {
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
        user.setCart(cart);
        ModifyCartRequest req = new ModifyCartRequest();
        req.setUsername("Dung");
        req.setItemId(1L);
        req.setQuantity(3);
        Mockito.when(userRepository.findByUsername("Dung")).thenReturn(user);
        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(item1));
        ResponseEntity<Cart> response = cartController.removeFromcart(req);
        assertEquals(200, response.getStatusCodeValue());
    }

}
