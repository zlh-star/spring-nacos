package com.example.storageserver.controller;

import com.example.common.OperationResponse;
import com.example.common.storage.StorageBo;
import com.example.storageserver.service.StorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "扣减库存")
@RestController
@RequestMapping("/stock")
public class reduceStockController {

    @Autowired
    private StorageService storageService;

    @ApiOperation(value = "库存",notes ="扣减库存")
    @RequestMapping(value = "/reduceStock",method = RequestMethod.POST)
    public OperationResponse reduceStock(@RequestBody StorageBo storageBo) throws Exception {
        try {
            return storageService.reduceStock(storageBo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OperationResponse.builder()
                .data(0)
                .build();
    }
}
