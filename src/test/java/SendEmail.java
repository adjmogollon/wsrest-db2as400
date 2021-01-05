/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 *
 * @author adjmo
 */
public class SendEmail {

    public static void main(String[] args) {
        String to = "adjmogollon@gmail.com";//change accordingly  
        String from = "from@host.com";//change accordingly  
        String host = "smtp.host.com";//or IP address  

        //Get the session object  
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);

        //compose the message  
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Ping");
            message.setText("Hello, this is example of sending email  ");

            // Send message  
            Transport.send(message);
            System.out.println("message sent successfully....");

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
