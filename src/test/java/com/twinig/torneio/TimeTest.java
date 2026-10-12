package com.twinig.torneio;

import static org.junit.jupiter.api.Assertions.*; //precisa ser esse para importar os metodos da classe Assertions

import org.junit.jupiter.api.Test;

import com.twinig.torneio.model.Funcao;
import com.twinig.torneio.model.Jogador;
import com.twinig.torneio.model.Time;

public class TimeTest {
    @Test
    void naoAceitaSegundoTank(){ //testar a regra de nao poder um tank extra(so podem 5 jogadores)
        Time time = new Time("1", "TimeTeste"); //cria um time vazio
        assertTrue(time.adicionarJogador(new Jogador("1", "Tank1", Funcao.TANK))); 
        assertFalse(time.adicionarJogador(new Jogador("2", "TankExtra", Funcao.TANK))); //tenta ad outro tank
        assertEquals(1, time.getJogadores().size()); //confirma que so tem um tank, se tiver 2, o teste falha
    }

    @Test 
    void naoAceitaTerceiroDamage(){
        Time time = new Time("1", "TimeTeste");
        time.adicionarJogador(new Jogador("1", "Dps1", Funcao.DAMAGE));
        time.adicionarJogador(new Jogador("2", "Dps2", Funcao.DAMAGE)); //aqui ja temos a regra de dois dps no time de 5, entao ele vai tentar ad mais um
        assertFalse(time.adicionarJogador(new Jogador("3", "DpsEtra", Funcao.DAMAGE))); //o sistema deve retornar false
    }

    @Test
    void naoAceitaSextoJogador(){ //apesar de existir um modo de 6 players, nao é o do campeonato
        Time time = TesteUtil.criarTimeCompleto("A", "TimeFenixTeste"); //usa o TesteUtil para criar um time cheio automaticamente
        assertFalse(time.adicionarJogador(new Jogador("99", "extra", Funcao.SUPPORT))); //tenta colocar um sexto jogador
        assertEquals(5, time.getJogadores().size()); //confirma que o tamanho continua 5 pae
    }

    @Test 
    void composicaoCompletaSoCom5Jogadores(){
        //cenario> time incompleto
        Time incompleto = new Time("1", "Time incompleto");
        incompleto.adicionarJogador(new Jogador("1", "Tank1", Funcao.TANK)); //so tem um tank
        assertFalse(incompleto.composicaoCompletata()); //teoricamente deve verificar se a composição ta completa, obviamente, nao deveria estar, pois nao usamos TesteUtil
        //cenario> time completo
        Time completo = TesteUtil.criarTimeCompleto("A", "Time completo"); //cria time completo
        assertTrue(completo.composicaoCompletata()); //Retorna se composição estiver completa, teoricamente sim
    }
}
