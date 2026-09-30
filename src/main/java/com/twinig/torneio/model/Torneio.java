package com.twinig.torneio.model;

import java.util.ArrayList;

public class Torneio {
    private String nome;
    private ArrayList<Time> times;
    private ArrayList<Partida> partidas;
    private boolean encerrado;

    public Torneio(String nome) {
        this.nome = nome;
        this.times = new ArrayList<>();
        this.partidas = new ArrayList<>();
        this.encerrado = false;
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Time> getTimes() {
        return times;
    }

    public ArrayList<Partida> getPartidas() {
        return partidas;
    }

    public boolean isEncerrado() {
        return encerrado;
    }

    public boolean inscreverTime(Time time) { //so aceita time se a composição estiver completa
        if (!time.composicaoCompletata()) { //usa o metodo da classe Time para verificar se a composição do time está completa
            return false;
        }
        times.add(time);
        return true;
    }

    public boolean agendarPartida(Partida partida) { //a partida só é agendada se não houver outra partida no mesmo horário com o mesmo time
        for (Partida p : partidas) {
            boolean mesmoHorario = p.getDataHora().equals(partida.getDataHora());
            boolean mesmoTime = p.envolveTime(partida.getTimeA()) || p.envolveTime(partida.getTimeB());
            if (mesmoHorario && mesmoTime) {
                return false;
            }
        }
        partidas.add(partida);
        return true;
    }

    public Time timeComMaisPontos() { //indicador de maior valor de pontos do torneio, retorna o time com mais pontos
        Time melhor = null;
        for (Time t : times) {
            if (melhor == null || t.getPontos() > melhor.getPontos()) {
                melhor = t;
            }
        }
        return melhor;
    }

    public double calcularMediaPontos() { //indicador de media de pontos do torneio, retorna a media de pontos dos times
        if (times.isEmpty()) {
            return 0;
        }
        int soma = 0;
        for (Time t : times) {
            soma += t.getPontos();
        }
        return (double) soma / times.size();
    }
    public int i, j;


    public ArrayList<Time> gerarRanking() { //bubble sort 
        ArrayList<Time> ranking = new ArrayList<>(times); //cria uma copia da lista de times para não alterar a lista original
        for(i = 0; i < ranking.size() - 1; i++) { //laço externo para percorrer a lista de times
            for(j = 0; j < ranking.size() - i - 1; j++) { //laço interno para percorrer a lista de times e comparar os pontos dos times
                if(ranking.get(j).getPontos() < ranking.get(j + 1).getPontos()) { //compara o time atual da posição j com o time da posição j + 1, se o time da posição j tiver menos pontos que o time da posição j + 1, troca os times de posição
                    Time temp = ranking.get(j);
                    ranking.set(j, ranking.get(j + 1));
                    ranking.set(j + 1, temp);
                }
            }
        }
        return ranking;
    }

    public void encerrar() {
        this.encerrado = true;
    }

    public String descricao() {
        return nome + " - " + times.size() + " times, " + partidas.size() + " partidas";
    }
}