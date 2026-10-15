# Projeto Gerenciador de Torneio de Overwatch amador

Projeto livre de Sistema de gerenciamento e Analise, focado na API OverFast com tema do jogo Overwatch, organizador de torneios amadores  de Overwatch

É um sistema em java, executado a partir de terminal para organizar torneios amadores de Overwatch: Contém cadastro de time e jogadores, agendamento de partidas, registro de resultados e desempenho, classificação e relatórios.

# Gerenciador de Torneio de Overwatch

Sistema em Java, executado no terminal, para organizar torneios amadores de Overwatch: cadastro de times e jogadores, agendamento de partidas, registro de resultados e desempenho, classificação e relatórios. Desenvolvido com Programação Orientada a Objetos para o **Projeto Livre** da disciplina.

---

## Integrantes

| Integrante | Função |
|---|---|
| **[Lucas Okiyama]** (@twinig) no github | Desenvolvimento completo: modelagem, regras de negócio, serviços, relatórios, menu, integração com API, testes automatizados e documentação |

 - Projeto desenvolvido individualmente.

---

## Descrição do problema

Campeonatos amadores de Overwatch costumam ser organizados em planilhas e grupos do Whatsapp, ou mesmo manualmente no discord. Isso gera erros comuns: times montados fora da composição permitida, dois jogos marcados no mesmo horário para o mesmo time, resultados lançados duas vezes e classificação calculada à mão.

Este sistema centraliza o controle do torneio e aplica as regras automaticamente, impedindo lançamentos inválidos e gerando a classificação e os indicadores de desempenho.

Possíveis usuários:

- organizadores de campeonatos amadores e de comunidades;
- administradores de ligas universitárias e de grupos de amigos;
- capitães de time que querem acompanhar o desempenho dos seus jogadores.

---

## Funcionalidades

Menu interativo no terminal:

| Opção | Funcionalidade |
|---|---|
| 1 | Listar times |
| 2 | Pesquisar time por nome (busca parcial, sem diferenciar maiúsculas) |
| 3 | Ver ranking (ordenado por pontos) |
| 4 | Ver média de pontos |
| 5 | Cadastrar time |
| 6 | Cadastrar jogador em um time |
| 7 | Inscrever time no torneio (valida composição completa) |
| 8 | Agendar partida (escolhe times, mapa vindo da API e horário) |
| 9 | Registrar resultado de partida |
| 10 | Listar partidas |
| 11 | Relatórios e indicadores |
| 12 | Registrar desempenho de um jogador em uma partida (herói vindo da API) |
| 13 | Listar heróis (dados da API) |
| 14 | Consultar time (mostra os dados do time e seus jogadores) |
| 15 | Desativar time |
| 0 | Sair |

### Operações do sistema

| Operação | Onde está |
|---|---|
| Cadastrar | Opções 5 (time) e 6 (jogador) |
| Consultar | Opção 14 **TorneioService.consultarTime** |
| Listar | Opções 1 (times), 10 (partidas) e 13 (heróis) |
| Pesquisar por atributo | Opção 2 (nome do time) |
| Remover ou desativar | Opção 15 **TorneioService.desativarTime** |
| Associar objetos | Opção 6 (jogador a time) e opção 12 (jogador e herói em uma partida) |
| Ordenar | Opção 3 (ranking por pontos) |
| Executar regras de negócio | Opções 6, 7, 8, 9 e 12 |
| Calcular indicadores e gerar relatórios | Opções 4 e 11 |

### Fluxo principal do sistema

**Inscrição -> Partida -> Resultado -> Classificação**

1. Cria-se o time e adicionam-se os 5 jogadores, respeitando a composição.
2. O time é inscrito no torneio.
3. Uma partida é agendada entre dois times em um mapa e horário.
4. Registra-se o desempenho individual (herói usado, eliminações, mortes e assistências).
5. Registra-se o vencedor: os pontos são atribuídos e a classificação é atualizada.

Esse processo envolve as classes Time.java, Partida.java e Torneio.java (além de Jogador.java e RegistroDesempenho.java).

---

## Principais classes

### Modelo (**model**)

