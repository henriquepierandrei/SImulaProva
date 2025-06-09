# SimulaProva API

![Logo SimulaProva](![image](https://github.com/user-attachments/assets/ff453b8d-78ce-41f6-985a-cc939bdc89c2)
)


[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Version](https://img.shields.io/badge/version-0.0.1--SNAPSHOT-blue)](https://github.com/seu-usuario/simulaprova)
[![License](https://img.shields.io/badge/license-MIT-green)](LICENSE)

> Uma API robusta e escalável para simulação de provas, desenvolvida com Spring Boot para facilitar a criação, gerenciamento e execução de avaliações digitais.

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Tecnologias](#tecnologias)
- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação)
- [Configuração](#configuração)
- [Uso da API](#uso-da-api)
- [Documentação](#documentação)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Contribuição](#contribuição)
- [Licença](#licença)
- [Contato](#contato)

## 🎯 Sobre o Projeto

O **SimulaProva** oferece funcionalidades abrangentes para criação de questões por meio da inteligência artifical utilizando Gemini AI, e SpringBoot buscando escabilidade e robustez.

### Objetivos Principais

- Facilitar a criação de simulado.
- Proporcionar uma experiência fluida para os usuários.
- Possibilitar do usuário responder as perguntas ou gerar um PDF para imprimir as questões.
- Garantir a acessibilidade dos usuários buscarem questões que influenciará no seu aprendizado.

## ⚡ Funcionalidades

- **Gerar Perguntas**: gere perguntas através do tema quer for requisitado.
- **Diferentes Dificuldades**: Possibilidade de alterar a dificuldade das questões (Fácil, Médio & Difícil).
- **Geração de PDF**: Geração de PDF caso o usuário prefira imprimir as perguntas.
- **Análise de Desempenho**: Possibilita o usuário analisar quantas respostas foram corretas e a explicação das respostas.
- **API RESTful**: Endpoints bem estruturados seguindo padrões REST.

## 🛠 Tecnologias

- **Java** 21+
- **Spring Boot** 3.5.0
- **Spring Web** - API REST
- **Spring Validation** - Validação de dados
- **Maven** - Gerenciamento de dependências

## 📋 Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- **Java 21** ou superior.
- **Maven 3.5+**
- **Git**

## 🚀 Instalação

### 1. Clone o repositório
```bash
git clone https://github.com/henriquepierandrei/SimulaProva
cd simulaprova
```

### 2. Instale as dependências
```bash
mvn clean install
```

### 3. Execute a aplicação
```bash
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`

## ⚙️ Configuração

### Banco de Dados

Por padrão, a aplicação utiliza H2 em memória. Para usar PostgreSQL:

```properties
# application.properties
spring.application.name=SimulaProva

# Api Key Gemini AI
gemini.api.key=${GEMINI_API_KEY}

# Nivel de log
logging.level.root=INFO
logging.level.tech.pierandrei.SimulaProva=DEBUG

# Formato do log
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
```

### Variáveis de Ambiente

```bash
# Opcional: configurar via variáveis de ambiente
export GEMINI_API_KEY=Gere_sua_Gemini_Key_aqui:https://ai.google.dev/gemini-api/docs
```

## 📡 Uso da API

### Gerar as Perguntas através da Gemini AI

```bash
POST /ai/generation/request
Content-Type: application/json

{
  "dificuldade_da_pergunta": "MEDIO",
  "quantidade_de_perguntas": "5"
  "tema_das_perguntas": "Programação"
}
```

### Evitar Cold Start

```bash
GET /health
```

### Gerar PDF através do response da geração

```bash
POST /pdf/generate
Params: "addGabarito" | Boolean
Content-Type: application/json
{
  {
    "perguntas": [
        {
            "pergunta": "Um astronauta flutua no espaço, longe de qualquer campo gravitacional significativo. Se ele arremessa uma ferramenta para longe, o que acontece com seu movimento subsequente?",
            "alternativas": [
                "A) Ele acelera gradualmente na direção oposta ao arremesso, devido à inércia.",
                "B) Ele permanece em repouso absoluto, pois não há forças externas atuando sobre ele.",
                "C) Ele se move com velocidade constante na direção oposta, conforme a Primeira Lei de Newton.",
                "D) Ele desacelera lentamente até parar, devido à resistência do vácuo espacial."
            ],
            "gabarito": "C",
            "explicacao": "A Primeira Lei de Newton (Lei da Inércia) estabelece que um corpo em movimento tende a manter seu estado de movimento retilíneo uniforme, a menos que uma força resultante externa atue sobre ele. Ao arremessar a ferramenta, o astronauta aplica uma força e sofre uma força de reação oposta, que o impulsiona. Sem outras forças, ele continuará com velocidade constante."
        },
        {
            "pergunta": "Uma força constante de 20 N atua sobre um objeto, causando uma aceleração de 4 m/s². Qual seria a aceleração desse mesmo objeto se uma força de 30 N fosse aplicada a ele, considerando a ausência de atrito?",
            "alternativas": [
                "A) 5 m/s², pois a aceleração é diretamente proporcional à força aplicada.",
                "B) 6 m/s², devido à relação entre força, massa e aceleração.",
                "C) 7.5 m/s², pois a massa do objeto é constante.",
                "D) 8 m/s², já que a força dobrou sua intensidade sobre o objeto."
            ],
            "gabarito": "B",
            "explicacao": "Pela Segunda Lei de Newton (F=ma), podemos calcular a massa do objeto: m = F/a = 20 N / 4 m/s² = 5 kg. Com a massa conhecida, aplicamos a nova força: a = F/m = 30 N / 5 kg = 6 m/s². A aceleração é diretamente proporcional à força e inversamente proporcional à massa."
        },
        {
            "pergunta": "Um bloco repousa sobre uma mesa horizontal. De acordo com a Terceira Lei de Newton, qual par de forças representa corretamente uma ação e reação?",
            "alternativas": [
                "A) O peso do bloco (Terra no bloco) e a força normal (mesa no bloco).",
                "B) A força que a mesa exerce no bloco e a força que o bloco exerce na mesa.",
                "C) O peso do bloco (Terra no bloco) e a força que o bloco exerce na Terra.",
                "D) A força normal da mesa no bloco e o peso do bloco (Terra no bloco)."
            ],
            "gabarito": "B",
            "explicacao": "A Terceira Lei de Newton afirma que para toda ação há uma reação de igual intensidade e sentido oposto, atuando em corpos diferentes. O par de forças ação-reação correto é a força que o bloco exerce na mesa (ação) e a força que a mesa exerce no bloco (reação). As outras opções descrevem forças que atuam no mesmo corpo ou não constituem pares ação-reação verdadeiros."
        }
    ],
    "quantidade_de_perguntas": 3,
    "tema_da_pergunta": "O tema das perguntas: Lei Newton",
    "dificuldade_da_pergunta": "Médio"
}
}
```

### Endpoints Principais

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/ai/generation/request` | Gere as perguntas utilizando GEMINI AI API |
| `GET` | `/health` | Evite Cold Start |
| `POST` | `/pdf/generate` | Gerar PDF através do response da API |


## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── tech/pierandrei/
│   │       ├── config/         # Configuração
│   │       ├── controller/     # Controladores da API
│   │       ├── dto/            # Data Transfer Objects
│   │       ├── enuns/          # Enuns
│   │       ├── exceptions/     # Tratar erros na aplicação
│   │       ├── schedule/       # Cronometrar requisição
│   │       └── service/        # Lógica da aplicação
│   └── resources/
│       ├── application.properties
│       ├── logo.png           # Logo SimulaProva
|       └── template.pdf       # Template base para gerar o PDF
└── test/
    └── java/                  # Testes unitários
```

## 🤝 Contribuição

Contribuições são bem-vindas! Para contribuir:

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -m 'feat: Feat nova`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

### Padrões de Código

- Siga as convenções do Java e Spring Boot.
- Documente métodos públicos.
- Use commits semânticos.

## 📝 Licença

Este projeto está sob a licença MIT.

## 📞 Contato

**Autor**: Henrique Pierandrei Fernandes
- 📧 Email: profissional.pierandrei@gmail.com
- 💼 LinkedIn: https://www.linkedin.com/in/henrique-pierandrei/
- 🐙 GitHub: https://github.com/henriquepierandrei

---

⭐ **Se este projeto foi útil, considere dar uma estrela no repositório!**

---

<div align="center">
  <sub>Desenvolvido com ❤️ usando Spring Boot!</sub>
</div>
