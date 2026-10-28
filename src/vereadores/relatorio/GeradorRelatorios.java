package vereadores.relatorio;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import vereadores.eleicao.Candidato;
import vereadores.eleicao.ComparadorPartidoPorMaisVotado;
import vereadores.eleicao.Eleicao;
import vereadores.eleicao.Genero;
import vereadores.eleicao.Partido;

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
                    c.getNomeUrna(), 
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
                c.getNomeUrna(), 
                c.getPartido().getSiglaPartido(), 
                votosFormatados, 
                textoVoto
            );
        }
        System.out.println();
    }

    private void imprimirRelatorio4PrejudicadosProporcional() {
        System.out.println("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:");
        System.out.println("(com sua posição no ranking de mais votados)");

        for (int i = 0; i < this.numeroVagas; i++) {
            Candidato c = this.todosCandidatosOrdenados.get(i);

            if (!c.isEleito()) {
                String asterisco = c.participaFederacao() ? "*" : "";
                String votosFormatados =
                    formatadorInteiros.format(c.getVotosNominais());
                String textoVoto =
                    c.getVotosNominais() > 1 ? "votos" : "voto";

                System.out.printf(
                    "%d - %s%s (%s, %s %s)\n",
                    i + 1,
                    asterisco,
                    c.getNomeUrna(),
                    c.getPartido().getSiglaPartido(),
                    votosFormatados,
                    textoVoto
                );
            }
        }

        System.out.println();
    }

    private void imprimirRelatorio5BeneficiadosProporcional() {
        System.out.println("Eleitos, que se beneficiaram do sistema proporcional:");
        System.out.println("(com sua posição no ranking de mais votados)");

        for (int i = this.numeroVagas;
            i < this.todosCandidatosOrdenados.size();
            i++) {

            Candidato c = this.todosCandidatosOrdenados.get(i);

            if (c.isEleito()) {
                String asterisco = c.participaFederacao() ? "*" : "";
                String votosFormatados =
                    formatadorInteiros.format(c.getVotosNominais());
                String textoVoto =
                    c.getVotosNominais() > 1 ? "votos" : "voto";

                System.out.printf(
                    "%d - %s%s (%s, %s %s)\n",
                    i + 1,
                    asterisco,
                    c.getNomeUrna(),
                    c.getPartido().getSiglaPartido(),
                    votosFormatados,
                    textoVoto
                );
            }
        }

        System.out.println();
    }

    private void imprimirRelatorio6VotacaoPartidos() {
        System.out.println("Votação dos partidos e número de candidatos eleitos:");

        List<Partido> partidos = new ArrayList<>(
            this.eleicao.getPartidos().values()
        );

        Collections.sort(partidos);

        for (int i = 0; i < partidos.size(); i++) {
        Partido p = partidos.get(i);

        int total = p.getTotalVotos();
        int nominais = p.getVotosNominais();
        int eleitos = p.getQtdCandidatosEleitos();

        String textoVoto = total > 1 ? "votos" : "voto";
        String textoNominal = nominais > 1 ? "nominais" : "nominal";
        String textoEleitos = eleitos > 1
            ? "candidatos eleitos"
            : "candidato eleito";

        System.out.printf(
            "%d - %s - %d, %s %s (%s %s e %s de legenda), %d %s\n",
            i + 1,
            p.getSiglaPartido(),
            p.getNumeroPartido(),
            formatadorInteiros.format(total),
            textoVoto,
            formatadorInteiros.format(nominais),
            textoNominal,
            formatadorInteiros.format(p.getVotosDeLegenda()),
            eleitos,
            textoEleitos
            );
        }

        System.out.println();
    }
    
    private void imprimirRelatorio7PrimeiroUltimoPartido() {
        System.out.println("Primeiro e último colocados de cada partido:");

        List<Partido> partidos = new ArrayList<>();

        for (Partido p : this.eleicao.getPartidos().values()) {
            if (p.possuiCandidatosComVotosPositivos()) {
                partidos.add(p);
            }
        }

        Collections.sort(partidos, new ComparadorPartidoPorMaisVotado());

        for (int i = 0; i < partidos.size(); i++) {
        Partido p = partidos.get(i);

        Candidato primeiro = p.getCandidatoMaisVotado();
        Candidato ultimo = p.getCandidatoMenosVotado();

        String textoPrimeiro = primeiro.getVotosNominais() > 1
            ? "votos" : "voto";

        String textoUltimo = ultimo.getVotosNominais() > 1
            ? "votos" : "voto";

        System.out.printf(
            "%d - %s - %d, %s (%d, %s %s) / %s (%d, %s %s)\n",
            i + 1,
            p.getSiglaPartido(),
            p.getNumeroPartido(),
            primeiro.getNomeUrna(),
            primeiro.getNumeroCandidato(),
            formatadorInteiros.format(primeiro.getVotosNominais()),
            textoPrimeiro,
            ultimo.getNomeUrna(),
            ultimo.getNumeroCandidato(),
            formatadorInteiros.format(ultimo.getVotosNominais()),
            textoUltimo
        );
    }
        System.out.println();
    }

    private void imprimirRelatorio8FaixaEtaria() {
        System.out.println(
            "Eleitos, por faixa etária (na data da eleição):"
        );

        int[] quantidades = new int[5];

        for (Candidato c : this.todosCandidatosOrdenados) {
            if (c.isEleito()) {
                int idade = c.calculaIdade(
                    this.eleicao.getDataEleicao()
                );

                if (idade < 30) {
                    quantidades[0]++;
                } else if (idade < 40) {
                    quantidades[1]++;
                } else if (idade < 50) {
                    quantidades[2]++;
                } else if (idade < 60) {
                    quantidades[3]++;
                } else {
                    quantidades[4]++;
                }
            }
        }

        String[] rotulos = {
            "      Idade < 30:",
            "30 <= Idade < 40:",
            "40 <= Idade < 50:",
            "50 <= Idade < 60:",
            "60 <= Idade     :"
        };

        for (int i = 0; i < quantidades.length; i++) {
            double percentual = this.numeroVagas > 0
                ? quantidades[i] * 100.0 / this.numeroVagas
                : 0.0;

            System.out.printf(
                Locale.forLanguageTag("pt-BR"),
                "%s %d (%.2f%%)\n",
                rotulos[i],
                quantidades[i],
                percentual
            );
        }

        System.out.println();
    }

    private void imprimirRelatorio9Genero() {
        System.out.println("Eleitos, por gênero:");

        int feminino = 0;
        int masculino = 0;

        for (Candidato c : this.todosCandidatosOrdenados) {
            if (c.isEleito()) {
                if (c.getGenero() == Genero.FEMININO) {
                    feminino++;
                } else if (c.getGenero() == Genero.MASCULINO) {
                    masculino++;
                }
            }
        }

        double percentualFeminino = this.numeroVagas > 0
            ? feminino * 100.0 / this.numeroVagas
            : 0.0;

        double percentualMasculino = this.numeroVagas > 0
            ? masculino * 100.0 / this.numeroVagas
            : 0.0;

        System.out.printf(
            Locale.forLanguageTag("pt-BR"),
            "Feminino: %d (%.2f%%)\n",
            feminino,
            percentualFeminino
        );

        System.out.printf(
            Locale.forLanguageTag("pt-BR"),
            "Masculino: %d (%.2f%%)\n",
            masculino,
            percentualMasculino
        );

        System.out.println();
    }

    private void imprimirRelatorio10TotalVotos() {
        int totalNominais = 0;
        int totalLegenda = 0;

        for (Partido p : this.eleicao.getPartidos().values()) {
            totalNominais += p.getVotosNominais();
            totalLegenda += p.getVotosDeLegenda();
        }

        int totalValidos = totalNominais + totalLegenda;

        double percentualNominais = totalValidos > 0
            ? totalNominais * 100.0 / totalValidos
            : 0.0;

        double percentualLegenda = totalValidos > 0
            ? totalLegenda * 100.0 / totalValidos
            : 0.0;

        System.out.printf(
            "Total de votos válidos:    %s\n",
            formatadorInteiros.format(totalValidos)
        );

        System.out.printf(
            Locale.forLanguageTag("pt-BR"),
            "Total de votos nominais:   %s (%.2f%%)\n",
            formatadorInteiros.format(totalNominais),
            percentualNominais
        );

        System.out.printf(
            Locale.forLanguageTag("pt-BR"),
            "Total de votos de legenda: %s (%.2f%%)\n",
            formatadorInteiros.format(totalLegenda),
            percentualLegenda
        );

        System.out.println();
    }
}