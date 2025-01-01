package com.thomaskavi.dscommerce.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.thomaskavi.dscommerce.dto.ProductDTO;
import com.thomaskavi.dscommerce.dto.ProductMinDTO;
import com.thomaskavi.dscommerce.entities.Product;
import com.thomaskavi.dscommerce.repositories.ProductRepository;
import com.thomaskavi.dscommerce.services.exceptions.DatabaseException;
import com.thomaskavi.dscommerce.services.exceptions.ResourceNotFoundException;
import com.thomaskavi.dscommerce.tests.ProductFactory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

  @InjectMocks
  private ProductService service;

  @Mock
  private ProductRepository repository;

  private long existingId, nonExistingId, dependentId;
  private String productName;
  private Product product;
  private ProductDTO productDTO;
  private PageImpl<Product> page;

  @BeforeEach
  void setUp() throws Exception {
    existingId = 1L;
    nonExistingId = 1000L;
    dependentId = 3L;

    productName = "The Lord of the Rings";

    product = ProductFactory.createProduct(productName);
    productDTO = new ProductDTO(product);
    page = new PageImpl<>(List.of(product));

    Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
    Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

    Mockito.when(repository.searchByName(any(), (Pageable) any())).thenReturn(page);

    Mockito.when(repository.save(any())).thenReturn(product);

    Mockito.when(repository.getReferenceById(existingId)).thenReturn(product);
    Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

    Mockito.when(repository.existsById(existingId)).thenReturn(true);
    Mockito.when(repository.existsById(dependentId)).thenReturn(true);
    Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);

    Mockito.doNothing().when(repository).deleteById(existingId);
    Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

  }

  @Test
  public void findByIdShouldReturnProductDTOWhenIdExists() {

    ProductDTO result = service.findById(existingId);

    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.getId(), existingId);
    Assertions.assertEquals(result.getName(), product.getName());
  }

  @Test
  public void findByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist() {

    Assertions.assertThrows(ResourceNotFoundException.class, () -> {
      service.findById(nonExistingId);
    });
  }

  @Test
  public void findAllShouldReturnPagedProductMinDTO() {

    Pageable pageable = PageRequest.of(0, 12);

    Page<ProductMinDTO> result = service.findAll(productName, pageable);

    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.getSize(), 1);
    Assertions.assertEquals(result.iterator().next().getName(), productName);
  }

  @Test
  public void insertShouldReturnProductDTO() {

    ProductDTO result = service.insert(productDTO);

    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.getId(), product.getId());
    Assertions.assertEquals(result.getName(), product.getName());
  }

  @Test
  public void updateShouldUpdateProductDTOWhenIdExists() {

    ProductDTO result = service.update(existingId, productDTO);

    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.getId(), productDTO.getId());
    Assertions.assertEquals(result.getName(), productDTO.getName());
    Assertions.assertEquals(result.getPrice(), productDTO.getPrice());
  }

  @Test
  public void updateIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist() {

    Assertions.assertThrows(ResourceNotFoundException.class, () -> {
      service.update(nonExistingId, productDTO);
    });
  }

  @Test
  public void deleteShouldDoNothingWhenIdExists() {

    Assertions.assertDoesNotThrow(() -> {
      service.delete(existingId); // Chama o método do serviço
    });
  }

  @Test
  public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

    Assertions.assertThrows(ResourceNotFoundException.class, () -> {
      service.delete(nonExistingId); // Chama o método do serviço
    });
  }

  @Test
  public void deleteShouldThrowDatabaseExceptionWhenIntegrityViolationOccurs() {

    Assertions.assertThrows(DatabaseException.class, () -> {
      service.delete(dependentId); // Simula o erro de integridade
    });
  }

}
