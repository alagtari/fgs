package com.example.filedemo.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsolePayload {
	private long idCreator;
	private long idLivreur;
	private List<String> colisBarCode;
	private Long idHubArrivee;
	private Long idHubDepart;
}
