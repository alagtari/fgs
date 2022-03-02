package com.example.filedemo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.filedemo.model.Charge;

@Repository
public interface ChargeRepository extends CrudRepository<Charge, Long>{

}
