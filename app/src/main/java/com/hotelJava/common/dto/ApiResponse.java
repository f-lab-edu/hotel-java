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

    public static <T2> ApiResponse<T2> success(T2 data) {
        return new ApiResponse<>(SUCCESS_STATUS, data);
    }
}
