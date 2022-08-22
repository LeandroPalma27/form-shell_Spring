package com.leancode.formspring.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.Validator;

import com.leancode.formspring.editors.PaisPropertyEditor;
import com.leancode.formspring.editors.RolePropertyEditor;
import com.leancode.formspring.models.domain.Pais;
import com.leancode.formspring.models.domain.Role;
import com.leancode.formspring.models.domain.Usuario;
import com.leancode.formspring.services.interfaces.PaisService;
import com.leancode.formspring.services.interfaces.RoleService;
import com.leancode.formspring.validation.UsuarioValidador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;


@Controller
// Para poder persistir un objeto y su informacion en una sesion que valide un formulario, usamos:
// Se usa para manejar id's en formularios, entre otros.
@SessionAttributes({"usuario"}) // <= colocamos el nombre del objeto de la entidad en los endpoint
                                //    con el mismo nombre de las variables de los POJO o los objetos
                                //    pasados al modelo (mismo nombre que la key del map).
@RequestMapping("/form")
public class FormController {
    
    @Autowired
    private UsuarioValidador usuarioValidador;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PaisService paisService;

    @Autowired
    private PaisPropertyEditor paisPropertyEditor;

    @Autowired
    private RolePropertyEditor rolePropertyEditor;

    // Para evitar usar la linea "usuarioValidador.validate(usuario, result);":
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(usuarioValidador);

