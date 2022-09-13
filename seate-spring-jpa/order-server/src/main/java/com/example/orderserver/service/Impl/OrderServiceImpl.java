package com.example.orderserver.service.Impl;

import com.example.common.OperationResponse;
import com.example.common.order.OrderBo;
import com.example.common.pay.PayBo;
import com.example.common.storage.StorageBo;
import com.example.orderserver.dao.OrderDao;
import com.example.orderserver.model.Order;
import com.example.orderserver.model.OrderStatus;
import com.example.orderserver.service.OrderService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderDao orderDao;

    private final String STORAGE_SERVICE_HOST = "http://storage-server/stock";
    private final String PAY_SERVICE_HOST = "http://pay-server/reduce";

    @Override
    @GlobalTransactional
    public OperationResponse placeOrder(OrderBo orderBo) {
        Integer amount = 1;
        Integer price = orderBo.getPrice();

        Order order = Order.builder()
                .userId(orderBo.getUserId())
                .productId(orderBo.getProductId())
                .status(OrderStatus.INIT)
                .payAmount(price)
                .build();


        // 扣减余额
        log.info("开始扣减余额");
        PayBo reduceBalanceRequestVO = PayBo.builder()
                .userId(orderBo.getUserId())
                .price(price)
                .build();

        String reduceBalanceUrl = String.format("%s/reduceBalance", PAY_SERVICE_HOST);
        OperationResponse balanceOperationResponse = restTemplate.postForObject(reduceBalanceUrl, reduceBalanceRequestVO, OperationResponse.class);
        log.info("扣减余额结果:{}", balanceOperationResponse);

        order = orderDao.save(order);

        log.info("保存订单{}", order.getId() != null ? "成功" : "失败");
        log.info("当前 XID: {}", RootContext.getXID());

        // 扣减库存
        log.info("开始扣减库存");
        StorageBo storageBo = StorageBo.builder()
                .productId(orderBo.getProductId())
                .amount(amount)
                .build();
        String storageReduceUrl = String.format("%s/reduceStock", STORAGE_SERVICE_HOST);
        OperationResponse storageOperationResponse = restTemplate.postForObject(storageReduceUrl, storageBo, OperationResponse.class);
        log.info("扣减库存结果:{}", storageOperationResponse);


        Integer updateOrderRecord = orderDao.updateOrder(order.getId(), OrderStatus.SUCCESS);
        log.info("更新订单:{} {}", order.getId(), updateOrderRecord > 0 ? "成功" : "失败");

        return OperationResponse.builder()
                .success(balanceOperationResponse.isSuccess() && storageOperationResponse.isSuccess())
                .build();
    }
}
