package com.bol.kalah.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.bol.kalah.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class KalahGameControllerAdvice {

  /**
   * Method to handle MethodArgumentNotValidException
   * @param ex
   * @return ErrorResponse
   */
  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    log.error("MethodArgumentNotValidException: {}", ex.getMessage());
    return new ErrorResponse("Request is Invalid");
  }

  /**
   * Method to handle KalahRuntimeException
   * @param ex
   * @return ErrorResponse
   */
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler(KalahRuntimeException.class)
  @ResponseBody
  public ErrorResponse handleKalahRuntimeException(Exception ex) {
    log.error("handleKalahRuntimeException: {}", ex.getMessage());
    return new ErrorResponse(ex.getMessage());
  }

}
