
import com.adjmogollon.app.ApplicationProperties;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adjmo
 */
public class test {
    public static void main(String[] args) {
        System.out.println("hello word");
        
        System.out.println(ApplicationProperties.INSTANCE.getIpAs400());
        System.out.println(ApplicationProperties.INSTANCE.getUsuarioAs400());
        System.out.println(ApplicationProperties.INSTANCE.getPasswordAs400());
        
    }
}
