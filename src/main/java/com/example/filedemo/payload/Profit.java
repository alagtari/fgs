package com.example.filedemo.payload;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profit {

	private Long id ;
	private float monatant ;
	private Date date_profit ;
}
