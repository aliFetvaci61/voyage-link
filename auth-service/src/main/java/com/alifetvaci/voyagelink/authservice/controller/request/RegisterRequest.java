package com.alifetvaci.voyagelink.authservice.controller.request;


import com.alifetvaci.voyagelink.authservice.api.validator.ValidName;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank
    //@ValidIdentificationNumber
    private String identificationNumber;

    @NotBlank
    @ValidName
    private String firstName;

    @NotBlank
    @ValidName
    private String lastName;

    @NotBlank
    private String password;
}
