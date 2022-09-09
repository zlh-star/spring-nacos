package com.example.storageserver.service.Impl;

import com.example.common.OperationResponse;
import com.example.common.storage.StorageBo;
import com.example.storageserver.dao.ProductDao;
import com.example.storageserver.service.StorageService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    @Autowired
    private ProductDao productDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationResponse reduceStock(StorageBo storageBo) throws Exception {
        log.info("当前XID:{}", RootContext.getXID());
        checkStock(storageBo.getProductId(),storageBo.getAmount());

        log.info("开始扣减{}库存",storageBo.getProductId());
        Integer reduceStocks=productDao.reduceStock(storageBo.getProductId(),storageBo.getAmount());
        log.info("扣减库存{}，操作结果:{}",storageBo.getProductId(),reduceStocks>0 ? "扣减成功":"扣减失败");

        return OperationResponse.builder()
                .message(reduceStocks>0 ? "扣减成功":"扣减失败")
                .success(reduceStocks>0)
                .build();
    }

    public void checkStock(Long productId,Integer requiredCount) throws Exception {
        log.info("检查{}库存",productId);
        Integer stock=productDao.getStock(productId);
        if(stock<requiredCount){
            log.warn("{}库存不足，当前库存{}",productId,stock);
            throw new Exception("库存不足");
        }
    }

}
