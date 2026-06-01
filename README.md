# SIAS - Sistema Integrado Academia da Saúde 🏥💪

O **SIAS** é uma plataforma de gestão clínica e esportiva desenvolvida para integrar as atividades da **UBSF** e da **Academia da Saúde** da Comunidade Esperança. O sistema visa eliminar a perda de dados em fichas de papel, permitindo o acompanhamento da evolução dos pacientes através de indicadores de saúde e avaliações físicas.

---

## 🚀 Stack Tecnológica

*   **Linguagem:** Java 21 (LTS)
*   **Framework:** Spring Boot 3.x
*   **Banco de Dados:** MySQL 8.0
*   **Containerização:** Docker & Docker Compose
*   **Gerenciador de Dependências:** Maven

---

## 🛠️ Pré-requisitos

Para rodar o projeto, você e o time de 6 colaboradores precisam ter instalado:

1.  **Docker & Docker Compose** (V2 recomendado)
2.  **Git**
3.  **Swagger** (Para testar os endpoints da API)

---

## ⚡ Como Executar o Projeto

Siga os passos abaixo para subir o ambiente completo (API + Banco de Dados):

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/Rogeriocsl/sias-backend.git
    cd sias-backend
    ```

2.  **Suba os containers:**
    ```bash
    docker compose up -d --build
    ```
    *O parâmetro `--build` garante que o Docker compile a versão mais recente do seu código Java.*

3.  **Verifique se tudo está rodando:**
    ```bash
    docker ps
    ```
    Você deve ver os containers `sias-api` e `sias-db` com o status `Up (healthy)`.

---

## 🔌 Informações de Acesso

*   **API Base URL:** `http://localhost:8080/swagger-ui/index.html`
*   **MySQL Database:** `localhost:3306`
*   **Database Name:** `sias_db`
*   **User:** `admin` | **Password:** `password123`

---

## 📂 Estrutura de Pastas (Padrão do Projeto)

Para manter a organização, seguimos a estrutura:

*   `src/main/java/br/com/sias/api/config`: Configurações globais (Segurança/CORS).
*   `src/main/java/br/com/sias/api/controller`: Endpoints REST.
*   `src/main/java/br/com/sias/api/model`: Entidades JPA (Tabelas do banco).
*   `src/main/java/br/com/sias/api/repository`: Interfaces de acesso a dados.
*   `src/main/java/br/com/sias/api/service`: Regras de negócio e cálculos.
*   `src/main/java/br/com/sias/api/dto`: Transferência de dados.
*   `src/main/java/br/com/sias/api/excepition`: Classe para tratamento de exceções das aplicações.
*   `src/main/java/br/com/sias/api/security`: Configurações e regras de segurança da aplicação.

---

## 🤝 Contribuição
* Entre na branch da sua tarefa: `git checkout -b feat/nome-da-tarefa`

* Faça o commit seguindo o padrão: `git commit -m "feat: descrição curta"`

* Mantenha sua branch atualizada (Evite conflitos):
  Antes de enviar seu código, garanta que você tem a versão mais recente da main: `git pull origin main`
* Envie para o repositório: `git push origin feat/nome-da-tarefa`

## ⚠️ Solução de Problemas (Troubleshooting)

**1. Erro "Command not found: docker-compose"**
Utilize o comando sem o hífen: `docker compose`. As versões mais novas do Docker integraram o compose diretamente no CLI.

**2. Erro "Unable to access jarfile"**
Certifique-se de que rodou o comando com `--build`. Isso força o Maven a gerar o arquivo `.jar` dentro do container antes de tentar executá-lo.

**3. Limpeza total do ambiente**
Se o banco de dados apresentar inconsistências, limpe os volumes e reinicie:
```bash
docker compose down -v
docker compose up --build
