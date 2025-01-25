package com.alifetvaci.travelmanagementservice.api;

public class BaseController {

    protected <T> BaseApiResponse<T> success(T data) {
        var response = new BaseApiResponse<>(data);
        response.setSuccess(true);
        return response;
    }

}
