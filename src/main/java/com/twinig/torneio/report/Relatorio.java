package com.twinig.torneio.report;

import java.util.ArrayList;
import com.twinig.torneio.model.*;
import com.twinig.torneio.model.Partida;
import com.twinig.torneio.model.RegistroDesempenho;
import com.twinig.torneio.model.Torneio;

public class Relatorio { //uma classe separada pois os calculos ficam nessa classe, e o main so chama e imprime, ainda que os metodos estejam em outros lugares
    private Torneio torneio;

    public Relatorio(Torneio torneio){
        this.torneio = torneio;
    }

    public double mediaPontosPorTime(){ //media de pontos por time
        return torneio.calcularMediaPontos();
    }

    public int totalPartidasFinalizadas(){ //partidas finalizadas
        int total = 0; //contador começa em 0 e vai incrementando conforme partidas sao finalizadas
        for(Partida p : torneio.getPartidas()){
            if(p.isFinalizada()){
                total++;
            }
        }
        return total;
    }

    public Time timeComMaisPontos(){
        return torneio.timeComMaisPontos(); //Pega APENAS o time com mais pontos
    }

    public ArrayList<Time> ranking(){
        return torneio.gerarRanking();
    }

    public RegistroDesempenho melhorKDA(){ //pega o melhor KDA entre todos os registros
        RegistroDesempenho melhor = null;
        for(Partida p : torneio.getPartidas()){ //dois fors: percorre as partidas
            for(RegistroDesempenho r : p.getRegistros()){  //percorre os registros de desempenho de cada uma
                if(melhor == null || r.calcularKDA() > melhor.calcularKDA()){
                    melhor = r;
                }
            }
        }
        return melhor;
    }

    public double percentualFinalizadas(){
        if(torneio.getPartidas().isEmpty()){
            return 0;
        }
        return totalPartidasFinalizadas() * 100.0 / torneio.getPartidas().size(); //100.0 para forçar casas decimais
    }

}
