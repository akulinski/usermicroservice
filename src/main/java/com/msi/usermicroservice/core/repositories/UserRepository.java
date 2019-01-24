package com.msi.usermicroservice.core.repositories;

import com.msi.usermicroservice.core.entites.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    ArrayList<UserEntity> findAll();
    Optional<UserEntity> findByUsernameAndPassword(String username,String password);
}
