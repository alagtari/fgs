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

import com.example.filedemo.model.Emballage;
import com.example.filedemo.service.EmballageService;



@RestController
public class EmballageController {


	@Autowired
    EmballageService pservice ;
	
	//http://localhost:8080/retrieve-all-Emballages
    @GetMapping("/retrieve-all-Emballages")
    @ResponseBody
    public List<Emballage> getFacture() {
        List<Emballage> list = pservice.retrieveAllEmballages();
        return list;
    }

    // http://localhost:8080/retrieve-Emballage/{Emballage-id}
    @GetMapping("/retrieve-Emballage/{Emballage-id}")
    @ResponseBody
    public Emballage retrieveEmballage(@PathVariable("Emballage-id") String id) {
        return pservice.retrieveEmballage(id);
    }

    // Ajouter  : http://localhost:8080/add-Emballage
    @PostMapping("/add-Emballage")
    @ResponseBody
    public Emballage addEmballage(@RequestBody Emballage p) {
    	Emballage p1 = pservice.addEmballage(p);
        return p1;
    }



    // http://localhost:8080/remove-Emballage/{id}
    @DeleteMapping("/remove-Emballage/{id}")
    @ResponseBody
    public void removeEmballage (@PathVariable("id") Long id) {
        pservice.deleteEmballage(id);
    }

    // http://localhost:8080/modify-Emballage
    @PutMapping("/modify-Emballage")
    @ResponseBody
    public Emballage modifyEmballage(@RequestBody Emballage p) {
        return pservice.updateEmballage(p);
    }
}
