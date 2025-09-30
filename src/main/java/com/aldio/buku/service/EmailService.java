package com.aldio.buku.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendBukuConfirmation(String to, String bukuJudul, String bukuId) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject("Konfirmasi Buku");
    message.setText("Buku dengan judul \"" + bukuJudul + "\" (ID: " + bukuId + ") telah berhasil diproses!");
    mailSender.send(message);
}
}

