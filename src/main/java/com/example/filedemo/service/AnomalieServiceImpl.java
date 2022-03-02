package com.example.filedemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.filedemo.model.Anomalie;
import com.example.filedemo.model.AnomalieType;
import com.example.filedemo.payload.AnomaliePayload;
import com.example.filedemo.repository.AnomalieRepository;

@Service
public class AnomalieServiceImpl implements AnomalieService {
	@Autowired
    private AnomalieRepository anomalieRepository;
	
	@Override
	public Anomalie createAnomalie(AnomaliePayload anomaliePayload) {
		Anomalie anomalie = Anomalie.builder().designation(anomaliePayload.getDesignation())
				.acronyme(anomaliePayload.getAcronyme()).nbrTentative(anomaliePayload.getNbrTentative())
				.type(anomaliePayload.getType())
				.build();
		return anomalieRepository.save(anomalie);
	}

	@Override
	public Anomalie updateAnomalie(AnomaliePayload anomaliePayload) {
		Anomalie anomalie = anomalieRepository.findById(anomaliePayload.getId()).get();
		anomalie.setAcronyme(anomaliePayload.getAcronyme());
		anomalie.setDesignation(anomaliePayload.getDesignation());
		anomalie.setNbrTentative(anomaliePayload.getNbrTentative());
		anomalie.setType(anomaliePayload.getType());
		return anomalieRepository.save(anomalie);
	}

	@Override
	public void deleteAnomalie(Long anomalieId) {
		 anomalieRepository.deleteById(anomalieId);
	}

	@Override
	public Anomalie findAnomalie(Long idAnomalie) {
		return anomalieRepository.findById(idAnomalie).orElse(null);
	}

	@Override
	public List<Anomalie> findAll() {
		return (List<Anomalie>) anomalieRepository.findAll();
	}

	@Override
	public Anomalie getAnomalieByType(AnomalieType anomalieType) {
		return anomalieRepository.findFirstByType(anomalieType).orElse(null);
	}
}
