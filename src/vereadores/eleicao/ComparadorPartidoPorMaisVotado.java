package vereadores.eleicao;

import java.util.Comparator;

public class ComparadorPartidoPorMaisVotado implements Comparator<Partido> {
    @Override
    public int compare(Partido p1, Partido p2) {
        Candidato c1 = p1.getCandidatoMaisVotado();
        Candidato c2 = p2.getCandidatoMaisVotado();

        int votosC1 = (c1 != null) ? c1.getVotosNominais() : 0;
        int votosC2 = (c2 != null) ? c2.getVotosNominais() : 0;

        int comparacaoVotos = Integer.compare(votosC2, votosC1);
        if (comparacaoVotos != 0) {
            return comparacaoVotos;
        }

        return Integer.compare(p1.getNumeroPartido(), p2.getNumeroPartido());
    }
}
