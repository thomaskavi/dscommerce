package com.thomaskavi.dscommerce.dto;

import com.thomaskavi.dscommerce.entities.Product;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class ProductDTO {

  private Long id;

  private String name;

  private String description;

  private Double price;

  private String imgUrl;

  public ProductDTO(Product entity) {
    id = entity.getId();
    name = entity.getName();
    description = entity.getDescription();
    price = entity.getPrice();
    imgUrl = entity.getImgUrl();
  }

}
