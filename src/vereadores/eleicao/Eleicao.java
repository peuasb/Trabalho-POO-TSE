package vereadores.eleicao;

import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;

public class Eleicao {
    private String codigoMunicipio;
    private LocalDate dataEleicao;
    private Map<Integer, Partido> partidos = new HashMap<>();
    private Map<Integer, Candidato> candidatos = new HashMap<>();
    
    public Eleicao(String codigoMunicipio, LocalDate dataEleicao) {
        this.codigoMunicipio = codigoMunicipio;
        this.dataEleicao = dataEleicao;
    }
    
    public Partido obterOuCriarPartido(int numero, String sigla) {
        Partido partidoProcurado = this.partidos.get(numero); 
        if (partidoProcurado != null) {
            return partidoProcurado;
        }

        Partido partidoNovo = new Partido(numero, sigla);
        this.partidos.put(numero, partidoNovo);
        return partidoNovo;
    }
    
    public void cadastrarCandidato(Candidato candidato) {
        this.candidatos.put(candidato.getNumeroCandidato(), candidato);
        candidato.getPartido().adicionarCandidato(candidato);
    }

    public void computarVoto(int numeroVotavel, int quantidadeDeVotos) {
        if (this.candidatos.containsKey(numeroVotavel)) {
            Candidato candidato = this.candidatos.get(numeroVotavel);
            candidato.adicionarVotosNominais(quantidadeDeVotos);
        } else if (this.partidos.containsKey(numeroVotavel)) {
            Partido partido = this.partidos.get(numeroVotavel);
            partido.adicionarVotosLegenda(quantidadeDeVotos);
        }
    }

    public String getCodigoMunicipio() {
        return codigoMunicipio;
    }
    
    public LocalDate getDataEleicao() {
        return dataEleicao;
    }
    
    public Map<Integer, Partido> getPartidos() {
        return new HashMap<>(this.partidos);
    }
    
    public Map<Integer, Candidato> getCandidatos() {
        return new HashMap<>(this.candidatos);
    }
}
