package com.example.filedemo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.filedemo.model.Fournisseur;
import com.example.filedemo.model.Personnel;
import com.example.filedemo.repository.FournisseurRepository;
import com.example.filedemo.repository.PersonnelRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApp {
    @Autowired
    FournisseurRepository repo;
    
    @Autowired
    PersonnelRepository repository;

	
	/*
	 * @Test public void testFourn() { Fournisseur fournisseur = new Fournisseur();
	 * fournisseur.setAdresse_livraison("t2"); fournisseur.setAdresse_societe("t2");
	 * fournisseur.setCin("t2"); //fournisseur.setCode_postal_livraison(8100);
	 * //fournisseur.setCode_postal_societe(4000); fournisseur.setEmail("t2");
	 * fournisseur.setTel_f(5411554);
	 * 
	 * fournisseur = repo.save(fournisseur); assertEquals(3,
	 * fournisseur.getIduser()); }
	 */
	@Test
	public void testPersonnel() {
		Personnel personnel = new Personnel();
		personnel.setCarte_grise("caretGrise");
		personnel.setCin(0512345l);
		personnel.setEmail("test1@gmail.com");
		personnel.setNom("nom");
		personnel.setImage("dkdjd");
		//personnel.setMail("test1@gmail.com");
		personnel.setPassword("123456");
		personnel.setUsername("username1");
		personnel= repository.save(personnel);
		assertEquals(3, personnel.getIduser());
	}

}
