package com.thomaskavi.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thomaskavi.dscommerce.entities.User;
import com.thomaskavi.dscommerce.services.exceptions.ForbiddenException;

@Service
public class AuthService {

  @Autowired
  private UserService userService;

  public void validateSelfOrAdmin(long userId) {
    User me = userService.authenticated();

    // if (!me.hasRole("ROLE_ADMIN") && !me.getId().equals(userId)) {
    // throw new ForbiddenException("Acceso negado");
    // }

    if (me.hasRole("ROLE_ADMIN")) {
      return;
    }
    if (!me.getId().equals(userId)) {
      throw new ForbiddenException("Acesso negado, o usuário só pode acessar seus próprios pedidos");
    }
  }
}
