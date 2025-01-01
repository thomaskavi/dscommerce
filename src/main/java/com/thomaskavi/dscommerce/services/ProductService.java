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

import com.thomaskavi.dscommerce.dto.CategoryDTO;
import com.thomaskavi.dscommerce.dto.ProductDTO;
import com.thomaskavi.dscommerce.dto.ProductMinDTO;
import com.thomaskavi.dscommerce.entities.Category;
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
        .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
    return new ProductDTO(product);
  }

  @Transactional(readOnly = true)
  public Page<ProductMinDTO> findAll(String name, Pageable pageable) {
    Page<Product> result = repository.searchByName(name, pageable);
    return result.map(x -> new ProductMinDTO(x));
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
      throw new ResourceNotFoundException("Recurso não encontrado");
    }
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public void delete(Long id) {
    if (!repository.existsById(id)) {
      throw new ResourceNotFoundException("Recurso não encontrado");
    }
    try {
      repository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Falha de integridade referencial");
    }
  }

  private void copyDtoToEntity(ProductDTO dto, Product entity) {
    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
    entity.setPrice(dto.getPrice());
    entity.setImgUrl(dto.getImgUrl());

    // Atualizar as categorias sem substituir a coleção diretamente
    entity.getCategories().clear(); // Limpa as categorias existentes
    for (CategoryDTO catDto : dto.getCategories()) {
      Category cat = new Category();
      cat.setId(catDto.getId());
      entity.getCategories().add(cat); // Adiciona as novas categorias
    }
  }

}
