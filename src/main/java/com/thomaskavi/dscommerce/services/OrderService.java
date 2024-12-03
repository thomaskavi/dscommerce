package com.thomaskavi.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thomaskavi.dscommerce.dto.OrderDTO;
import com.thomaskavi.dscommerce.entities.Order;
import com.thomaskavi.dscommerce.repositories.OrderRepository;
import com.thomaskavi.dscommerce.services.exceptions.DatabaseException;

@Service
public class OrderService {

  @Autowired
  private OrderRepository repository;

  @Transactional(readOnly = true)
  public OrderDTO findById(Long id) {
    Order order = repository.findById(id)
        .orElseThrow(() -> new DatabaseException("Resource not found"));
    return new OrderDTO(order);
  }

}
