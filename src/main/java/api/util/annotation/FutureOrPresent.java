package api.util.annotation;

import api.util.validator.FutureOrPresentValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FutureOrPresentValidator.class)
public @interface FutureOrPresent {
    String message() default "Date and time must be in the future";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
