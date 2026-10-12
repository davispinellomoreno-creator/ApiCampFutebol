# ⚽ CampFutebol API

API REST para gestão de campeonatos de futebol: times, jogadores, partidas, súmulas, classificação e artilharia.

Desenvolvida com **Java 21 + Spring Boot 3** e **PostgreSQL 16**, pensada para ser consumida por um front-end React ou por aplicativos móveis.

---

## Sumário

- [Funcionalidades](#funcionalidades)
- [Stack](#stack)
- [Arquitetura](#arquitetura)
- [Como rodar](#como-rodar)
- [Configuração](#configuração)
- [Autenticação](#autenticação)
- [Endpoints](#endpoints)
- [Regras de negócio](#regras-de-negócio)
- [Modelo de dados](#modelo-de-dados)
- [Exemplos de uso](#exemplos-de-uso)
- [Erros](#erros)
- [Testes](#testes)
- [Roadmap](#roadmap)
- [Contribuindo](#contribuindo)
- [Licença](#licença)

---

## Funcionalidades

- Cadastro de **campeonatos** nos formatos pontos corridos, mata-mata ou grupos + mata-mata
- Cadastro de **times**, **jogadores** e **inscrição** de times e jogadores por campeonato
- **Geração automática da tabela** de jogos (rodadas, ida e volta)
- Registro de **súmula**: placar, gols, cartões e substituições
- **Classificação** calculada em tempo real com critérios de desempate configuráveis
- **Artilharia**, ranking de assistências e controle de **suspensões** por cartões
- Perfis de acesso: `ADMIN`, `ORGANIZADOR`, `ARBITRO` e acesso público de leitura
- Documentação interativa com **Swagger / OpenAPI**

## Stack

| Camada | Tecnologia |
| --- | --- |
| Linguagem | Java 21 |
| Framework | Spring Boot 3 (Web, Data JPA, Security, Validation) |
| Banco de dados | PostgreSQL 16 |
| Migrações | Flyway |
| Autenticação | JWT (access + refresh token) |
| Documentação | springdoc-openapi (Swagger UI) |
| Mapeamento | MapStruct |
| Testes | JUnit 5, Mockito, Testcontainers |
| Build | Maven |
| Containers | Docker e Docker Compose |

## Arquitetura

Organização em pacotes por domínio, cada um com suas camadas:

```
src/main/java/com/campfutebol
├── config/            # Segurança, CORS, OpenAPI
├── auth/              # Login, registro, JWT
├── campeonato/        # Campeonatos, fases, grupos
├── time/              # Times e inscrições
├── jogador/           # Jogadores e inscrições
├── partida/           # Partidas, rodadas, geração de tabela
├── sumula/            # Gols, cartões, substituições
├── classificacao/     # Cálculo de tabela e desempates
├── estatistica/       # Artilharia, assistências, suspensões
└── shared/            # Exceções, handler global, utilitários
```

Cada domínio segue o fluxo: `Controller → Service → Repository`, com DTOs de entrada e saída. As regras de negócio ficam nos `Service`, nunca nos controllers.

## Como rodar

### Pré-requisitos

- Java 21
- Maven 3.9+
- Docker e Docker Compose (recomendado)

### Com Docker Compose (recomendado)

```bash
git clone https://github.com/seu-usuario/campfutebol-api.git
cd campfutebol-api
docker compose up -d
```

A API sobe em `http://localhost:8080` e o PostgreSQL em `localhost:5432`.

### Local, sem Docker para a API

```bash
# sobe apenas o banco
docker compose up -d postgres

# roda a aplicação
./mvnw spring-boot:run
```

### Documentação interativa

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Configuração

Variáveis de ambiente (com valores padrão para desenvolvimento):

| Variável | Descrição | Padrão |
| --- | --- | --- |
| `DB_URL` | URL JDBC do PostgreSQL | `jdbc:postgresql://localhost:5432/campfutebol` |
| `DB_USER` | Usuário do banco | `campfutebol` |
| `DB_PASSWORD` | Senha do banco | `campfutebol` |
| `JWT_SECRET` | Chave de assinatura do JWT (mín. 32 caracteres) | — (obrigatória em produção) |
| `JWT_EXPIRATION_MINUTES` | Validade do access token | `15` |
| `JWT_REFRESH_DAYS` | Validade do refresh token | `7` |
| `CORS_ALLOWED_ORIGINS` | Origens permitidas | `http://localhost:5173` |

Exemplo de `.env`:

```env
DB_URL=jdbc:postgresql://localhost:5432/campfutebol
DB_USER=campfutebol
DB_PASSWORD=campfutebol
JWT_SECRET=troque-esta-chave-por-uma-bem-longa-e-aleatoria
CORS_ALLOWED_ORIGINS=http://localhost:5173
```

## Autenticação

A API usa **JWT Bearer Token**. Endpoints de leitura pública (tabela, jogos, artilharia) não exigem login.

```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "email": "organizador@campfutebol.com",
  "senha": "Senha@123"
}
```

Resposta:

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "c1a7f0e2-...",
  "expiraEm": 900
}
```

Use o token nas próximas requisições:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

### Perfis

| Perfil | Pode fazer |
| --- | --- |
| `ADMIN` | Tudo, inclusive gerenciar usuários |
| `ORGANIZADOR` | Criar e gerenciar os próprios campeonatos, times, inscrições e tabela |
| `ARBITRO` | Preencher e encerrar a súmula das partidas em que foi escalado |
| Público | Consultar campeonatos, jogos, classificação e estatísticas |

## Endpoints

Prefixo base: `/api/v1`

### Autenticação

| Método | Rota | Acesso | Descrição |
| --- | --- | --- | --- |
| POST | `/auth/register` | Público | Cria conta de organizador |
| POST | `/auth/login` | Público | Gera tokens |
| POST | `/auth/refresh` | Público | Renova o access token |
| POST | `/auth/logout` | Autenticado | Revoga o refresh token |

### Campeonatos

| Método | Rota | Acesso | Descrição |
| --- | --- | --- | --- |
| GET | `/campeonatos` | Público | Lista campeonatos (filtros: `status`, `temporada`) |
| GET | `/campeonatos/{id}` | Público | Detalhe do campeonato |
| POST | `/campeonatos` | ORGANIZADOR | Cria campeonato |
| PUT | `/campeonatos/{id}` | ORGANIZADOR | Atualiza (só antes de iniciar) |
| POST | `/campeonatos/{id}/iniciar` | ORGANIZADOR | Fecha inscrições e inicia |
| POST | `/campeonatos/{id}/encerrar` | ORGANIZADOR | Encerra e define campeão |
| DELETE | `/campeonatos/{id}` | ORGANIZADOR | Exclui (só em rascunho) |

### Times e jogadores

| Método | Rota | Acesso | Descrição |
| --- | --- | --- | --- |
| GET | `/times` | Público | Lista times |
| POST | `/times` | ORGANIZADOR | Cria time |
| PUT | `/times/{id}` | ORGANIZADOR | Atualiza time |
| GET | `/times/{id}/jogadores` | Público | Elenco do time |
| POST | `/jogadores` | ORGANIZADOR | Cadastra jogador |
| PUT | `/jogadores/{id}` | ORGANIZADOR | Atualiza jogador |

### Inscrições

| Método | Rota | Acesso | Descrição |
| --- | --- | --- | --- |
| POST | `/campeonatos/{id}/times` | ORGANIZADOR | Inscreve time no campeonato |
| DELETE | `/campeonatos/{id}/times/{timeId}` | ORGANIZADOR | Remove inscrição (antes do início) |
| POST | `/campeonatos/{id}/times/{timeId}/jogadores` | ORGANIZADOR | Inscreve jogador pelo time |

### Partidas e súmula

| Método | Rota | Acesso | Descrição |
| --- | --- | --- | --- |
| POST | `/campeonatos/{id}/tabela/gerar` | ORGANIZADOR | Gera rodadas e confrontos |
| GET | `/campeonatos/{id}/partidas` | Público | Lista partidas (filtros: `rodada`, `timeId`, `status`) |
| GET | `/partidas/{id}` | Público | Detalhe com eventos da súmula |
| PATCH | `/partidas/{id}` | ORGANIZADOR | Altera data, local ou árbitro |
| POST | `/partidas/{id}/iniciar` | ARBITRO | Inicia a partida |
| POST | `/partidas/{id}/eventos` | ARBITRO | Registra gol, cartão ou substituição |
| DELETE | `/partidas/{id}/eventos/{eventoId}` | ARBITRO | Remove evento lançado por engano |
| POST | `/partidas/{id}/encerrar` | ARBITRO | Encerra e consolida o placar |
| POST | `/partidas/{id}/wo` | ORGANIZADOR | Registra W.O. |

### Classificação e estatísticas

| Método | Rota | Acesso | Descrição |
| --- | --- | --- | --- |
| GET | `/campeonatos/{id}/classificacao` | Público | Tabela de classificação (por grupo, se houver) |
| GET | `/campeonatos/{id}/artilharia` | Público | Ranking de gols |
| GET | `/campeonatos/{id}/assistencias` | Público | Ranking de assistências |
| GET | `/campeonatos/{id}/suspensoes` | Público | Jogadores suspensos na próxima rodada |

## Regras de negócio

### Campeonato

- **RN-01** — Status do campeonato: `RASCUNHO → INSCRICOES_ABERTAS → EM_ANDAMENTO → ENCERRADO`. Não há volta de status.
- **RN-02** — Mínimo de 4 times para iniciar; em mata-mata, a quantidade deve ser potência de 2 (4, 8, 16, 32).
- **RN-03** — Depois de iniciado, não é possível incluir ou remover times.
- **RN-04** — Pontuação padrão: vitória 3, empate 1, derrota 0 (configurável por campeonato).

### Times e jogadores

- **RN-05** — Cada time inscreve de 11 a 25 jogadores por campeonato.
- **RN-06** — Um jogador só pode estar inscrito por um time no mesmo campeonato.
- **RN-07** — Número da camisa é único dentro do time no campeonato.
- **RN-08** — Inscrição de jogadores fecha ao final do primeiro turno (configurável).

### Partidas e súmula

- **RN-09** — Status da partida: `AGENDADA → EM_ANDAMENTO → ENCERRADA`, ou `ADIADA` / `WO`.
- **RN-10** — Um time não pode ter duas partidas no mesmo dia.
- **RN-11** — Só jogadores inscritos e não suspensos podem constar na súmula.
- **RN-12** — O placar é derivado dos eventos de gol; não é digitado manualmente. Gol contra conta para o adversário.
- **RN-13** — Depois de encerrada, a súmula só pode ser alterada por `ADMIN`, com justificativa registrada em auditoria.
- **RN-14** — W.O.: o time presente vence por 3 x 0; o ausente perde os pontos e pode ser eliminado após 2 W.O.s.
- **RN-15** — Em mata-mata, empate no tempo normal é decidido por pênaltis, registrados à parte e sem efeito na artilharia.

### Cartões e suspensões

- **RN-16** — 3 cartões amarelos acumulados = suspensão automática de 1 partida; o contador zera depois de cumprida.
- **RN-17** — Cartão vermelho direto ou segundo amarelo na mesma partida = suspensão de 1 partida.
- **RN-18** — A suspensão é cumprida na próxima partida oficial do time no mesmo campeonato.

### Classificação

- **RN-19** — Critérios de desempate padrão, nesta ordem:
  1. Pontos
  2. Número de vitórias
  3. Saldo de gols
  4. Gols marcados
  5. Confronto direto
  6. Menos cartões vermelhos
  7. Menos cartões amarelos
  8. Sorteio
- **RN-20** — A classificação é recalculada a cada partida encerrada e mantida em cache até a próxima alteração.

## Modelo de dados

Principais tabelas (PostgreSQL):

```
usuario            (id, nome, email, senha_hash, perfil, ativo, criado_em)
campeonato         (id, nome, temporada, formato, status, pontos_vitoria, pontos_empate, organizador_id)
time               (id, nome, sigla, escudo_url, cidade)
jogador            (id, nome, apelido, data_nascimento, posicao, documento)
inscricao_time     (id, campeonato_id, time_id, grupo)
inscricao_jogador  (id, inscricao_time_id, jogador_id, numero_camisa)
partida            (id, campeonato_id, rodada, fase, mandante_id, visitante_id,
                    data_hora, local, arbitro_id, status, gols_mandante, gols_visitante)
evento_partida     (id, partida_id, jogador_id, tipo, minuto, assistencia_jogador_id)
suspensao          (id, jogador_id, campeonato_id, motivo, partida_origem_id, cumprida_em_partida_id)
auditoria          (id, entidade, entidade_id, acao, antes, depois, usuario_id, criado_em)
```

`evento_partida.tipo`: `GOL`, `GOL_CONTRA`, `GOL_PENALTI`, `AMARELO`, `VERMELHO`, `SUBSTITUICAO`.

## Exemplos de uso

### Criar campeonato

```bash
curl -X POST http://localhost:8080/api/v1/campeonatos \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Copa Bairros 2026",
    "temporada": 2026,
    "formato": "PONTOS_CORRIDOS",
    "idaEVolta": true,
    "pontosVitoria": 3,
    "pontosEmpate": 1
  }'
```

### Registrar um gol

```bash
curl -X POST http://localhost:8080/api/v1/partidas/42/eventos \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "tipo": "GOL",
    "jogadorId": 10,
    "assistenciaJogadorId": 7,
    "minuto": 37
  }'
```

### Consultar classificação

```bash
curl http://localhost:8080/api/v1/campeonatos/1/classificacao
```

```json
{
  "campeonatoId": 1,
  "rodadaAtual": 8,
  "tabela": [
    {
      "posicao": 1,
      "time": { "id": 3, "nome": "Leões do Norte", "sigla": "LEN" },
      "pontos": 19,
      "jogos": 8,
      "vitorias": 6,
      "empates": 1,
      "derrotas": 1,
      "golsPro": 17,
      "golsContra": 6,
      "saldo": 11,
      "aproveitamento": 79.2
    }
  ]
}
```

### Paginação

Listagens aceitam `page`, `size` e `sort`:

```
GET /api/v1/campeonatos/1/partidas?rodada=3&page=0&size=20&sort=dataHora,asc
```

## Erros

Todas as respostas de erro seguem o padrão [RFC 7807 (Problem Details)](https://www.rfc-editor.org/rfc/rfc7807):

```json
{
  "type": "https://campfutebol.dev/erros/jogador-suspenso",
  "title": "Jogador suspenso",
  "status": 422,
  "detail": "O jogador 10 está suspenso para esta partida (3 amarelos).",
  "instance": "/api/v1/partidas/42/eventos"
}
```

| Status | Quando acontece |
| --- | --- |
| 400 | Dados inválidos (Bean Validation) |
| 401 | Token ausente, inválido ou expirado |
| 403 | Perfil sem permissão para a ação |
| 404 | Recurso não encontrado |
| 409 | Conflito: time já inscrito, camisa repetida, partida no mesmo dia |
| 422 | Violação de regra de negócio: jogador suspenso, campeonato já iniciado |
| 500 | Erro inesperado |

## Testes

```bash
# testes unitários
./mvnw test

# testes de integração (sobe PostgreSQL via Testcontainers)
./mvnw verify
```

Cobertura mínima esperada: **80%** nas classes de serviço, com foco em classificação, desempate e suspensões.

## Roadmap

- [ ] Notificações em tempo real do placar via WebSocket
- [ ] Upload de escudos e fotos de jogadores (S3)
- [ ] Exportação da súmula em PDF
- [ ] Sorteio automático de grupos com potes
- [ ] Estatísticas avançadas (média de gols por rodada, melhor ataque/defesa)

## Contribuindo

1. Faça um fork do projeto
2. Crie uma branch: `git checkout -b feature/minha-feature`
3. Commit seguindo [Conventional Commits](https://www.conventionalcommits.org/): `git commit -m "feat: adiciona ranking de assistências"`
4. Envie: `git push origin feature/minha-feature`
5. Abra um Pull Request

## Licença

Distribuído sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.
