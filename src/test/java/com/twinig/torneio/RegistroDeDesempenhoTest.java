package com.twinig.torneio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.twinig.torneio.model.Funcao;
import com.twinig.torneio.model.Heroi;
import com.twinig.torneio.model.Jogador;
import com.twinig.torneio.model.RegistroDesempenho;
import org.junit.jupiter.api.Test;
public class RegistroDeDesempenhoTest {

    private RegistroDesempenho criar(){
        Jogador jogador = new Jogador("1", "TWINIG", Funcao.DAMAGE);
        Heroi heroi = new Heroi("Echo", Funcao.DAMAGE);

        return new RegistroDesempenho(jogador, heroi); //pq nao beforeEach? porque cada teste aqui precisa rodar um registro novo para colocar estatisticas
        // diferentes
    }

    @Test
    void calculaKDAcomMortes(){
        RegistroDesempenho r = criar();
        r.registrarEstatisticas(24, 5, 12); //verifica o teste com essas estatisticas pra retornar o valor do KDA
        assertEquals(7.2, r.calcularKDA(), 0.001);
    }

    @Test
    void kdaSemMortesNaoPodeDividirPorZero(){
        RegistroDesempenho r = criar();
        r.registrarEstatisticas(5, 0, 3); //cenario diferente: caso ele esteja com 0 deaths, poderia fazer K + A / 0, oq daria erro
        assertEquals(8.0, r.calcularKDA(), 0.001); //o divisor é tratado como 1, para nao travar
    }



}