package com.example.orderserver.service;

import com.example.common.OperationResponse;
import com.example.common.order.OrderBo;
import org.springframework.data.repository.query.Param;

public interface OrderService {
    OperationResponse placeOrder(@Param("orderBo")OrderBo orderBo);
}
