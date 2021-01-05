/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adjmogollon.app.controller;

import com.adjmogollon.app.datos.CorePreAfiliacion;
import com.adjmogollon.app.mail.EmailBBU;
import com.adjmogollon.app.opt.OtpManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.mail.MessagingException;

/**
 *
 * @author anibal.mogollon
 */
@Singleton
@Stateless
public class PreAfiliacionController {

    private String cedula;
    private String nombre;
    private String correo;
    private String transaccion;
    private String codigoTotp;
    private String secretoTotp;
    private boolean success;
    private String message;

    public PreAfiliacionController() {

    }

    public PreAfiliacionController(String cedula) {
        this.cedula = cedula;
    }

    public PreAfiliacionController(String cedula, String transaccion) {
        this.cedula = cedula;
        this.transaccion = transaccion;
    }

    public PreAfiliacionController(String cedula, String nombre, String correo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.correo = correo;
    }

    public void preAfiliar() {

        this.success = true;
        String nacionalidad = this.cedula.substring(0, 1);
        String numCedula = this.cedula.substring(1);
        if (this.transaccion == null || this.transaccion.isEmpty()) {
            this.transaccion = new SimpleDateFormat("MMddhhmmss").format(new Date());
        }
        CorePreAfiliacion corePreAfiliacion = new CorePreAfiliacion(this.transaccion, nacionalidad, numCedula);

        if (corePreAfiliacion.isSuccess()) {
            ///////////
            if (!corePreAfiliacion.getS_CODRES().equals("0000")) {
                this.message = corePreAfiliacion.getS_DESRES();
            } else {
                OtpManager optManager = new OtpManager();
                String secreto = optManager.generarSecreto(this.cedula);
                String totp = optManager.generarCodigo(secreto);
                this.codigoTotp = totp;
                this.correo = corePreAfiliacion.getS_EMAIL1();
                EmailBBU emailBBU = new EmailBBU();
                String mensaje = emailBBU.cuerpoOTP(this.codigoTotp, this.correo);
                if (!emailBBU.enviarEmailBBU(this.correo, "PreAfiliacion Mueve", mensaje)) {
                    this.message = "Error al enviar EMAIL";
                }else{
                    this.message = "APROBADA";
                    this.success = true;
                }                   
            }
            ///////////////
            Logger.getLogger(PreAfiliacionController.class.getSimpleName()).log(Level.INFO, this.message);
        } else {
            //this.message = coreCashIn.getMessage();
            this.message = "Error de comunicacion Interno";
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
        return "PreAfiliacionController{" + "cedula=" + cedula + ", nombre=" + nombre + ", correo=" + correo + ", transaccion=" + transaccion + ", codigoTotp=" + codigoTotp + ", secretoTotp=" + secretoTotp + ", success=" + success + ", message=" + message + '}';
    }

}
