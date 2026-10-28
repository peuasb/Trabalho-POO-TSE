package vereadores.eleicao;

import java.time.LocalDate;
import java.time.Period;

public class Candidato implements Comparable<Candidato> {
    private int numeroCandidato;
    private String nomeUrna;
    private int numeroFederacao;
    private boolean isEleito;
    private LocalDate dataNascimento;
    private Genero genero;
    private Partido partido;
    private int votosNominais = 0;
    
    public Candidato(int numeroCandidato, String nomeUrna, int numeroFederacao, boolean isEleito, 
                     LocalDate dataNascimento, Genero genero, Partido partido) {
        this.numeroCandidato = numeroCandidato;
        this.nomeUrna = nomeUrna;
        this.numeroFederacao = numeroFederacao;
        this.isEleito = isEleito;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.partido = partido;
    }
        
    public int calculaIdade(LocalDate dataEleicao) {
        return Period.between(this.dataNascimento, dataEleicao).getYears();
    }    

    public boolean participaFederacao() {
        return this.numeroFederacao != -1;
    }

    public void adicionarVotosNominais(int qtd) {
        this.votosNominais += qtd;
    }

    @Override
    public int compareTo(Candidato outro) {
        int comparacaoVotos = Integer.compare(outro.getVotosNominais(), this.votosNominais);
        
        if (comparacaoVotos != 0) { 
            return comparacaoVotos;
        } 
        
        return this.dataNascimento.compareTo(outro.getDataNascimento());
    }

    public int getNumeroCandidato() {
        return numeroCandidato;
    }
        
    public String getNomeUrna() {
        return nomeUrna;
    }
        
    public int getNumeroFederacao() {
        return numeroFederacao;
    }
        
    public boolean isEleito() {
        return isEleito;
    }
        
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
        
    public Genero getGenero() { 
        return genero;
    }
        
    public Partido getPartido() {
        return partido;
    }
        
    public int getVotosNominais() {
        return votosNominais;
    }
}