package com.example.filedemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.filedemo.model.Pickup;
import com.example.filedemo.repository.PickupRepository;



@Service
public class PickupService {

	@Autowired
    PickupRepository prepository ;
	
	public List<Pickup> retrieveAllPickups() {
        // TODO Auto-generated method stub
        return (List<Pickup>) prepository.findAll();

    }



    public Pickup addPickup(Pickup p) {
        // TODO Auto-generated method stub
        return (prepository.save(p));
    }


    public void deletePickup(Long id) {
        // TODO Auto-generated method stub
        prepository.deleteById(id);
    }


    public Pickup updatePickup(Pickup p) {
        // TODO Auto-generated method stub
        return (prepository.save(p));
    }


    public Pickup retrievePickup(String id) {
        // TODO Auto-generated method stub
        return (prepository.findById(Long.parseLong(id)).orElse(null));
    }
	
}
