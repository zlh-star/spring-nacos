package com.example.springidcard.test;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "校验身份证")
public class Test1 {

    @ApiOperation(value = "测试身份证",notes = "测试身份证")
    @PostMapping("/test")
    public void test(){
        checkIDCard("",null);

        System.out.println("校验成功");
    }

    /**
     * @param content 需要校验的数据内容
     * @param serTime 校验日期，格式为yyyy-MM-dd，用于校验出生日期是否早于该日期。如传null，则默认为当前日期
     * @return 校验合法返回true，不合法返回false
     */
    private static boolean checkIDCard(String content, String serTime) {
        Test regex = new Test(content, serTime);
        return regex.validate();
    }

}
