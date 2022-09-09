package com.example.payserver.service.Impl;

import com.example.common.OperationResponse;
import com.example.common.pay.PayBo;
import com.example.payserver.dao.AccountDao;
import com.example.payserver.model.Account;
import com.example.payserver.service.PayService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    @Autowired
    private AccountDao accountDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationResponse reduceBalance(PayBo payBo) throws Exception {
        log.info("当前 XID: {}", RootContext.getXID());

        checkBalance(payBo.getUserId(),payBo.getPrice());

        log.info("开始扣减用户 {} 余额", payBo.getUserId());
        Integer record = accountDao.reduceBalance(payBo.getUserId(), payBo.getPrice());
        log.info("扣减用户 {} 余额结果:{}", payBo.getUserId(), record > 0 ? "操作成功" : "扣减余额失败");

        return OperationResponse.builder()
                .success(record > 0)
                .message(record > 0 ? "操作成功" : "扣余额失败")
                .build();
    }

    public void checkBalance(Long userId,Integer price) throws Exception {
        log.info("检查当前账户{}余额",userId);
        Optional<Account> account=accountDao.findById(userId);
        if(account.isPresent()){
            Integer balance=account.get().getBalance();
            if(balance<price){
                log.warn("当前账户:{}余额不足，当前余额:{}",userId,balance);
                throw new Exception("余额不足");
            }
        }
    }
}
