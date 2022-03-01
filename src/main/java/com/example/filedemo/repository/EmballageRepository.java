package com.example.filedemo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.filedemo.model.Emballage;

@Repository
public interface EmballageRepository extends CrudRepository<Emballage, Long>{

}
