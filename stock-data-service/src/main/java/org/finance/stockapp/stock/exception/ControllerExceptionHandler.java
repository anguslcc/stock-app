package org.finance.stockapp.stock.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@CrossOrigin
public class ControllerExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Void> handleNotFoundException(
      NotFoundException ex) {
    return buildErrorResponse(ex, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Void> handleGlobalException(
      Exception ex) {
    LOG.error("Application error in: [{}]", ex.getClass().getName(), ex);
    return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<Void> buildErrorResponse(
      Throwable ex, HttpStatus httpStatus) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-ADVICE", ex.getMessage());
    return ResponseEntity.status(httpStatus).headers(headers).build();
  }
}
