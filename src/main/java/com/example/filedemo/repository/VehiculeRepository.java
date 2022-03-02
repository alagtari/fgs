package com.example.filedemo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.filedemo.model.Vehicule;

@Repository
public interface VehiculeRepository extends CrudRepository<Vehicule, Long> {

}
