package api.util.annotation;

import api.util.IsActiveValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {IsActiveValidator.class})
@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsActiveDependent {

    String message() default "Must be NotNull when active is true";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String isActiveField() default "isActive";
}
