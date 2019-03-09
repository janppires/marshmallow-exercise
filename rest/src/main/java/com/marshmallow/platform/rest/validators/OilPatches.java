package com.marshmallow.platform.rest.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = OilPatchesValidator.class)
@Documented
public @interface OilPatches {

  String message() default "{OilPatches.invalid}";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

}