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

import com.example.filedemo.model.Facture;
import com.example.filedemo.model.Personnel;
import com.example.filedemo.service.FactureService;
import com.example.filedemo.service.PersonnelService;

@RestController
public class FactureController {

	@Autowired
    FactureService pservice ;
	
	//http://localhost:8080/retrieve-all-Factures
    @GetMapping("/retrieve-all-Factures")
    @ResponseBody
    public List<Facture> getFacture() {
        List<Facture> list = pservice.retrieveAllFactures();
        return list;
    }

    // http://localhost:8080/retrieve-Facture/{Facture-id}
    @GetMapping("/retrieve-Facture/{Facture-id}")
    @ResponseBody
    public Facture retrieveFacture(@PathVariable("Facture-id") String id) {
        return pservice.retrieveFacture(id);
    }

    // Ajouter  : http://localhost:8080/add-Facture
    @PostMapping("/add-Facture")
    @ResponseBody
    public Facture addFacture(@RequestBody Facture p) {
    	Facture p1 = pservice.addFacture(p);
        return p1;
    }



    // http://localhost:8080/remove-Facture/{id}
    @DeleteMapping("/remove-Facture/{id}")
    @ResponseBody
    public void removeFacture (@PathVariable("id") Long id) {
        pservice.deleteFacture(id);
    }

    // http://localhost:8080/modify-Facture
    @PutMapping("/modify-Facture")
    @ResponseBody
    public Facture modifyFacture(@RequestBody Facture p) {
        return pservice.updateFacture(p);
    }
}
