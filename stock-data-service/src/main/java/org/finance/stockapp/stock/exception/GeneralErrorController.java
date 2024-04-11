package org.finance.stockapp.stock.exception;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class GeneralErrorController implements ErrorController {

  @RequestMapping("/error")
  public ResponseEntity<Void> handleError(HttpServletRequest request) {
    String statusCode = String.valueOf(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
    HttpStatus httpStatus = HttpStatus.valueOf(Integer.parseInt(statusCode));
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-ADVICE", "API Endpoint not found");

    return ResponseEntity.status(httpStatus).headers(headers).build();
  }
}
