package com.twinig.torneio;

import static org.junit.jupiter.api.Assertions.*;

import com.twinig.torneio.model.Funcao;
import com.twinig.torneio.model.Heroi;
import com.twinig.torneio.model.Mapa;
import com.twinig.torneio.model.Partida;
import com.twinig.torneio.model.RegistroDesempenho;
import com.twinig.torneio.model.Time;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PartidaTest {
    private Time timeA;
    private Time timeB;
    private Partida partida;

    @BeforeEach //Executa o metodo preparation para criar os times antes de cada metodo, para usar um novo em cada metodo abaixo
    void preparation(){
        timeA = TesteUtil.criarTimeCompleto("A", "FenixTeste");
        timeB = TesteUtil.criarTimeCompleto("B", "DragonTeste");
        partida = new Partida("1", LocalDateTime.of(2026, 10, 12, 20, 0), new Mapa("Kings Row", "Escolta"), timeA, timeB);
    }

    @Test 
    void vencedorGanhaPontosEFinalizaPartida(){ //teste pra contabilizar pontos e fechar partida
        partida.registrarResultado(timeA); //define A como vencedor
        assertTrue(partida.isFinalizada()); //partida deve acabar
        assertEquals(timeA, partida.getVencedor()); //O vencedor deve ser A
        assertEquals(1, timeA.getPontos()); //Time A ganha 3 pontos e B 0
        assertEquals(0, timeB.getPontos());
    }

    @Test 
    void naoRegistrarResultadoDuplicado(){
        partida.registrarResultado(timeA); //define A como vencedor
        partida.registrarResultado(timeB); //Tenta tambem dar a vitoria para B
        assertEquals(timeA, partida.getVencedor()); //vencedor deve continuar sendo A 
        assertEquals(0, timeB.getPontos()); //Time B nao pode receber pontos
    }
    
    @Test 
    void naoAceitaVencedorQueNaoEstaInscrito(){
        Time timeExtra = TesteUtil.criarTimeCompleto("C", "Molestadinhos FC"); //time novo fora da partida criada no preparation()
        partida.registrarResultado(timeExtra); //tenta dar a vitoria a ele(CORRUPÇAO!)
        assertFalse(partida.isFinalizada()); //a partida nao deve finalizar, pois nao foi registrado nem A nem B
        assertEquals(0, timeExtra.getPontos()); //o C nao pode receber pontos
    }

    @Test 
    void bloqueiaHeroiRepetidoNoMesmoTime(){
        Heroi mercy = new Heroi("Mercy", Funcao.SUPPORT);

        RegistroDesempenho r1 = new RegistroDesempenho(timeA.getJogadores().get(3), mercy); 
        RegistroDesempenho r2 = new RegistroDesempenho(timeA.getJogadores().get(4), mercy); //pega o terceiro e quarto jogador, e tenta dar Mercy para os dois

        assertTrue(partida.adicionarRegistro(r1)); //o primeiro deve ser aceito pois fez primeiro
        assertFalse(partida.adicionarRegistro(r2)); //deve ser bloqueado
        assertEquals(1, partida.getRegistros().size()); //deve ter apenas um registro
    }

    @Test
    void permiteHeroisIguaisEmTimesDiferentes(){
        Heroi bastion = new Heroi("Bastion", Funcao.DAMAGE);

        RegistroDesempenho r1 = new RegistroDesempenho(timeB.getJogadores().get(3), bastion); 
        RegistroDesempenho r2 = new RegistroDesempenho(timeA.getJogadores().get(3), bastion); //mesmo heroi mas em times diff

        assertTrue(partida.adicionarRegistro(r1));
        assertTrue(partida.adicionarRegistro(r2)); //deve aceitar ambos
    }
}
