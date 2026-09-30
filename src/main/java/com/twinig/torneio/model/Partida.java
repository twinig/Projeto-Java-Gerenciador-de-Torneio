package com.twinig.torneio.model;
import java.time.LocalDateTime; //Classe do proprio Java para guardar data e hora
import java.util.ArrayList;

public class Partida {
    private String id;
    private LocalDateTime dataHora;
    private Mapa mapa;
    private Time timeA;
    private Time timeB;
    private ArrayList<RegistroDesempenho> registros;
    private Time vencedor;
    private boolean finalizada;

    public Partida(String id, LocalDateTime dataHora, Mapa mapa, Time timeA, Time timeB) {
        this.id = id;
        this.dataHora = dataHora;
        this.mapa = mapa;
        this.timeA = timeA;
        this.timeB = timeB;
        this.registros = new ArrayList<>();
        this.vencedor = null; //significa que ainda não houve vencedor
        this.finalizada = false;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public Mapa getMapa() {
        return mapa;
    }

    public Time getTimeA() {
        return timeA;
    }

    public Time getTimeB() {
        return timeB;
    }

    public ArrayList<RegistroDesempenho> getRegistros() {
        return registros;
    }

    public Time getVencedor() {
        return vencedor;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public boolean envolveTime(Time time){
        return timeA == time || timeB == time; //Verifica se um time especifico está jogando a partida, se sim retorna true, se não retorna false
    }

    public boolean adicionarRegistro(RegistroDesempenho registro){
        Jogador jogador = registro.getJogador();
        Heroi heroi = registro.getHeroiUsado();
        Time timeDoJogador = timeA.getJogadores().contains(jogador) ? timeA : timeB; //operador ternario, é uma forma resumida de escrever um if, se o jogador estiver no timeA, timeDoJogador recebe timeA, se não, recebe timeB

        for (RegistroDesempenho r : registros) { //um for percorre os registros ja adicionados e verifica se outro jogador do mesmo time ja lockou o msm heroi
            boolean mesmoTime = timeA.getJogadores().contains(r.getJogador()) == timeDoJogador.getJogadores().contains(jogador);
            if(mesmoTime && r.getHeroiUsado() == heroi){
                return false; // Já existe um registro para esse jogador com o mesmo herói
            }
        }
        registros.add(registro);
        return true;
    }

    public void registrarResultado(Time vencedor){
        if(finalizada){
            return; // Partida já finalizada, não faz nada
        }
        if (vencedor != timeA && vencedor != timeB) {
            return; // Time vencedor não está na partida, não faz nada
        }
        this.vencedor = vencedor;
        this.finalizada = true;
        vencedor.adicionarPontos(1);
        Time perdedor = (vencedor == timeA) ? timeB : timeA;
        perdedor.adicionarPontos(0);
    }
    
    public String descricao(){
        String status = finalizada ? "Finalizada" : "Agendada";
        return timeA.getNome() + " vs " + timeB.getNome() + " em " + mapa.getNome() + " - " + status;
    }

    

    
}
