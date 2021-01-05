/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adjmogollon.app.controller;

import com.adjmogollon.app.ApplicationProperties;
import com.adjmogollon.app.datos.CoreCashOut;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author adjmo
 */
@Singleton
@Stateless
public class CashOutController {

    
    private String cedula;
    private String monto;
    private String transaccion;
    private String referencia;
    private final String porcentaje = ApplicationProperties.INSTANCE.getPorcentajeCashOut();
    private boolean success;
    private String message;

    public CashOutController() {
    }

    public CashOutController(String cedula, String monto) {
        this.cedula = cedula;
        this.monto = monto;
    }

    public CashOutController(String cedula, String monto, String transaccion) {
        this.cedula = cedula;
        this.monto = monto;
        this.transaccion = transaccion;
    }


    public void EjecutarCashOut() {
       
            String nacionalidad = this.cedula.substring(0, 1);
            String numCedula = this.cedula.substring(1);
            if (this.transaccion == null || this.transaccion.isEmpty()) {
                this.transaccion = new SimpleDateFormat("MMddhhmmss").format(new Date());
            }
            
            String[] splitMonto = StringUtils.split(this.monto, ".");      
            String monto400 = StringUtils.leftPad(splitMonto[0], 13,"0")+StringUtils.rightPad(splitMonto[1], 2, "0");
            
            String[] splitPorcentaje = StringUtils.split(this.porcentaje, ".");      
            String porcentaje400 = StringUtils.leftPad(splitPorcentaje[0], 3,"0")+StringUtils.rightPad(splitPorcentaje[1], 2, "0");
            
            CoreCashOut coreCashOut = new CoreCashOut(this.transaccion, nacionalidad, numCedula,monto400,porcentaje400);
           
            if (coreCashOut.isSuccess()) {
            if (!coreCashOut.getS_CODRES().equals("0000")) {
                this.message = coreCashOut.getS_DESRES();
            } else {
                this.referencia = coreCashOut.getS_REFPGO();
                this.message = coreCashOut.getS_DESRES();
                this.success = true;
            }
            Logger.getLogger(CashOutController.class.getSimpleName()).log(Level.INFO,coreCashOut.getS_DESRES());
        } else {
            //this.message = coreCashOut.getMessage();
            this.message = "Error de comunicacion Interno";
        }
   
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
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

    

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getPorcentaje() {
        return porcentaje;
    }

    @Override
    public String toString() {
        return "CashOutController{" + "cedula=" + cedula + ", monto=" + monto + ", transaccion=" + transaccion + ", referencia=" + referencia + ", porcentaje=" + porcentaje + ", operacionExitosa=" + success + ", operacionRespuesta=" + message + '}';
    }
    
}
