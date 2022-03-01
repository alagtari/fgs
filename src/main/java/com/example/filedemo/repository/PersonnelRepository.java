package com.example.filedemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.filedemo.model.Personnel;

@Repository
public interface PersonnelRepository extends CrudRepository<Personnel, Long> {

	@Query("SELECT p FROM Personnel p WHERE p.role_personnel = 'livreur'")
	public List<Personnel> getLivreurList();

	@Query("SELECT p FROM Personnel p WHERE p.role_personnel = 0 And p.hub.id_hub =:id")
	public List<Personnel> getLivreurListByHub(@Param("id") Long id);
}