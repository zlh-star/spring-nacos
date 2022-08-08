package com.example.servletdown.test;


//import com.gbpi.digitization.util.JsonResult;
//import com.gbpi.digitization.util.RequiredPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RestController
@Api(value = "文件下载到浏览器接口", description = "文件下载到浏览器接口")
public class DownLoad {


    @GetMapping("/download")
    @ApiOperation(value = "文件下载", notes = "参数:无")
    public void findOperatorCharBar2(HttpServletResponse response, String path, String docName) throws IOException {
        toDownload(response, path, docName);

    }


    public static void toDownload(HttpServletResponse response, String path, String docName) {
        ServletOutputStream out = null;
        InputStream inputStream = null;

        try {
            //中文转义,浏览器中文需要转义,否则报400参数错误
            int lastIndexOf = path.lastIndexOf('/');
            String fileName = path.substring(lastIndexOf + 1);
            // 转义关键代码
            String newFileName = URLEncoder.encode(fileName, "utf-8");
            String subUrl = path.substring(0, lastIndexOf + 1);
            String newUrl = subUrl + newFileName;

            URL url = new URL(newUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();


            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            inputStream = conn.getInputStream();
            /**
             * 输出文件到浏览器
             */
            int len = 0;
            // 输出 下载的响应头，如果下载的文件是中文名，文件名需要经过url编码
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(docName, "UTF-8"));
            response.setHeader("Cache-Control", "no-cache");
            out = response.getOutputStream();
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}