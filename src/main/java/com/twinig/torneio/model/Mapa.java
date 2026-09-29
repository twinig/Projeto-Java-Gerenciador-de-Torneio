package com.twinig.torneio.model;

public class Mapa {
    private String nome;
    private String mapaDeJogo;

    public Mapa(String nome, String mapaDeJogo) {
        this.nome = nome;
        this.mapaDeJogo = mapaDeJogo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMapaDeJogo() {
        return mapaDeJogo;
    }

    public void setMapaDeJogo(String mapaDeJogo) {
        this.mapaDeJogo = mapaDeJogo;
    }
    
    public String descricao(){
        return nome + " [ " + mapaDeJogo + "]";
    }

    
}
