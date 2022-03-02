package com.example.filedemo.service;

import java.util.List;

import com.example.filedemo.model.Runsheet;
import com.example.filedemo.payload.RunsheetPayload;

public interface RunsheetService {
	List<Runsheet> retrieveAllRunsheets();
	Runsheet addRunsheet(RunsheetPayload runsheetPayload);
	void deleteRunsheet(Long id);
	Runsheet updateRunsheet(RunsheetPayload runsheetPayload);
	Runsheet findByBarCode(String barCode);
	Runsheet findById(Long codeRunsheet);
	Runsheet addColisToRunsheet(List<String> barCodeList ,  Long idRunsheet );
	String generateRunsheetBarCode (Runsheet runsheet);
	Runsheet encloseRunsheet(Long id);
}
