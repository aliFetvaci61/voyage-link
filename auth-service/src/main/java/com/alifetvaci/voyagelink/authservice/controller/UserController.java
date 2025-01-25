package com.alifetvaci.voyagelink.authservice.controller;


import com.alifetvaci.voyagelink.authservice.api.BaseController;
import com.alifetvaci.voyagelink.authservice.controller.request.LoginRequest;
import com.alifetvaci.voyagelink.authservice.controller.request.RegisterRequest;
import com.alifetvaci.voyagelink.authservice.controller.response.BaseApiResponse;
import com.alifetvaci.voyagelink.authservice.controller.response.LoginResponse;
import com.alifetvaci.voyagelink.authservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController extends BaseController {

   private final UserService service;

    @PostMapping("api/v1/register")
    public BaseApiResponse<Void> registerUser(@RequestBody  @Valid RegisterRequest request) {
        service.register(request);
        return success(null);
    }

    @PostMapping("api/v1/login")
    public BaseApiResponse<LoginResponse> loginUser(@RequestBody  @Valid LoginRequest request) {
        return success(service.login(request));
    }

}
