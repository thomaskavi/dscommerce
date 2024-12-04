package com.thomaskavi.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thomaskavi.dscommerce.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  

}
