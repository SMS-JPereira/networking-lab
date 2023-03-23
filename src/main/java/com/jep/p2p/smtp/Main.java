package com.jep.p2p.smtp;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static java.lang.System.out;

public class Main {

    public static void main(String[] args) {
        String to = "${recipientEmailAddress}"; // recipient
        String from = "${originatorEmailAddress}";
        String host = "smtp.gmail.com";

        Properties props = System.getProperties();

        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", 465);
        props.put("mail.smtp.ssl.enable", true);
        props.put("mail.smtp.auth", true);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("${originatorEmailAddress}", "${originatorPassword}");
            }
        });

        session.setDebug(true);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Sent from My Java App");
            message.setText("Hi folk! How are you? =)");
            out.println("Sending Email...");
            Transport.send(message);
        } catch (MessagingException mexc) {
            out.println("Error : " + mexc.getMessage());
        }
    }
}
