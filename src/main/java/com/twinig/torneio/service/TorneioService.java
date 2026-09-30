package com.twinig.torneio.service;

import com.twinig.torneio.model.*;
import java.util.ArrayList;

public class TorneioService {
    private Torneio torneio;

    public TorneioService(Torneio torneio) { //recebe o nome do torneio e guarda no this.torneio, e a partir daqui, todos os metodos usarao esse this.torneio
        this.torneio = torneio;
    }

    public boolean cadastrarTime(Time time) { //recebe um time ja pronto, como ja explicado, o service so repassa a info da classe Torneio, servindo mais para organizar operações
        return torneio.inscreverTime(time);
    }
 
    public Time consultarTime(String id) { 
        for (Time t : torneio.getTimes()) { //pega a lista dos times cadastrados, e percorre um a um no for
            if (t.getId().equals(id)) { //compara o id do time com o id passado como parametro, se for igual, retorna o time, usamos .equals() para comparar strings, pois o == compara referencias de objetos, e não o conteudo
                return t;
            }
        }
        return null; //se percorrer e nao achar o id, devolve null
    }

    public ArrayList<Time> listarTimes() { //operação listar do projeto
        return torneio.getTimes();
    }

    public ArrayList<Time> listarTimesAtivos() { //um listarTimes, mas apenas para devolver os ativos
        ArrayList<Time> ativos = new ArrayList<>(); //por isso, criamos uma nova ArrayList e vamos preenchendo conforme o for
        for (Time t : torneio.getTimes()) { //percorre a lista de times cadastrados e verifica se estao ativos no t.isAtivo()
            if (t.isAtivo()) {
                ativos.add(t); //caso sim, adiciona na lista de ativos
            }
        }
        return ativos;
    }

    public boolean desativarTime(String id) { 
        Time time = consultarTime(id); //primeiro reaproveita o metodo consultarTime para verificar se o time existe
        if (time == null) { //caso nao exista, devolve false 
            return false;
        }
        time.desativar(); //no else, chama o metodo desativar() da classe Time
        return true;
    }

    public boolean associarJogadorATime(String idTime, Jogador jogador) { //achar o time pelo id dnv
        Time time = consultarTime(idTime);
        if (time == null) { //caso nao, devolve false
            return false;
        }
        return time.adicionarJogador(jogador); //no else, usa o time e o metodo de adicionarJogador na classe Time
    }

    public ArrayList<Time> pesquisarTimePorNome(String trecho) {  //cria a String trecho pra pesquisar 
        ArrayList<Time> encontrados = new ArrayList<>(); //cria uma nova lista de times encontrados, que vai ser preenchida conforme o for
        for (Time t : torneio.getTimes()) {
            if (t.getNome().toLowerCase().contains(trecho.toLowerCase())) { //.toLowerCase() transforma tudo em minusculo, isso para nao diferenciar na pesquisa> FENIX = fenix = Fenix
                encontrados.add(t); //.contains verifica se o nome do time contem o trecho digitado, assim, buscar por fen--ja busca fenix
            } //quando encontra, ad o time na lista de encontrados, e no final do for, devolve a lista de encontrados
        }
        return encontrados;
    }

    public ArrayList<Time> ordenarTimesPorPontos() {
        return torneio.gerarRanking(); //so repassa o metodo de gerar ranking do Torneio, que ja faz a ordenação dos times por pontos 
    }

    public boolean agendarPartida(Partida partida) {
        return torneio.agendarPartida(partida); //repassa o metodo de agendar partida do Torneio, que ja faz a verificação de horario e times
    }

    public double calcularMediaPontos() {
        return torneio.calcularMediaPontos(); //repassa o calculo de media 
    }

    public Time timeComMaisPontos() {
        return torneio.timeComMaisPontos(); //repassa APENAS o time com mais pontos
    }

}
