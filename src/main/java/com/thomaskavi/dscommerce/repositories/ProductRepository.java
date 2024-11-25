package com.thomaskavi.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thomaskavi.dscommerce.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
