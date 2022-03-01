package com.example.filedemo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.filedemo.model.Paiement_perso;

@Repository
public interface Paiement_persoRepository extends CrudRepository<Paiement_perso, Long>{

}