| Classe | Responsabilidade |
|---|---|
| Funcao | Enum com as funções do jogo: **TANK**, **DAMAGE**, **SUPPORT** |
| Heroi | Herói do jogo (nome e função). Carregado da API |
| Mapa | Mapa do jogo (nome e modo de jogo). Carregado da API |
| Jogador | Jogador cadastrado, com função, lista de heróis favoritos e status ativo/inativo |
| Time| Conjunto de até 5 jogadores, com pontuação e validação da composição |
| RegistroDesempenho| Desempenho de um jogador, com um herói, em uma partida (eliminações, mortes, assistências, KDA) |
| Partida | Confronto entre dois times em um mapa e horário, com os registros de desempenho e o vencedor |
| Torneio| Organiza times e partidas, aplica regras de inscrição e agendamento e gera o ranking |

### Outras camadas

| Pacote | Classe | Responsabilidade |
|---|---|---|
| service | TorneioService | Operações do sistema: cadastrar, consultar, listar, desativar, associar, pesquisar, ordenar |
| report | Relatorio | Relatórios e indicadores |
| api | OverFastClient | Consome a API OverFast e converte o JSON em objetos Heroi e Mapa |
| main | Main | Menu interativo (sem regras de negócio) |

### Relacionamentos e coleções

```
> Torneio
 ├── ArrayList<Time>
 │     └── Time ── ArrayList<Jogador>
 │                    └── Jogador ── ArrayList<Heroi> (favoritos)
 └── ArrayList<Partida>
       ├── Mapa, Time A, Time B   (associações)
       └── ArrayList<RegistroDesempenho>
             └── RegistroDesempenho ── Jogador + Heroi
```

RegistroDesempenho liga um Jogador a um Heroi dentro de uma Partida, registrando informações relacionadas aos três objetos.

---

## Regras de negócio

1. **Composição do time:** um time tem no máximo 5 jogadores, sendo 1 `TANK`, 2 `DAMAGE` e 2 `SUPPORT`. Jogador que exceda o limite da função não é aceito (`Time.adicionarJogador`).
2. **Time completo para competir:** só é inscrito no torneio, e só pode agendar partida, o time com a composição completa de 5 jogadores (`Time.inscrever`, usado na opção 7 do menu, e `Torneio.inscreverTime`).
3. **Conflito de horário:** não é possível agendar duas partidas no mesmo horário envolvendo o mesmo time (`Torneio.agendarPartida`).
4. **Herói repetido:** dentro de uma mesma partida, dois jogadores do mesmo time não podem usar o mesmo herói. Times adversários podem (`Partida.adicionarRegistro`).
5. **Pontuação e resultado:** o vencedor recebe **1 ponto** e a partida é finalizada. O resultado só pode ser registrado uma vez, e o vencedor precisa ser um dos times da partida (`Partida.registrarResultado`).
6. **Cálculo de KDA:** `(eliminações + assistências) / mortes`. Quando não há mortes, o cálculo evita divisão por zero (`RegistroDesempenho.calcularKDA`).
7. **Desativação em vez de exclusão:** times e jogadores são desativados, e não apagados, preservando o histórico das partidas (opção 15 do menu).
8. **Time desativado não joga:** um time desativado não pode ser escalado em novas partidas (`Torneio.agendarPartida`).

---

## Relatórios e indicadores

| Indicador | Tipo | Método |
|---|---|---|
| Média de pontos por time | Média | Relatorio.mediaPontosPorTime |
| Total de partidas finalizadas | Total | Relatorio.totalPartidasFinalizadas |
| Time com mais pontos | Maior valor | Relatorio.timeComMaisPontos |
| Ranking dos times | Ranking | Relatorio.ranking |
| Melhor KDA entre todos os registros | Indicador próprio | Relatorio.melhorKDA |
| Percentual de partidas finalizadas | Extra | Relatorio.percentualFinalizadas |

---

## Tecnologias utilizadas

| Tecnologia | Uso |
|---|---|
| Java 25 | Linguagem |
| Maven | Gerenciamento de dependências e build |
| **OverFast API** (tecnologia externa 1) | Fornece heróis e mapas reais do Overwatch |
| **java.net.http.HttpClient** | Requisições HTTP à API |
| Gson 2.11.0 | Leitura do JSON retornado pela API |
| **JUnit 5** (tecnologia externa 2) | Testes automatizados das regras de negócio |

### Sobre a API

