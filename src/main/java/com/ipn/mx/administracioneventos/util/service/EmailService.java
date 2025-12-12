package com.ipn.mx.administracioneventos.util.service;

public interface EmailService {
    void enviarEmail(String to, String subject, String text);

    public void sendEmail(String to, String subject, String text );
}
