package com.twinig.torneio.model;
import java.util.ArrayList;

public class Time {
    private String id;
    private String nome;
    private ArrayList<Jogador> jogadores;
    private int pontos;
    private boolean ativo;

    public Time(String id, String nome) {
        this.id = id;
        this.nome = nome;
        this.jogadores = new ArrayList<>();
        this.pontos = 0;
        this.ativo = true;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Jogador> getJogadores() {
        return jogadores;
    }

    public int getPontos() {
        return pontos;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void desativar() {
        this.ativo = false;
    }

    public boolean adicionarJogador(Jogador jogador) {
        if (jogadores.size() >= 5) {
            return false; // Time já possui 5 jogadores
        }
        int limite = limitePorFuncao(jogador.getFuncao());
        long jaTem = contarPorFuncao(jogador.getFuncao());
        if(jaTem >= limite) {
            return false; // Já atingiu o limite de jogadores para essa função
        }
        jogadores.add(jogador);
        return true;
    }

    private int limitePorFuncao(Funcao funcao){
        if(funcao == Funcao.TANK) {
            return 1;
        }
        return 2;
    }

    private long contarPorFuncao(Funcao funcao){
        long total = 0;
        for(Jogador jogador : jogadores) {
            if(jogador.getFuncao() == funcao) {
                total++;
            }
    }
    return total;
    }   

    public boolean composicaoCompletata(){
        return jogadores.size() == 5;
    }

    public void adicionarPontos(int quantidade) {
        this.pontos += quantidade;
    }
    public String descricao() {
        return nome + " [Pontos: " + pontos + " pts ( " + jogadores.size() + " jogadores)]";
    }

    public boolean inscrever(){
        if(!composicaoCompletata()){
            return false; //so aceita time se a composição estiver completa
        }
        return true;
    }
}


