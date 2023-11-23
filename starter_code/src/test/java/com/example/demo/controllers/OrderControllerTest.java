package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private OrderController orderController;

    private UserRepository userRepository = mock(UserRepository.class);

    private OrderRepository orderRepository = mock(OrderRepository.class);


    @Before
    public void setUp(){
        orderController = new OrderController();
        TestUtils.injectObject(orderController, "userRepository", userRepository);
        TestUtils.injectObject(orderController, "orderRepository", orderRepository);
    }

    @Test
    public void submit_happy_path(){
        User user = new User();
        Cart cart = new Cart();
        List<Item> items = new ArrayList<>();
        cart.setItems(items);
        user.setCart(cart);
        when(userRepository.findByUsername("test")).thenReturn(user);
        final ResponseEntity<UserOrder> response = orderController.submit("test");
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void submit_negative_path(){
        when(userRepository.findByUsername("test")).thenReturn(null);
        final ResponseEntity<UserOrder> response = orderController.submit("test");
        assertEquals(404, response.getStatusCodeValue());
    }


    @Test
    public void getOrdersForUser_happy_path(){
        User user = new User();
        List<UserOrder> userOrders = new ArrayList<>();
        when(userRepository.findByUsername("test")).thenReturn(user);
        when(orderRepository.findByUser(user)).thenReturn(userOrders);
        final ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("test");
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getOrdersForUser_negative_path(){
        when(userRepository.findByUsername("test")).thenReturn(null);
        final ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("test");
        assertEquals(404, response.getStatusCodeValue());
    }

}
