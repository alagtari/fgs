package com.example.filedemo.service;

import com.example.filedemo.model.SocietePrincipal;

public interface SocietePrincipalService {
  
	SocietePrincipal configureSocietePrincipal(SocietePrincipal societePrincipal);
	SocietePrincipal updateSocietePrincipal(SocietePrincipal societePrincipal);
	SocietePrincipal retrieveConfigSocietePrincipal();
}
