package com.example.filedemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.filedemo.model.Personnel;
import com.example.filedemo.model.PersonnelRole;
import com.example.filedemo.model.Roles;
import com.example.filedemo.repository.HubRepository;
import com.example.filedemo.repository.PersonnelRepository;

@Service
public class PersonnelService {

	@Autowired
	PersonnelRepository personnelRepository;
	@Autowired
	HubRepository hb;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	EmailSender emailSender;
	@Autowired
	com.example.filedemo.twilio.Service twilioSer;
	@Autowired
	Iuserservice us;

	public List<Personnel> retrieveAllPersonnels() {
		return (List<Personnel>) personnelRepository.findAll();
	}

	public Personnel addPersonnel(Personnel personnel) {
		String hashPW = bCryptPasswordEncoder.encode(personnel.getCin().toString());
		personnel.setPassword(hashPW);
		personnel.setUsername(personnel.getEmail());
		if (personnel.getRole_personnel().equals(PersonnelRole.livreur)) {
			personnel.setCanConnect(false);
		}
		personnel.setRoleUser(Roles.Personnel);
		emailSender.send(personnel.getEmail(), personnel.getUsername(), personnel.getCin().toString());
		return personnelRepository.save(personnel);
	}

	public void deletePersonnel(Long id) {
		personnelRepository.deleteById(id);
	}

	public Personnel retrievePersonnel(long id) {
		return personnelRepository.findById(id).orElse(null);
	}

	public List<Personnel> getLivreurList() {
		return personnelRepository.getLivreurList();
	}

	public List<Personnel> getLivreurListByHub(Long id) {
		return personnelRepository.getLivreurListByHub(id);
	}

	public Personnel changePassWord(long iduser, String password) {
		Personnel personnel = personnelRepository.findById(iduser).orElse(null);
		if (personnel != null) {
			personnel.setPassword(new BCryptPasswordEncoder().encode(password));
			return personnelRepository.save(personnel);
		}
		return null;
	}

}
