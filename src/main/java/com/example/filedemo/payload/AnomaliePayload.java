package com.example.filedemo.payload;

import java.util.List;

import com.example.filedemo.model.AnomalieType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnomaliePayload {
 private Long id;
 private String designation;
 private String acronyme;
 private AnomalieType type;
 private int nbrTentative;
 private List<String> barCodeColis;
}