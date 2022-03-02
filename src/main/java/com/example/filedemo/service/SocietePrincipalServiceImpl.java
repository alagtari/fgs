package com.example.filedemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.filedemo.model.SocietePrincipal;
import com.example.filedemo.repository.SocietePrincipalRepository;

@Service
public class SocietePrincipalServiceImpl implements SocietePrincipalService {
    @Autowired
    SocietePrincipalRepository societePrincipalRepository;
    
	@Override
	public SocietePrincipal configureSocietePrincipal(SocietePrincipal societePrincipal) {
		return societePrincipalRepository.save(societePrincipal);
	}

	@Override
	public SocietePrincipal updateSocietePrincipal(SocietePrincipal societePrincipal) {
		return societePrincipalRepository.save(societePrincipal);
	}

	@Override
	public SocietePrincipal retrieveConfigSocietePrincipal() {
		return societePrincipalRepository.findFirstByOrderByIdAsc().orElse(null);
	}

}
