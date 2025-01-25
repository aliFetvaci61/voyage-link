package com.alifetvaci.voyagelink.authservice.api;


import com.alifetvaci.voyagelink.authservice.controller.response.BaseApiResponse;

public class BaseController {

    protected <T> BaseApiResponse<T> success(T data) {
        var response = new BaseApiResponse<>(data);
        response.setSuccess(true);
        return response;
    }

}
