package br.com.alura.tabelafipe.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosAnos(String codigo,
                        String nome) {
}
