package com.kopivad.testingsystem.service;

public interface MailService {
    void sendMassage(String receiver, String subject, String text);
}
