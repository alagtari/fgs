package com.example.filedemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.filedemo.model.Hub;
import com.example.filedemo.payload.HubPayload;
import com.example.filedemo.repository.HubRepository;



@Service
public class HubService {

	@Autowired
    private HubRepository hubrepository ;
	
	public List<Hub> retrieveAllHubs() {
        return (List<Hub>) hubrepository.findAll();
    }
	
    public Hub addHub(Hub hub) {
    	Hub checkHubExists = hubrepository.findByTitre(hub.getTitre()).orElse(null);
    	if(checkHubExists!=null) throw new RuntimeException("hub exists");
    	ArrayList<Hub> hubs = (ArrayList<Hub>) hubrepository.retrieveHubsHavingGovernoratLie(hub.getGouvernorat());
    	if(hubs.size()!=0) {
    		hubs.stream().forEach(h->{
    			h.getGouvernorat_lie().remove(hub.getGouvernorat());
    			hubrepository.save(h);
    		});
    	}
    	return (hubrepository.save(hub));	
    }


    public List<String> deleteHub(Long id) {
    	Hub hub = hubrepository.findById(id).get();
    	if(hub.getColis().size()==0) {
        	hubrepository.deleteById(id);
        	return new ArrayList<>();
    	}
    	return hub.getColis().stream().
    			map(c->c.getBar_code()).collect(Collectors.toList());
    }


    public Hub updateHub(Hub p) {
        return (hubrepository.save(p));
    }


    public Hub retrieveHub(Long id) {
        return hubrepository.findById(id).orElse(null);
    }
    
	public Hub updateGovernoratsLiesHub(HubPayload hubPayload) {
		Hub hub= hubrepository.findById(hubPayload.getIdHub()).get();
		hub.setGouvernorat_lie(hubPayload.getGovernoratsLies());
		hub = hubrepository.save(hub);
		return hub;
	}
}
