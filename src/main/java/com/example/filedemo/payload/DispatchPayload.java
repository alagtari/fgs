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
public class DispatchPayload {
  private long idDispatcher;
  private long idLivreur;
  private List<Long> colisReferences;
}
