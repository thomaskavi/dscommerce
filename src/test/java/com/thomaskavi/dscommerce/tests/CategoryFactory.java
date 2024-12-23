package com.thomaskavi.dscommerce.tests;

import com.thomaskavi.dscommerce.entities.Category;

public class CategoryFactory {

  public static Category createCategory() {
    return new Category(1L, "Livros");
  }

  public static Category createCategory(Long id, String name) {
    return new Category(id, name);
  }
}
