package com.sil.bejpa.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 등록 수정시 밸리데이션
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {YnCodeValidator.class})
@Documented
public @interface YnCode {

	String message() default "{validation.ynCode}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}