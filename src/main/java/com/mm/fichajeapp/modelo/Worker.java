package com.mm.fichajeapp.modelo;

import java.time.LocalDate;

public class Worker {
    private String dni_trabajador;
    private String nombre_trabajador;
    private String apellido_trabajador;
    private LocalDate fecha_nacimiento;
    private Double horas_fichadas_trabajador;
    private String empresa_responsable;

    public Worker(String dni_trabajador, String nombre_trabajador, String apellido_trabajador,
            Double horas_fichadas_trabajador) {
        this.dni_trabajador = dni_trabajador;
        this.nombre_trabajador = nombre_trabajador;
        this.apellido_trabajador = apellido_trabajador;
        this.horas_fichadas_trabajador = horas_fichadas_trabajador;
    }

    public Worker(String dni_trabajador, String nombre_trabajador, String apellido_trabajador, LocalDate fecha_nacimiento, String empresa_responsable) {
        this.dni_trabajador = dni_trabajador;
        this.nombre_trabajador = nombre_trabajador;
        this.apellido_trabajador = apellido_trabajador;
        this.fecha_nacimiento = fecha_nacimiento;
        this.empresa_responsable = empresa_responsable;
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


    public LocalDate getFecha_nacimiento() {
        return this.fecha_nacimiento;
    }

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getEmpresa_responsable() {
        return this.empresa_responsable;
    }

    public void setEmpresa_responsable(String empresa_responsable) {
        this.empresa_responsable = empresa_responsable;
    }


}
