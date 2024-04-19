package br.com.alura.tabelafipe.services;

import java.util.List;

public interface IConverteDados {
    <T> T ObterDados(String json, Class<T> tClass);
    <T> List<T> ObterLista(String json, Class<T> tClass);
}
