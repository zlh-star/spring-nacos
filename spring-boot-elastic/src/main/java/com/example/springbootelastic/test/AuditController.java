package com.example.springbootelastic.test;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(value = "测试")
@RestController
public class AuditController {

    @Autowired
    private  AuditService auditService;

    @ApiModelProperty(value = "测试")
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public void insert(){
        List<DemoDto> list=new ArrayList<>();
        DemoDto demoDto=new DemoDto();
        demoDto.setId("2345678");
        demoDto.setName("zlhwertyu");
        demoDto.setName1("3");
        demoDto.setDate(new Date());
        list.add(demoDto);
        auditService.insertLog(list);
    }

    @ApiModelProperty(value = "删除")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public void delete(@RequestParam(value = "IdList",required = false) List<String>IdList){
        try {
            auditService.deleteLog(IdList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
