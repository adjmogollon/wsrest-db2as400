
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.exceptions.CodeGenerationException;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import java.io.UnsupportedEncodingException;
import static java.lang.Thread.sleep;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base32;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author adjmo
 */
public class testOTP {

    public static void main(String[] args) throws CodeGenerationException, InterruptedException, UnsupportedEncodingException {
        SecretGenerator secretGenerator = new DefaultSecretGenerator();
        String secret = secretGenerator.generate();
        System.out.println("secret = " + secret);

        TimeProvider timeProvider = new SystemTimeProvider();
        long time = timeProvider.getTime();
        System.out.println("time = " + time);

        DefaultCodeGenerator codeGenerator = new DefaultCodeGenerator();
        long counter = Math.floorDiv(time, 30);
        System.out.println("counter = " + counter);
        String codigo = codeGenerator.generate(secret, counter);
        System.out.println("codigo = " + codigo);

        DefaultCodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
        verifier.setTimePeriod(30);

        // allow codes valid for 2 time periods before/after to pass as valid
        verifier.setAllowedTimePeriodDiscrepancy(1);
        /*
        while (true) {
            boolean successful = verifier.isValidCode(secret, codigo);
            long diferencia = timeProvider.getTime() - time;
            System.out.println("verificacion secreto y codigo = " + successful
                    +" Diferencia de tiempo = " + diferencia);
            if(!successful){
                break;
            }
            
            System.out.println();
            sleep(1000);
        }*/

        //int numCharacters = 32;
        //SecureRandom randomBytes = new SecureRandom();
        //byte[] bytes = new byte[(numCharacters * 5) / 8];
        //randomBytes.setSeed("V17166155".getBytes("us-ascii"));
        //randomBytes.nextBytes(bytes);
      
        byte[] bytes = "V4765320".getBytes();
        Base32 encoder = new Base32();
        String secreto2 = new String(encoder.encode(bytes));
        String codigo2 = codeGenerator.generate(secreto2, counter);

        
   
        
        while (true) {
            boolean successful = verifier.isValidCode(secreto2, codigo2);
            long diferencia = timeProvider.getTime() - time;
            System.out.println("verificacion secreto: "+secreto2+" y codigo:"+codigo2+" = " + successful
                    + " Diferencia de tiempo = " + diferencia);
            if (!successful) {
                break;
            }

            System.out.println();
            sleep(1000);
        }
    }
}
