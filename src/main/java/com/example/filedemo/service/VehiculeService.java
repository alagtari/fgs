package com.example.filedemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.filedemo.model.Vehicule;
import com.example.filedemo.repository.VehiculeRepository;



@Service
public class VehiculeService {

	@Autowired
	VehiculeRepository slrrepository ;
	
	public List<Vehicule> retrieveAllVehicules() {
        // TODO Auto-generated method stub
        return (List<Vehicule>) slrrepository.findAll();

    }



    public Vehicule addVehicule(Vehicule p) {
        // TODO Auto-generated method stub
        return (slrrepository.save(p));
    }


    public void deleteVehicule(Long id) {
        // TODO Auto-generated method stub
    	slrrepository.deleteById(id);
    }


    public Vehicule updateVehicule(Vehicule p) {
        // TODO Auto-generated method stub
        return (slrrepository.save(p));
    }


    public Vehicule retrieveVehicule(String id) {
        // TODO Auto-generated method stub
        return (slrrepository.findById(Long.parseLong(id)).orElse(null));
    }
	
}
