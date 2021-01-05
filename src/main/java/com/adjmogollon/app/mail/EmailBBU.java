/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adjmogollon.app.mail;

import com.adjmogollon.app.ApplicationProperties;
import com.adjmogollon.app.controller.PreAfiliacionController;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author adjmo
 */
public class EmailBBU {

    private final String host = ApplicationProperties.INSTANCE.getHostServidorSMTP();
    private final String port = ApplicationProperties.INSTANCE.getPortServidorSMTP();
    private final String from = ApplicationProperties.INSTANCE.getCorreoOrigen();

    public EmailBBU() {
    }

    public boolean enviarEmailBBU(String destinatario, String titulo, String cuerpo) {

        Properties props = new Properties();
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.port", this.port);
        String contenido = cuerpo;
        // Get the Session object.
        Session session = Session.getDefaultInstance(props);
        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(this.from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));

            // Set Subject: header field
            message.setSubject(titulo);

            // Send the actual HTML message, as big as you like
            message.setContent(contenido, "text/html");

            // Send message
            Transport.send(message);

            return true;
        } catch (MessagingException e) {
            Logger.getLogger(PreAfiliacionController.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }

        /*
        this.HostServidorSMTP = ApplicationProperties.INSTANCE.getHostServidorSMTP();
        this.CorreoOrigen = ApplicationProperties.INSTANCE.getCorreoOrigen();
        this.destinatario = destinatario;
        this.titulo = titulo;
        this.contenido = contenido;
        String to = this.destinatario;
        String from = this.CorreoOrigen;  
        String host = this.HostServidorSMTP; 
        //Get the session object  
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);

        //compose the message  
   //     try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(this.titulo);
            message.setText(this.contenido,);
            
            // Send message  
            Transport.send(message);
         */
 /*
        } catch (MessagingException mex) {
            mex.printStackTrace();

        }
         */
    }

    public String cuerpoOTP(String otp, String correo) {
        String html;
        html = "<div dir='auto'>"
                + "    <div><br><br>"
                + "        <div class='gmail_quote'>"
                + "            <div dir='ltr' class='gmail_attr'>"
                + "<div marginwidth='0' marginheight='0' style='margin:0pt auto;padding:0px;background:#f4f7fa'>"
                + "<table id='m_3040220776377935777main' width='100%' height='100%' cellpadding='0' cellspacing='0' border='0' bgcolor='#ebeced'>"
                + "<tbody>"
                + "<tr>"
                + "<td valign='top'>"
                + "<table cellpadding='0' width='580' cellspacing='0' border='0' bgcolor='#312965' align='center' style='margin:0 auto;table-layout:fixed'>"
                + "<tbody>"
                + "<tr>"
                + "<td colspan='4'>"
                + "<table width='100%' cellpadding='0' cellspacing='0' border='0'>"
                + "<tbody>"
                + "    <tr>"
                + "        <td colspan='2' height='30'></td>"
                + "    </tr>"
                + "    <tr>"
                + "        <td valign='top' align='center'>"
                + "            <a href='' style='display:inline-block;text-align:center' target='_blank' rel='noreferrer'>"
                + "                "
                + "            </a>"
                + "        </td>"
                + "    </tr>"
                + "    <tr>"
                + "        <td colspan='2' height='30'></td>"
                + "    </tr>"
                + "</tbody>"
                + "</table>"
                + "<table width='100%' cellpadding='0' cellspacing='0' border='0' bgcolor='#ffffff' style='border-radius:0px'>"
                + "<tbody>"
                + "    <tr>"
                + "        <td height='40'></td>"
                + "    </tr>"
                + "    <tr style='font-family:Rubik;color:#4e5c6e;font-size:14px;line-height:20px;margin-top:20px'>"
                + "        <td colspan='2' valign='top' align='center' style='padding-left:90px;padding-right:90px'>"
                + "            <table width='100%' cellpadding='0' cellspacing='0' border='0' bgcolor='#ffffff'>"
                + "                <tbody>"
                + "                    <tr>"
                + "                        <td align='center'>"
                + "                            <span style='color:#48545d;font-size:22px;line-height:24px;font-weight:400'>"
                + "                                ¡Bienvenido/a!"
                + "                            </span><br><br>"
                + "                        </td>"
                + "                    </tr>"
                + "                    <tr>"
                + "                        <td height='05'></td>"
                + "                    </tr>"
                + "                    <tr>"
                + "                        <td align='center' valign='bottom' colspan='2' cellpadding='3'>"
                + "                           "
                + "                        </td>"
                + "                    </tr>"
                + "                    <tr>"
                + "                        <td height='4'></td>"
                + "                    </tr>"
                + "                    <tr>"
                + "                        <td height='24'></td>"
                + "                    </tr>"
                + "                    <tr>"
                + "                        <td align='center'>"
                + "                            <span style='color:#48545d;font-size:14px;line-height:24px'>"
                + "                                Hola <a href='mailto:"+correo+"' target='_blank' rel='noreferrer'>"+correo+"</a>,<br><br>"
                + "                                Banco Bicentenario te da la bienvenida a <b>Mueve.</b><br><br>"
                + "                                ¡Ingrese el siguiente codigo de validacion para que empieces a enviar y recibir dinero desde tu aplicacion!<br><br>"
                + "                            </span>"
                + "                        </td>"
                + "                    </tr>"
                + "                    <tr>"
                + "                        <td height='10'></td>"
                + "                    </tr>"
                + "                    <tr>"
                + "                        <td valign='top' width='48%' align='center'> <span>"
                + "                                <a style='display:block;padding:15px 25px;background-color:#312965;color:#ffffff;border-radius:3px;text-decoration:none' rel='noreferrer'><b>"+otp+"</b></a>"
                + "                            </span>"
                + "                        </td>"
                + "                    </tr>"
                + "                    <tr>"
                + "                    </tr>"
                + "                    <tr>"
                + "                        <td height='20'></td>"
                + "                    </tr>"
                + "                    <tr>"
                + "                        <td align='center'>"
                + "                            <img src='' width='400' height='2' border='0'>"
                + "                        </td>"
                + "                    </tr>"
                + "                    <tr>"
                + "                        <td height='20'></td>"
                + "                    </tr>"
                + "                    <tr>"
                + "                        <td align='center'>"
                + "                            <p style='color:#48545d;font-family:Rubik;font-size:14px;line-height:24px'>"
                + "                               </p>"
                + "                        </td>"
                + "                    </tr>"
                + "                </tbody>"
                + "            </table>"
                + "        </td>"
                + "    </tr>"
                + "    <tr>"
                + "        <td height='20'></td>"
                + "    </tr>"
                + "</tbody>"
                + "</table>"
                + "<table width='100%' cellpadding='0' cellspacing='0' border='0'>"
                + "<tbody>"
                + "    <tr>"
                + "        <td><br>"
                + "        </td>"
                + "        <td valign='bottom' align='center'> <span style='font-family:Rubik;color:#ffffff;font-size:10px'>"
                + "                <a href='http://link' style='color:#ffffff!important;text-decoration:none' target='_blank' rel='noreferrer'></a>"
                + "            </span>"
                + "        </td>"
                + "    </tr>"
                + "    <tr>"
                + "        <td height='20'>&nbsp;</td>"
                + "    </tr>"
                + "</tbody>"
                + "</table>"
                + "</td>"
                + "</tr>"
                + "</tbody>"
                + "</table>"
                + "</td>"
                + "</tr>"
                + "</tbody>"
                + "</table>"
                + "</div>"
                + "</div>"
                + "        </div>"
                + "    </div>";

        return html;
    }

}
