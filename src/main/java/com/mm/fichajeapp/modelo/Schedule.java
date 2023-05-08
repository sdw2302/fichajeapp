package com.mm.fichajeapp.modelo;

public class Schedule {
    private int id;
    private String hora_inicio;
    private String hora_final;
    private String descanso;

    public Schedule(int id, String hora_inicio, String hora_final, String descanso) {
        this.id = id;
        this.hora_inicio = hora_inicio;
        this.hora_final = hora_final;
        this.descanso = descanso;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHora_inicio() {
        return this.hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_final() {
        return this.hora_final;
    }

    public void setHora_final(String hora_final) {
        this.hora_final = hora_final;
    }

    public String getDescanso() {
        return this.descanso;
    }

    public void setDescanso(String descanso) {
        this.descanso = descanso;
    }

}
