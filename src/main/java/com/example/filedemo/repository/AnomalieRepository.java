package com.example.filedemo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.filedemo.model.Anomalie;
import com.example.filedemo.model.AnomalieType;

@Repository
public interface AnomalieRepository extends CrudRepository<Anomalie, Long>{
    Optional<Anomalie> findFirstByType(AnomalieType anomalieType);
}
