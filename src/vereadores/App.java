package vereadores;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import vereadores.eleicao.Eleicao;
import vereadores.leitura.LeitorDadosEleitorais;
import vereadores.relatorio.GeradorRelatorios;

public class App {
    public static void main(String[] args) {
        // Validação dos argumentos da linha de comandos
        if (args.length < 4) {
            System.out.println("Erro: Faltam argumentos!");
            return;
        }

        String codigoMunicipio = args[0];
        String caminhoCandidatos = args[1];
        String caminhoVotacao = args[2];
        String dataString = args[3];

        // Conversão da data
        LocalDate dataEleicao;
        try {
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dataEleicao = LocalDate.parse(dataString, formatador);
        } catch (DateTimeParseException e) {
            System.out.println("Erro: Data com formato inválido. Utilize dd/MM/yyyy.");
            return;
        }

        // Inicialização da Eleição
        Eleicao eleicao = new Eleicao(codigoMunicipio, dataEleicao);

        // Leitura dos ficheiros CSV
        try {
            LeitorDadosEleitorais.lerCandidatos(caminhoCandidatos, eleicao);
            LeitorDadosEleitorais.lerVotacao(caminhoVotacao, eleicao);
        } catch (Exception e) {
            System.out.println("Erro ao ler os ficheiros CSV: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Geração dos Relatórios
        GeradorRelatorios gerador = new GeradorRelatorios(eleicao);
        gerador.gerarTodosRelatorios();
    }
}