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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Funcao getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }
    
    public String descricao(){
        return nome + " (" + funcao + ")";
    }
    
}
