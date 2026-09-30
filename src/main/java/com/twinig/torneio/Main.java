package com.twinig.torneio;

import java.time.LocalDateTime;

import com.twinig.torneio.model.Funcao;
import com.twinig.torneio.model.Jogador;
import com.twinig.torneio.model.Mapa;
import com.twinig.torneio.model.Partida;
import com.twinig.torneio.model.Time;
import com.twinig.torneio.model.Torneio;

public class Main {
    public static void main(String[] args) {
        Torneio torneio = new Torneio("Copa Overwatch");

        Time timeA = new Time("A", "Fenix");
        timeA.adicionarJogador(new Jogador("1", "Tank1", Funcao.TANK));
        timeA.adicionarJogador(new Jogador("2", "Dps1", Funcao.DAMAGE));
        timeA.adicionarJogador(new Jogador("3", "Dps2", Funcao.DAMAGE));
        timeA.adicionarJogador(new Jogador("4", "Sup1", Funcao.SUPPORT));
        timeA.adicionarJogador(new Jogador("5", "Sup2", Funcao.SUPPORT));

        Time timeB = new Time("B", "Dragões");
        timeB.adicionarJogador(new Jogador("6", "Tank2", Funcao.TANK));
        timeB.adicionarJogador(new Jogador("7", "Dps3", Funcao.DAMAGE));
        timeB.adicionarJogador(new Jogador("8", "Dps4", Funcao.DAMAGE));
        timeB.adicionarJogador(new Jogador("9", "Sup3", Funcao.SUPPORT));
        timeB.adicionarJogador(new Jogador("10", "Sup4", Funcao.SUPPORT));

        torneio.inscreverTime(timeA);
        torneio.inscreverTime(timeB);

        Mapa kingsRow = new Mapa("King's Row", "Escolta");
        Partida partida = new Partida("1", LocalDateTime.now(), kingsRow, timeA, timeB);
        torneio.agendarPartida(partida);
        partida.registrarResultado(timeA);

        System.out.println(torneio.descricao());
        System.out.println("Ranking:");
        for (Time t : torneio.gerarRanking()) {
            System.out.println(t.descricao());
        }
    }
}