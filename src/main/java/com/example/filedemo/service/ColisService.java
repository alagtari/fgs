package com.example.filedemo.service;

import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.filedemo.controller.FournisseurController;
import com.example.filedemo.model.Anomalie;
import com.example.filedemo.model.Colis;
import com.example.filedemo.model.ColisEtat;
import com.example.filedemo.model.Console;
import com.example.filedemo.model.Dispatch;
import com.example.filedemo.model.Fournisseur;
import com.example.filedemo.model.Hub;
import com.example.filedemo.payload.ColisHubsResponsePayload;
import com.example.filedemo.repository.ColisRepository;
import com.example.filedemo.repository.ColisRepository.HistoStateOnly;
import com.example.filedemo.repository.FournisseurRepository;
import com.example.filedemo.repository.HubRepository;
import com.example.filedemo.utility.Utility;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

@Service
public class ColisService {
	@Value("${barCodeDirectory.path}")
	private String barCodeDirectory;

	@Autowired
	private FournisseurService fournisseurService;

	@Autowired
	private FournisseurRepository fournisseurRepository;

	@Autowired
	private HubService hubService;
	@Autowired
	private HubRepository hubRepository;
	@Autowired
	private final ColisRepository colisRepository;
	@Autowired
	private AnomalieService anomalieService;
	
	@Autowired
	public ColisService(ColisRepository colisRepository) {
		this.colisRepository = colisRepository;
	}

