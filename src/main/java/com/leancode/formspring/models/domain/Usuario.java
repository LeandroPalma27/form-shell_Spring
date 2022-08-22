package com.leancode.formspring.models.domain;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.leancode.formspring.validation.IdentificadorRegex;

import org.springframework.format.annotation.DateTimeFormat;

public class Usuario {
    
    private String dni;

    // Los campos deben ser iguales que el th:field o que el name de los input para que los POJO
    // se puedan aprovechar mas sin tener la necesidad de escribir codigo en vano.
    /* @NotEmpty */
    @Size(min = 4, max = 40) // <= Valida que el campo no exceda los 40 caracteres y como minimo tenga 4.
    private String username;

    @NotBlank(message = "No ingrese espacios en blanco, eso es mala fe.")
    private String nombre;
    
    // Valida que no sea vacio o que tenga solo espacios en blanco:
    @NotEmpty(message = "No debe estar vacio")
    private String apellidos;

    // Para clases que no sean de tipo String, debemos usar @NotNull
    @NotNull(message = "No debe estar vacio")
    @Min(value = 18, message = "Debe ser mayor de edad")
    @Max(200)
    private Integer edad;

    // TODO: ¿Como obtener la fecha actual sin necesidad e ingresarla?
    // TODO: Verificar como se toma la fecha, ya que detecta por hora a veces como una fecha pasada cuando es una fecha actual (en mi zona horaria).

    // Si queremos que solo se acepten fechas pasadas:
    /* @Past */
    // Si queremos que solo se acepten fechas pasadas o presentes:
    /* @PastOrPresent */
    // Si queremos fechas futuras:
    /* @Future */
    // Si queremos fechas futuras o presentes:
    /* @FutureOrPresent */
    
    /* private static Instant instant; */

    // Importante: Usar java.util
    @NotNull(message = "No debe estar vacio")
    // Debemos usar este formateo para no tener problemas en el reenvio de formularios:
    // Si usamos un input de tipo date debemos colocar este patron:
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;

    private LocalDateTime fechaActual;

    // Si queremos personalizar los mensajes aparte del uso de BindingResult y el mapeo del objeto enviado,
    // podemos incluir el atributo message en las anotaciones de validacion:
    @NotEmpty(message = "El campo no debe estar vacio.")
    @Email(message = "El formato debe ser de tipo email.")
    private String email;

    @NotEmpty(message = "El campo es requerido xd")
    private String password;

    @Size(max = 20)
    @Pattern(regexp = "[0-9]{3}[-][\\d]{3}[-][\\d]{3}[-][a-zA-Z]{1}", message = "Ingrese un codigo válido.") // 255-255-255-A
    @IdentificadorRegex
    private String codigoDescuento;

    @NotEmpty(message = "Debe seleccionar alguna opcion.")
    private String pais;

    // Requerimos que lleve esta anotacion para que se pueda validar el objeto de esta CLASE:
    @Valid
    private Pais paisP;

    @NotNull(message = "Escoga una opcion.")
    private Pais paisPv2;

    // Para listas tambien ocupamos el uso de @NotEmpty:
    @NotEmpty(message = "Escoga una opcion.")
    private List<String> roles;

    @NotEmpty(message = "Escoga una opcion.")
    private List<Role> role;

    // No se valida porque siempre tendra un valor por defecto
    private Boolean recibeNotificaciones = false;

    @NotBlank(message = "Seleccione una opcion.")
    private String genero;

    private String valorSecreto;

    public Usuario() {
    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Usuario(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCodigoDescuento() {
        return codigoDescuento;
    }

    public void setCodigoDescuento(String codigoDescuento) {
        this.codigoDescuento = codigoDescuento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDateTime getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(LocalDateTime fechaActual) {
        this.fechaActual = fechaActual;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Pais getPaisP() {
        return paisP;
    }

    public void setPaisP(Pais paisP) {
        this.paisP = paisP;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }

    public Pais getPaisPv2() {
        return paisPv2;
    }

    public void setPaisPv2(Pais paisPv2) {
        this.paisPv2 = paisPv2;
    }

    public Boolean getRecibeNotificaciones() {
        return recibeNotificaciones;
    }

    public void setRecibeNotificaciones(Boolean recibeNotificaciones) {
        this.recibeNotificaciones = recibeNotificaciones;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getValorSecreto() {
        return valorSecreto;
    }

    public void setValorSecreto(String valorSecreto) {
        this.valorSecreto = valorSecreto;
    }

}
