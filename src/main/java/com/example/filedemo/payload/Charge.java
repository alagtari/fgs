package com.example.filedemo.payload;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Charge {

	private Long id ;
	private String designation ;
	private float montant ;
	private String type ;
	private Date date_charge ;
}
