/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adjmogollon.app.controller;

import com.adjmogollon.app.opt.OtpManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.Singleton;
import javax.ejb.Stateless;

/**
 *
 * @author anibal.mogollon
 */
@Singleton
@Stateless
public class AfiliacionController {

    private String cedula;
    private String nombre;
    private String correo;
    private String transaccion;
    private String codigoTotp;
    private String secretoTotp;
    private boolean codigoValido;
    private boolean success;
    private String message;

    public AfiliacionController() {

    }

    public AfiliacionController(String cedula) {
        this.cedula = cedula;
    }

    public AfiliacionController(String cedula, String codigoTotp) {
        this.cedula = cedula;
        this.codigoTotp = codigoTotp;
    }

    public AfiliacionController(String cedula, String codigoTotp, String transaccion) {
        this.cedula = cedula;
        this.codigoTotp = codigoTotp;
        this.transaccion = transaccion;
    }

    public void afiliar() {
        OtpManager optManager = new OtpManager();
        String secreto = optManager.generarSecreto(this.cedula);
        if (this.transaccion == null || this.transaccion.isEmpty()) {
            this.transaccion = new SimpleDateFormat("MMddhhmmss").format(new Date());
        }

        this.codigoValido = optManager.validarCodigo(secreto, this.codigoTotp);
        this.message = "La afiliacion ha sido: ";

        if (this.codigoValido) {
            this.message += "Exitosa";
            this.success = true;
        } else {
            this.message += "Fallida Codigo Invalido";
        }

    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
    }

    public String getCodigoTotp() {
        return codigoTotp;
    }

    public void setCodigoTotp(String codigoTotp) {
        this.codigoTotp = codigoTotp;
    }

    public String getSecretoTotp() {
        return secretoTotp;
    }

    public void setSecretoTotp(String secretoTotp) {
        this.secretoTotp = secretoTotp;
    }

    public boolean isCodigoValido() {
        return codigoValido;
    }

    public void setCodigoValido(boolean codigoValido) {
        this.codigoValido = codigoValido;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AfiliacionController{" + "cedula=" + cedula + ", nombre=" + nombre + ", correo=" + correo + ", transaccion=" + transaccion + ", codigoTotp=" + codigoTotp + ", secretoTotp=" + secretoTotp + ", codigoValido=" + codigoValido + ", success=" + success + ", message=" + message + '}';
    }

}
