package com.twinig.torneio;

import com.twinig.torneio.model.Funcao;
import com.twinig.torneio.model.Heroi;
import com.twinig.torneio.model.Jogador;
import com.twinig.torneio.model.RegistroDesempenho;

public class Main {
    public static void main(String[] args) {
        System.out.println("Gerenciador de Torneio de Overwatch");
        Jogador j = new Jogador("1", "Twinig", Funcao.DAMAGE);
        Heroi soldado = new Heroi("Soldado-76", Funcao.DAMAGE);
        RegistroDesempenho r = new RegistroDesempenho(j, soldado);
        r.registrarEstatisticas(10, 2, 4);
        System.out.println(r.descricao());
        System.out.println("KDA: " + r.calcularKDA());
    }
}