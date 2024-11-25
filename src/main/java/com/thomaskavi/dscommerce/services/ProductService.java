package com.thomaskavi.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
//PAGINAÇÃO (USAR O TIPO PAGE<>)
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//PAGINAÇÃO
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thomaskavi.dscommerce.dto.ProductDTO;
import com.thomaskavi.dscommerce.entities.Product;
import com.thomaskavi.dscommerce.repositories.ProductRepository;

@Service
public class ProductService {

  @Autowired
  private ProductRepository repository;

  @Transactional(readOnly = true) // READ ONLY POIS Ñ TEM ALTERAÇÕES NO BANCO
  public ProductDTO findById(Long id) {
    Product product = repository.findById(id).get(); // BUSCA NO BANCO DADO UM ID
    return new ProductDTO(product); // RETORNA CONVERTIDO PARA DTO
  }

  @Transactional(readOnly = true) // READ ONLY POIS Ñ TEM ALTERAÇÕES NO BANCO
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
    Product entity = repository.getReferenceById(id);
    copyDtoToEntity(dto, entity);
    entity = repository.save(entity);
    return new ProductDTO(entity);
  }

  private void copyDtoToEntity(ProductDTO dto, Product entity) {
    entity.setName(dto.getDescription());
    entity.setDescription(dto.getDescription());
    entity.setPrice(dto.getPrice());
    entity.setImgUrl(dto.getImgUrl());
  }
}
