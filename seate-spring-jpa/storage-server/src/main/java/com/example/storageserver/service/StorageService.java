package com.example.storageserver.service;

import com.example.common.OperationResponse;
import com.example.common.storage.StorageBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

public interface StorageService {
    OperationResponse reduceStock(@Param("storageBo") StorageBo storageBo) throws Exception;
}
