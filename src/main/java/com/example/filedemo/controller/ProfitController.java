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
import com.example.filedemo.model.Profit;
import com.example.filedemo.service.ChargeService;
import com.example.filedemo.service.ProfitService;

@RestController
public class ProfitController {

	@Autowired
    ProfitService pservice ;
	
	//http://localhost:8080/retrieve-all-Profits
    @GetMapping("/retrieve-all-Profits")
    @ResponseBody
    public List<Profit> getProfit() {
        List<Profit> list = pservice.retrieveAllProfits();
        return list;
    }

    // http://localhost:8080/retrieve-Profit/{Profit-id}
    @GetMapping("/retrieve-Profit/{Profit-id}")
    @ResponseBody
    public Profit retrieveProfit(@PathVariable("Profit-id") String id) {
        return pservice.retrieveProfit(id);
    }

    // Ajouter  : http://localhost:8080/add-Profit
    @PostMapping("/add-Profit")
    @ResponseBody
    public Profit addProfit(@RequestBody Profit p) {
    	Profit p1 = pservice.addProfit(p);
        return p1;
    }



    // http://localhost:8080/remove-Profit/{id}
    @DeleteMapping("/remove-Profit/{id}")
    @ResponseBody
    public void removeProfit (@PathVariable("id") Long id) {
        pservice.deleteProfit(id);
    }

    // http://localhost:8080/modify-Profit
    @PutMapping("/modify-Profit")
    @ResponseBody
    public Profit modifyProfit(@RequestBody Profit p) {
        return pservice.updateProfit(p);
    }
	
}
