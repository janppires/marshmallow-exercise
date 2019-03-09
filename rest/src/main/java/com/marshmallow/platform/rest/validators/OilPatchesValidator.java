package com.marshmallow.platform.rest.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class OilPatchesValidator implements ConstraintValidator<OilPatches, List<List<Integer>>> {
    @Override
    public boolean isValid(List<List<Integer>> oilPatchesCoordinates, ConstraintValidatorContext context) {
        List<String> errors = new ArrayList<>();
        if(oilPatchesCoordinates == null) {
            errors.add("Oil patches cannot be null");
        } else {
            for (List<Integer> oilPatchesCoordinate : oilPatchesCoordinates) {
                if (oilPatchesCoordinate.size() != 2) {
                    errors.add("Oil patches coordinates must be in the format x,y");
                }
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
