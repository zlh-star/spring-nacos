package com.example.springbootmail.service.Impl;

import com.example.springbootmail.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;

@Service
public class MailServiceImpl implements MailService {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    MailService mailService;

    @Autowired
    JavaMailSender  javaMailSender;

    @Value("${spring.mail.fromAddr}")
    private String fromWhere;

    @Value("${spring.mail.nickName}")
    private String nickName;

    @Override
    public void sendSimpleMail(String to, String contex, String subject) {
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom(nickName+"<"+fromWhere+">");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setText(contex);
        simpleMailMessage.setSubject(subject);

        try{
            javaMailSender.send(simpleMailMessage);
            logger.info("邮件送成功");
        }catch (Exception e){
            logger.error("邮件发送失败",e);
        }

    }

    @Override
    public void sendHTMLMail(String to, String contex, String subject) {
        MimeMessage mimeMailMessage=javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper message=new MimeMessageHelper(mimeMailMessage,true);
            message.setFrom(new InternetAddress(fromWhere,nickName,"UTF-8"));
            message.setTo(to);
            message.setText(contex);
            message.setSubject(subject);

            javaMailSender.send(mimeMailMessage);
            logger.info("发送成功");
        } catch (MessagingException e) {
            logger.error("发送失败",e);
        }catch (UnsupportedEncodingException exception)
        {
            logger.error("编码不符合要求",exception);
        }

    }

    @Override
    public void sendAttcheMail(String to, String contex, String subject, String fileName, String filePath) {
        MimeMessage mimeMail=javaMailSender.createMimeMessage();//创建邮件
        try{
            MimeMessageHelper message=new MimeMessageHelper(mimeMail,true);
            message.setFrom(new InternetAddress(fromWhere,nickName,"UTF-8"));
            message.setTo(to);
            message.setText(contex);
            message.setSubject(subject);

            FileSystemResource fileSystemResource=new FileSystemResource(new File(filePath));//添加附件
            message.addAttachment(fileName,fileSystemResource);

            javaMailSender.send(mimeMail);
            logger.info("带附件邮件发送成功");
        } catch (MessagingException e) {
            logger.error("带附件邮件发送失败",e);
        }catch (UnsupportedEncodingException exception)
        {
            logger.error("收件地址编码不符合要求",exception);
        }
    }
}
