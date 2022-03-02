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

import com.example.filedemo.model.Charge;
import com.example.filedemo.model.Pickup;
import com.example.filedemo.service.ChargeService;
import com.example.filedemo.service.PickupService;

@RestController
public class PickupController {

	@Autowired
    PickupService pservice ;
	
	//http://localhost:8080/retrieve-all-Pickups
    @GetMapping("/retrieve-all-Pickups")
    @ResponseBody
    public List<Pickup> getPickup() {
        List<Pickup> list = pservice.retrieveAllPickups();
        return list;
    }

    // http://localhost:8080/retrieve-Pickup/{Pickup-id}
    @GetMapping("/retrieve-Pickup/{Pickup-id}")
    @ResponseBody
    public Pickup retrievePickup(@PathVariable("Pickup-id") String id) {
        return pservice.retrievePickup(id);
    }

    // Ajouter  : http://localhost:8080/add-Pickup
    @PostMapping("/add-Pickup")
    @ResponseBody
    public Pickup addPickup(@RequestBody Pickup p) {
    	Pickup p1 = pservice.addPickup(p);
        return p1;
    }



    // http://localhost:8080/remove-Pickup/{id}
    @DeleteMapping("/remove-Pickup/{id}")
    @ResponseBody
    public void removePickup (@PathVariable("id") Long id) {
        pservice.deletePickup(id);
    }

    // http://localhost:8080/modify-Pickup
    @PutMapping("/modify-Pickup")
    @ResponseBody
    public Pickup modifyPickup(@RequestBody Pickup p) {
        return pservice.updatePickup(p);
    }
	
}
