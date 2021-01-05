/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adjmogollon.app.controller;

import com.adjmogollon.app.datos.CoreCashIn;
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
public class CashInController {

    private String cedula;
    private String monto;
    private String transaccion;
    private String referencia;
    private boolean success;
    private String message;

    public CashInController() {
    }

    public CashInController(String cedula, String monto) {
        this.cedula = cedula;
        this.monto = monto;
    }

    public CashInController(String cedula, String monto, String transaccion) {
        this.cedula = cedula;
        this.monto = monto;
        this.transaccion = transaccion;
    }

    public void EjecutarCashIn() {

        String nacionalidad = this.cedula.substring(0, 1);
        String numCedula = this.cedula.substring(1);
        if (this.transaccion == null || this.transaccion.isEmpty()) {
            this.transaccion = new SimpleDateFormat("MMddhhmmss").format(new Date());
        }

        String[] splitMonto = StringUtils.split(this.monto, ".");
        String monto400 = StringUtils.leftPad(splitMonto[0], 13, "0") + StringUtils.rightPad(splitMonto[1], 2, "0");

        CoreCashIn coreCashIn = new CoreCashIn(this.transaccion, nacionalidad, numCedula, monto400);

        if (coreCashIn.isSuccess()) {
            if (!coreCashIn.getS_CODRES().equals("0000")) {
                this.message = coreCashIn.getS_DESRES();
            } else {
                this.referencia = coreCashIn.getS_REFPGO();
                this.message = coreCashIn.getS_DESRES();
                this.success = true;
            }
            Logger.getLogger(CashInController.class.getSimpleName()).log(Level.INFO,coreCashIn.getS_DESRES());
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

}
