package com.example.demo.ControllerTest;

import com.example.demo.controllers.ItemController;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerTest {
    @Mock
    ItemRepository itemRepository;
    @InjectMocks
    ItemController itemController;

    @Test
    public void getItemsSuccess() {
        Item item1 = new Item();
        Item item2 = new Item();
        item1.setId(1L);
        item1.setPrice(BigDecimal.valueOf(10));
        item1.setName("item1");
        item1.setDescription("Description");
        item2.setId(1L);
        item2.setPrice(BigDecimal.valueOf(10));
        item2.setName("item1");
        item2.setDescription("Description");

        Mockito.when(itemRepository.findAll()).thenReturn(Arrays.asList(item1, item2));
        final ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        List<Item> items = response.getBody();
        assertNotNull(items);
        assertEquals(2, items.size());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getItemsByIdSuccess() {
        Item item = new Item();
        item.setId(1L);
        item.setPrice(BigDecimal.valueOf(10));
        item.setName("item1");
        item.setDescription("Description");

        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        ResponseEntity<Item> response = itemController.getItemById(1L);
        assertNotNull(response);
        Item item1 = response.getBody();
        assertNotNull(item);
        assertEquals(item.getName(), item1.getName());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getItemsByNameSuccess() {
        Item item1 = new Item();
        Item item2 = new Item();
        item1.setId(1L);
        item1.setPrice(BigDecimal.valueOf(10));
        item1.setName("item1");
        item1.setDescription("Description");
        item2.setId(1L);
        item2.setPrice(BigDecimal.valueOf(10));
        item2.setName("item1");
        item2.setDescription("Description");
        Mockito.when(itemRepository.findByName(item1.getName())).thenReturn(Arrays.asList(item1));
        Mockito.when(itemRepository.findByName(item2.getName())).thenReturn(Arrays.asList(item2));
        ResponseEntity<List<Item>> response = itemController.getItemsByName("item1");
        assertNotNull(response);
        List<Item> items = response.getBody();
        assertNotNull(items);
        assertEquals(1, items.size());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getItemByID_NotFound() {
        ResponseEntity<Item> response = itemController.getItemById(50L);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void getItemsByName_NotFound() {
        ResponseEntity<List<Item>> response = itemController.getItemsByName("test");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }
}
