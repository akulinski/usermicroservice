package com.msi.usermicroservice.core.repositories;

import com.msi.usermicroservice.core.entites.AuthorityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends CrudRepository<AuthorityEntity,Integer> {
}
