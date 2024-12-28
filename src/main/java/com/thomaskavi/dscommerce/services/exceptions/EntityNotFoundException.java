package com.thomaskavi.dscommerce.services.exceptions;

public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(String msg) {
    super(msg);
  }
}