package com.example.payserver.service;

import com.example.common.OperationResponse;
import com.example.common.pay.PayBo;
import org.springframework.data.repository.query.Param;

public interface PayService {
    OperationResponse reduceBalance(@Param("payBo")PayBo payBo) throws Exception;
}
