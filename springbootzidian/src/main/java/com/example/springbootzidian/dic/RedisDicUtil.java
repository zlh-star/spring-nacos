package com.example.springbootzidian.dic;


import com.example.springbootzidian.service.DictService;
import com.example.springbootzidian.util.SpringUtil;
import org.springframework.context.ApplicationContext;


public class RedisDicUtil {

    public static ApplicationContext context;

    public static String transForm(String name,int value){
        ApplicationContext context= SpringUtil.getApplicationContext();
        DictService dictService=context.getBean(DictService.class);
        return dictService.transForm(name, value);
    }
}
