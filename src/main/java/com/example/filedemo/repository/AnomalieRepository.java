package com.example.filedemo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.filedemo.model.Anomalie;

@Repository
public interface AnomalieRepository extends CrudRepository<Anomalie, Long>{

}
