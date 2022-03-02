package com.example.filedemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.filedemo.model.Charge;
import com.example.filedemo.model.Profit;
import com.example.filedemo.repository.ChargeRepository;
import com.example.filedemo.repository.ProfitRepository;

@Service
public class ProfitService {

	@Autowired
    ProfitRepository prepository ;
	
	public List<Profit> retrieveAllProfits() {
        // TODO Auto-generated method stub
        return (List<Profit>) prepository.findAll();

    }



    public Profit addProfit(Profit p) {
        // TODO Auto-generated method stub
        return (prepository.save(p));
    }


    public void deleteProfit(Long id) {
        // TODO Auto-generated method stub
        prepository.deleteById(id);
    }


    public Profit updateProfit(Profit p) {
        // TODO Auto-generated method stub
        return (prepository.save(p));
    }


    public Profit retrieveProfit(String id) {
        // TODO Auto-generated method stub
        return (prepository.findById(Long.parseLong(id)).orElse(null));
    }
	
}
