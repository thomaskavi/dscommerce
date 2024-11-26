package com.thomaskavi.dscommerce.services.exceptions;

public class MethodArgumentNotValidException extends RuntimeException {

  MethodArgumentNotValidException(String msg) {
    super(msg);
  }
}
