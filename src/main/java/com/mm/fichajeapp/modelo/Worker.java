package com.mm.fichajeapp.modelo;

import java.time.LocalDate;

public class Worker {
    private int id_trabajador;
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

    public Worker(String dni_trabajador, String nombre_trabajador, String apellido_trabajador,
            LocalDate fecha_nacimiento, String empresa_responsable) {
        this.dni_trabajador = dni_trabajador;
        this.nombre_trabajador = nombre_trabajador;
        this.apellido_trabajador = apellido_trabajador;
        this.fecha_nacimiento = fecha_nacimiento;
        this.empresa_responsable = empresa_responsable;
    }


    public Worker(int id_trabajador, String dni_trabajador, String nombre_trabajador, String apellido_trabajador, LocalDate fecha_nacimiento, Double horas_fichadas_trabajador, String empresa_responsable) {
        this.id_trabajador = id_trabajador;
        this.dni_trabajador = dni_trabajador;
        this.nombre_trabajador = nombre_trabajador;
        this.apellido_trabajador = apellido_trabajador;
        this.fecha_nacimiento = fecha_nacimiento;
        this.horas_fichadas_trabajador = horas_fichadas_trabajador;
        this.empresa_responsable = empresa_responsable;
    }


    public String getNombre_trabajador() {
        return this.nombre_trabajador;
    }

    public String getApellido_trabajador() {
        return this.apellido_trabajador;
    }

    public String getDni_trabajador() {
        return this.dni_trabajador;
    }

    public Double getHoras_fichadas_trabajador() {
        return this.horas_fichadas_trabajador;
    }

    public LocalDate getFecha_nacimiento() {
        return this.fecha_nacimiento;
    }

    public String getEmpresa_responsable() {
        return this.empresa_responsable;
    }

    public void setEmpresa_responsable(String empresa_responsable) {
        this.empresa_responsable = empresa_responsable;
    }


    public int getId_trabajador() {
        return this.id_trabajador;
    }

    public void setId_trabajador(int id_trabajador) {
        this.id_trabajador = id_trabajador;
    }

}
