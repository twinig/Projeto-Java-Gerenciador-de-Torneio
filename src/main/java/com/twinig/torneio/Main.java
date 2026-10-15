package com.twinig.torneio;

import com.twinig.torneio.report.Relatorio;
import com.twinig.torneio.model.*;
import com.twinig.torneio.service.TorneioService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import com.twinig.torneio.api.OverFastClient;

public class Main {
    public static void main(String[] args) {
        Torneio torneio = new Torneio("Copa Overwatch");
        TorneioService service = new TorneioService(torneio);
        Scanner scanner = new Scanner(System.in);
        Relatorio relatorio = new Relatorio(torneio);
        OverFastClient api = new OverFastClient();
        ArrayList<Mapa> mapas = api.carregarMapas();
        ArrayList<Heroi> herois = api.carregarHerois();
        System.out.println(herois.size() + " heróis e " + mapas.size() + " mapas carregados.");

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n=== GERENCIADOR DE TORNEIOS DE OVERWATCH ===");
            System.out.println("1 - Listar times");
            System.out.println("2 - Pesquisar time por nome");
            System.out.println("3 - Ver ranking");
            System.out.println("4 - Ver média de pontos");
            System.out.println("5 - Cadastrar time");
            System.out.println("6 - Cadastrar jogador em time");
            System.out.println("7 - Inscrever time em torneio");
            System.out.println("8 - Agendar Partida");
            System.out.println("9 - Registrar resultado de Partida");
            System.out.println("10 - Listar Partidas");
            System.out.println("11 - Relatórios");
            System.out.println("12 - Registrar desempenho de Jogador");
            System.out.println("13 - Listar heróis (API)");
            System.out.println("14 - Consultar time");
            System.out.println("15 - Desativar time");

            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            System.out.println("========================================");

            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    ArrayList<Time> times = service.listarTimes(); // pede ao Service a lista dos times
                    for (Time t : times) { // percorre essa lista
                        System.out.println(t.descricao()); // imprime a descricao de cada time percorrido no for, usando
                                                           // o metodo descricao() da classe Time
                    }
                    break;
                case 2:
                    System.out.println("Nome do time: ");
                    String busca = scanner.nextLine(); // le o que o usuario digitou e guarda na variavel busca
                    ArrayList<Time> encontrados = service.pesquisarTimePorNome(busca); // chama o metodo do service pra
                                                                                       // pesquisar o time, passando a
                                                                                       // variavel busca como parametro,
                                                                                       // e guarda o retorno na lista
                                                                                       // encontrados
                    if (encontrados.isEmpty()) { // verifica se a lista de encontrados esta vazia, se sim, imprime que
                                                 // nao encontrou
                        System.out.println("Nenhum time encontrado.");
                    }
                    for (Time t : encontrados) {
                        System.out.println(t.descricao());
                    }
                    break;
                case 3:
                    ArrayList<Time> ranking = service.ordenarTimesPorPontos(); // pega a funcao do service e usa o
                                                                               // bubble sort da classe Torneio
                    int posicao = 1; // cria um contador no 1, para
                    // erar os times de 1 a x
                    for (Time t : ranking) { // percorre a lista do ranking
                        System.out.println(posicao + "º - " + t.descricao()); // imprime a posicao do time no ranking, e
                                                                              // a descricao do time, usando o metodo
                                                                              // descricao() da classe Time
                        posicao++; // e imcrementa no contador para o proximo time ser o segundo, etc
                    }
                    break;
                case 4:
                    System.out.println("Média: " + service.calcularMediaPontos());
                    break;
                case 5:
                    System.out.print("ID do time: ");
                    String idTime = scanner.nextLine();
                    System.out.print("Nome do time: ");
                    String nomeTime = scanner.nextLine();
                    Time novoTime = new Time(idTime, nomeTime); // cria o objeto novoTime usando o construtor da classe
                                                                // Time
                    torneio.getTimes().add(novoTime); // pega a lista dos times do torneio e cria um novo time
                    System.out.println("Time cadastrado com sucesso!");
                    System.out.println("Agora, cadastre os jogadores do time (5 no total).");
                    break;
                case 6:
                    System.out.print("ID do time: ");
                    String idTimeDestino = scanner.nextLine();
                    System.out.println("ID do jogador: ");
                    String idJogador = scanner.nextLine(); // as duas Strings servem para definir o jogador a ser
                                                           // adicionado, depois usando o metodo do service
                    System.out.println("Nick do jogador: ");
                    String nickJogador = scanner.nextLine();
                    System.out.println("Função do jogador (TANK, DAMAGE, SUPPORT): ");
                    String funcaoJogador = scanner.nextLine();

                    Funcao funcao = Funcao.valueOf(funcaoJogador.toUpperCase()); // aqui converte o texto digitado,
                                                                                 // padronizando tudo para
                                                                                 // maiusuclo(tank -> TANK)
                    Jogador novoJogador = new Jogador(idJogador, nickJogador, funcao); // cria o objeto novoJogador
                                                                                       // usando o construtor da classe
                                                                                       // Jogador, passando as variaveis
                                                                                       // digitadas pelo usuario
                    boolean adicionado = service.associarJogadorATime(idTimeDestino, novoJogador); // chama o metodo do
                                                                                                   // service para
                                                                                                   // associar o jogador
                                                                                                   // ao time, passando
                                                                                                   // o id do time e o
                                                                                                   // objeto do jogador
                                                                                                   // como parametros, e
                                                                                                   // guarda o retorno
                                                                                                   // em uma variavel
                                                                                                   // booleana
                    if (adicionado) {
                        System.out.println("Jogador adicionado com sucesso!");
                    } else {
                        System.out.println(
                                "Não foi possível adicionar o jogador. Verifique se o time existe ou se já atingiu o limite de jogadores.");
                    }
                    break;
                case 7:
                    System.out.print("ID do time: ");
                    String idTimeInscricao = scanner.nextLine();
                    Time timeParaInscricao = service.consultarTime(idTimeInscricao); // chama o metodo ja existente

                    if (timeParaInscricao == null) { // se nao achar o time ja sabe
                        System.out.println("Time nao encontrado");
                    } else if (timeParaInscricao.inscrever()) {
                        System.out.println("Time inscrito com sucesso!");
                    } else {
                        System.out.println("Time não pode ser inscrito. Verifique se a composição está completa.");
                    }
                    break;

                case 8:
                    System.out.println("ID da partida: ");
                    String idPartida = scanner.nextLine();
                    System.out.println("ID do time A: ");
                    Time timeA = service.consultarTime(scanner.nextLine());
                    System.out.println("ID do time B: ");
                    Time timeB = service.consultarTime(scanner.nextLine()); // pede os IDs da partida e dos times, o
                                                                            // scanner le o que é digitado
                    // e é entregue a funcao de consultarTime, que devolve o time correspondente
                    if (timeA == null || timeB == null || timeA == timeB) {
                        System.out.println("Times invalidos");
                        break; // ou null ou se for igual, volta invalido
                    }
                    if (!timeA.composicaoCompletata() || !timeB.composicaoCompletata()) {
                        System.out.println("Os dois times precisam ter 5 jogadores.");
                        break;
                    }

                    System.out.println("Mapas:");
                    for (int i = 0; i < mapas.size(); i++) {
                        System.out.println(" - " + mapas.get(i).descricao()); // percorre a array dos mapas, escolhe o
                                                                              // mapa com get(i), o index do mapa
                    }
                    System.out.println("Numero do mapa: ");
                    int indiceMapa = Integer.parseInt(scanner.nextLine()); // devolve o texto em string, Integer sendo
                                                                           // uma classe do java que trabalha com
                                                                           // numeros inteiros
                    // parseInt converte texto para int, transforma "2" para int 2
                    System.out.println("Ano-Mês-diaTHora:Minuto"); // 2026-10-12T20:00 ex
                    try { // tudo dentro do try é tentado uma vez, se der erro, o java pula para o catch
                          // em vez de dar break
                        LocalDateTime dataHora = LocalDateTime.parse(scanner.nextLine()); // novamente, pega o texto
                                                                                          // digitado e converte para o
                                                                                          // objeto LocalDateTime
                        Partida novaPartida = new Partida(idPartida, dataHora, mapas.get(indiceMapa), timeA, timeB); // cria
                                                                                                                     // objeto
                                                                                                                     // partida
                        if (service.agendarPartida(novaPartida)) { // devolve true ou false, se der false, deu conflito
                                                                   // na data
                            System.out.println("Partida agendada.");
                        } else {
                            System.out.println("Conflito: Times ja jogam esse horario");
                        }
                    } catch (Exception e) { // aqui, caso tenha erro, cobre data, numero do mapa ou se nao houver mapa
                        System.out.println("Data ou mapa invalido.");
                    }
                    break;

                case 9:
                    System.out.println("ID da partida:");
                    String idPartidasRes = scanner.nextLine();
                    System.out.println("ID do time vencedor");
                    String idVencedor = scanner.nextLine(); // le os dois ids, e chama o registrarResultado do service
                    if (service.registrarResultado(idPartidasRes, idVencedor)) { // usei PartidaRes pq dentro do switch
                                                                                 // inteiro, todas as variaveis do case
                                                                                 // ocupam o msm espaço, caso tenha
                                                                                 // outro idPartida, pode dar variavel
                                                                                 // duplicada
                        System.out.println("Resultado registrado.");
                    } else {
                        System.out.println("Nao foi possivel (Partida inexistente ou ja finalizada)");
                    }
                    break;

                case 10: // simplismente lista as partidas
                    for (Partida p : service.listaPartidas()) { // percorre todas as partidas e imprime o id
                        System.out.println(" - " + p.descricao());
                    }
                    break;

                case 11:
                    System.out.println("--- RELATÓRIOS --- ");
                    System.out.println("Média de pontos por time: " + relatorio.mediaPontosPorTime());
                    System.out.println("Partidas finalizadas: " + relatorio.totalPartidasFinalizadas());
                    System.out.println("% Finalizadas: " + relatorio.percentualFinalizadas());

                    Time lider = relatorio.timeComMaisPontos();
                    if (lider != null) { // time com mais pontos retorna null se nao há times, caso contrario, vai
                                         // retornar o lider + descrição dele
                        System.out.println("LIDER: " + lider.descricao());
                    }

                    System.out.println("Ranking:");
                    int pos = 1;
                    for (Time t : relatorio.ranking()) {
                        System.out.println(pos + "º - " + t.descricao());
                        pos++;
                    }

                    RegistroDesempenho melhor = relatorio.melhorKDA();
                    if (melhor != null) {
                        System.out.println("Melhor KDA: " + melhor.descricao() + "( KDA " + melhor.calcularKDA() + ")");
                    } else {
                        System.out.println("Nenhum desempenho registrado ainda.");
                    }
                    break;

                case 12:
                    System.out.print("ID da partida: ");
                    Partida partidaReg = service.consultarPartida(scanner.nextLine());
                    if (partidaReg == null) {
                        System.out.println("Partida não encontrada.");
                        break;
                    }
                    System.out.print("Nick do jogador: ");
                    String nickReg = scanner.nextLine();
                    Jogador jogadorReg = null;
                    for (Jogador j : partidaReg.getTimeA().getJogadores()) {
                        if (j.getNick().equalsIgnoreCase(nickReg)) {
                            jogadorReg = j;
                        }
                    }
                    for (Jogador j : partidaReg.getTimeB().getJogadores()) {
                        if (j.getNick().equalsIgnoreCase(nickReg)) {
                            jogadorReg = j;
                        }
                    }
                    if (jogadorReg == null) {
                        System.out.println("Jogador não participa dessa partida.");
                        break;
                    }

                    ArrayList<Heroi> opcoes = new ArrayList<>();
                    for (Heroi h : herois) {
                        if (h.getFuncao() == jogadorReg.getFuncao()) {
                            opcoes.add(h);
                        }
                    }
                    System.out.println("Heróis disponíveis para " + jogadorReg.getFuncao() + ":");
                    for (int i = 0; i < opcoes.size(); i++) {
                        System.out.println(i + " - " + opcoes.get(i).getNome());
                    }

                    try {
                        System.out.print("Número do herói: ");
                        Heroi heroiReg = opcoes.get(Integer.parseInt(scanner.nextLine()));
                        System.out.print("Eliminações: ");
                        int el = Integer.parseInt(scanner.nextLine());
                        System.out.print("Mortes: ");
                        int mo = Integer.parseInt(scanner.nextLine());
                        System.out.print("Assistências: ");
                        int asst = Integer.parseInt(scanner.nextLine());

                        RegistroDesempenho reg = new RegistroDesempenho(jogadorReg, heroiReg);
                        reg.registrarEstatisticas(el, mo, asst);
                        if (partidaReg.adicionarRegistro(reg)) {
                            System.out.println("Desempenho registrado.");
                        } else {
                            System.out.println("Herói repetido no mesmo time nessa partida.");
                        }
                    } catch (Exception e) {
                        System.out.println("Entrada inválida.");
                    }
                    break;

                case 13:
                    for (Heroi h : herois) {
                        System.out.println(h.descricao());
                    }
                    break;

                case 14:
                    System.out.print("ID do time: ");
                    String idConsulta = scanner.nextLine();
                    Time timeConsultado = service.consultarTime(idConsulta);
                    if (timeConsultado == null) {
                        System.out.println("Time não encontrado.");
                    } else {
                        System.out.println(timeConsultado.descricao());
                        for (Jogador jogadorDoTime : timeConsultado.getJogadores()) {
                            System.out.println(" - " + jogadorDoTime.descricao());
                        }
                    }
                    break;

                case 15:
                    System.out.print("ID do time a desativar: ");
                    String idDesativar = scanner.nextLine();
                    if (service.desativarTime(idDesativar)) {
                        System.out.println("Time desativado.");
                    } else {
                        System.out.println("Time não encontrado.");
                    }
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }

        scanner.close();
    }
}