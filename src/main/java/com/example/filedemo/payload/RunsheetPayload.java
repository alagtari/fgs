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
public class RunsheetPayload {
	private Long id;
	private Long livreurId;
	private Long creatorId;
	private List<String> colisBarCodes;
}
