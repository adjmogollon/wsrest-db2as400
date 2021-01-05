/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adjmogollon.app.datos;


/**
 *
 * @author adjmo
 */
import com.adjmogollon.app.ApplicationProperties;
import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.ProgramCall;
import com.ibm.as400.access.AS400Message;
import com.ibm.as400.access.ProgramParameter;
import com.ibm.as400.data.PcmlException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ConectorPGMAS400 {

    private final String HOST;
    private final String USER;
    private final String PASSWORD;
    private AS400 connAs400;
    private byte[] inputData;
    private byte[] outputData;
    private ProgramCall programCall;
    private String nombreProgramaCompleto;
    
    public ConectorPGMAS400() throws FileNotFoundException, IOException {
          
        HOST = ApplicationProperties.INSTANCE.getIpAs400();
        USER = ApplicationProperties.INSTANCE.getUsuarioAs400();
        PASSWORD = ApplicationProperties.INSTANCE.getPasswordAs400();
        
    }

    public byte[] getInputData() {
        return inputData;
    }

    public void setInputData(byte[] inputData) {
        this.inputData = inputData;
    }

    public byte[] getOutputData() {
        return outputData;
    }

    public void setOutputData(byte[] outputData) {
        this.outputData = outputData;
    }

    public ProgramCall getProgramCall() {
        return programCall;
    }

    public void setProgramCall(ProgramCall programCall) {
        this.programCall = programCall;
    }

    public String getNombreProgramaCompleto() {
        return this.nombreProgramaCompleto;
    }

    public final void setnombreProgramaCompleto(String libreria, String nombrePrograma, String tipoPrograma) {
        this.nombreProgramaCompleto = "/QSYS.LIB/" + libreria + ".LIB/" + nombrePrograma + "." + tipoPrograma;
    }

    public ProgramParameter[] getRafagaFormatoAs400(String rafagaEntrada) {

        ProgramParameter[] parmList = new ProgramParameter[5];

        return parmList;

    }

    public AS400 getConnectionAS400() {
        try {
            connAs400 = new AS400(HOST, USER, PASSWORD);
            return connAs400;
        } catch (Exception e) {
            System.err.println(":: Exception ::" + e.toString());
        }
        return connAs400;

    }

    public String ejecutarPGM(String libreria, String nombrePrograma, String tipoPrograma, ProgramParameter[] parmList, AS400Text txtRespuesta) throws Throwable {
        AS400 conexionAs400 = this.getConnectionAS400();
        this.setnombreProgramaCompleto(libreria, nombrePrograma, tipoPrograma);
        String NombreProgramaCompleto = this.getNombreProgramaCompleto();
        String rafagaRespuesta = null;
        try {
            ProgramCall cmd = new ProgramCall(conexionAs400, NombreProgramaCompleto, parmList);
            try {
                if (cmd.run() != true) {
                    // System.out.println("Programa ha fallado!");
                    // muestra mensajes de error
                    AS400Message[] messagelist = cmd.getMessageList();

                    for (int i = 0; i < messagelist.length; i++) {
                        //System.out.println(messagelist[i]);
                        throw new Throwable("Error programa: " + nombrePrograma + "" + messagelist[i]);
                    }
                } else {
                    rafagaRespuesta = (String) txtRespuesta.toObject(parmList[parmList.length-1].getOutputData());             
                    parmList = null;
                    cmd = null;
                }
            } catch (Exception ex) {
                rafagaRespuesta = ex.getMessage();
                ex.printStackTrace();
                throw ex;
            }
        } catch (PcmlException e) {
            e.printStackTrace();
        }
        if (conexionAs400.isConnected()) {
            conexionAs400.disconnectAllServices();
        }
        return rafagaRespuesta;

    }
}
