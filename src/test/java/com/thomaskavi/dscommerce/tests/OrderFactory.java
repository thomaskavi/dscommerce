package com.thomaskavi.dscommerce.tests;

import java.time.Instant;

import com.thomaskavi.dscommerce.entities.Order;
import com.thomaskavi.dscommerce.entities.OrderItem;
import com.thomaskavi.dscommerce.entities.OrderStatus;
import com.thomaskavi.dscommerce.entities.Payment;
import com.thomaskavi.dscommerce.entities.Product;
import com.thomaskavi.dscommerce.entities.User;

public class OrderFactory {

  public static Order createOrder(User client) {

    Order order = new Order(1L, Instant.now(), OrderStatus.WAITING_PAYMENT, client, new Payment());

    Product product = ProductFactory.createProduct();

    OrderItem orderItem = new OrderItem(order, product, 2, 10.0);
    order.getItems().add(orderItem);

    return order;
  }
}
