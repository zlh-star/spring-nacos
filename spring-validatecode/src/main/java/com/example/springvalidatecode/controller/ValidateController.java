package com.example.springvalidatecode.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSONArray;
import com.example.springvalidatecode.test.ValidateCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.connector.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Api(tags = "validateCode")
@RestController
public class ValidateController {


    @ApiOperation(value = "validate",tags = "验证码图片")
    @RequestMapping(value = "/getCaptchaImage",method = RequestMethod.POST)
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {

        try {

            response.setContentType("image/png");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Expire", "0");
            response.setHeader("Pragma", "no-cache");

            ValidateCode validateCode = new ValidateCode();

            // 直接返回图片
            validateCode.getRandomCodeImage(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "validate",tags = "验证码JSON")
    @RequestMapping(value = "/getCaptchaImageByJson",method = RequestMethod.POST)
    public Object getCaptchaImageByJson(HttpServletRequest request, HttpServletResponse response){

//        Map map=new HashMap();
        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Expire", "0");
        response.setHeader("Pragma", "no-cache");

        ValidateCode validateCode = new ValidateCode();
        String base64String=validateCode.getRandomCodeImage(request, response);
//        map.put("url", base64String);
//        "data:image/png;base64," +
//        map.put("message", "created successfull");
//        System.out.println("test=" + map.get("url"));
        return JSON.toJSONString(base64String);
    }


}
