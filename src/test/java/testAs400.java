
import com.adjmogollon.app.datos.CorePreAfiliacion;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adjmo
 */
public class testAs400 {
    public static void main(String[] args) throws IOException, Throwable {
    
    String E_TRACE = new SimpleDateFormat("MMddhhmmss").format(new Date()); 
    String E_NACCLI = "V"; 
    String E_CEDCLI = "76575676";    
        
    CorePreAfiliacion consulta = new CorePreAfiliacion(E_TRACE, E_NACCLI, E_CEDCLI);
    
    System.out.println(consulta);    
        
    }
}
