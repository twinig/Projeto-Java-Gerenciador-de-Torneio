package com.twinig.torneio.model;

public class RegistroDesempenho {
    private Jogador jogador; 
    private Heroi heroiUsado; //as duas associações B-C, guardando uma ref unica
    private int kills;
    private int deaths;
    private int assists;

    public RegistroDesempenho(Jogador jogador, Heroi heroiUsado) {
        this.jogador = jogador;
        this.heroiUsado = heroiUsado;
        this.kills = 0;
        this.deaths = 0;
        this.assists = 0;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public Heroi getHeroiUsado() {
        return heroiUsado;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getAssists() {
        return assists;
    }
    
    public void registrarEstatisticas(int kills, int deaths, int assists) {
        this.kills += kills;
        this.deaths += deaths;
        this.assists += assists;
    } //um metodo só que calcula ja, os tres de uma vez,como se fossem tres setters
    
    public double calcularKDA(){ //para retornar valor em decimal
        if(deaths == 0){ //denominador nao pode ser 0 ne rapeize
            return kills + assists;
        }
        return (double)(kills + assists) / deaths;
    }

    public String descricao(){
        return jogador.getNick() + " [ " + heroiUsado.getNome() + " - " + kills + "/" + deaths + "/" + assists + " - KDA: " + String.format("%.2f", calcularKDA()) + " ]";
    }

    

}
