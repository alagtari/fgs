package com.example.filedemo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.filedemo.model.Profit;

@Repository
public interface ProfitRepository extends CrudRepository<Profit, Long>{

}
