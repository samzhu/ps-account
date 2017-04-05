package com.ps.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by samchu on 2017/2/9.
 */
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { UniqueUsernameValidator.class })
public @interface UniqueUsername {

    String message();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}