	// save Colis
	public Colis saveColis(Colis colis) {
		colis.setEtat(ColisEtat.cree);
		Hub hub = getRelatedHub(colis.getGouvernorat());
		if (hub != null) {
			colis.setHub(hub);
		}
		if (colis.getService().equals(com.example.filedemo.model.ColisService.echange)
				&& colis.getBarCodeAncienColis() != null) {
			if (getAncienColis(colis.getBarCodeAncienColis()) != null) {
				colis.setAncienColisId(getAncienColis(colis.getBarCodeAncienColis()).getReference());
			}
		}
		Date dateLivraison = null;
		if (colis.getDateLivraison() == null) {
			try {
				dateLivraison = new SimpleDateFormat("yyyy-MM-dd")
						.parse(colis.getDate_creation().plusDays(2).toLocalDate().toString());
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		} else {
			dateLivraison = colis.getDateLivraison();
		}
		dateLivraison.setHours(12);
		colis.setDateLivraison(dateLivraison);
		Colis colisResult = colisRepository.save(colis);
		colisResult.setBar_code(colisResult.toColisBarCode());
		colisRepository.save(colisResult);
		generateColisBarCode(colisResult.getReference());
		return colisResult;
	}

	// update Colis
	public Colis updateColis(Colis colis) {
		colis.setFournisseur(fournisseurRepository.findById(colis.fournisseur.getIduser()).orElse(new Fournisseur()));
		if (colis.getHub() != null && colis.getHub().getId_hub() != null) {
			colis.setHub(hubRepository.findById(colis.getHub().getId_hub()).orElse(null));
		}
		colis.setBar_code(colis.toColisBarCode());
		generateColisBarCode(colis.getReference());
		return colisRepository.save(colis);
	}

	// lister les colis
	public List<Colis> findAllColisByFournisseur(Long id) {
		return colisRepository.findByFournisseur_id(id);
	}

	// liste des audits

	public List<HistoStateOnly> getColisAud(Long reference) {
		return colisRepository.getColisAud(reference);

	}

	// supprimer un colis
	public void deleteColis(Long reference) {
		colisRepository.deleteById(reference);
	}

	// findColisCreé
	public List<Colis> findColisByEtat(ColisEtat etat) {
		List<Colis> list = colisRepository.findColisByEtat(etat);
		return list;
	}

	public List<Colis> findAllColis() {
		return colisRepository.findAll();
	}

	public List<Colis> findByObjectList(List<Long> inputList) {
		List<Colis> List1 = colisRepository.findByObjectList(inputList);
		return List1;
	}

	public Colis findColisByBarCode(String bar_code) {
		Colis List1 = colisRepository.findColisByBarCode(bar_code);
		return List1;
	}

	// findColis by id
	public Optional<Colis> findById(Long referene)

	{
		return colisRepository.findById(referene);
	}

	public List<Colis> findColisByRunsheet_code(Long runsheet_code)

	{
		return colisRepository.findColisByRunsheet_code(runsheet_code);
	}

	// findColis by id and by etat
	public List<Colis> findByFournisseurAndEtat(ColisEtat etat, Long referene)

	{
		return colisRepository.findByFournisseurAndEtat(etat, referene);
	}

	// findColis by id and by service
	public List<Colis> findByFournisseurAndService(com.example.filedemo.model.ColisService service, Long referene)

	{
		return colisRepository.findByFournisseurAndService(service, referene);
	}

	// findColis by service
	public List<Colis> findByService(com.example.filedemo.model.ColisService service)

	{
		return colisRepository.findAllColisByService(service);
	}

	public String generateColisBarCode(Long reference) {
		Colis colisBarCode = findById(reference).orElse(new Colis());
		String text = colisBarCode.toColisBarCode();
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

	public int countByEtat(Long id, ColisEtat etat) {

		return colisRepository.countByEtat(id, etat);

	}

	public void RemoveColisFromRunsheet(Long reference) {

		Colis colis = findById(reference).get();
		colis.setRunsheet(null);
		updateColis(colis);
	}

	public Float totalCodPerRunsheet(Long id) {

		return colisRepository.totalCodPerRunsheet(id);
	}

	public String getBarCodeColisDirectory() {
		return this.barCodeDirectory;
	}

	private Hub getRelatedHub(String colisGovernorat) {
		if (colisGovernorat != null) {
			return hubService.retrieveAllHubs().stream().filter(
					h -> h.getGouvernorat().equals(colisGovernorat) || h.getGouvernorat_lie().contains(colisGovernorat))
					.findFirst().orElse(null);
		}
		return null;
	}

	private Colis getAncienColis(String barCode) {
		return colisRepository.findColisByBarCode(barCode);
	}

	public List<Colis> assignColisToDispatch(Dispatch dispatch, List<Long> colisReferences) {
		List<Colis> colis = findByObjectList(colisReferences);
		colis.stream().forEach(c -> {
			c.setDispatch(dispatch);
			c.setEtat(ColisEtat.enAttenteDEnlevement);
			colisRepository.save(c);
		});
		return colis;
	}

	public Colis assignColisToHub(Long refColis, Long idHub) {
		Colis colis = colisRepository.findById(refColis).get();
		Hub hub = hubRepository.findById(idHub).get();
		colis.setHub(hub);
		colis.setBar_code(colis.toColisBarCode());
		colis = colisRepository.save(colis);
		generateColisBarCode(colis.getReference());
		return colis;
	}

	public List<Colis> assignColisToConsole(Console console, List<Long> colisReferences) {
		List<Colis> colis = findByObjectList(colisReferences);
		colis.stream().forEach(c -> {
			c.setConsole(console);
			colisRepository.save(c);
		});
		return colis;
	}

	public ColisHubsResponsePayload findColisHubDepartHubArrivee(String colisBarcode) {
		ColisHubsResponsePayload colisHubsResponsePayload = new ColisHubsResponsePayload();
		Colis colis = findColisByBarCode(colisBarcode);
		if (colis == null)
			return ColisHubsResponsePayload.builder().error("404").build();
		if (colis != null && !colis.getEtat().equals(ColisEtat.enAttenteDEnlevement))
			return ColisHubsResponsePayload.builder().error("406").build();
		Long idHubArrivee = 0l;
		if (colis.getHub() != null) {
			idHubArrivee = colis.getHub().getId_hub();
			colisHubsResponsePayload.setHubArriveeId(idHubArrivee);
			colisHubsResponsePayload.setHubArriveeGovernorat(colis.getHub().getGouvernorat());
			colisHubsResponsePayload.setHubArriveeTitre(colis.getHub().getTitre());
		}
		Hub hubService = fournisseurService.getRelatedHub(colis.getFournisseur().getGouvernorat_societe());
		Long idHubDepart = 0l;
		if (hubService != null) {
			idHubDepart = hubService.getId_hub();
			colisHubsResponsePayload.setHubDepartId(idHubDepart);
			colisHubsResponsePayload.setHubDepartGovernorat(hubService.getGouvernorat());
			colisHubsResponsePayload.setHubDepartTitre(hubService.getTitre());
			colisHubsResponsePayload.setColis(colis);
		}
		if (idHubArrivee == 0 || idHubDepart == 0)
			return colisHubsResponsePayload;
		return colisHubsResponsePayload;
	}
	public List<Colis> findColisByBarCodesList(List<String> barCodesList) {
		return colisRepository.findColisByBarCodesList(barCodesList);
	}
	public List<Colis> findColisByIdLivreur(Long idLivreur){
		List<Colis> colis = new ArrayList<>();
		colis.addAll(colisRepository.findColisDispatchByIdLivreur(idLivreur));
		colis.addAll(colisRepository.findColisRunsheetByIdLivreur(idLivreur));
		return colis;
	}
	public Colis findColisEchange(String barCode) {
		return colisRepository.findColisEchange(barCode).orElse(null);
	}

	public Colis assignAnomalieToColis(String barCode, Long idAnomalie) {
		Anomalie anomalie = anomalieService.findAnomalie(idAnomalie);
		Colis colis =  colisRepository.findColisByBarCode(barCode);
		colis.setNbrt(colis.getNbrt()+1);
		colis.setAnomalie(anomalie);
		return colisRepository.save(colis);
	}
	public Colis findNewColisEchange(String barCode) {
		return colisRepository.findNewColisEchange(barCode).orElse(null);
	}
}
