package vereadores.relatorio;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import vereadores.sistemaEleitoral.Candidato;
import vereadores.sistemaEleitoral.Eleicao;
import vereadores.sistemaEleitoral.Partido;

public class GeradorRelatorios {
    private Eleicao eleicao;
    private NumberFormat formatadorInteiros;
    private int numeroVagas = 0;
    private List<Candidato> todosCandidatosOrdenados;

    public GeradorRelatorios(Eleicao eleicao) {
        this.eleicao = eleicao;
        this.formatadorInteiros = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
        
        this.todosCandidatosOrdenados = new ArrayList<>(eleicao.getCandidatos().values());
        Collections.sort(this.todosCandidatosOrdenados);
        
        for (Candidato c : this.todosCandidatosOrdenados) {
            if (c.isEleito()) {
                this.numeroVagas++;
            }
        }
    }

    public void gerarTodosRelatorios() {
        imprimirRelatorio1NumeroVagas();
        imprimirRelatorio2CandidatosEleitos();
        imprimirRelatorio3MaisVotados();
        imprimirRelatorio4PrejudicadosProporcional();
        imprimirRelatorio5BeneficiadosProporcional();
        imprimirRelatorio6VotacaoPartidos();
        imprimirRelatorio7PrimeiroUltimoPartido();
        imprimirRelatorio8FaixaEtaria();
        imprimirRelatorio9Genero();
        imprimirRelatorio10TotalVotos();
    }

    private void imprimirRelatorio1NumeroVagas() {
        System.out.println("Número de vagas: " + this.numeroVagas);
        System.out.println();
    }

    private void imprimirRelatorio2CandidatosEleitos() {
        System.out.println("Vereadores eleitos:");
        int posicao = 1;
        
        for (Candidato c : todosCandidatosOrdenados) {
            if (c.isEleito()) {
                String asterisco = c.participaFederacao() ? "*" : "";
                String votosFormatados = formatadorInteiros.format(c.getVotosNominais());
                String textoVoto = c.getVotosNominais() > 1 ? "votos" : "voto";
                
                System.out.printf("%d - %s%s (%s, %s %s)\n", 
                    posicao, 
                    asterisco, 
                    c.getNomeDaUrna(), 
                    c.getPartido().getSiglaPartido(), 
                    votosFormatados, 
                    textoVoto
                );
                posicao++;
            }
        }
        System.out.println();
    }

    private void imprimirRelatorio3MaisVotados() {
        System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");
        
        for (int i = 0; i < this.numeroVagas; i++) {
            Candidato c = this.todosCandidatosOrdenados.get(i);
            
            String asterisco = c.participaFederacao() ? "*" : "";
            String votosFormatados = formatadorInteiros.format(c.getVotosNominais());
            String textoVoto = c.getVotosNominais() > 1 ? "votos" : "voto";
            
            System.out.printf("%d - %s%s (%s, %s %s)\n", 
                (i + 1),
                asterisco, 
                c.getNomeDaUrna(), 
                c.getPartido().getSiglaPartido(), 
                votosFormatados, 
                textoVoto
            );
        }
        System.out.println();
    }

    private void imprimirRelatorio4PrejudicadosProporcional() {
        // A implementar...
    }

    private void imprimirRelatorio5BeneficiadosProporcional() {
        // A implementar...
    }

    private void imprimirRelatorio6VotacaoPartidos() {
        // A implementar...
    }

    private void imprimirRelatorio7PrimeiroUltimoPartido() {
        // A implementar...
    }

    private void imprimirRelatorio8FaixaEtaria() {
        // A implementar...
    }

    private void imprimirRelatorio9Genero() {
        // A implementar...
    }

    private void imprimirRelatorio10TotalVotos() {
        // A implementar...
    }
}