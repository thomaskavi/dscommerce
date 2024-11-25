package com.thomaskavi.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
//PAGINAÇÃO (USAR O TIPO PAGE<>)
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//PAGINAÇÃO
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thomaskavi.dscommerce.dto.ProductDTO;
import com.thomaskavi.dscommerce.entities.Product;
import com.thomaskavi.dscommerce.repositories.ProductRepository;
import com.thomaskavi.dscommerce.services.exceptions.DatabaseException;
import com.thomaskavi.dscommerce.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

  @Autowired
  private ProductRepository repository;

  @Transactional(readOnly = true)
  public ProductDTO findById(Long id) {
    Product product = repository.findById(id)
        .orElseThrow(() -> new DatabaseException("Resource not found"));
    return new ProductDTO(product);
  }

  @Transactional(readOnly = true)
  public Page<ProductDTO> findAll(Pageable pageable) {
    Page<Product> result = repository.findAll(pageable);
    return result.map(x -> new ProductDTO(x));
  }

  @Transactional
  public ProductDTO insert(ProductDTO dto) {
    Product entity = new Product();
    copyDtoToEntity(dto, entity);
    entity = repository.save(entity);
    return new ProductDTO(entity);
  }

  @Transactional
  public ProductDTO update(Long id, ProductDTO dto) {
    try {
      Product entity = repository.getReferenceById(id);
      copyDtoToEntity(dto, entity);
      entity = repository.save(entity);
      return new ProductDTO(entity);
    } catch (EntityNotFoundException e) {
      throw new DatabaseException("Resource not found");
    }
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public void delete(Long id) {
    if (!repository.existsById(id)) {
      throw new ResourceNotFoundException("Resource not found");
    }
    try {
      repository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Referential integrity failure");
    }
  }

  private void copyDtoToEntity(ProductDTO dto, Product entity) {
    entity.setName(dto.getDescription());
    entity.setDescription(dto.getDescription());
    entity.setPrice(dto.getPrice());
    entity.setImgUrl(dto.getImgUrl());
  }

}
