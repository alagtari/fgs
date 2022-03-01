package com.example.filedemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.filedemo.model.Colis;
import com.example.filedemo.model.Fournisseur;
import com.example.filedemo.model.Hub;
import com.example.filedemo.model.Roles;
import com.example.filedemo.repository.ColisRepository;
import com.example.filedemo.repository.FournisseurRepository;

@Service
public class FournisseurService {

	@Autowired
	private final FournisseurRepository fournisseurRepository;

	@Autowired
	private ColisService colisservice;

	@Autowired
	private ColisRepository colisRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	EmailSender emailSender;
	@Autowired
	com.example.filedemo.twilio.Service twilioSer;
	@Autowired
	Iuserservice us;
	@Autowired
	private HubService hubService;
	@Autowired
	public FournisseurService(FournisseurRepository fournisseurRepository) {
		this.fournisseurRepository = fournisseurRepository;
	}

	public Optional<Fournisseur> findById(Long id) {
		return fournisseurRepository.findById(id);
	}

	public Fournisseur saveFournisseur(Fournisseur fournisseur) {
		return fournisseurRepository.save(fournisseur);
	}

	public Fournisseur updateFournisseur(Fournisseur fournisseur) {
		return fournisseurRepository.save(fournisseur);
	}

	/*
	 * public List<Fournisseur> getFournisseurBySocieteLiv(Long id) { return
	 * fournisseurRepository.getFournisseurBySocieteLiv(id); }
	 */

	public List<Fournisseur> getAllFournisseur() {
		return fournisseurRepository.findAll();
	}

	public void deleteLogiqueFournisseur(Long id) {
		Fournisseur deletedFournisseur = findById(id).get();

		deletedFournisseur.isDeleted = true;
		deletedFournisseur.setUsername(null);
		deletedFournisseur.setPassword(null);
		List<Colis> listColis = colisservice.findAllColisByFournisseur(id);

		for (Colis colis : listColis) {
			colis.setFournisseur(null);
			colisRepository.save(colis);
		}
		updateFournisseur(deletedFournisseur);
	}

	public Object updateFournisseur(Fournisseur e, Long id) {

		return fournisseurRepository.updateFournisseur(e.getNom_societe(), e.getNom_f(), e.getPrenom_f(),
				e.getCin(), e.getDate_fin_contrat(), e.getAdresse_societe(), e.getGouvernorat_societe(),
				e.getLocalite_societe(), e.getDelegation_societe(), e.getAdresse_livraison(),
				e.getGouvernorat_livraison(), e.getLocalite_livraison(), e.getDelegation_livraison(),
				e.getPrix_livraison(), e.getPrix_retour(), e.getPassword(), e.getIduser());
	}

	public Fournisseur saveFour(Fournisseur four) {
		String hashPW = bCryptPasswordEncoder.encode(four.getCin());
		four.setPassword(hashPW);
		four.setUsername(four.getEmail());
		four.setRoleUser(Roles.Fournisseur);
		//four.setImage(four.getLogo());
		emailSender.send(four.getEmail(),four.getUsername(), four.getCin());
		return fournisseurRepository.save(four);
	}

	public void update(Fournisseur fournisseur) {
		String encryptedPassword = new BCryptPasswordEncoder().encode(fournisseur.getPassword());
		fournisseur.setPassword(encryptedPassword);
		fournisseurRepository.save(fournisseur);
	}

	public void changePassWord(Long id, String password) {
		Fournisseur fournisseur = fournisseurRepository.findById(id).get();
		if (fournisseur != null) {
			fournisseur.setPassword(password);
			this.update(fournisseur);
		}
	}
	public Hub getRelatedHub(String fournisseurGovernorat) {
		if (fournisseurGovernorat != null) {
			return hubService.retrieveAllHubs().stream().filter(
					h -> h.getGouvernorat().equals(fournisseurGovernorat) || h.getGouvernorat_lie().contains(fournisseurGovernorat))
					.findFirst().orElse(null);
		}
		return null;
	}

}
