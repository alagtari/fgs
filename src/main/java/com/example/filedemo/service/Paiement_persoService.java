package com.example.filedemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.filedemo.model.Paiement_perso;
import com.example.filedemo.repository.Paiement_persoRepository;

@Service
public class Paiement_persoService {

	@Autowired
	Paiement_persoRepository prepository ;
	
	public List<Paiement_perso> retrieveAllPaiement_persos() {
        // TODO Auto-generated method stub
        return (List<Paiement_perso>) prepository.findAll();

    }


    public Paiement_perso addPaiement_perso(Paiement_perso p) {
        // TODO Auto-generated method stub
        return (prepository.save(p));
    }


    public void deletePaiement_perso(Long id) {
        // TODO Auto-generated method stub
        prepository.deleteById(id);
    }


    public Paiement_perso updatePaiement_perso(Paiement_perso p) {
        // TODO Auto-generated method stub
        return (prepository.save(p));
    }


    public Paiement_perso retrievePaiement_perso(String id) {
        // TODO Auto-generated method stub
        return (prepository.findById(Long.parseLong(id)).orElse(null));
    }
	
}
