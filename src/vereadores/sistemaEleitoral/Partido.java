package vereadores.sistemaEleitoral;

import java.util.List;
import java.util.ArrayList;

public class Partido implements Comparable<Partido> {
    private int numeroPartido;
    private String siglaPartido;
    private int votosDeLegenda = 0;
    private List<Candidato> candidatos = new ArrayList<>();
    
    public Partido(int numeroPartido, String siglaPartido) {
        this.numeroPartido = numeroPartido;
        this.siglaPartido = siglaPartido;
    }
    
    public void adicionarCandidato(Candidato candidato) {
        this.candidatos.add(candidato);
    }
    
    public void adicionarVotosLegenda(int qtd) {
        this.votosDeLegenda += qtd;
    }   
    
    @Override
    public int compareTo(Partido outro) {
        int comparacaoVotos = Integer.compare(outro.getTotalVotos(), this.getTotalVotos());
        
        if (comparacaoVotos != 0) {
            return comparacaoVotos;
        }
        
        return Integer.compare(this.numeroPartido, outro.getnumeroPartido());
    }

    public int getVotosNominais() {
        int somaVotos = 0;
        
        for (Candidato candidato : this.candidatos) {
            somaVotos += candidato.getVotosNominais();
        }
        
        return somaVotos;
    }
    
    public int getTotalVotos() {
        return this.getVotosNominais() + this.votosDeLegenda;
    }

    public int getnumeroPartido() {
        return numeroPartido;
    }
    
    public String getSiglaPartido() {
        return siglaPartido;
    }
    
    public int getVotosDeLegenda() {
        return votosDeLegenda;
    }
 
    public int getQtdCandidatosEleitos() {
        int quantCandidatosEleitos = 0;

        for (Candidato candidato : this.candidatos) {
            if (candidato.isEleito()) {
                quantCandidatosEleitos++;
            }
        }

        return quantCandidatosEleitos;
    }
    
    public List<Candidato> getCandidatosOrdenados() {
        List<Candidato> copia = new ArrayList<>(this.candidatos);
        java.util.Collections.sort(copia);
        return copia;
    }

    public Candidato getCandidatoMaisVotado() {
        if (this.candidatos.isEmpty()) {
            return null;
        }
        return getCandidatosOrdenados().get(0);
    }

    public Candidato getCandidatoMenosVotado() {
        if (this.candidatos.isEmpty()) {
            return null;
        }
        List<Candidato> ordenados = getCandidatosOrdenados();
        return ordenados.get(ordenados.size() - 1);
    }

    public boolean possuiCandidatosComVotosPositivos() {
        Candidato maisVotado = getCandidatoMaisVotado();
        return maisVotado != null && maisVotado.getVotosNominais() > 0;
    }

    public List<Candidato> getCandidatos() {
        return new ArrayList<>(this.candidatos);
    }   
}
