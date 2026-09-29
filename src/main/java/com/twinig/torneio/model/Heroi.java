package com.twinig.torneio.model;

public class Heroi {
    private String nome;
    private Funcao funcao;
    
    public Heroi(String nome, Funcao funcao) {
        this.nome = nome;
        this.funcao = funcao;
    }

    public String getNome() {
        return nome;
    }

    public Funcao getFuncao() {
        return funcao;
    }
  
    public String descricao(){
        return nome + " (" + funcao + ")";
    }
    
}
