package com.example.filedemo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.filedemo.model.Pickup;

@Repository
public interface PickupRepository extends CrudRepository<Pickup, Long>{

}
