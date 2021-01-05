/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adjmogollon.app.opt;

import com.adjmogollon.app.ApplicationProperties;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.exceptions.CodeGenerationException;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base32;

/**
 *
 * @author adjmo
 */
public class OtpManager {

    long time;
    long counter;
    String secreto;
    String codigo;
    DefaultCodeGenerator codeGenerator;
    TimeProvider timeProvider;
    DefaultCodeVerifier verifier;
    int DuracionTOTP;
    private boolean success;
    private String message;

    public OtpManager() {
        this.DuracionTOTP = ApplicationProperties.INSTANCE.getDuracionTOTP();
    }

    public String generarSecreto() {
        SecretGenerator secretGenerator = new DefaultSecretGenerator();
        this.secreto = secretGenerator.generate();
        return this.secreto;
    }

    public String generarSecreto(String semilla) {
        // Queda pendiente generar un Base32 encriptado con la cedula del cliente

        byte[] bytes = semilla.getBytes();
        Base32 encoder = new Base32();
        this.secreto = new String(encoder.encode(bytes));
        return this.secreto;
    }

    public HashMap<String, String> generarCodigo() throws CodeGenerationException {
        this.timeProvider = new SystemTimeProvider();
        this.time = this.timeProvider.getTime();
        this.secreto = this.generarSecreto();
        this.codeGenerator = new DefaultCodeGenerator();
        this.counter = Math.floorDiv(time, this.DuracionTOTP);
        this.codigo = this.codeGenerator.generate(secreto, counter);
        HashMap<String, String> totp = new HashMap<>();
        totp.put("secreto", secreto);
        totp.put("codigo", codigo);
        return totp;

    }

    public String generarCodigo(String secretoIn)  {
        try {
            this.timeProvider = new SystemTimeProvider();
            this.time = this.timeProvider.getTime();
            //this.secreto = secretoIn;
            this.codeGenerator = new DefaultCodeGenerator();
            this.counter = Math.floorDiv(time, this.DuracionTOTP);
            this.codigo = codeGenerator.generate(secretoIn, counter);
            this.message = "Generacion de codigo exitosa";
            this.success = true;
            return this.codigo;
        } catch (CodeGenerationException ex) {        
            Logger.getLogger(OtpManager.class.getName()).log(Level.SEVERE, null, ex);
            return this.message = "Generacion de codigo fallida";
        }

    }

    public boolean validarCodigo(HashMap<String, String> totpIn) {
        this.secreto = (String) totpIn.get("secreto");
        this.codigo = (String) totpIn.get("codigo");
        this.codeGenerator = new DefaultCodeGenerator();
        this.timeProvider = new SystemTimeProvider();
        this.verifier = new DefaultCodeVerifier(this.codeGenerator, this.timeProvider);
        this.verifier.setTimePeriod(this.DuracionTOTP);
        this.verifier.setAllowedTimePeriodDiscrepancy(1);
        boolean successful = this.verifier.isValidCode(this.secreto, this.codigo);
        return successful;
    }

    public boolean validarCodigo(String secretoIN, String codigoIn) {
        this.secreto = secretoIN;
        this.codigo = (String) codigoIn;
        this.codeGenerator = new DefaultCodeGenerator();
        this.timeProvider = new SystemTimeProvider();
        this.verifier = new DefaultCodeVerifier(this.codeGenerator, this.timeProvider);
        this.verifier.setTimePeriod(this.DuracionTOTP);
        this.verifier.setAllowedTimePeriodDiscrepancy(1);
        boolean successful = this.verifier.isValidCode(this.secreto, this.codigo);
        return successful;
    }

    public String getSecreto() {
        return secreto;
    }

    public void setSecreto(String secreto) {
        this.secreto = secreto;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
        return "OtpManager{" + "time=" + time + ", counter=" + counter + ", secreto=" + secreto + ", codigo=" + codigo + ", codeGenerator=" + codeGenerator + ", timeProvider=" + timeProvider + ", verifier=" + verifier + ", DuracionTOTP=" + DuracionTOTP + ", success=" + success + ", message=" + message + '}';
    }

}
