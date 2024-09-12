package com.example.semanamoviles;

public class Contacto {
    private long id; // Cambio de int a long
    private String nombre;
    private String telefono;
    private String email;
    private String direccion;
    private String fechaNacimiento;

    public Contacto(long id, String nombre, String telefono, String email, String direccion, String fechaNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getters and Setters
    public long getId() { // Cambio de Contacto a long
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return nombre + " - " + telefono; // Customize as needed
    }
}
