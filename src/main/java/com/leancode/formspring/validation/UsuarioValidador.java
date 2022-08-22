// Este paquete contiene las validaciones mas personalizadas y mas complejas de desarrollar de cualquier
// clase POJO.
package com.leancode.formspring.validation;

import com.leancode.formspring.models.domain.Usuario;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

// Anotamos como component para inyectarlo desde la interfaz Validator:
@Component
// Y de esta otra manera, podemos manejar las validaciones de los campos en un POJO que se alimenta desde
// un formulario.
public class UsuarioValidador implements Validator{

    @Override
    // El metodo supports de la interfaz recibe como argumento un objeto de alguna clase cualquiera. El
    // argumento es de tipo Class y esta clase recibe un generico del tipo T que hace referencia al tipo
    // de la clase que recibe como argumento, por ejemplo:
    // Class<?> clazz (donde el argumento es de tipo String): Class<String> clazz.
    public boolean supports(Class<?> clazz) {
        // Comprueba si el objeto de la clase, pasado como argumento, es de tipo Usuario.
        return Usuario.class.isAssignableFrom(clazz);
    }

    @Override
    // Esta funcion recibe el objeto de la clase a validar:
    public void validate(Object target, Errors errors) {

        // POR AHORA ESTA SERIA LA UNICA MANERA DE EVITAR QUE ALGUIEN RELLENE LOS CAMPOS CON PUROS ESPACIOS VACIOS

        // Encontre otra (para el campo password):
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "whiteSpaces.usuario.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "whiteSpaces.usuario.username");

        // <========== Una manera de generar los errores: =============>
        // (Desactivado momentaneamente porque este error ya se carga desde el messages.properties)
        /* ValidationUtils.rejectIfEmpty(errors, "username", "NotEmpty.usuario.username"); */

        // Es necesario hacer un downcast (porque el compilador no nos marcara los atributos y/o metodos
        // de la clase Usuario):
        
        // <========== Otra mucho mas especifica frente a comportamientos: ===============>
        // ES CASI NECESARIO USAR ESTO PARA VALIDAR SI ALGUN IDIOTA INTENTA COLOCAR VARIOS ESPACIOS EN BLANCO
        // (aunque podemos optar por usar los RequestParam y hacer un trim a los campos antes de ser procesados): 


        // Para mi esta seria la mejor manera:
        Usuario usuario = (Usuario) target;
        if (!usuario.getApellidos().isEmpty()) {
            if (usuario.getApellidos().trim().isEmpty()) {
                errors.rejectValue("apellidos", "whiteSpaces.usuario.apellidos");
            }
        }
        
    }
    
}
