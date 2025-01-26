package com.alifetvaci.voyagelink.authservice.api.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class ValidIdentificationNumberValidator implements ConstraintValidator<ValidIdentificationNumber, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return false;
        }
        return verifyNumber(value);
    }

    private static boolean verifyNumber(String nationalityNumberStr) {
        if(nationalityNumberStr.startsWith("0") || !nationalityNumberStr.matches("[0-9]{11}") ){
            return false;
        }

        int [] temp = new int [11];
        for (int i = 0; i < 11; i++){
            temp[i] = Character.getNumericValue(nationalityNumberStr.toCharArray()[i]);
        }

        int c1 = 0;
        int c2 = temp[9];
        for(int j = 0; j < 9; j++){
            if(j%2 == 0) {
                c1 += temp[j] * 7;
            }
            else {
                c1 -=  temp[j];
            }
            c2 += temp[j];
        }

        return  temp[9]== c1 % 10 && temp[10] == c2 %10;
    }
}
