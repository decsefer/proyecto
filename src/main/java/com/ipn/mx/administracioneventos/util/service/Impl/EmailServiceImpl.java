package com.ipn.mx.administracioneventos.util.service.Impl;

import com.ipn.mx.administracioneventos.util.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.InternetAddress;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.beans.factory.annotation.Value;



@Service
@ConditionalOnProperty(name = {"spring.mail.host","spring.mail.username","spring.mail.password"})
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("classpath:static/CEBOLLA.pdf")
    private Resource resourceFile;
    
    @Value("${spring.mail.username:}")
    private String fromEmail;

    @Override
    public void enviarEmail(String to, String subject, String text) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setFrom(new InternetAddress(fromEmail, "Administracion de eventos"));
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(text);
            if (resourceFile != null && resourceFile.exists()) {
                messageHelper.addAttachment(resourceFile.getFilename(), resourceFile.getFile());
            }
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(" Error al enviar correo", e);
        }
    }

    @Override
    public void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
            helper.setFrom(new InternetAddress(fromEmail, "Administracion de eventos"));
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(" Error al enviar correo", e);
        }
    }
}
