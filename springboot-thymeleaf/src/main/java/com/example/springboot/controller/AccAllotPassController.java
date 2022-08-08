package com.example.springboot.controller;

import com.example.springboot.dao.AccAllotPassBo;
import com.example.springboot.dao.AccAllotPassDao;
import com.example.springboot.service.AccAllotPassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ceshi")
public class AccAllotPassController {
     @Autowired
    private AccAllotPassDao accAllotPassDao;

 @PostMapping("/insert")
    public void allotPassBos(){
     List<AccAllotPassBo> allotPassBos=new ArrayList<>();
     AccAllotPassBo bo =new AccAllotPassBo();

     bo.setAccount("1257");
     bo.setId("1");
     bo.setPassword("123456");
     bo.setShortname("zhao");
     bo.setFullname("zhaolinhai");
     allotPassBos.add(bo);

     AccAllotPassBo bo1 =new AccAllotPassBo();
     bo1.setAccount("125");
     bo1.setId("2");
     bo1.setPassword("12345");
     bo1.setShortname("zha");
     bo1.setFullname("zhaolin");
     allotPassBos.add(bo1);
     accAllotPassDao.AccAllotPass(allotPassBos);

 }
}
