package com.example.springbootjpadruid.repository;

import com.example.springbootjpadruid.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserModel,String> {
    UserModel getByIdIs(String id);

    UserModel findByNickName(String nickName);

    int countByAge(int age);

    List<UserModel> findByNickNameLike(String nickName);
}
