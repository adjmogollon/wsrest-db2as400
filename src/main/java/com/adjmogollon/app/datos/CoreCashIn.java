/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adjmogollon.app.datos;

import com.adjmogollon.app.ApplicationProperties;
import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.ProgramParameter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adjmo
 */
public class CoreCashIn {

    private final String libreria = "LIBRARYNAME";
    private final String nombrePrograma = "PROGRAMNAME";
    private final String tipoPrograma = "pgm";
    private final String E_CANAL = "01";
    private final String E_SERVICE = "MUE";
    private String E_TRACE;
    private String E_NACCLI;
    private String E_CEDCLI;
    private final String E_NACJUR = ApplicationProperties.INSTANCE.getNacJur();
    private final String E_CEDJUR = ApplicationProperties.INSTANCE.getCedJur();
    private String E_MONTO;
    private String S_CODRES;
    private String S_DESRES;
    private String S_REFPGO;
    private boolean success;
    private String message;

    public CoreCashIn() throws IOException {
    }

    public CoreCashIn(String E_TRACE, String E_NACCLI, String E_CEDCLI, String E_MONTO) {

        try {
            this.E_TRACE = E_TRACE;
            this.E_NACCLI = E_NACCLI;
            this.E_CEDCLI = E_CEDCLI;
            this.E_MONTO = E_MONTO;

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
            AS400Text txtE_NACJUR = new AS400Text(1);
            AS400Text txtE_CEDJUR = new AS400Text(10);
            AS400Text txtE_MONTO = new AS400Text(15);
            AS400Text txtRESPUESTA = new AS400Text(119);

            /* Se prepara la lista de parametros para enviar al AS400 */
            ProgramParameter[] parmList = new ProgramParameter[9];

            parmList[0] = new ProgramParameter(txtE_CANAL.toBytes(this.E_CANAL), 2);
            parmList[1] = new ProgramParameter(txtE_SERVICE.toBytes(this.E_SERVICE), 3);
            parmList[2] = new ProgramParameter(txtE_TRACE.toBytes(this.E_TRACE), 10);
            parmList[3] = new ProgramParameter(txtE_NACCLI.toBytes(this.E_NACCLI), 1);
            parmList[4] = new ProgramParameter(txtE_CEDCLI.toBytes(this.E_CEDCLI), 10);
            parmList[5] = new ProgramParameter(txtE_NACJUR.toBytes(this.E_NACJUR), 1);
            parmList[6] = new ProgramParameter(txtE_CEDJUR.toBytes(this.E_CEDJUR), 10);
            parmList[7] = new ProgramParameter(txtE_MONTO.toBytes(this.E_MONTO), 15);
            parmList[8] = new ProgramParameter(txtRESPUESTA.toBytes(""), 119);

            /* Se ejecuta la llamada al programa con todos los parametros
            y se recibe la respuesta del AS400 */
            String respuestaAS400 = conectorPGMAS400.ejecutarPGM(libreria, nombrePrograma, tipoPrograma, parmList, txtRESPUESTA);
            this.S_CODRES = respuestaAS400.substring(3, 7).trim();
            if (Integer.parseInt(this.S_CODRES) >= 0 && Integer.parseInt(this.S_CODRES) <= 8) {
                this.S_DESRES = respuestaAS400.substring(7, 57).trim();
            } else {
                this.S_DESRES = respuestaAS400.substring(19, 57).trim();
            }
            this.S_REFPGO = respuestaAS400.substring(57, 119).trim();
            
            this.message = this.S_DESRES;
            this.success = true;
            
        } catch (IOException ex) {
            this.message = CoreCashIn.class.getName() + " | " + ex.getMessage();
            Logger.getLogger(CoreCashIn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            this.message = CoreCashIn.class.getName() + " | " + ex.getMessage();
            Logger.getLogger(CoreCashIn.class.getName()).log(Level.SEVERE, null, ex);
        }

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

    public String getE_MONTO() {
        return E_MONTO;
    }

    public void setE_MONTO(String E_MONTO) {
        this.E_MONTO = E_MONTO;
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

    public String getS_REFPGO() {
        return S_REFPGO;
    }

    public void setS_REFPGO(String S_REFPGO) {
        this.S_REFPGO = S_REFPGO;
    }

    public String getLibreria() {
        return libreria;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public String getTipoPrograma() {
        return tipoPrograma;
    }

    public String getE_CANAL() {
        return E_CANAL;
    }

    public String getE_SERVICE() {
        return E_SERVICE;
    }

    public String getE_NACJUR() {
        return E_NACJUR;
    }

    public String getE_CEDJUR() {
        return E_CEDJUR;
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
        return "CoreCashIn{" + "libreria=" + libreria + ", nombrePrograma=" + nombrePrograma + ", tipoPrograma=" + tipoPrograma + ", E_CANAL=" + E_CANAL + ", E_SERVICE=" + E_SERVICE + ", E_TRACE=" + E_TRACE + ", E_NACCLI=" + E_NACCLI + ", E_CEDCLI=" + E_CEDCLI + ", E_NACJUR=" + E_NACJUR + ", E_CEDJUR=" + E_CEDJUR + ", E_MONTO=" + E_MONTO + ", S_CODRES=" + S_CODRES + ", S_DESRES=" + S_DESRES + ", S_REFPGO=" + S_REFPGO + ", success=" + success + ", message=" + message + '}';
    }

    

}
