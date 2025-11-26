# ImobiGest API

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)
![Maven](https://img.shields.io/badge/Maven-Build%20Tool-orange)
![License](https://img.shields.io/badge/License-MIT-yellow)

## 📖 Sobre o Projeto

**ImobiGest** é uma API REST robusta desenvolvida em Spring Boot para gestão completa de imobiliárias. O sistema oferece controle de profissionais, vendas, comissões e relatórios, proporcionando uma solução eficiente para o gerenciamento de operações imobiliárias.

## ✨ Funcionalidades Principais

- 🔐 **Sistema de Autenticação**: Login seguro com JWT tokens
- 👥 **Gestão de Profissionais**: CRUD completo com associação de cargos
- 💼 **Gestão de Cargos**: Sistema hierárquico de cargos e funções
- 🏢 **Gestão de Imobiliárias**: Cadastro e controle de imobiliárias
- 🏠 **Sistema de Vendas**: Registro de vendas com múltiplas formas de pagamento
- 💰 **Cálculo Automático de Comissões**: Sistema inteligente baseado em cargos
- 📊 **Controle de Parcelas**: Gestão de parcelas com status de pagamento
- 📈 **Dashboard Analytics**: Métricas e relatórios em tempo real
- 📚 **Documentação Swagger**: API totalmente documentada
- ⚡ **Health Checks**: Monitoramento de saúde da aplicação

## 🏗️ Tecnologias

### Backend
- **Java 17**: Linguagem principal
- **Spring Boot 3.5.6**: Framework base
- **Spring Security**: Autenticação e autorização
- **Spring Data JPA**: Persistência de dados
- **PostgreSQL**: Banco de dados principal
- **Flyway**: Migração de banco de dados
- **JWT**: Autenticação stateless
- **Lombok**: Redução de boilerplate
- **SpringDoc OpenAPI**: Documentação automática

### Ferramentas
- **Maven**: Gerenciamento de dependências
- **Docker**: Containerização
- **Git**: Controle de versão

## 🚀 Início Rápido

### Pré-requisitos

- Java 17+
- PostgreSQL 12+
- Maven 3.6+
- Git

### Instalação

1. **Clone o repositório**
```bash
git clone https://github.com/ArthurLopes191/imobigest-api.git
cd imobigest-api
```

2. **Configure o banco de dados**
```sql
CREATE DATABASE "imobigest-api";
```

3. **Configure as variáveis de ambiente**
```bash
export DB_USER=seu_usuario
export DB_PASSWORD=sua_senha
export JWT_SECRET=sua_chave_secreta_jwt
```

4. **Execute a aplicação**
```bash
mvn spring-boot:run
```

5. **Acesse a aplicação**
- API: `http://localhost:8080`
- Documentação: `http://localhost:8080/swagger-ui.html`
- Health Check: `http://localhost:8080/health`

## 🐳 Docker

### Executar com Docker

```bash
# Build da imagem
docker build -t imobigest-api .

# Executar container
docker run -p 8080:8080 \
  -e DB_USER=postgres \
  -e DB_PASSWORD=senha \
  -e JWT_SECRET=chave-secreta \
  imobigest-api
```

### Docker Compose

```bash
# Subir todos os serviços
docker-compose up -d

# Apenas banco de dados
docker-compose up -d postgres

# Com logs
docker-compose up
```

## 📚 Documentação da API

### Principais Endpoints

#### 🔐 Autenticação
```http
POST /auth/login     # Login de usuário
POST /auth/register  # Registro de usuário
```

#### 👥 Profissionais
```http
GET    /profissionais        # Listar profissionais
POST   /profissionais        # Criar profissional
GET    /profissionais/{id}   # Buscar por ID
PUT    /profissionais/{id}   # Atualizar profissional
DELETE /profissionais/{id}   # Deletar profissional
```

#### 💼 Cargos
```http
GET    /cargos     # Listar cargos
POST   /cargos     # Criar cargo
PUT    /cargos/{id} # Atualizar cargo
DELETE /cargos/{id} # Deletar cargo
```

#### 🏠 Vendas
```http
GET    /vendas        # Listar vendas (paginado)
POST   /vendas        # Criar venda
GET    /vendas/{id}   # Buscar venda
PUT    /vendas/{id}   # Atualizar venda
DELETE /vendas/{id}   # Deletar venda
```

#### 💰 Comissões
```http
GET    /comissoes     # Listar comissões
POST   /comissoes     # Criar comissão
PUT    /comissoes/{id} # Atualizar comissão
```

#### 📊 Dashboard
```http
GET /dashboard/metricas        # Métricas gerais
GET /dashboard/vendas-periodo  # Relatório por período
```

### Exemplos de Uso

#### Login
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@exemplo.com","senha":"senha123"}'
```

#### Criar Venda
```bash
curl -X POST http://localhost:8080/vendas \
  -H "Authorization: Bearer SEU_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "descricaoImovel": "Casa 3 quartos - Centro",
    "valorTotal": 300000.00,
    "formaPagamento": "PARCELADO",
    "numeroParcelas": 12,
    "idImobiliaria": 1,
    "idsProfissionais": [1]
  }'
```

## 🗄️ Estrutura do Banco de Dados

### Principais Entidades

```sql
-- Profissionais
PROFISSIONAL (id, nome, data_criacao, data_atualizacao)

-- Cargos
CARGO (id, nome, comissao_automatica)

-- Vendas
VENDA (id, descricao_imovel, valor_total, data_venda, forma_pagamento, numero_parcelas)

-- Comissões
COMISSAO (id, valor, tipo, percentual, id_profissional, id_venda)

-- Parcelas
PARCELA (id, numero, valor, data_vencimento, data_pagamento, status, id_venda)
```

### Relacionamentos
- Profissional ↔ Cargo (N:N)
- Venda → Imobiliária (N:1)
- Venda ↔ Profissional (N:N)
- Venda → Parcela (1:N)
- Venda → Comissão (1:N)

## 📁 Estrutura do Projeto

```
src/main/java/com/pds/ImobiGest/
├── config/             # Configurações (CORS, OpenAPI)
├── controller/         # Controllers REST
├── dto/               # Data Transfer Objects
├── entity/            # Entidades JPA
├── enums/             # Enumerações
├── exceptions/        # Tratamento de exceções
├── repository/        # Repositórios JPA
├── security/          # Configurações de segurança
└── service/           # Regras de negócio
```

## 🧪 Testes

```bash
# Executar todos os testes
mvn test

# Testes com cobertura
mvn test jacoco:report

# Testes específicos
mvn test -Dtest=ProfissionalServiceTest
```

## ⚙️ Configuração

### application.properties

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/imobigest-api
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}

# JPA
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

# Flyway
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration

# Security
api.security.token.secret=${JWT_SECRET}
```

### Profiles Disponíveis

- **dev**: Desenvolvimento (logs detalhados)
- **test**: Testes (H2 in-memory)
- **prod**: Produção (configurações otimizadas)

## 🔒 Segurança

### Autenticação JWT

1. Login com email/senha
2. Recebimento do token JWT
3. Inclusão do token no header: `Authorization: Bearer {token}`
4. Validação automática em todos os endpoints protegidos

### Autorização

- **USER**: Operações básicas
- **ADMIN**: Acesso completo ao sistema

## 📊 Monitoramento

### Health Check
```bash
curl http://localhost:8080/health
```

### Métricas (Actuator)
```bash
curl http://localhost:8080/actuator/health
curl http://localhost:8080/actuator/info
curl http://localhost:8080/actuator/metrics
```

## 🚀 Deploy

### Variáveis de Ambiente para Produção

```bash
DB_USER=usuario_producao
DB_PASSWORD=senha_segura
JWT_SECRET=chave_jwt_super_segura_256_bits
SPRING_PROFILES_ACTIVE=prod
```

### Docker em Produção

```bash
docker run -d \
  --name imobigest-api \
  -p 8080:8080 \
  -e DB_USER=postgres \
  -e DB_PASSWORD=senha_producao \
  -e JWT_SECRET=chave_super_segura \
  -e SPRING_PROFILES_ACTIVE=prod \
  --restart unless-stopped \
  imobigest-api:latest
```

## 🛠️ Desenvolvimento

### Configuração do Ambiente

1. **Clone e instale dependências**
```bash
git clone https://github.com/ArthurLopes191/imobigest-api.git
cd imobigest-api
mvn clean install
```

2. **Configure IDE** (IntelliJ/Eclipse/VS Code)
   - Instale plugin do Lombok
   - Configure formatação de código
   - Configure executar/debug

3. **Execute em modo desenvolvimento**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Padrões de Código

- **Naming**: camelCase para métodos/variáveis, PascalCase para classes
- **Estrutura**: Uma responsabilidade por classe
- **Documentação**: Javadoc para métodos públicos
- **Testes**: Cobertura mínima de 80%

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-funcionalidade`)
3. Commit suas mudanças (`git commit -am 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abra um Pull Request


## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👥 Equipe

- **Arthur Lopes** - Desenvolvedor Principal - [@ArthurLopes191](https://github.com/ArthurLopes191)

---

<div align="center">

**ImobiGest API** - Sistema de Gestão Imobiliária

Desenvolvido com ❤️ usando Spring Boot

[![GitHub stars](https://img.shields.io/github/stars/ArthurLopes191/imobigest-api)](https://github.com/ArthurLopes191/imobigest-api/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/ArthurLopes191/imobigest-api)](https://github.com/ArthurLopes191/imobigest-api/network)
[![GitHub issues](https://img.shields.io/github/issues/ArthurLopes191/imobigest-api)](https://github.com/ArthurLopes191/imobigest-api/issues)

</div>