package api.util.validator;

import api.util.annotation.IsActiveDependent;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

public class IsActiveValidator implements ConstraintValidator<IsActiveDependent, Object> {

    private String isActiveField;

    @Override
    public void initialize(IsActiveDependent constraintAnnotation) {
        this.isActiveField = constraintAnnotation.isActiveField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if(value == null) {
            return true;
        }

        try {
            Field isActive = value.getClass().getDeclaredField(isActiveField);
            isActive.setAccessible(true);

            boolean isActiveValue = (boolean) isActive.get(value);

            if(isActiveValue && value == null) {
                return false;
            }
        } catch (NoSuchFieldException | IllegalAccessException ignored) {

        }

        return true;
    }
}
