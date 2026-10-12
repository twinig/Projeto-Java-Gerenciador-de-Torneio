package com.twinig.torneio.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.twinig.torneio.model.Funcao;
import com.twinig.torneio.model.Heroi;
import com.twinig.torneio.model.Mapa;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;

public class OverFastClient { // O OverFastClient tras uma lista que seja, da internet, no meu caso, a API
                              // OverFast, pega a resposta da solicitação e entrega ao programa

    private static final String BASE_URL = "https://overfast-api.tekrop.fr"; // HttpClient é a ferramenta que faz
                                                                             // solicitação da internet, no caso a API
                                                                             // publica
    private HttpClient http; // isso guarda o endereço da API
    // cria a variavel da solicitação

    public OverFastClient() {
        this.http = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    } // construtor que define que se nao conseguir conectar em 5 segundos, se a
      // internet estiver lenta, ele desiste da solicitação para nao travar

    private String buscar(String caminho) throws Exception { // sem o trhow Exception, esse metodo nao é definido para
                                                             // caso a internet caia
        HttpRequest requisicao = HttpRequest.newBuilder() // Http é a requisicao pela internet, sendo requisicao apenas
                                                          // a variavel
                .uri(URI.create(BASE_URL + caminho)) // endereco da API
                .timeout(Duration.ofSeconds(10)) // duracao da requisicao(10 segundos)
                .GET() // GET: metodo HTTP para buscar os dados da API
                .build(); // define a requisicao
        HttpResponse<String> resposta = http.send(requisicao, HttpResponse.BodyHandlers.ofString()); // é a resposta
                                                                                                     // solicitada,
                                                                                                     // <String> define
                                                                                                     // que vem como
                                                                                                     // texto
        if (resposta.statusCode() != 200) { // 200 resposta da internet definida como mensagem recebida, entao, caso
                                            // contrario, dispara o throw Exception, esse erro pega no try do metodo
                                            // carregarHerois e carregarMapas
            throw new Exception("API NAO RESPONDEU COM STATUS" + resposta.statusCode());
        }
        return resposta.body(); // conteudo da resposta, depois, lerHerois e lerMapas vai transformar em objetos
                                // Heroi e Mapa
    } //todo esse metodo buscar, devolve um texto, em formato Json, o java nao entende isso como objeto, então é preciso converter la embaixo

    public ArrayList<Heroi> carregarHerois() {  //esse e o carregarMapas sao os unicos que o main chama
        try { //se qualquer passo falhar, vai pro catch, pra nao quebrar por causa de falta de internet
            return lerHerois(buscar("/heroes")); //buscar /heroes vai na API no site e devolve em String, e o lerHerois transforma a arrayList<Heroi>
        } catch (Exception e) {
            System.out.println("Nao foi possivel acessar a API. Usando lista Local");
            return heroisPadrao();
        }
    }

    public ArrayList<Mapa> carregarMapa() { //merma coisa
        try {
            return lerMapas(buscar("/maps"));
        } catch (Exception e) {
            System.out.println("Nao foi possivel acessar a API. Usando lista local");
            return mapasPadrao();
        }
    }
    //para todo o busca, ele usa a biblioteca Gson para converter tudo em objetos, ja fiz isso la atras no pom.xml
    public ArrayList<Heroi> lerHerois(String json){
        ArrayList<Heroi> herois = new ArrayList<>(); //cria uma lista vazia onde os herois vao entrar
        JsonArray lista = JsonParser.parseString(json).getAsJsonArray(); //transforma o texto em uma lista JSON que o java consegue percorrer, o .parseString transforma o texto da API da internet, do formato JSON para objeto
        for(JsonElement item : lista){
            JsonObject obj = item.getAsJsonObject();
            String nome = obj.get("name").getAsString();
            Funcao funcao = converterFuncao(obj.get("role").getAsString());
            herois.add(new Heroi(nome, funcao));
        }
        return herois;
    }

    public ArrayList<Mapa> lerMapas(String json) {
        ArrayList<Mapa> mapas = new ArrayList<>();
        JsonArray lista = JsonParser.parseString(json).getAsJsonArray();
        for (JsonElement item : lista) {
            JsonObject obj = item.getAsJsonObject();
            String nome = obj.get("name").getAsString();

            String modos = "";
            JsonArray gamemodes = obj.getAsJsonArray("gamemodes");
            if (gamemodes != null) {
                for (int i = 0; i < gamemodes.size(); i++) {
                    if (i > 0) {
                        modos += ", ";
                    }
                    modos += gamemodes.get(i).getAsString();
                }
            }
            if (modos.equals("workshop") || modos.equals("pratice-range")) {
                continue;
            }
            mapas.add(new Mapa(nome, modos));
        }
        return mapas;
    }

    private Funcao converterFuncao(String role) {
        if (role.equalsIgnoreCase("tank")) {
            return Funcao.TANK;
        }
        if (role.equalsIgnoreCase("support")) {
            return Funcao.SUPPORT;
        }
        return Funcao.DAMAGE;
    }

    private ArrayList<Heroi> heroisPadrao() {
        ArrayList<Heroi> herois = new ArrayList<>();
        herois.add(new Heroi("Reinhardt", Funcao.TANK));
        herois.add(new Heroi("Winston", Funcao.TANK));
        herois.add(new Heroi("Soldier: 76", Funcao.DAMAGE));
        herois.add(new Heroi("Tracer", Funcao.DAMAGE));
        herois.add(new Heroi("Genji", Funcao.DAMAGE));
        herois.add(new Heroi("Mercy", Funcao.SUPPORT));
        herois.add(new Heroi("Ana", Funcao.SUPPORT));
        herois.add(new Heroi("Lucio", Funcao.SUPPORT));
        return herois;
    }

    private ArrayList<Mapa> mapasPadrao() {
        ArrayList<Mapa> mapas = new ArrayList<>();
        mapas.add(new Mapa("King's Row", "hybrid"));
        mapas.add(new Mapa("Ilios", "control"));
        mapas.add(new Mapa("Hanamura", "assault"));
        return mapas;
    }
}
