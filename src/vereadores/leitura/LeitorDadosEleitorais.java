package vereadores.leitura;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Map;

import vereadores.eleicao.Candidato;
import vereadores.eleicao.Eleicao;
import vereadores.eleicao.Genero;
import vereadores.eleicao.Partido;

public abstract class LeitorDadosEleitorais {
    public static void lerCandidatos(String caminhoArquivo, Eleicao eleicao) throws IOException {
        try (FileInputStream fis = new FileInputStream(caminhoArquivo);
            InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1");
            BufferedReader br = new BufferedReader(isr)) {
            // Ler a primeira linha (cabeçalho)
            String linhaCabecalho = br.readLine();
            if (linhaCabecalho == null) return; // Ficheiro vazio
            
            // Limpar as aspas e dividir pelo ponto e vírgula
            linhaCabecalho = linhaCabecalho.replace("\"", "");
            String[] nomesColunas = linhaCabecalho.split(";");
            
            // Qual coluna está em qual índice?
            Map<String, Integer> cabecalho = new HashMap<>();
            for (int i = 0; i < nomesColunas.length; i++) {
                cabecalho.put(nomesColunas[i], i);
            }
            
            // Formatar a data (padrão brasileiro)
            DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            String linha;
            // Ler o ficheiro linha a linha até acabar
            while ((linha = br.readLine()) != null) {
                linha = linha.replace("\"", "");
                String[] valores = linha.split(";");
                
                // 1. SEMPRE cadastra o partido primeiro (independente de cargo, município ou situação)
                int numeroPartido = Integer.parseInt(valores[cabecalho.get("NR_PARTIDO")]);
                String siglaPartido = valores[cabecalho.get("SG_PARTIDO")];
                Partido partido = eleicao.obterOuCriarPartido(numeroPartido, siglaPartido);
                
                // 2. Filtra apenas cargo 13 (Vereador), situação != -1 (válido) e município correto
                int codigoCargo = Integer.parseInt(valores[cabecalho.get("CD_CARGO")]);
                int situacaoTurno = Integer.parseInt(valores[cabecalho.get("CD_SIT_TOT_TURNO")]);
                String codigoMunicipio = valores[cabecalho.get("SG_UE")];
                
                if (codigoCargo != 13 
                        || situacaoTurno == -1 
                        || Integer.parseInt(codigoMunicipio) != Integer.parseInt(eleicao.getCodigoMunicipio())) {
                    continue;
                }
                
                // 3. Instancia e cadastra o candidato válido do município
                int numeroCandidato = Integer.parseInt(valores[cabecalho.get("NR_CANDIDATO")]);
                String nomeUrna = valores[cabecalho.get("NM_URNA_CANDIDATO")];
                int numeroFederacao = Integer.parseInt(valores[cabecalho.get("NR_FEDERACAO")]);
                Genero genero = Genero.fromCodigo(Integer.parseInt(valores[cabecalho.get("CD_GENERO")]));
                LocalDate dataNascimento = LocalDate.parse(valores[cabecalho.get("DT_NASCIMENTO")], formatadorData);
                boolean isEleito = (situacaoTurno == 2 || situacaoTurno == 3);

                Candidato candidato = new Candidato(
                    numeroCandidato, nomeUrna, numeroFederacao, isEleito, dataNascimento, genero, partido
                );
                eleicao.cadastrarCandidato(candidato);
            }
        }
    }

    public static void lerVotacao(String caminhoArquivo, Eleicao eleicao) throws IOException {
        try (FileInputStream fis = new FileInputStream(caminhoArquivo);
            InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1");
            BufferedReader br = new BufferedReader(isr)) {
            // Ler a primeira linha (cabeçalho)
            String linhaCabecalho = br.readLine();
            if (linhaCabecalho == null) return; // Ficheiro vazio
            
            // Limpar as aspas e dividir pelo ponto e vírgula
            linhaCabecalho = linhaCabecalho.replace("\"", "");
            String[] nomesColunas = linhaCabecalho.split(";");
            
            // Qual coluna está em qual índice?
            Map<String, Integer> cabecalho = new HashMap<>();
            for (int i = 0; i < nomesColunas.length; i++) {
                cabecalho.put(nomesColunas[i], i);
            }

            String linha;
            // Ler o ficheiro linha a linha até acabar
            while ((linha = br.readLine()) != null) {
                linha = linha.replace("\"", "");
                String[] valores = linha.split(";");
                                
                int codigoCargo = Integer.parseInt(valores[cabecalho.get("CD_CARGO")]);
                String codigoMunicipio = valores[cabecalho.get("CD_MUNICIPIO")];
                if (codigoCargo != 13 || Integer.parseInt(codigoMunicipio) != Integer.parseInt(eleicao.getCodigoMunicipio())) {
                    continue;
                }

                int numeroVotavel = Integer.parseInt(valores[cabecalho.get("NR_VOTAVEL")]);
                if (numeroVotavel >= 95 && numeroVotavel <= 98) {
                    continue;
                }

                int qtVotos = Integer.parseInt(valores[cabecalho.get("QT_VOTOS")]);
                eleicao.computarVoto(numeroVotavel, qtVotos);
            }
        }
    }
}