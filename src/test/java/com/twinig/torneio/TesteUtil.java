package com.twinig.torneio;

import com.twinig.torneio.model.Funcao;
import com.twinig.torneio.model.Jogador;
import com.twinig.torneio.model.Time;

public class TesteUtil {
    
    public static Time criarTimeCompleto(String id, String nome){ //precisa ser static para se manter apenas dentro da classde testes
        Time time = new Time(id, nome);
        time.adicionarJogador(new Jogador(id + "1", nome + "Tank", Funcao.TANK));
        time.adicionarJogador(new Jogador(id + "1", nome + "Tank", Funcao.TANK));
        time.adicionarJogador(new Jogador(id + "2", nome + "Dps1", Funcao.DAMAGE));
        time.adicionarJogador(new Jogador(id + "3", nome + "Dps2", Funcao.DAMAGE));
        time.adicionarJogador(new Jogador(id + "4", nome + "Sup1", Funcao.SUPPORT));
        time.adicionarJogador(new Jogador(id + "5", nome + "Sup2", Funcao.SUPPORT));
        return time;
    }
} //criar o ttese de um time novo, apenas dentro da pasta de Teste
