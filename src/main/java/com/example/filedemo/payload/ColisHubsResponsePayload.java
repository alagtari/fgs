package com.example.filedemo.payload;

import com.example.filedemo.model.Colis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ColisHubsResponsePayload {
	private Long hubDepartId;
	private Long hubArriveeId;
	private String hubDepartGovernorat;
	private String hubArriveeGovernorat;
	private String hubDepartTitre;
	private String hubArriveeTitre;
	private Colis colis;
	private String error;
}
