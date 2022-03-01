package com.example.filedemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.filedemo.controller.ColisController;
import com.example.filedemo.model.Colis;
import com.example.filedemo.model.ColisEtat;
import com.example.filedemo.model.Personnel;
import com.example.filedemo.model.Runsheet;
import com.example.filedemo.model.RunsheetEtat;
import com.example.filedemo.payload.RunsheetPayload;
import com.example.filedemo.repository.ColisRepository;
import com.example.filedemo.repository.RunsheetRepository;
import com.example.filedemo.utility.Utility;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

@Service
public class RunsheetServiceImpl implements RunsheetService {
	@Value("${barCodeDirectory.path}")
	private String barCodeDirectory;
	
	@Autowired
	RunsheetRepository runsheetRepository;

	@Autowired
	private ColisService colisService;
	
	@Autowired
	private ColisRepository colisRepository;
	
	@Autowired
	private PersonnelService personnelService;
	
	@Override
	public List<Runsheet> retrieveAllRunsheets() {
		return (List<Runsheet>) runsheetRepository.findAll();
	}
	@Override
	public Runsheet addRunsheet(RunsheetPayload runsheetPayload) {
		Personnel livreur = personnelService.retrievePersonnel(runsheetPayload.getLivreurId());
		Personnel creator = personnelService.retrievePersonnel(runsheetPayload.getLivreurId());
		Runsheet runsheet = new Runsheet();
		runsheet.setEtat(RunsheetEtat.nonCloture);
		runsheet.setLivreur(livreur);
		runsheet.setCreator(creator);
		List<Colis> colis= colisService.findColisByBarCodesList(runsheetPayload.getColisBarCodes());
		runsheet.setTotalPrix((float) colis.stream().mapToDouble(c->c.getCod()).sum());
		runsheet = runsheetRepository.save(runsheet);
		runsheet.setColis(updateEtatColis(colis, runsheet));
		runsheet.setBarCode(generateRunsheetBarCode(runsheet));
		return runsheetRepository.save(runsheet);
	}
	@Override
	public void deleteRunsheet(Long id) {
		List<Colis> listColis = colisService.findColisByRunsheet_code(id);
		listColis.forEach(c->{
			c.setRunsheet(null);
			c.setEtat(ColisEtat.enStock);
			colisRepository.save(c);
		});
		runsheetRepository.deleteById(id);
	}
	@Override
	public Runsheet updateRunsheet(RunsheetPayload runsheetPayload) {
		Runsheet runsheet = runsheetRepository.findById(runsheetPayload.getId()).get();
		if(runsheetPayload.getLivreurId()!=null) {
			Personnel livreur =  personnelService.retrievePersonnel(runsheetPayload.getLivreurId());
            runsheet.setLivreur(livreur);
		}
		float total = colisService.totalCodPerRunsheet(runsheetPayload.getId());
		if (total > 0) {
			runsheet.setTotalPrix(total);
		} else {
			runsheet.setTotalPrix(0);
		}
		runsheet.setBarCode(generateRunsheetBarCode(runsheet));
		return runsheetRepository.save(runsheet);
	}
	@Override
	public Runsheet findByBarCode(String barCode) {
		return runsheetRepository.findByBarCode(barCode).orElse(null);
	}
	@Override
	public Runsheet findById(Long codeRunsheet) {
		return runsheetRepository.findById(codeRunsheet).orElse(null);
	}
	@Override
	public Runsheet addColisToRunsheet(List<String> barCodeList, Long idRunsheet) {
		Runsheet runsheet = findById(idRunsheet);
		float totalRunsheet = 0;
		for (String barCode : barCodeList) {
			Colis colis = colisService.findColisByBarCode(barCode);
			totalRunsheet += colis.getCod();
			colis.setRunsheet(runsheet);
			colis.setEtat(ColisEtat.enCoursDeLivraison);
			colisRepository.save(colis);
		}
		runsheet.setTotalPrix(totalRunsheet+runsheet.getTotalPrix());
		return runsheetRepository.save(runsheet);
	}
    
	@Override
	public String generateRunsheetBarCode(Runsheet runsheet) {
		String text = runsheet.createRunsheetBarCode();
		Utility.createDirectoryIfNotExist(barCodeDirectory);
		String path = barCodeDirectory + "\\" + text + ".jpg";
		try {
			Code128Writer writer = new Code128Writer();
			BitMatrix matrix = writer.encode(text, BarcodeFormat.CODE_128, 400, 90);
			MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return text;
	}
	
	private List<String> existingColisBarCodes(Runsheet runsheet){
		return runsheet.getColis().stream().map(c->c.getBar_code()).collect(Collectors.toList());
	}
	private List<Colis> updateEtatColis(List<Colis> colis, Runsheet runsheet){
        colis.stream().forEach(c->{
			c.setRunsheet(runsheet);
			c.setEtat(ColisEtat.enCoursDeLivraison);
			c = colisRepository.save(c);
		});
        return colis;
	}
	@Override
	public Runsheet encloseRunsheet(Long id) {
		Runsheet runsheet = findById(id);
		runsheet.setEtat(RunsheetEtat.cloture);
		runsheet.getColis().stream().forEach(c->{
			c.setRunsheet(runsheet);
			c.setEtat(ColisEtat.livre);
			c = colisRepository.save(c);
		});
		runsheetRepository.save(runsheet);
		return runsheet;
	}

}
