package com.sil.bejpa.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * FileName    : IntelliJ IDEA
 * Author      : Seowon
 * Date        : 2025-02-04
 * Description :
 */
public class YnCodeValidator implements ConstraintValidator<YnCode, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return switch (value) {
            case "Y" -> true;
            case "N" -> true;
            default -> false;
        };
    }
}
