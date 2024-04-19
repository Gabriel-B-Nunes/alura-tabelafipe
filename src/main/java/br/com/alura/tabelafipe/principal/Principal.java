package br.com.alura.tabelafipe.principal;

import br.com.alura.tabelafipe.models.*;
import br.com.alura.tabelafipe.services.ConsultaFIPE;
import br.com.alura.tabelafipe.services.ConverteDados;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private final String urlBase = "https://parallelum.com.br/fipe/api/v1/";
    private ConsultaFIPE consulta = new ConsultaFIPE();
    private ConverteDados converteDados = new ConverteDados();


    public void exibeMenu() {
        System.out.println("""
                **** OPÇÕES ****
                                
                Carros
                                
                Motos
                                
                Caminhões
                                
                Digite uma das opções para consultar valores:
                """);
        String categoria = leitura.nextLine();
        String enderecoMarcas = urlBase + categoria.toLowerCase() + "/marcas";
        String json = consulta.consultaFIPE(enderecoMarcas);
        List<DadosMarcas> marcas = converteDados.ObterLista(json, DadosMarcas.class);
        marcas.forEach(m -> System.out.println("Código= " + m.codigo() + " Marca= " + m.nome()));

        System.out.println("Informe o código da marca");
        String modelo = leitura.nextLine();
        String enderecoModelos = enderecoMarcas + "/" + modelo + "/modelos";
        json = consulta.consultaFIPE(enderecoModelos);
        DadosModelos modelos = converteDados.ObterDados(json, DadosModelos.class);
        modelos.modelos().forEach(m -> System.out.println("Código= " + m.codigo() + " Modelo= " + m.nome()));

        System.out.println("Digite um trecho do nome do modelo");
        String buscaModelo = leitura.nextLine();
        List<DadosMarcas> veiculosBuscados = modelos.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(buscaModelo.toLowerCase()))
                .collect(Collectors.toList());
        veiculosBuscados.forEach(m -> System.out.println("Código= " + m.codigo() + " Modelo= " + m.nome()));

        System.out.println("Digite o código do veículo");
        String veiculo = leitura.nextLine();
        String enderecoVeiculo = enderecoModelos + "/" + veiculo + "/anos";
        json = consulta.consultaFIPE(enderecoVeiculo);
        List<DadosAnos> anos = converteDados.ObterLista(json, DadosAnos.class);

        List<DadosVeiculos> dadosVeiculos = anos.stream()
                .map(a -> converteDados.ObterDados(consulta.consultaFIPE(enderecoVeiculo + "/" + a.codigo()), DadosVeiculos.class))
                .collect(Collectors.toList());
        dadosVeiculos.forEach(System.out::println);
    }
}
