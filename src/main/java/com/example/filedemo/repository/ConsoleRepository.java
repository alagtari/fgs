package com.example.filedemo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.filedemo.model.Console;

@Repository
public interface ConsoleRepository extends CrudRepository<Console, Long> {
  Console findByIdHubDepartAndIdHubArrivee(Long idDeprat, Long idArrivee);
  Optional<Console> findByBarCode(String barCode);
  @Query("select c from Console c where c.validator.iduser= :idValidator")
  List<Console> findByValidator(@Param("idValidator")long idValidator);
  
  @Query("select c from Console c where c.creator.iduser= :idCreator")
  List<Console> findByCreator(@Param("idCreator")long idCreator);
  
  List<Console> findByIdHubArrivee(Long idHub);
  List<Console> findByIdHubDepart(Long idHub);

}
