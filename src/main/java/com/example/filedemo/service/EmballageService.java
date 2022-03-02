package com.example.filedemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.filedemo.model.Emballage;
import com.example.filedemo.repository.EmballageRepository;



@Service
public class EmballageService {

	@Autowired
    EmballageRepository prepository ;
	
	public List<Emballage> retrieveAllEmballages() {
        // TODO Auto-generated method stub
        return (List<Emballage>) prepository.findAll();

    }



    public Emballage addEmballage(Emballage p) {
        // TODO Auto-generated method stub
        return (prepository.save(p));
    }


    public void deleteEmballage(Long id) {
        // TODO Auto-generated method stub
        prepository.deleteById(id);
    }


    public Emballage updateEmballage(Emballage p) {
        // TODO Auto-generated method stub
        return (prepository.save(p));
    }


    public Emballage retrieveEmballage(String id) {
        // TODO Auto-generated method stub
        return (prepository.findById(Long.parseLong(id)).orElse(null));
    }
}
