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
import com.example.filedemo.model.Emballage;
import com.example.filedemo.service.ChargeService;
import com.example.filedemo.service.EmballageService;

@RestController
public class ChargeController {

	@Autowired
    ChargeService pservice ;
	
	//http://localhost:8080/retrieve-all-Charges
    @GetMapping("/retrieve-all-Charges")
    @ResponseBody
    public List<Charge> getCharge() {
        List<Charge> list = pservice.retrieveAllCharges();
        return list;
    }

    // http://localhost:8080/retrieve-Charge/{Charge-id}
    @GetMapping("/retrieve-Charge/{Charge-id}")
    @ResponseBody
    public Charge retrieveCharge(@PathVariable("Charge-id") String id) {
        return pservice.retrieveCharge(id);
    }

    // Ajouter  : http://localhost:8080/add-Charge
    @PostMapping("/add-Charge")
    @ResponseBody
    public Charge addCharge(@RequestBody Charge p) {
    	Charge p1 = pservice.addCharge(p);
        return p1;
    }



    // http://localhost:8080/remove-Charge/{id}
    @DeleteMapping("/remove-Charge/{id}")
    @ResponseBody
    public void removeCharge (@PathVariable("id") Long id) {
        pservice.deleteCharge(id);
    }

    // http://localhost:8080/modify-Charge
    @PutMapping("/modify-Charge")
    @ResponseBody
    public Charge modifyCharge(@RequestBody Charge p) {
        return pservice.updateCharge(p);
    }
}
