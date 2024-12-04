package com.thomaskavi.dscommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
//PAGINAÇÃO
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thomaskavi.dscommerce.dto.CategoryDTO;
import com.thomaskavi.dscommerce.entities.Category;
import com.thomaskavi.dscommerce.repositories.CategoryRepository;

@Service
public class CategoryService {

  @Autowired
  private CategoryRepository repository;

  @Transactional(readOnly = true)
  public List<CategoryDTO> findAll() {
    List<Category> result = repository.findAll();
    return result.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
  }

}
