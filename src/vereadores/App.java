package vereadores;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import vereadores.leitura.LeitorDadosEleitorais;
import vereadores.relatorio.GeradorRelatorios;
import vereadores.sistemaEleitoral.Eleicao;

public class App {
    public static void main(String[] args) {
        // 1. Validação dos argumentos da linha de comandos
        if (args.length < 4) {
            System.out.println("Erro: Faltam argumentos!");
            System.out.println("Uso: java -jar vereadores.jar <codigo_municipio> <arquivo_candidatos> <arquivo_votacao> <data_eleicao>");
            return;
        }

        String codigoMunicipio = args[0];
        String caminhoCandidatos = args[1];
        String caminhoVotacao = args[2];
        String dataString = args[3];

        // 2. Conversão da data
        LocalDate dataEleicao;
        try {
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dataEleicao = LocalDate.parse(dataString, formatador);
        } catch (DateTimeParseException e) {
            System.out.println("Erro: Data com formato inválido. Utilize dd/MM/yyyy.");
            return;
        }

        // 3. Inicialização da Eleição
        Eleicao eleicao = new Eleicao(codigoMunicipio, dataEleicao);

        // 4. Leitura dos ficheiros CSV
        try {
            LeitorDadosEleitorais.lerCandidatos(caminhoCandidatos, eleicao);
            LeitorDadosEleitorais.lerVotacao(caminhoVotacao, eleicao);
        } catch (Exception e) {
            System.out.println("Erro ao ler os ficheiros CSV: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // 5. Geração dos Relatórios
        GeradorRelatorios gerador = new GeradorRelatorios(eleicao);
        gerador.gerarTodosRelatorios();
    }
}