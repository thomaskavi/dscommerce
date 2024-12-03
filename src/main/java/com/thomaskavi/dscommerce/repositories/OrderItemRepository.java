package com.thomaskavi.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thomaskavi.dscommerce.entities.OrderItem;
import com.thomaskavi.dscommerce.entities.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
