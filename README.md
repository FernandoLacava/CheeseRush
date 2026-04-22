# 🧀 CheeseRush

> Jogo 2D de corrida e plataforma desenvolvido em Java com libGDX

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)
![libGDX](https://img.shields.io/badge/libGDX-1.12-red?style=flat-square)
![Gradle](https://img.shields.io/badge/Gradle-8.x-blue?style=flat-square)
![Status](https://img.shields.io/badge/status-concluído-green?style=flat-square)

## 🎮 Sobre o Jogo

CheeseRush é um jogo 2D de corrida e plataforma onde você controla um **rato** que precisa fugir de um **gato** enquanto coleta o máximo de queijos possível. O cenário evolui ao longo da partida — o mundo muda do dia para a tarde e depois para a noite, enquanto o gato fica progressivamente mais rápido e agressivo.

### Funcionalidades
- **Corrida infinita** com geração procedural de obstáculos e coletáveis
- **IA do inimigo** com velocidade adaptativa e comportamento de perseguição dinâmico — o gato até pula sobre lixeiras para te alcançar
- **Double jump** para desviar de obstáculos
- **Progressão de dificuldade** baseada em queijos coletados
- **Transição de cenários** com efeito de fade suave (Dia → Tarde → Noite)
- **Game states** completos: Menu, Jogando e Game Over

## 🕹️ Como Jogar

| Tecla | Ação |
|-------|------|
| `SPACE` | Acelerar / Iniciar jogo |
| `W` | Pular (pressione duas vezes para double jump) |

**Objetivo:** colete queijos e sobreviva o máximo que puder sem ser capturado pelo gato!

## 🏗️ Arquitetura

O projeto segue o padrão **MVC (Model-View-Controller)**, separando claramente as responsabilidades:

```
core/src/main/java/dev/toni/zip/
├── CheeseRushGame.java    # Ponto de entrada — inicializa SpriteBatch e AssetManager
├── GameScreen.java        # Screen principal — conecta Controller e Renderer
├── WorldController.java   # Lógica do jogo (física, IA, colisões, spawn)
├── WorldRenderer.java     # Renderização (câmera, HUD, backgrounds, sprites)
├── Player.java            # Entidade do jogador (física, pulo, animação)
├── Cat.java               # Entidade do inimigo (perseguição adaptativa)
└── Constants.java         # Constantes globais do jogo
```

## 🧠 Destaques Técnicos

- **Física customizada** — gravidade, aceleração e desaceleração implementadas manualmente sem engine de física externa
- **IA adaptativa** — o gato ajusta sua velocidade com base na distância ao jogador (`closeFactor` e `farFactor`) e pula obstáculos de forma autônoma
- **Câmera com scroll** — câmera ortográfica que segue o jogador com fundo parallax
- **Spawn procedural** — queijos e lixeiras gerados dinamicamente com espaçamento aleatório controlado
- **Hitbox precisa** — colisões com `Rectangle.overlaps()` do libGDX

## 🛠️ Tecnologias

- **Java 17**
- **libGDX 1.12** — framework de desenvolvimento de jogos
- **Gradle** — gerenciamento de build e dependências
- **IntelliJ IDEA** — IDE utilizada no desenvolvimento

## ▶️ Como Executar

### Pré-requisitos
- Java 17+
- Gradle (ou use o wrapper `./gradlew`)

### Rodando o projeto

```bash
# Clone o repositório
git clone https://github.com/FernandoLacava/CheeseRush.git
cd CheeseRush

# Execute com o Gradle wrapper
./gradlew lwjgl3:run
```

## 👥 Equipe

Projeto desenvolvido em grupo para a disciplina de **Programação Orientada a Objetos** na **Universidade Presbiteriana Mackenzie** (2024).

## 📄 Licença

Este projeto é de uso acadêmico.
