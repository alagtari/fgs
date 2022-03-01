package com.example.filedemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.filedemo.model.Facture;
import com.example.filedemo.model.Personnel;
import com.example.filedemo.repository.FactureRepository;
import com.example.filedemo.repository.PersonnelRepository;

@Service
public class FactureService {

	@Autowired
    FactureRepository prepository ;
	
	public List<Facture> retrieveAllFactures() {
        // TODO Auto-generated method stub
        return (List<Facture>) prepository.findAll();

    }



    public Facture addFacture(Facture p) {
        // TODO Auto-generated method stub
        return (prepository.save(p));
    }


    public void deleteFacture(Long id) {
        // TODO Auto-generated method stub
        prepository.deleteById(id);
    }


    public Facture updateFacture(Facture p) {
        // TODO Auto-generated method stub
        return (prepository.save(p));
    }


    public Facture retrieveFacture(String id) {
        // TODO Auto-generated method stub
        return (prepository.findById(Long.parseLong(id)).orElse(null));
    }
}
