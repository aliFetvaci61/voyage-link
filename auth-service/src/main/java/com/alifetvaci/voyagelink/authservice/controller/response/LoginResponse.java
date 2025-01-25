package com.alifetvaci.voyagelink.authservice.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private String token;
}
