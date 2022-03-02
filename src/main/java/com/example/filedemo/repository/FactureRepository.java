package com.example.filedemo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.filedemo.model.Facture;
import com.example.filedemo.model.Fournisseur;

@Repository
public interface FactureRepository  extends CrudRepository<Facture,Long> {

}
