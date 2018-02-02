package com.dam.proyecto.prct1broadcastreceiver;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.util.Date;

/**
 * Created by fran on 31/01/18.
 */

public class LlamadaInfo {

    private int id;
    private String fecha;
    private String tipo;
    private String numTlf;

    public LlamadaInfo() {
    }

    public LlamadaInfo(int id, String fecha, String tipo, String numTlf) {
        this.id = id;
        this.fecha = fecha;
        this.tipo = tipo;
        this.numTlf = numTlf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumTlf() {
        return numTlf;
    }

    public void setNumTlf(String numTlf) {
        this.numTlf = numTlf;
    }

    @Override
    public String toString() {
        return "LlamadaInfo{" +
                "id=" + id +
                ", fecha='" + fecha + '\'' +
                ", tipo='" + tipo + '\'' +
                ", numTlf='" + numTlf + '\'' +
                '}';
    }
}
