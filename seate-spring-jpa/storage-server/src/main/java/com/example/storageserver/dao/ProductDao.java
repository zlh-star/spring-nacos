package com.example.storageserver.dao;

import com.example.storageserver.model.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductDao extends CrudRepository<Product,Long>{

    @Query("SELECT stock FROM Product WHERE id=:productId")
    Integer getStock(@Param("productId") Long productId);

    @Modifying
    @Query("UPDATE Product SET stock=stock-:count WHERE id=:productId")
    Integer reduceStock(@Param("productId")Long productId,@Param("count") Integer count);
}
