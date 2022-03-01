package com.example.filedemo.service;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.filedemo.model.Colis;
import com.example.filedemo.model.ColisEtat;
import com.example.filedemo.model.Console;
import com.example.filedemo.model.ConsoleEtat;
import com.example.filedemo.model.Hub;
import com.example.filedemo.model.Personnel;
import com.example.filedemo.payload.ConsolePayload;
import com.example.filedemo.repository.ColisRepository;
import com.example.filedemo.repository.ConsoleRepository;
import com.example.filedemo.utility.Utility;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

@Service
public class ConsoleService {
	@Value("${barCodeDirectory.path}")
	private String barCodeDirectory;
	@Autowired
	ConsoleRepository consoleRepository;
	@Autowired
	ColisService colisService;
	@Autowired
	FournisseurService fournisseurService;
	@Autowired
	ColisRepository colisRepository;
	@Autowired
	PersonnelService personnelService;
	@Autowired
	HubService hubService;
	public List<Console> retrieveAllConsoles() {
		return (List<Console>) consoleRepository.findAll();
	}

	public Console addConsole(ConsolePayload consolePayload) {
		Console console = new Console();
		Personnel creator = personnelService.retrievePersonnel(consolePayload.getIdCreator());
		Personnel livreur = personnelService.retrievePersonnel(consolePayload.getIdLivreur());
		console.setEtat(ConsoleEtat.enAttente);
		console.setIdHubArrivee(consolePayload.getIdHubArrivee());
		console.setIdHubDepart(consolePayload.getIdHubDepart());
		console.setTitreHubArrivee(hubService.retrieveHub(consolePayload.getIdHubArrivee()).getTitre());
		console.setTitreHubDepart(hubService.retrieveHub(consolePayload.getIdHubDepart()).getTitre());
		console.setCreator(creator);
		console.setLivreur(livreur);
		console = consoleRepository.save(console);
		generateColisBarCode(console.createBarCode());
		console.setColis(processColisConsole(consolePayload.getColisBarCode(), console));
		return consoleRepository.save(console);
	}

	public void deleteConsole(Long id) {
		Console console = consoleRepository.findById(id).get();
		console.getColis().stream().forEach(c -> {
			c.setConsole(null);
	        c.setEtat(ColisEtat.enAttenteDEnlevement);
			colisRepository.save(c);
		});
		consoleRepository.deleteById(id);
	}

	public Console updateConsole(Console p) {
		return (consoleRepository.save(p));
	}

	public Console retrieveConsole(String id) {
		return (consoleRepository.findById(Long.parseLong(id)).orElse(null));
	}

	public Hub getArriveeByBarCode(String barcode) {
		if (colisService.findColisByBarCode(barcode).getHub() == null) {
			return null;
		} else {
			return colisService.findColisByBarCode(barcode).getHub();
		}
	}

	private List<Colis> processColisConsole(List<String> colisBarCode, Console console) {
		List<Colis> coliss = new ArrayList<>();
		colisBarCode.stream().forEach(c -> {
			Colis colis = colisRepository.findColisByBarCode(c);
			colis.setConsole(console);
			colis.setEtat(ColisEtat.enCoursDeTransfert);
			coliss.add(colisRepository.save(colis));
		});
		return coliss;
	}

	public Console removeColisFromConsole(String barCode, Long idConsole) {
           Colis colis = colisRepository.findColisByBarCode(barCode);
           colis.setConsole(null);
           colis.setEtat(ColisEtat.enAttenteDEnlevement);
           colisRepository.save(colis);
           return consoleRepository.findById(idConsole).get();
	}
	private void generateColisBarCode(String consoleBarCode) {
		Utility.createDirectoryIfNotExist(barCodeDirectory);
		String path = barCodeDirectory + "\\" + consoleBarCode + ".jpg";
		try {
			Code128Writer writer = new Code128Writer();
			BitMatrix matrix = writer.encode(consoleBarCode, BarcodeFormat.CODE_128, 400, 90);
			MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public Console findConsoleByBarCode(String barCode) {
		return consoleRepository.findByBarCode(barCode).orElse(null);
	}
	
	public Console approveConsole(String barCode, long idValidator) {
		Console console = consoleRepository.findByBarCode(barCode).orElse(null);
		if(console==null) return null;
		Personnel validator = personnelService.retrievePersonnel(idValidator);
		console.setValidator(validator);
		console.setEtat(ConsoleEtat.approuve);
		console.getColis().stream().forEach(c -> {
			c.setEtat(ColisEtat.enStock);
			c=colisRepository.save(c);
		});
		return consoleRepository.save(console);
	}
	public List<Console> findConsoleByIdValidor(long idValidator){
		return consoleRepository.findByValidator(idValidator);
	}
	public List<Console> findConsoleByIdCreator(long idCreator){
		return consoleRepository.findByCreator(idCreator);
	}
	public List<Console> findConsolesEntrant(long idPersonnel){
		Personnel personnel=personnelService.retrievePersonnel(idPersonnel);
		if (personnel==null) return new ArrayList<>();
		Hub hubPersonnel = personnel.getHub();
		return hubPersonnel!=null?consoleRepository.findByIdHubArrivee(hubPersonnel.getId_hub()): new ArrayList<>();
	}
	public List<Console> findConsolesSortant(long idPersonnel){
		Personnel personnel=personnelService.retrievePersonnel(idPersonnel);
		if (personnel==null) return new ArrayList<>();
		Hub hubPersonnel = personnel.getHub();
		return hubPersonnel!=null?consoleRepository.findByIdHubDepart(hubPersonnel.getId_hub()): new ArrayList<>();
	}
}
