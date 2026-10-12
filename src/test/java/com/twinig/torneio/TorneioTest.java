package com.twinig.torneio;

import static org.junit.jupiter.api.Assertions.*;

import com.twinig.torneio.model.Mapa;
import com.twinig.torneio.model.Partida;
import com.twinig.torneio.model.Time;
import com.twinig.torneio.model.Torneio;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

public class TorneioTest {
    private final Mapa mapa = new Mapa("Ilios", "Controle");
    private final LocalDateTime horario = LocalDateTime.of(2026, 10, 12, 20, 00);

    //cria o mapa e a horario da partida
    @Test 
    void naoInscreveTimeIncompleto(){
        Torneio torneio = new Torneio("Copa"); //cria torneio novo chamado copa
        assertFalse(torneio.inscreverTime(new Time("1", "Vazio"))); //tenta inscrever time sem jogadores, deve retornar false 
        assertEquals(0, torneio.getTimes().size());  //confirma que nenhum time foi inscrito
    }

    @Test 
    void inscreveTimeCompleto(){
        Torneio torneio = new Torneio("Copa"); //cria outro torneio copa
        assertTrue(torneio.inscreverTime(TesteUtil.criarTimeCompleto("A", "Fenix"))); //ja aqui, cria o time completo e tenta inscreve-lo, deve retornar true
    }

    @Test 
    void bloqueiaConflitoDeHorarioDoMesmoTime(){   //cria tres times, Tenta agendar uma partida as 20:00, como feito na variavel Horario la em cima
        Torneio torneio = new Torneio("Copa Grande");
        Time a = TesteUtil.criarTimeCompleto("A", "ZNE");
        Time b = TesteUtil.criarTimeCompleto("B", "TBD");
        Time c = TesteUtil.criarTimeCompleto("C", "CDB"); 

        assertTrue(torneio.agendarPartida(new Partida("1", horario, mapa, a, b))); //tenta agendar ZNE x TDB e ZNE x CDB, ou seja, dois no msm horario
        assertFalse(torneio.agendarPartida(new Partida("2", horario, mapa, a, c))); //expectado que a segunda falhe
        assertEquals(1, torneio.getPartidas().size()); //garante que apenas um horario foi guardado no Partidas
    }

    @Test 
    void permiteTimeEmHorariosDiferentes(){ //agora, para permitir partida em horarios diferentes, apenas pegando o horario e somando
        Torneio torneio = new Torneio("Copa");
        Time a = TesteUtil.criarTimeCompleto("A","Pain Gaming");
        Time b = TesteUtil.criarTimeCompleto("B", "LOUD");

        assertTrue(torneio.agendarPartida(new Partida("1", horario, mapa, a, b)));
        assertTrue(torneio.agendarPartida(new Partida("2", horario.plusHours(2), mapa, a, b)));
    }

    @Test
    void rankearDoMaiorParaMenor(){
        Torneio torneio = new Torneio("Copa"); //cria tres times, e da os pontos a eles
        Time a = TesteUtil.criarTimeCompleto("A", "Molestadinhos Esports");
        Time b = TesteUtil.criarTimeCompleto(" B", "Abusadinhos Esports");
        Time c = TesteUtil.criarTimeCompleto("C", "Pederastia Esports");

        torneio.inscreverTime(a);
        torneio.inscreverTime(c);
        torneio.inscreverTime(b);

        a.adicionarPontos(9);
        b.adicionarPontos(6);
        c.adicionarPontos(7);

        ArrayList<Time> ranking = torneio.gerarRanking(); //pede a aArraylist do ranking
        assertEquals(b, ranking.get(2)); //o teste espera em ordem decrescente
        assertEquals(a, ranking.get(0));
        assertEquals(c, ranking.get(1));
    }

    @Test 
    void calcularMediaEMaiorPontuacao(){
        Torneio torneio = new Torneio("Copa");
        Time a = TesteUtil.criarTimeCompleto("A", "TesteTime1");
        Time b = TesteUtil.criarTimeCompleto("B", "TesteTime2");
        torneio.inscreverTime(b);
        torneio.inscreverTime(a);
        a.adicionarPontos(6);
        b.adicionarPontos(3);

        assertEquals(4.5, torneio.calcularMediaPontos(), 0.001); //primeira vericação: calcula a media da pontuacao dos dois times, 0.001 é uma margem de erro para evitar erros de arredondamento
        assertEquals(a, torneio.timeComMaisPontos()); //Ela espera que retorne o time com mais pontos(A)
    }
    
}
