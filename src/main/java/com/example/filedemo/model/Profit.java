package com.example.filedemo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Profit {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id ;
	private float monatant ;
	private Date date_profit ;

}