A  OverFast API (https://overfast-api.tekrop.fr) é uma API **não oficial**, gratuita e sem autenticação, que fornece dados do jogo. O sistema acessa os endpoints `/heroes` e `/maps` uma única vez ao iniciar.

Se a API estiver indisponível ou sem internet, o sistema **continua funcionando** com listas locais de heróis e mapas.

---

## Estrutura do projeto

```
src/
├── main/java/com/twinig/torneio/
│   ├── Main.java
│   ├── api/        OverFastClient
│   ├── model/      Funcao, Heroi, Mapa, Jogador, Time,
│   │               RegistroDesempenho, Partida, Torneio
│   ├── report/     Relatorio
│   └── service/    TorneioService
└── test/java/com/twinig/torneio/
    ├── TesteUtil, TimeTest, PartidaTest,
    └── TorneioTest, RegistroDeDesempenhoTest
```

---

## Configuração

**Pré-requisitos:**

- JDK 25 (ou superior compatível)
- Maven 3.9+
- Git

**Passos:**

```bash
git clone https://github.com/twinig/Projeto-Java-Gerenciador-de-Torneio.git
cd Projeto-Java-Gerenciador-de-Torneio
java -version
mvn -version
```

Os dois comandos de versão devem indicar o Java 25.

---

## Execução

**Pelo terminal:**

```bash
mvn compile exec:java
```

**Pelo VS Code:** abra o projeto, instale o *Extension Pack for Java*, abra `Main.java` e clique em **Run**.

**Executar os testes:**

```bash
mvn test
```

---

## Exemplo de utilização

Ao iniciar, o sistema carrega os dados da API:

```
54 heróis e 54 mapas carregados.
```

Fluxo de exemplo para montar uma partida:

1. **Opção 5:** cadastrar o time **A** (NomeTimeA) e o time **B** (NomeTimeB) .
2. **Opção 6** (repetir 5 vezes para cada time): adicionar os jogadores, informando o id do time, id, nick e função (**TANK**, **DAMAGE** ou **SUPPORT**).
3. **Opção 7:** inscrever cada time. O sistema só aceita se houver 5 jogadores na composição correta.
4. **Opção 8:** agendar a partida, informando os times, escolhendo o mapa pelo número e a data no formato **2026-10-12T20:00**.
5. **Opção 12:** registrar o desempenho de um jogador, escolhendo o herói pelo número e informando eliminações, mortes e assistências.
6. **Opção 9:** registrar o time vencedor.
7. **Opção 3:** ver o ranking atualizado.
8. **Opção 11:** ver os relatórios.
9. **Opção 14:** consultar um time e ver seus jogadores. **Opção 15:** desativar um time, que passa a aparecer como [DESATIVADO] na listagem.

Exemplos de regras em ação:

- Tentar adicionar um segundo **TANK** ao mesmo time é recusado.
- Agendar outra partida no mesmo horário com um time já escalado é recusado [Não foi possível agendar: conflito de horário ou time desativado.].
- Agendar partida com um time desativado (opção 15) também é recusado.
- Registrar o resultado duas vezes na mesma partida é recusado.
- Dois jogadores do mesmo time escolhendo o mesmo herói na mesma partida: o segundo é recusado.

---

## Testes automatizados

Os testes JUnit verificam as regras de negócio:

| Classe de teste | O que verifica |
|---|---|
| **TimeTest** | Limite por função, limite de 5 jogadores, composição completa |
| **PartidaTest** | Pontuação do vencedor, resultado único, vencedor válido, herói repetido |
| **TorneioTest** | Inscrição de time incompleto, conflito de horário, time desativado, ranking, média e maior pontuação |
| **RegistroDeDesempenhoTest** | Cálculo de KDA, incluindo o caso sem mortes |

---

## Utilização de IA

| Ferramenta | Objetivo | Resumo do uso | Revisão pelo Dev |
|---|---|---|---|
| Claude Sonnet 5.5 | Código Base e Regras de Negócio | Testes unitários e manuais das regras, validação dos casos de borda (time cheio, herói repetido, conflito de horário, divisão por zero no KDA), correção de erros de compilação e anotação do código para compreensão. |
| Claude Sonnet 5.5 | Integração com API e Testes | Auxiliou na estrutura do cliente HTTP, na conversão do JSON com Gson, no plano de contingência sem internet e na criação dos testes JUnit. | Conferência dos campos reais retornados pela API, execução dos testes e ajuste dos nomes de métodos e dados. |

---

## Autor

**[Lucas Okiyama]**
GitHub: [@twinig](https://github.com/twinig)