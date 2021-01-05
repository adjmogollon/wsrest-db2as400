/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adjmogollon.app.datos;

import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.ProgramParameter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adjmo
 */
public class CoreSaldoCuentaPrincipal {

    private final String libreria = "LIBRARYNAME";
    private final String nombrePrograma = "WWWRP002";
    private final String tipoPrograma = "pgm";
    private final String E_CANAL = "01";
    private final String E_SERVICE = "MUE";
    private String E_TRACE;
    private String E_NACCLI;
    private String E_CEDCLI;
    private String S_CODRES;
    private String S_DESRES;
    private String S_EMAIL1;
    private String S_EMAIL2;
    private String S_CUENTA;
    private String S_SIGSLD;
    private String S_SALDO;
    private boolean success;
    private String message;

    public CoreSaldoCuentaPrincipal(){
    }

    public String EjecutarCoreSaldoCuentaPrincipal(String E_TRACE, String E_NACCLI, String E_CEDCLI) {
        try {
            /*
            Parametros de variables
             */
            this.E_TRACE = E_TRACE;
            this.E_NACCLI = E_NACCLI;
            this.E_CEDCLI = E_CEDCLI;

            ConectorPGMAS400 conectorPGMAS400 = new ConectorPGMAS400();

            /*
            Declaracion de parametros de entrada del servicio
            en formato AS400 el tamaño lo da el AS400
             */
            AS400Text txtE_CANAL = new AS400Text(2);
            AS400Text txtE_SERVICE = new AS400Text(3);
            AS400Text txtE_TRACE = new AS400Text(10);
            AS400Text txtE_NACCLI = new AS400Text(1);
            AS400Text txtE_CEDCLI = new AS400Text(10);
            AS400Text txtRESPUESTA = new AS400Text(177);

            /* Se prepara la lista de parametros para enviar al AS400 */
            ProgramParameter[] parmList = new ProgramParameter[6];

            parmList[0] = new ProgramParameter(txtE_CANAL.toBytes(this.E_CANAL), 2);
            parmList[1] = new ProgramParameter(txtE_SERVICE.toBytes(this.E_SERVICE), 3);
            parmList[2] = new ProgramParameter(txtE_TRACE.toBytes(this.E_TRACE), 10);
            parmList[3] = new ProgramParameter(txtE_NACCLI.toBytes(this.E_NACCLI), 1);
            parmList[4] = new ProgramParameter(txtE_CEDCLI.toBytes(this.E_CEDCLI), 10);
            parmList[5] = new ProgramParameter(txtRESPUESTA.toBytes(""), 177);

            /* Se ejecuta la llamada al programa con todos los parametros
            y se recibe la respuesta del AS400 */
            String respuestaAS400 = conectorPGMAS400.ejecutarPGM(libreria, nombrePrograma, tipoPrograma, parmList, txtRESPUESTA);
            //System.out.println("respuestaAS400 = " + respuestaAS400.trim().length());
            this.S_CODRES = respuestaAS400.substring(3, 7).trim();
            //this.S_DESRES = respuestaAS400.substring(7, 57).trim();
            if (Integer.parseInt(this.S_CODRES) >= 0 && Integer.parseInt(this.S_CODRES) <= 8) {
                this.S_DESRES = respuestaAS400.substring(7, 57).trim();
            } else {
                this.S_DESRES = respuestaAS400.substring(19, 57).trim();
            }
            this.S_EMAIL1 = respuestaAS400.substring(57, 96).trim();
            this.S_EMAIL2 = respuestaAS400.substring(96, 136).trim();
            this.S_CUENTA = respuestaAS400.substring(157, 161).trim();
            this.S_SIGSLD = respuestaAS400.substring(161, 162).trim();
            this.S_SALDO = respuestaAS400.substring(162, 177).trim();
            this.message = this.S_DESRES;
            this.success = true; 
        } catch (IOException ex) {
            this.message = CoreSaldoCuentaPrincipal.class.getName() + " | " + ex.getMessage();
            Logger.getLogger(CoreSaldoCuentaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            this.message = CoreSaldoCuentaPrincipal.class.getName() + " | " + ex.getMessage();
            Logger.getLogger(CoreSaldoCuentaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  this.message;
    }

    public String getE_TRACE() {
        return E_TRACE;
    }

    public void setE_TRACE(String E_TRACE) {
        this.E_TRACE = E_TRACE;
    }

    public String getE_NACCLI() {
        return E_NACCLI;
    }

    public void setE_NACCLI(String E_NACCLI) {
        this.E_NACCLI = E_NACCLI;
    }

    public String getE_CEDCLI() {
        return E_CEDCLI;
    }

    public void setE_CEDCLI(String E_CEDCLI) {
        this.E_CEDCLI = E_CEDCLI;
    }

    public String getS_CODRES() {
        return S_CODRES;
    }

    public void setS_CODRES(String S_CODRES) {
        this.S_CODRES = S_CODRES;
    }

    public String getS_DESRES() {
        return S_DESRES;
    }

    public void setS_DESRES(String S_DESRES) {
        this.S_DESRES = S_DESRES;
    }

    public String getS_EMAIL1() {
        return S_EMAIL1;
    }

    public void setS_EMAIL1(String S_EMAIL1) {
        this.S_EMAIL1 = S_EMAIL1;
    }

    public String getS_EMAIL2() {
        return S_EMAIL2;
    }

    public void setS_EMAIL2(String S_EMAIL2) {
        this.S_EMAIL2 = S_EMAIL2;
    }

    public String getS_CUENTA() {
        return S_CUENTA;
    }

    public void setS_CUENTA(String S_CUENTA) {
        this.S_CUENTA = S_CUENTA;
    }

    public String getS_SIGSLD() {
        return S_SIGSLD;
    }

    public void setS_SIGSLD(String S_SIGSLD) {
        this.S_SIGSLD = S_SIGSLD;
    }

    public String getS_SALDO() {
        return S_SALDO;
    }

    public void setS_SALDO(String S_SALDO) {
        this.S_SALDO = S_SALDO;
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
        return "CoreSaldoCuentaPrincipal{" + "libreria=" + libreria + ", nombrePrograma=" + nombrePrograma + ", tipoPrograma=" + tipoPrograma + ", E_CANAL=" + E_CANAL + ", E_SERVICE=" + E_SERVICE + ", E_TRACE=" + E_TRACE + ", E_NACCLI=" + E_NACCLI + ", E_CEDCLI=" + E_CEDCLI + ", S_CODRES=" + S_CODRES + ", S_DESRES=" + S_DESRES + ", S_EMAIL1=" + S_EMAIL1 + ", S_EMAIL2=" + S_EMAIL2 + ", S_CUENTA=" + S_CUENTA + ", S_SIGSLD=" + S_SIGSLD + ", S_SALDO=" + S_SALDO + ", success=" + success + ", message=" + message + '}';
    }



}
