package com.alifetvaci.voyagelink.authservice.controller.request;


import com.alifetvaci.voyagelink.authservice.api.validator.ValidIdentificationNumber;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank
    @ValidIdentificationNumber
    private String identificationNumber;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String password;
}
