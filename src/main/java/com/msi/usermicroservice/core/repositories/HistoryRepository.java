package com.msi.usermicroservice.core.repositories;

import com.msi.usermicroservice.core.entites.HistoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface HistoryRepository extends CrudRepository<HistoryEntity, Integer> {
    ArrayList<HistoryEntity> findAll();
}
