package com.leancode.formspring.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = IdentificadorRegexValidador.class) // El atributo class sirve para recopilar datos de una clase en tiempo de ejecucion.
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface IdentificadorRegex {

    String message() default "Identificador inválido";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
    
}
