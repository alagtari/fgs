package com.example.filedemo.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.filedemo.model.Anomalie;
import com.example.filedemo.model.AnomalieType;
import com.example.filedemo.model.Colis;
import com.example.filedemo.model.ColisEtat;
import com.example.filedemo.model.Debrief;
import com.example.filedemo.model.DebriefEtat;
import com.example.filedemo.model.Personnel;
import com.example.filedemo.payload.DebriefPayload;
import com.example.filedemo.repository.ColisRepository;
import com.example.filedemo.repository.DebriefRepository;

@Service
public class DebriefServiceImpl implements DebriefService {
    @Autowired
    DebriefRepository debriefRepository;
    @Autowired
    ColisService colisService;
    @Autowired
    ColisRepository colisRepository;
    @Autowired
    AnomalieService anomalieService;
    @Autowired
    PersonnelService personnelService;
    @Autowired
    RunsheetService runsheetService;
	@Override
	public List<Debrief> getAllDebrief() {
		return (List<Debrief>) debriefRepository.findAll();
	}

	@Override
	public List<Debrief> getAllDebriefByIdValidator(Long idValidator) {
		return debriefRepository.getDebriefByIdValidator(idValidator);
	}

	@Override
	public List<Debrief> getAllDebriefByIdLivreur(Long idLivreur) {
		return debriefRepository.getDebriefByIdLivreur(idLivreur);
	}
    @Transactional
	@Override
	public Debrief createDebrief(DebriefPayload debriefPayload) {
		Debrief  debrief = new Debrief();
		debrief.setEtat(DebriefEtat.cloture);
		Personnel livreur = personnelService.retrievePersonnel(debriefPayload.getIdLivreur());
		Personnel validator = personnelService.retrievePersonnel(debriefPayload.getIdValidator());
        debrief.setLivreur(livreur);
        debrief.setValidator(validator);
        debrief.setCreationDate(new Date());
        debrief = debriefRepository.save(debrief);
		List<Colis> livreurColis  = colisService.findColisByIdLivreur(debriefPayload.getIdLivreur());
		List<Colis> manipulatedColis = colisService.findColisByBarCodesList(debriefPayload.getColisBarCodes());
		livreurColis.forEach(c->{
			if(!c.getService().equals(com.example.filedemo.model.ColisService.echange)) {
			Colis echange= colisService.findNewColisEchange(c.getBar_code());
			if(echange!=null) {
				if(manipulatedColis.contains(c)) {
					echange.setEtat(ColisEtat.livre);
					colisRepository.save(echange);
					c.setEtat(ColisEtat.enStock);
					colisRepository.save(c);
				}
				/*else {
					c.setEtat(ColisEtat.enStock);
					echange.setEtat(ColisEtat.livre);
				}*/
			}
			else if(c.getEtat().equals(ColisEtat.enCoursDeLivraison)) {
				if(!manipulatedColis.contains(c)) {
					c.setEtat(ColisEtat.livre);
				}
				else c.setEtat(ColisEtat.enStock);
				if(c.getAnomalie()!=null & c.getNbrt()>c.getAnomalie().getNbrTentative()) {
					c.setEtat(ColisEtat.planificationRetour);//should be verified
				}
				colisRepository.save(c);

			}
			else if(c.getEtat().equals(ColisEtat.planificationRetour)) {
				if(!manipulatedColis.contains(c)) {
					c.setEtat(ColisEtat.retourne);
				}
				else c.setEtat(ColisEtat.enStock);
				colisRepository.save(c);

			}
			else if(c.getEtat().equals(ColisEtat.enAttenteDEnlevement)) {
				if(manipulatedColis.contains(c)) {
					c.setEtat(ColisEtat.enStock);
				}
				else c.setAnomalie(anomalieService.getAnomalieByType(AnomalieType.enlevement));
				colisRepository.save(c);
			}
			}
		});
		encloseRunsheets(livreurColis);
		debrief.setColis(assignDebriefToColis(livreurColis, debrief));
		return debriefRepository.save(debrief);
	}

	@Override
	public Boolean checkDebriefEligibility(Long idLivreur) {
		Debrief debrief =  debriefRepository.checkDebriefEligibility(idLivreur,new Date()).orElse(null);
		return debrief==null? Boolean.TRUE: Boolean.FALSE;
	}
	private List<Colis> assignDebriefToColis(List<Colis> colis, Debrief debrief) {
		colis.stream().forEach(c->{
			c.setDebrief(debrief);
			colisRepository.save(c);
		});
		return colis;
	}
	private void encloseRunsheets(List<Colis> livreurDebriefColis) {
		livreurDebriefColis.stream().forEach(c->{
			if(c.getRunsheet()!=null) {
				runsheetService.encloseRunsheet(c.getRunsheet().getId());
			}
		});
	}

}
