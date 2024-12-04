package com.thomaskavi.dscommerce.services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thomaskavi.dscommerce.dto.OrderDTO;
import com.thomaskavi.dscommerce.dto.OrderItemDTO;
import com.thomaskavi.dscommerce.entities.Order;
import com.thomaskavi.dscommerce.entities.OrderItem;
import com.thomaskavi.dscommerce.entities.OrderStatus;
import com.thomaskavi.dscommerce.entities.Product;
import com.thomaskavi.dscommerce.entities.User;
import com.thomaskavi.dscommerce.repositories.OrderItemRepository;
import com.thomaskavi.dscommerce.repositories.OrderRepository;
import com.thomaskavi.dscommerce.repositories.ProductRepository;
import com.thomaskavi.dscommerce.services.exceptions.DatabaseException;

@Service
public class OrderService {

  @Autowired
  private OrderRepository repository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  OrderItemRepository orderItemRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private AuthService authService;

  @Transactional(readOnly = true)
  public OrderDTO findById(Long id) {
    Order order = repository.findById(id)
        .orElseThrow(() -> new DatabaseException("Recurso não encontrado"));
    authService.validateSelfOrAdmin(order.getClient().getId());
    return new OrderDTO(order);
  }

  @Transactional
  public OrderDTO insert(OrderDTO dto) {
    Order order = new Order();

    order.setMoment(Instant.now());
    order.setStatus(OrderStatus.WAITING_PAYMENT);

    User user = userService.athenticated();
    order.setClient(user);

    for (OrderItemDTO itemDto : dto.getItems()) {
      Product product = productRepository.getReferenceById(itemDto.getProductId());
      OrderItem item = new OrderItem(order, product, itemDto.getQuantity(), product.getPrice());
      order.getItems().add(item);
    }
    repository.save(order);
    orderItemRepository.saveAll(order.getItems());
    return new OrderDTO(order);
  }

}
