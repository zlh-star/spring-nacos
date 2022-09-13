package com.example.payserver.controller;

import com.example.common.OperationResponse;
import com.example.common.pay.PayBo;
import com.example.payserver.service.PayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "扣除余额")
@RestController
@RequestMapping("/reduce")
public class PayController {

    @Autowired
    private PayService payService;

    @ApiOperation(value = "扣除余额",notes = "扣除")
    @RequestMapping(value = "/reduceBalance",method = RequestMethod.POST)
    public OperationResponse reduceBalance(@RequestBody PayBo payBo) throws Exception {
        try {
            return payService.reduceBalance(payBo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OperationResponse.builder()
                .data(0)
                .build();
    }
}
