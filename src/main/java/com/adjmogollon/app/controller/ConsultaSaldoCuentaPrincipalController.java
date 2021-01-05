/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adjmogollon.app.controller;

import com.adjmogollon.app.datos.CoreSaldoCuentaPrincipal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.ejb.Stateless;

/**
 *
 * @author anibal.mogollon
 */
@Singleton
@Stateless
public class ConsultaSaldoCuentaPrincipalController {

    private String cedula;
    private String saldoCuentaPrincipal;
    private String transaccion;
    private boolean success;
    private String message;

    public ConsultaSaldoCuentaPrincipalController() {

    }

    public ConsultaSaldoCuentaPrincipalController(String cedula) {
        this.cedula = cedula;
    }

    public ConsultaSaldoCuentaPrincipalController(String cedula, String transaccion) {
        this.cedula = cedula;
        this.transaccion = transaccion;
    }

    public void consultarSaldo() {

        String nacionalidad = this.cedula.substring(0, 1);
        String numCedula = this.cedula.substring(1);
        if (this.transaccion == null || this.transaccion.isEmpty()) {
            this.transaccion = new SimpleDateFormat("MMddhhmmss").format(new Date());
        }
        CoreSaldoCuentaPrincipal consultaSaldoCuentaPrincipal = new CoreSaldoCuentaPrincipal();
        String messageConsultaSaldo = consultaSaldoCuentaPrincipal.EjecutarCoreSaldoCuentaPrincipal(this.transaccion, nacionalidad, numCedula);

        if (consultaSaldoCuentaPrincipal.isSuccess()) {
            if (!consultaSaldoCuentaPrincipal.getS_CODRES().equals("0000")) {
                this.message = consultaSaldoCuentaPrincipal.getS_DESRES();
            } else {
                
                double saldo = Double.parseDouble(consultaSaldoCuentaPrincipal.getS_SALDO()) / 100;
                NumberFormat numberFormat = NumberFormat.getNumberInstance();            
                numberFormat.setMaximumFractionDigits(2);           
                this.saldoCuentaPrincipal = String.valueOf(numberFormat.format(saldo));
                //double saldo = Double.parseDouble(consultaSaldoCuentaPrincipal.getS_SALDO());
                //this.saldoCuentaPrincipal = String.valueOf(saldo / 100);
                this.success = true;
                this.message = consultaSaldoCuentaPrincipal.getS_DESRES();
            }
            Logger.getLogger(ConsultaSaldoCuentaPrincipalController.class.getSimpleName()).log(Level.INFO, consultaSaldoCuentaPrincipal.getS_DESRES());
        } else {
            //this.message = messageConsultaSaldo;
            this.message = "Error de comunicacion Interno";
        }

    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getSaldoCuentaPrincipal() {
        return saldoCuentaPrincipal;
    }

    public void setSaldoCuentaPrincipal(String saldoCuentaPrincipal) {
        this.saldoCuentaPrincipal = saldoCuentaPrincipal;
    }

    public String getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
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
        return "ConsultaSaldoCuentaPrincipalController{" + "cedula=" + cedula + ", saldoCuentaPrincipal=" + saldoCuentaPrincipal + ", transaccion=" + transaccion + ", success=" + success + ", message=" + message + '}';
    }

}
