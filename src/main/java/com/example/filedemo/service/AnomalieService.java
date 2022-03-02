package com.example.filedemo.service;


import java.util.List;

import com.example.filedemo.model.Anomalie;
import com.example.filedemo.model.AnomalieType;
import com.example.filedemo.payload.AnomaliePayload;

public interface AnomalieService {
  Anomalie createAnomalie(AnomaliePayload anomalie);
  Anomalie updateAnomalie(AnomaliePayload anomalie);
  void deleteAnomalie(Long anomalieId);
  Anomalie findAnomalie(Long idAnomalie);
  List<Anomalie> findAll();
  Anomalie getAnomalieByType(AnomalieType anomalieType);
}
