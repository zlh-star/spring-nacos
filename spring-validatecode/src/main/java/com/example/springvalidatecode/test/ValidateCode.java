package com.example.springvalidatecode.test;

import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

public class ValidateCode {

    @Value("${validate.test}")
    private boolean validate;

    private static Random random = new Random();
    private int width = 40;// 长
    private int height = 10;// 宽
    private int lineSize = 30;// 干扰线数量
    private int stringNum = 6;//随机产生字符的个数

    private String randomString = "0123456789abcdefghijklmnopqrstuvwxyz";

    private String RANDOMSTRING="0123456789ABCDEFGHIJKLMOPQRSTUVWXYZ";

    private final String sessionKey = "RANDOMKEY";


    /*
     *  获取字体
     */
    private Font getFont() {
        return new Font("Times New Roman", Font.ROMAN_BASELINE, 40);
    }

    /*
     *  获取颜色
     */
    private static Color getRandomColor(int fc, int bc) {

        fc = Math.min(fc, 255);
        bc = Math.min(bc, 255);

        int r = fc + random.nextInt(bc - fc - 16);
        int g = fc + random.nextInt(bc - fc - 14);
        int b = fc + random.nextInt(bc - fc - 12);

        return new Color(r, g, b);
    }

    /*
     *  绘制干扰线
     */
    private void drawLine(Graphics g) {
        int x = random.nextInt(width*stringNum);
        int y = random.nextInt(height*stringNum);
        int xl = random.nextInt(20);
        int yl = random.nextInt(10);
        g.drawLine(x, y, x + xl, y + yl);
    }

    /*
     *  获取随机字符
     */
    private String getRandomString(int num) {
        num = num > 0 ? num : randomString.length();
        return String.valueOf(randomString.charAt(random.nextInt(num)));
    }

    private String getRandom(int num) {
        num = num > 0 ? num : RANDOMSTRING.length();
        return String.valueOf(RANDOMSTRING.charAt(random.nextInt(num)));
    }

    /*
     *  绘制字符串
     */
    private String drawString(Graphics g, String randomStr, int i) {
        g.setFont(getFont());
        g.setColor(getRandomColor(108, 190));
        System.out.println(random.nextInt(randomString.length()));
        String rand = getRandomString(random.nextInt(randomString.length()));
        randomStr += rand;
        g.translate(random.nextInt(3), random.nextInt(6));
        g.drawString(rand, 40 * i + 10, 25);
        return randomStr;
    }

    private String drawStringA(Graphics g, String randomStr, int i) {
        g.setFont(getFont());
        g.setColor(getRandomColor(108, 190));
        System.out.println(random.nextInt(RANDOMSTRING.length()));
        String rand = getRandom(random.nextInt(RANDOMSTRING.length()));
        randomStr += rand;
        g.translate(random.nextInt(3), random.nextInt(6));
        g.drawString(rand, 40 * i + 10, 25);
        return randomStr;
    }

    /*
     *  生成加密json
     */
    public String getRandomCodeImage(HttpServletRequest request, HttpServletResponse response)  {
        HttpSession session = request.getSession();
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, width, height);
        g.setColor(getRandomColor(105, 189));
        g.setFont(getFont());

        // 绘制干扰线
        for (int i = 0; i < lineSize; i++) {
            drawLine(g);
        }

        // 绘制随机字符
        String random_string = "";
        if(validate){
            for (int i = 0; i < stringNum; i++) {
                random_string = drawString(g, random_string, i);
            }
        }else {
            for (int i = 0; i < stringNum; i++) {
                random_string = drawStringA(g, random_string, i);
            }
        }

        System.out.println(random_string);

        g.dispose();

        session.removeAttribute(sessionKey);
        session.setAttribute(sessionKey, random_string);

        String base64String = "";
        try {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", byteArrayOutputStream);

            byte[] bytes = byteArrayOutputStream.toByteArray();
            Base64.Encoder encoder = Base64.getEncoder();
            base64String = encoder.encodeToString(bytes);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64String;
    }

    /*
     *  生成随机图片
     */
    public String getRandomCodeImages(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width*stringNum, height*stringNum, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, width*stringNum, height*stringNum);
        g.setColor(getRandomColor(105, 189));
        g.setFont(getFont());

        // 绘制干扰线
        for (int i = 0; i < lineSize; i++) {
            drawLine(g);
        }

        // 绘制随机字符
        String random_string = "";
        if (validate) {
            for (int i = 0; i < stringNum; i++) {
                random_string = drawString(g, random_string, i);
            }
        } else {
            for (int i = 0; i < stringNum; i++) {
                random_string = drawStringA(g, random_string, i);
            }
        }

        System.out.println(random_string);

        g.dispose();

        session.removeAttribute(sessionKey);
        session.setAttribute(sessionKey, random_string);

        ImageIO.write(image, "PNG", response.getOutputStream());
        return random_string;
    }
}
