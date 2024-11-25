package com.thomaskavi.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thomaskavi.dscommerce.dto.ProductDTO;
import com.thomaskavi.dscommerce.entities.Product;
import com.thomaskavi.dscommerce.repositories.ProductRepository;

@Service
public class ProductService {

  @Autowired
  private ProductRepository repository;

  @Transactional(readOnly = true)
  public ProductDTO findById(Long id) {
    Product product = repository.findById(id).get();
    return new ProductDTO(product);
  }
}
