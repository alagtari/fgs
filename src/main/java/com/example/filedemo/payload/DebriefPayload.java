package com.example.filedemo.payload;


import java.util.List;

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
public class DebriefPayload {
  private Long idValidator;
  private Long idLivreur;
  private List<String> colisBarCodes;
}
