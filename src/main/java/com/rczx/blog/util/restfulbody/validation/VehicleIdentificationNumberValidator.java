package com.rczx.blog.util.restfulbody.validation;

import com.zm.system.utils.restfulbody.validation.VehicleIdentificationNumber;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VehicleIdentificationNumberValidator implements ConstraintValidator<VehicleIdentificationNumber, String> {
    public VehicleIdentificationNumberValidator() {
    }

    public void initialize(VehicleIdentificationNumber constraintAnnotation) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value != null && !Objects.equals(value, "")) {
            value = value.toUpperCase();
            return value.length() != 17?false:(!value.contains("I") && !value.contains("O") && !value.contains("Q")?this.getCheckDigit(value) == value.charAt(8):false);
        } else {
            return true;
        }
    }

    private int transliterate(char c) {
        return "0123456789.ABCDEFGH..JKLMN.P.R..STUVWXYZ".indexOf(c) % 10;
    }

    private char getCheckDigit(String vin) {
        String map = "0123456789X";
        String weights = "8765432X098765432";
        int sum = 0;

        for(int i = 0; i < 17; ++i) {
            sum += this.transliterate(vin.charAt(i)) * map.indexOf(weights.charAt(i));
        }

        return map.charAt(sum % 11);
    }
}
