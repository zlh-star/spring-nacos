package com.example.springbootmail;

import com.example.springbootmail.service.MailService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@RunWith(SpringRunner.class)
@SpringBootTest
class SpringBootMailApplicationTests {

    @Autowired
    MailService mailService;
    @Autowired
    TemplateEngine templateEngine;

    @Test
    public void sendSimpleMail(){
        mailService.sendSimpleMail("2248416786@qq.com","邮件题目","邮件内容");
    }

    @Test
    public void sendHTMLMail(){
        Context context=new Context();
        context.setVariable("code","123456");
        String emailHtml=templateEngine.process("email",context);
        mailService.sendHTMLMail("2248416786@qq.com","测试模板",emailHtml);
    }
    @Test
    public void sendAttcheMail(){
        String fileName="图片.jpg";
        String filePath="C:\\Users\\lslinhaizhao\\Pictures\\Saved Pictures\\图片.jpg";
        Context context=new Context();
        context.setVariable("code","123456");
        String emailHtml=templateEngine.process("email",context);
        mailService.sendAttcheMail("2248416786@qq.com",emailHtml,"测试带附件模板",fileName,filePath);

    }

}
