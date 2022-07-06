package com.example.springbootmail.service;

public interface MailService {
    void sendSimpleMail(String to,String contex,String subject);

    void sendHTMLMail(String to,String contex,String subject);

    void sendAttcheMail(String to ,String contex,String subject,String fileName,String filePath);

}
