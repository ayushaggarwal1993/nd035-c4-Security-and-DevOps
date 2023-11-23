package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private CartController cartController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp(){
        cartController = new CartController();
        TestUtils.injectObject(cartController, "userRepository", userRepository);
        TestUtils.injectObject(cartController, "cartRepository", cartRepository);
        TestUtils.injectObject(cartController, "itemRepository", itemRepository);

    }

    @Test
    public void addTocart_happy_path(){
        User user = new User();
        Cart cart = new Cart();
        user.setCart(cart);
        Long itemId = 1L;
        ModifyCartRequest r = new ModifyCartRequest();
        r.setUsername("test");
        r.setItemId(itemId);
        r.setQuantity(1);
        Item item = new Item();
        item.setId(itemId);
        item.setName("item");
        item.setDescription("desc");
        item.setPrice(BigDecimal.valueOf(10));
        when(userRepository.findByUsername("test")).thenReturn(user);
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        final ResponseEntity<Cart> response = cartController.addTocart(r);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void addTocart_negative_path(){
        when(userRepository.findByUsername("test")).thenReturn(null);
        ModifyCartRequest r = new ModifyCartRequest();
        r.setUsername("test");
        final ResponseEntity<Cart> response = cartController.addTocart(r);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void removeFromcart_happy_path(){
        User user = new User();
        Cart cart = new Cart();
        user.setCart(cart);
        Long itemId = 1L;
        ModifyCartRequest r = new ModifyCartRequest();
        r.setUsername("test");
        r.setItemId(itemId);
        r.setQuantity(1);
        Item item = new Item();
        item.setId(itemId);
        item.setName("item");
        item.setDescription("desc");
        item.setPrice(BigDecimal.valueOf(10));
        when(userRepository.findByUsername("test")).thenReturn(user);
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        final ResponseEntity<Cart> response = cartController.removeFromcart(r);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void removeFromcart_negative_path(){
        when(userRepository.findByUsername("test")).thenReturn(null);
        ModifyCartRequest r = new ModifyCartRequest();
        r.setUsername("test");
        final ResponseEntity<Cart> response = cartController.removeFromcart(r);
        assertEquals(404, response.getStatusCodeValue());
    }



}
