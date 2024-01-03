package com.subproblem.apikeysecurity.repository;

import com.subproblem.apikeysecurity.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Integer> {

    Optional<ApiKey> findByUserEmail(String email);

    Optional<ApiKey> findByKey(String key);
}
