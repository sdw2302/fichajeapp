package com.mm.fichajeapp.modelo;

public class Worker {
    private String nombre_trabajador;
    private String apellido_trabajador;
    private String dni_trabajador;
    private Double horas_fichadas_trabajador;

    public Worker(String nombre_trabajador, String apellido_trabajador, String dni_trabajador,
            Double horas_fichadas_trabajador) {
        this.nombre_trabajador = nombre_trabajador;
        this.apellido_trabajador = apellido_trabajador;
        this.dni_trabajador = dni_trabajador;
        this.horas_fichadas_trabajador = horas_fichadas_trabajador;
    }

    public String getNombre_trabajador() {
        return this.nombre_trabajador;
    }

    public void setNombre_trabajador(String nombre_trabajador) {
        this.nombre_trabajador = nombre_trabajador;
    }

    public String getApellido_trabajador() {
        return this.apellido_trabajador;
    }

    public void setApellido_trabajador(String apellido_trabajador) {
        this.apellido_trabajador = apellido_trabajador;
    }

    public String getDni_trabajador() {
        return this.dni_trabajador;
    }

    public void setDni_trabajador(String dni_trabajador) {
        this.dni_trabajador = dni_trabajador;
    }

    public Double getHoras_fichadas_trabajador() {
        return this.horas_fichadas_trabajador;
    }

    public void setHoras_fichadas_trabajador(Double horas_fichadas_trabajador) {
        this.horas_fichadas_trabajador = horas_fichadas_trabajador;
    }

}
