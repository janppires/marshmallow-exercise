package com.marshmallow.platform.rest.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class NavigationInstructionsValidator implements ConstraintValidator<NavigationInstructions, String> {
    @Override
    public boolean isValid(String navigationInstructions, ConstraintValidatorContext context) {
        List<String> errors = new ArrayList<>();
        if(navigationInstructions == null || navigationInstructions.isEmpty()) {
            errors.add("Navigation instructions cannot be blank");
        } else {
            if (!navigationInstructions.matches("^[NESW]+$")) {
                errors.add("Navigation instructions only allows: N, E, S, W");
            }
        }

        if(!errors.isEmpty()) {
            addMessages(context, errors);
            return false;
        }

        return true;
    }

    private void addMessages(ConstraintValidatorContext context, List<String> messages) {
        context.disableDefaultConstraintViolation();
        for (String message : messages) {
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        }
    }
}
