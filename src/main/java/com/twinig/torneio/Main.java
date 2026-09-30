package com.twinig.torneio;

import java.time.LocalDateTime;

import com.twinig.torneio.model.Funcao;
import com.twinig.torneio.model.Heroi;
import com.twinig.torneio.model.Jogador;
import com.twinig.torneio.model.Mapa;
import com.twinig.torneio.model.Partida;
import com.twinig.torneio.model.RegistroDesempenho;
import com.twinig.torneio.model.Time;

public class Main {
    public static void main(String[] args) {
        System.out.println("Gerenciador de Torneio de Overwatch");
        Jogador j = new Jogador("1", "Twinig", Funcao.DAMAGE);
        Heroi soldado = new Heroi("Soldado-76", Funcao.DAMAGE);
        RegistroDesempenho r = new RegistroDesempenho(j, soldado);
        r.registrarEstatisticas(10, 2, 4);
        System.out.println(r.descricao());
        System.out.println("KDA: " + r.calcularKDA());

        Mapa kingsRow = new Mapa("King's Row", "Escolta");
        Time timeA = new Time("A", "Fenix");
        Time timeB = new Time("B", "Dragões");
        Partida p = new Partida("1", LocalDateTime.now(), kingsRow, timeA, timeB);
        System.out.println(p.descricao());
        p.registrarResultado(timeA);
        System.out.println(p.descricao());
        System.out.println("Pontos Fenix: " + timeA.getPontos());
    }
}