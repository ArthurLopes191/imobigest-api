package com.pds.ImobiGest.exceptions;

import org.springframework.http.HttpStatus;

public class RegraDeNegocioException extends Exception {
  private final HttpStatus status;

  public RegraDeNegocioException(String mensagem, HttpStatus status) {
    super(mensagem);
    this.status = status;
  }

  public RegraDeNegocioException(String mensagem) {
    this(mensagem, HttpStatus.BAD_REQUEST); // Default para BAD_REQUEST
  }

  public HttpStatus getStatus() {
    return status;
  }
}