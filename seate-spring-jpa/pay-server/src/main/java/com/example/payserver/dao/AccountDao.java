package com.example.payserver.dao;

import com.example.payserver.model.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AccountDao extends CrudRepository<Account,Long> {

    @Modifying
    @Query("UPDATE Account SET balance=balance-:balan WHERE id=:userId")
    Integer reduceBalance(@Param("userId") Long userId,@Param("balan") Integer balan);
}
