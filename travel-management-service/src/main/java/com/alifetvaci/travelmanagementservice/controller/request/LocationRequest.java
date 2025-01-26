package com.alifetvaci.travelmanagementservice.controller.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class LocationRequest {

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @NotBlank(message = "Country cannot be blank")
    @Pattern(regexp = "^[a-zA-ZçÇıİğĞöÖşŞüÜ ]+$", message = "Country must only contain letters, spaces, and Turkish characters")
    private String country;

    @NotBlank(message = "City cannot be blank")
    @Pattern(regexp = "^[a-zA-ZçÇıİğĞöÖşŞüÜ ]+$", message = "City must only contain letters, spaces, and Turkish characters")
    private String city;

    @NotBlank(message = "Location code cannot be blank")
    @Pattern(regexp = "^[A-Z]{3,}$", message = "Location code must contain at least 3 uppercase letters and only uppercase letters")
    private String locationCode;
}
