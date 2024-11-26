package com.thomaskavi.dscommerce.dto;

import com.thomaskavi.dscommerce.entities.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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

  @Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 carácteres")
  @NotBlank(message = "Nome é obrigatório")
  private String name;

  @Size(min = 10, message = "Descrição deve conter no mínimo 10 carácteres")
  @NotBlank
  private String description;

  @Positive(message = "O preço deve ser um número positivo")
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
