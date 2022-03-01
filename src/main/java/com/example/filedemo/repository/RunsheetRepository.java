package com.example.filedemo.repository;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.filedemo.model.Runsheet;
@Repository
public interface RunsheetRepository extends CrudRepository<Runsheet, Long>{
	Optional<Runsheet> findByBarCode(String barCode);
}
