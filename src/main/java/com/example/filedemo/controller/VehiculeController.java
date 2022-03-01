package com.example.filedemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.filedemo.model.Vehicule;
import com.example.filedemo.service.VehiculeService;



@RestController
public class VehiculeController {

	@Autowired
	VehiculeService pservice ;
	
	//http://localhost:8080/retrieve-all-Vehicules
    @GetMapping("/retrieve-all-Vehicules")
    @ResponseBody
    public List<Vehicule> getVehicule() {
        List<Vehicule> list = pservice.retrieveAllVehicules();
        return list;
    }

    // http://localhost:8080/retrieve-Vehicule/{Vehicule-id}
    @GetMapping("/retrieve-Vehicule/{Vehicule-id}")
    @ResponseBody
    public Vehicule retrieveVehicule(@PathVariable("Vehicule-id") String id) {
        return pservice.retrieveVehicule(id);
    }

    // Ajouter  : http://localhost:8080/add-Vehicule
    @PostMapping("/add-Vehicule")
    @ResponseBody
    public Vehicule addVehicule(@RequestBody Vehicule p) {
    	Vehicule p1 = pservice.addVehicule(p);
        return p1;
    }



    // http://localhost:8080/remove-Vehicule/{id}
    @DeleteMapping("/remove-Vehicule/{id}")
    @ResponseBody
    public void removeVehicule(@PathVariable("id") Long id) {
        pservice.deleteVehicule(id);
    }

    // http://localhost:8080/modify-Vehicule
    @PutMapping("/modify-Vehicule")
    @ResponseBody
    public Vehicule modifyVehicule(@RequestBody Vehicule p) {
        return pservice.updateVehicule(p);
    }
	
}