        // Asi se registra un custom editor en el initBinder:
        webDataBinder.registerCustomEditor(Pais.class, "paisPv2", paisPropertyEditor);
        webDataBinder.registerCustomEditor(Role.class, "role", rolePropertyEditor);
    }

    // Este metodo carga la vista del formulario a llenar:
    @GetMapping({"", "/"})
    public String Form(Model model) {
        // Para no generar un error en la primera carga de la vista del formulario (ya que se trata de
        // persistir los datos de los campos del formulario desde la vista):
        Usuario usuario = new Usuario();
        /* usuario.setUsername("leandro27"); */
        // Para enviar un dato por defecto a la vista:
        usuario.setDni("77276954");
        
        model.addAttribute("usuario", usuario);
        return "form";
    }

    @GetMapping({"/form2"})
    public String Form2(Model model) {
        // Para no generar un error en la primera carga de la vista del formulario (ya que se trata de
        // persistir los datos de los campos del formulario desde la vista):
        Usuario usuario = new Usuario();
        /* usuario.setUsername("leandro27"); */
        // Para enviar un dato por defecto a la vista:
        usuario.setDni("77276954");
        usuario.setFechaActual(LocalDateTime.now());
        usuario.setValorSecreto("valorSecreto");
        // Para pasar un dato ya seleccionado (visto en operaciones de actualizar):
        // Requiere un toString en el POJO
        usuario.setPaisPv2(new Pais(2, "CHI", "Chile"));
        // De esta manera y sobrescribiendo un metodo que compare que alguno de los roles 
        // sea igual al rol ya previamente pasado, comparandose por el id:
        usuario.setRole(Arrays.asList(new Role(2, "ROLE_USER", "Usuario")));
        model.addAttribute("usuario", usuario);
        return "formv2";
    }

    // Controllador con un model global para cualquier otro controlador:
    @ModelAttribute("paises") // <= esto hace que este modelAttribute se carge a todas las vistas
    public List<String> obtenerPaises() {
        List<String> list = Arrays.asList("Peru", "Colombia", "Chile", "Bolivia", "Paraguay");
        return list;
    }

    @ModelAttribute("listaPaisesObj")
    public List<Pais> listaPaisesObj() {
        return paisService.listar();
    }

    @ModelAttribute("listaRolesString")
    public List<String> listaPaisesString() {
        // Asi se maneja la nomenclatura en spring security:
        return Arrays.asList("ROLE_ADMIN", "ROLE_USER", "ROLE_TEACH", "ROLE_AUX");
    }

    @ModelAttribute("listaRoles")
    public List<Role> listaRoles() {
        return roleService.listar();
    }

    @ModelAttribute("listaPaises")
    public List<Pais> listarPaises() {
        return Arrays.asList(new Pais(1, "BOL", "Bolivia"),
                             new Pais(2, "PER", "Peru"), 
                             new Pais(3, "CHI", "Chile"), 
                             new Pais(4, "MEX", "Mexico"));
    }

    // Y este recibe los campos llenos, procesa los datos a traves de un service y da como respuesta
    // la redireccion a otra vista:
    @PostMapping({"/process"})
    public String process(Model model,
                         @RequestParam(name = "username") String username, 
                         @RequestParam(name = "password") String password,
                         @RequestParam(name = "email") String email) {
        @Valid Usuario usuario = new Usuario(username, email, password);
        model.addAttribute("usuario", usuario);
        return "login";
    }

    // Se puede manejar un codigo mas limpio para los accioner del formulario, trabajando con POJO's:
    @PostMapping({"/process2"})
    public String process2(Usuario usuario, Model model) {
        model.addAttribute("usuario", usuario);
        return "login";
        // Todo esto siempre y cuando los name de cada input sean iguales a los nombres de los atributos
        // de la clase POJO. 
    }

    // USANDO VALIDACION POR DEBAJO:
    @PostMapping("/login") // Podemos customizar la key del objeto que se incluira en el model:
    public String process3(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model, SessionStatus status) {
        if(result.hasErrors()) {
            // ESTOS ERRORES SOLO SE CARGAN DESDE EL OBJETO ERRORS PASADO POR EL MODEL
            // (los otros errores que esten fuera de este contexto seran los message o los del properties)

            Map<String, String> errors = new HashMap<String, String>();
            /* result.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), "".concat(err.getField() + " ").concat(err.getDefaultMessage()));
            }); */
            for (int i = 0; i < result.getFieldErrors().size(); i++) {
                var res = result.getFieldErrors().get(i).getField();   
                if (res.equals("email") || res.equals("password")) {
                    errors.put(result.getFieldErrors().get(i).getField(), result.getFieldErrors().get(i).getDefaultMessage());
                } else if (res.equals("codigoDescuento")) {
                    errors.put(result.getFieldErrors().get(i).getField(), "Su codigo esta caducado o no es válido.");
                } else {
                    errors.put(result.getFieldErrors().get(i).getField(), "El campo " + result.getFieldErrors().get(i).getDefaultMessage());
                }
            }
            model.addAttribute("errores", errors); // Enviamos solo la coleccion de los mensajes de error.
            return "form";
        } else {
            // Al usar valid, por debajo se hace esto: "model.addAttribute("usuario", usuario)"
            // Limpia el objeto de la sessionAttributes:
            status.setComplete();
            return "login";
        }
    }

    @PostMapping("/form2/login2")
    public String process4(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model, SessionStatus status) {
        /* usuarioValidador.validate(usuario, result); */
        if (result.hasErrors()) {
            // SALE NULL PORQUE YA NO SE REGISTRAN ESOS ERRORES EN LA LISTA (debido al validador).
            // Entonces no tiene mucho sentido manejar el result asi, todo debe de hacerse con el validador
            /* Map<String, String> errors = new HashMap<String, String>();
            result.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), "".concat(err.getField() + " ").concat(err.getDefaultMessage()));
            });
            for (int i = 0; i < result.getFieldErrors().size(); i++) {
                var res = result.getFieldErrors().get(i).getField();   
                if (res.equals("email") || res.equals("password")) {
                    errors.put(result.getFieldErrors().get(i).getField(), result.getFieldErrors().get(i).getDefaultMessage());
                } else if (res.equals("role") || res.equals("roles") ) {
                    System.out.println(result.getFieldErrors().get(i).getDefaultMessage());
                    errors.put(result.getFieldErrors().get(i).getField(), result.getFieldErrors().get(i).getDefaultMessage());
                } else if (res.equals("pais")) {
                    errors.put(result.getFieldErrors().get(i).getField(), result.getFieldErrors().get(i).getDefaultMessage());
                } else {
                    errors.put(result.getFieldErrors().get(i).getField(), "El campo " + result.getFieldErrors().get(i).getDefaultMessage());
                }
            }
            model.addAttribute("errores", errors); */
            return "formv2";

        } else {
            // Esto se hace cuando ya se terminado por completo algo:
            return "redirect:/form/form2/ver";
        }
    }

    // ES NECESARIO CARGAR UNA VISTA QUE NO PROCESE LA INFORMACION YA QUE SI USAMOS EL ENDPOINT QUE 
    // PROCESA INFORMACION, AL RECARGAR ESTAREMOS HACIENDO UN INSERT.
    @GetMapping("/form2/ver")
    // Indicamos que deseamos obtener el atributo usuario de los session atributes:
    // Tiene un efecto similar al de modelAttribute:
    public String ver(@SessionAttribute(name="usuario", required=false) Usuario usuario, Model model, SessionStatus status) {
        if (usuario == null) {
            return "redirect:/form/form2";
        }
        status.setComplete();
        return "login";
    }
    
}
