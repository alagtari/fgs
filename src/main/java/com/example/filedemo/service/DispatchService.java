package com.example.filedemo.service;

import java.util.List;

import com.example.filedemo.model.Dispatch;

public interface DispatchService {
  Dispatch addDispatch(Dispatch dispatch);
  List<Dispatch> findByCreationDate(String date);
  Dispatch synchronize(Dispatch dispatch);
  List<Dispatch> findAllDispatchs();
  List<Dispatch> findDispatchsByDisptacher(long idDispatcher);
}
