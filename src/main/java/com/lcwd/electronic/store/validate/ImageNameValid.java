package com.lcwd.electronic.store.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImageNameValidator.class)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageNameValid {
    String message() default "{Invalid image name!!}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
