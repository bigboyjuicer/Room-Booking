package api.util.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDateTime;

public class FutureOrPresentValidator implements ConstraintValidator<FutureOrPresent, LocalDateTime> {
    @Override
    public void initialize(FutureOrPresent constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {
        if (localDateTime == null) {
            return true;
        }

        return localDateTime.isAfter(LocalDateTime.now()) || localDateTime.isEqual(LocalDateTime.now());
    }
}
