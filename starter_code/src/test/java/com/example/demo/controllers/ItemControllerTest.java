package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private ItemController itemController;

    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp(){
        itemController = new ItemController();
        TestUtils.injectObject(itemController, "itemRepository", itemRepository);
    }

    @Test
    public void getItems_happy_path(){

        List<Item> items = new ArrayList<>();

        when(itemRepository.findAll()).thenReturn(items);

        final ResponseEntity<List<Item>> response = itemController.getItems();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getItemById_happy_path(){
        Item item = new Item();
        Long itemId = 1L;
        item.setId(itemId);
        item.setName("item");
        item.setDescription("desc");
        item.setPrice(BigDecimal.valueOf(10));
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        final ResponseEntity<Item> response = itemController.getItemById(itemId);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getItemsByName_happy_path(){
        List<Item> items = new ArrayList<>();
        Item item = new Item();
        String itemName = "item";
        item.setId(1L);
        item.setName("item");
        item.setDescription("desc");
        item.setPrice(BigDecimal.valueOf(10));
        items.add(item);
        when(itemRepository.findByName(itemName)).thenReturn(items);

        final ResponseEntity<List<Item>> response = itemController.getItemsByName(itemName);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getItemsByName_negative_path(){
        List<Item> items = new ArrayList<>();
        String itemName = "item";
        when(itemRepository.findByName(itemName)).thenReturn(items);

        final ResponseEntity<List<Item>> response = itemController.getItemsByName(itemName);
        assertEquals(404, response.getStatusCodeValue());
    }
}
