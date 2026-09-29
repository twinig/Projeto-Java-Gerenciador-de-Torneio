package com.twinig.torneio.model;
import java.util.ArrayList;

public class Jogador {
    private String id;
    private String nick;
    private Funcao funcao;
    private ArrayList<Heroi> herois;
    private boolean ativo;

    public Jogador(String id, String nick, Funcao funcao) {
        this.id = id;
        this.nick = nick;
        this.funcao = funcao;
        this.herois = new ArrayList<>();
        this.ativo = true;
    }

    public String getId() {
        return id;
    }

    public String getNick() {
        return nick;
    }

    public Funcao getFuncao() {
        return funcao;
    }

    public ArrayList<Heroi> getHerois() {
        return herois;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void AdicionarMainHeroi(Heroi heroi){ 
        if(!herois.contains(heroi)){ //metodo da ArrayList que verifica se o objeto já existe na lista
            herois.add(heroi); //outro metodo da ArrayList que adiciona o objeto na lista
        }
    }
    public String descricao() {
        return nick + " [" + funcao + "]"; //concatena o nick do jogador com a função dele
    }
}