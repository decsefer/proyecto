package com.ipn.mx.administracioneventos.util.service.Impl;

import com.ipn.mx.administracioneventos.util.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.InternetAddress;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource; // ✅ Import correcto



@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("classpath:static/CEBOLLA.pdf") // ✅ Archivo dentro de src/main/resources/static/
    private Resource resourceFile;

    @Override
    public void enviarEmail(String to, String subject, String text) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

            messageHelper.setFrom(new InternetAddress("obeddyc2000@gmail.com", "Administracion de eventos"));
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(text);

            //  Usa el archivo directamente (no .toURI())
            messageHelper.addAttachment(resourceFile.getFilename(), resourceFile.getFile());

            mailSender.send(message);
            System.out.println(" Correo enviado correctamente con archivo adjunto: " + resourceFile.getFilename());
        } catch (Exception e) {
            throw new RuntimeException(" Error al enviar correo", e);
        }
    }

    @Override
    public void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
            helper.setFrom(new InternetAddress("obeddyc2000@gmail.com", "Administracion de eventos"));
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(" Error al enviar correo", e);
        }
    }
}
