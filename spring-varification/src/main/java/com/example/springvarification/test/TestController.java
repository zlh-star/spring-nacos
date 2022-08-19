package com.example.springvarification.test;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "判断用户是否有权限")
@RestController
public class TestController {

    @Autowired
    private MenuService menuService;

    public static final String ONLY_LETTER ="^[-/a-zA-Z]+$";
    public static final String ONLY_LETTER_CHINESE ="[\u4e00-\u9fa5]+$";


    @ApiOperation(value = "校验名称", httpMethod = "POST")
    @RequestMapping(value = "/getPerByAccountAndObjPath", method = RequestMethod.POST)
    public Object get(@RequestBody MenuBo menuBo){
        try {
            if(menuBo.getMenuTitle().matches(ONLY_LETTER_CHINESE)){
                menuService.insert(menuBo);
                return "新增成功";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "名称只允许为中文";
    }

    @ApiOperation(value = "校验字符", httpMethod = "POST")
    @RequestMapping(value = "/getPer", method = RequestMethod.POST)
    public Object getPer(@RequestBody MenuBo menuBo){
        try {
            if(menuBo.getMenuObjPath().matches(ONLY_LETTER)){
                menuService.insert(menuBo);
                return "新增成功";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "路径格式错误";
    }
}
