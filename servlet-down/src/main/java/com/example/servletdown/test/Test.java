package com.example.servletdown.test;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet(name = "servletContextDemo6", value = "/servletContextDemo6")
public class Test extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename = request.getParameter("filename");           //获取请求参数

        //使用字节输入流加载文件进内存
        ServletContext servletContext = this.getServletContext();
        String realPath = servletContext.getRealPath("/WEB-INF/classes/servletContext/" + filename);  //找到文件的服务器路径
        FileInputStream fis = new FileInputStream(realPath);                    //使用字节输入流读取文件

        //设置response响应头
        String mimeType = servletContext.getMimeType(filename);                   //获取mime类型
        response.setHeader("content-type",mimeType);                              //设置响应头类型
        response.setHeader("content-disposition","attachment;filename=" + filename);    //设置响应头打开方式

        //将输入流的事件写出到输出事件
        ServletOutputStream sos = response.getOutputStream();
        byte[] buffer = new byte[1024 * 8];
        int length = 0;
        while((length = fis.read(buffer)) != -1){
            sos.write(buffer,0,length);
        }
        sos.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}
