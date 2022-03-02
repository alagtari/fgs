package com.example.filedemo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.filedemo.model.Dispatch;
import com.example.filedemo.repository.DispatchRepository;
@Service
public class DispatchServiceImpl implements DispatchService {
    @Autowired
    private DispatchRepository dispatchRepository;
    
	@Override
	public Dispatch addDispatch(Dispatch dispatch) {
		Date dateCreation = new Date();
		dateCreation.setHours(12);
		dispatch.setDateCreation(dateCreation);
		return dispatchRepository.save(dispatch);
	}

	@Override
	public List<Dispatch> findByCreationDate(String date) {
	    return dispatchRepository.findByDateCreation(date);
	}

	@Override
	public Dispatch synchronize(Dispatch dispatch) {
		return dispatchRepository.save(dispatch);
	}

	@Override
	public List<Dispatch> findAllDispatchs() {
		return (List<Dispatch>) dispatchRepository.findAll();
	}

	@Override
	public List<Dispatch> findDispatchsByDisptacher(long idDispatcher) {
		return (List<Dispatch>) dispatchRepository.findByDispatcher(idDispatcher);
	}

}
