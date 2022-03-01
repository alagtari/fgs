package com.example.filedemo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.filedemo.model.SocietePrincipal;

@Repository
public interface SocietePrincipalRepository extends CrudRepository<SocietePrincipal, Long> {
   Optional<SocietePrincipal> findFirstByOrderByIdAsc();
}
