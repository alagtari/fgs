package com.example.filedemo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.filedemo.model.Hub;

import java.util.List;
import java.util.Optional;


@Repository
public interface HubRepository extends CrudRepository<Hub, Long> {
	@Query(value= "SELECT * FROM Hub h INNER JOIN hub_gouvernorat_lie g where  g.gouvernorat_lie = :governorat", nativeQuery = true )
	List<Hub> retrieveHubsHavingGovernoratLie(@Param("governorat") String governorat);
	
	Optional<Hub> findByTitre(String titre);
}
