package org.finance.stockapp.stock.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@CrossOrigin
public class ControllerExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

  @ExceptionHandler({NotFoundException.class, NoResourceFoundException.class})
  public ResponseEntity<Void> handleNotFoundException(
      Exception ex) {
    return buildErrorResponse(ex, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Void> handleAccessDeniedException(
      Exception ex) {
    return buildErrorResponse(ex, HttpStatus.FORBIDDEN);
  }


  @ExceptionHandler(Exception.class)
  public ResponseEntity<Void> handleGlobalException(
      Exception ex) {
    LOG.error("Application error in: [{}]", ex.getClass().getName(), ex);
    return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<Void> buildErrorResponse(
      Exception ex, HttpStatus httpStatus) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-ADVICE", ex.getMessage());
    return ResponseEntity.status(httpStatus).headers(headers).build();
  }
}
