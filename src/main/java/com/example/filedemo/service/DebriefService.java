package com.example.filedemo.service;

import java.util.List;

import com.example.filedemo.model.Debrief;
import com.example.filedemo.payload.DebriefPayload;

public interface DebriefService {
  List<Debrief> getAllDebrief();
  List<Debrief> getAllDebriefByIdValidator(Long idValidator);
  List<Debrief> getAllDebriefByIdLivreur(Long idValidator);
  Debrief createDebrief(DebriefPayload debriefPayload);
  Boolean checkDebriefEligibility (Long idLivreur);
}
