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
import com.example.filedemo.model.Paiement_perso;
import com.example.filedemo.service.ChargeService;
import com.example.filedemo.service.Paiement_persoService;

@RestController
public class Paiement_persoController {

	@Autowired
	Paiement_persoService pservice ;
	
	//http://localhost:8080/retrieve-all-Paiement_persos
    @GetMapping("/retrieve-all-Paiement_persos")
    @ResponseBody
    public List<Paiement_perso> getPaiement_perso() {
        List<Paiement_perso> list = pservice.retrieveAllPaiement_persos();
        return list;
    }

    // http://localhost:8080/retrieve-Paiement_perso/{Paiement_perso-id}
    @GetMapping("/retrieve-Paiement_perso/{Paiement_perso-id}")
    @ResponseBody
    public Paiement_perso retrievePaiement_perso(@PathVariable("Paiement_perso-id") String id) {
        return pservice.retrievePaiement_perso(id);
    }

    // Ajouter  : http://localhost:8080/add-Paiement_perso
    @PostMapping("/add-Paiement_perso")
    @ResponseBody
    public Paiement_perso addPaiement_perso(@RequestBody Paiement_perso p) {
    	Paiement_perso p1 = pservice.addPaiement_perso(p);
        return p1;
    }



    // http://localhost:8080/remove-Paiement_perso/{id}
    @DeleteMapping("/remove-Paiement_perso/{id}")
    @ResponseBody
    public void removePaiement_perso (@PathVariable("id") Long id) {
        pservice.deletePaiement_perso(id);
    }

    // http://localhost:8080/modify-Paiement_perso
    @PutMapping("/modify-Paiement_perso")
    @ResponseBody
    public Paiement_perso modifyPaiement_perso(@RequestBody Paiement_perso p) {
        return pservice.updatePaiement_perso(p);
    }
	
}
