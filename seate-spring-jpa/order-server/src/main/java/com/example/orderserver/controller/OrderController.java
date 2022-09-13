package com.example.orderserver.controller;

import com.example.common.OperationResponse;
import com.example.common.order.OrderBo;
import com.example.orderserver.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "下单")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "下单",tags = "下单")
    @RequestMapping(value = "/order",method = RequestMethod.POST)
    public OperationResponse order(@RequestBody OrderBo orderBo){
        try {
            return orderService.placeOrder(orderBo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OperationResponse.builder()
                .data(0)
                .build();
    }

}
