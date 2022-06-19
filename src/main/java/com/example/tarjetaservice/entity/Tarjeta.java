package com.example.tarjetaservice.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tarjetas")
public class Tarjeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtarjeta", nullable = false)
    private Integer id;

    @Column(name = "numero", nullable = false, length = 12)
    private String numero;

    @Column(name = "vencimiento", nullable = false)
    private LocalDate vencimiento;

    @Column(name = "verificacion", nullable = false)
    private Integer verificacion;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 45)
    private String apellidos;

    @Column(name = "correo", nullable = false, length = 45)
    private String correo;

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getVerificacion() {
        return verificacion;
    }

    public void setVerificacion(Integer verificacion) {
        this.verificacion = verificacion;
    }

    public LocalDate getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(LocalDate vencimiento) {
        this.vencimiento = vencimiento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}