package com.hotelJava.common.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ApiResponse<T> {

  private static final String SUCCESS_STATUS = "success";
  private static final String FAIL_STATUS = "fail";
  private static final String ERROR_STATUS = "error";

  private String responseMessage;
  private T data;

  public ApiResponse(String responseMessage) {
    this.responseMessage = responseMessage;
  }

  public static <T> ApiResponse<T> success() {
    return new ApiResponse<>(SUCCESS_STATUS);
  }

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>(SUCCESS_STATUS, data);
  }

  public static <T> ApiResponse<T> fail(T data) {
    return new ApiResponse<>(FAIL_STATUS, data);
  }

  public static <T> ApiResponse<T> error(T data) {
    return new ApiResponse<>(ERROR_STATUS, data);
  }
}
