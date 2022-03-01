package com.example.filedemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.filedemo.model.Charge;
import com.example.filedemo.model.Emballage;
import com.example.filedemo.repository.ChargeRepository;
import com.example.filedemo.repository.EmballageRepository;

@Service
public class ChargeService {

	@Autowired
    ChargeRepository prepository ;
	
	public List<Charge> retrieveAllCharges() {
        // TODO Auto-generated method stub
        return (List<Charge>) prepository.findAll();

    }



    public Charge addCharge(Charge p) {
        // TODO Auto-generated method stub
        return (prepository.save(p));
    }


    public void deleteCharge(Long id) {
        // TODO Auto-generated method stub
        prepository.deleteById(id);
    }


    public Charge updateCharge(Charge p) {
        // TODO Auto-generated method stub
        return (prepository.save(p));
    }


    public Charge retrieveCharge(String id) {
        // TODO Auto-generated method stub
        return (prepository.findById(Long.parseLong(id)).orElse(null));
    }
}
