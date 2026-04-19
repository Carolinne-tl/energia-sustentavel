# Sistema de Energia Sustentável - API REST

## 📋 Descrição

Sistema de monitoramento de eficiência energética e sustentabilidade desenvolvido com Spring Boot, focado em ESG (Environmental, Social and Governance). O sistema permite o monitoramento e otimização do consumo de energia, alertas automáticos e geração de relatórios de sustentabilidade.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security + JWT**
- **Spring Data JPA**
- **Oracle Database 21c**
- **Flyway** (Migrações de banco de dados)
- **Docker & Docker Compose**
- **Swagger/OpenAPI 3.0**
- **Maven**

## 📦 Estrutura do Projeto

```
energia-sustentavel/
├── src/
│   ├── main/
│   │   ├── java/com/esg/energiasustentavel/
│   │   │   ├── config/              # Configurações (Security, etc)
│   │   │   ├── controller/          # Controllers REST
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── exception/           # Tratamento de exceções
│   │   │   ├── model/               # Entidades JPA
│   │   │   ├── repository/          # Repositórios JPA
│   │   │   ├── security/            # Segurança e JWT
│   │   │   └── EnergiaSustentavelApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/        # Scripts Flyway
│   └── test/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

## 🔧 Pré-requisitos

- **Docker** e **Docker Compose** instalados
- **Java 17** (para desenvolvimento local)
- **Maven 3.6+** (para desenvolvimento local)
- **IntelliJ IDEA** ou outra IDE Java

## 🐳 Executando com Docker

### 1. Clone o repositório
```bash
git clone <seu-repositorio>
cd energia-sustentavel
```

### 2. Execute com Docker Compose
```bash
docker-compose up -d
```

Este comando irá:
- Iniciar o banco de dados Oracle XE 21c
- Executar as migrações Flyway automaticamente
- Iniciar a aplicação Spring Boot na porta 8080

### 3. Verificar status dos containers
```bash
docker-compose ps
```

### 4. Logs da aplicação
```bash
docker-compose logs -f app
```

## 💻 Executando Localmente (sem Docker)

### 1. Configure o Oracle Database

Você precisa ter um Oracle Database rodando localmente. Pode usar Docker:

```bash
docker run -d \
  --name oracle-local \
  -p 1521:1521 \
  -e ORACLE_PASSWORD=oracle \
  gvenzl/oracle-xe:21-slim
```

### 2. Atualize o application.properties

```properties
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:XE
spring.datasource.username=system
spring.datasource.password=oracle
```

### 3. Compile e execute
```bash
mvn clean install
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 📚 Documentação da API

### Swagger UI

Após iniciar a aplicação, acesse:
```
http://localhost:8080/swagger-ui.html
```

### OpenAPI JSON

```
http://localhost:8080/api-docs
```

## 🔐 Autenticação

O sistema utiliza **JWT (JSON Web Token)** para autenticação.

### 1. Realizar Login

**Endpoint:** `POST /api/auth/login`

**Body:**
```json
{
  "username": "admin",
  "password": "admin123"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "username": "admin",
  "role": "ADMIN"
}
```

### 2. Usar o Token

Inclua o token no header de todas as requisições protegidas:

```
Authorization: Bearer {seu-token-aqui}
```

### Usuários Padrão

| Username | Password  | Role  |
|----------|-----------|-------|
| admin    | admin123  | ADMIN |
| usuario  | user123   | USER  |

## 📡 Endpoints Principais

### Autenticação
- `POST /api/auth/login` - Fazer login
- `POST /api/auth/register` - Registrar novo usuário

### Empresas
- `GET /api/empresas` - Listar todas as empresas
- `GET /api/empresas/{id}` - Buscar empresa por ID
- `POST /api/empresas` - Cadastrar nova empresa
- `PUT /api/empresas/{id}` - Atualizar empresa
- `DELETE /api/empresas/{id}` - Excluir empresa

### Equipamentos
- `GET /api/equipamentos` - Listar todos os equipamentos
- `GET /api/equipamentos/{id}` - Buscar equipamento por ID
- `GET /api/equipamentos/empresa/{empresaId}` - Equipamentos por empresa
- `POST /api/equipamentos` - Cadastrar equipamento
- `PUT /api/equipamentos/{id}` - Atualizar equipamento
- `PATCH /api/equipamentos/{id}/desligar` - Desligar equipamento ocioso
- `DELETE /api/equipamentos/{id}` - Excluir equipamento

### Consumo de Energia
- `GET /api/consumo-energia` - Listar todos os consumos
- `GET /api/consumo-energia/{id}` - Buscar consumo por ID
- `GET /api/consumo-energia/empresa/{empresaId}` - Histórico por empresa
- `GET /api/consumo-energia/empresa/{empresaId}/periodo` - Consumo por período
- `GET /api/consumo-energia/empresa/{empresaId}/total` - Consumo total
- `POST /api/consumo-energia` - Registrar novo consumo
- `PUT /api/consumo-energia/{id}` - Atualizar registro
- `DELETE /api/consumo-energia/{id}` - Excluir registro

### Alertas
- `GET /api/alertas` - Listar todos os alertas
- `GET /api/alertas/{id}` - Buscar alerta por ID
- `GET /api/alertas/empresa/{empresaId}` - Alertas por empresa
- `GET /api/alertas/status/{status}` - Filtrar por status
- `POST /api/alertas` - Criar novo alerta
- `PATCH /api/alertas/{id}/resolver` - Resolver alerta
- `DELETE /api/alertas/{id}` - Excluir alerta

### Metas de Eficiência
- `GET /api/metas-eficiencia` - Listar todas as metas
- `GET /api/metas-eficiencia/{id}` - Buscar meta por ID
- `GET /api/metas-eficiencia/empresa/{empresaId}` - Metas por empresa
- `POST /api/metas-eficiencia` - Criar nova meta
- `PATCH /api/metas-eficiencia/{id}/progresso` - Atualizar progresso
- `DELETE /api/metas-eficiencia/{id}` - Excluir meta

### Relatórios de Sustentabilidade
- `GET /api/relatorios-sustentabilidade` - Listar todos os relatórios
- `GET /api/relatorios-sustentabilidade/{id}` - Buscar relatório por ID
- `GET /api/relatorios-sustentabilidade/empresa/{empresaId}` - Relatórios por empresa
- `POST /api/relatorios-sustentabilidade` - Gerar novo relatório
- `DELETE /api/relatorios-sustentabilidade/{id}` - Excluir relatório

## 🗄️ Banco de Dados

### Migrações Flyway

As migrações são executadas automaticamente ao iniciar a aplicação:

- **V1__Create_Initial_Tables.sql** - Cria todas as tabelas do sistema
- **V2__Insert_Initial_Data.sql** - Insere dados iniciais (usuários, empresas, equipamentos, etc)

### Estrutura de Tabelas

- `usuarios` - Usuários do sistema
- `empresas` - Empresas cadastradas
- `equipamentos` - Equipamentos/sensores IoT
- `consumo_energia` - Registros de consumo
- `alertas` - Alertas de consumo excessivo
- `metas_eficiencia` - Metas de eficiência energética
- `relatorios_sustentabilidade` - Relatórios gerados

## 🧪 Testando a API

### Usando cURL

```bash
# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'

# Listar empresas (com autenticação)
curl -X GET http://localhost:8080/api/empresas \
  -H "Authorization: Bearer {seu-token}"
```

### Usando Insomnia/Postman

Importe a collection fornecida em `postman_collection.json`

## 🛑 Parando a Aplicação

```bash
# Parar containers
docker-compose down

# Parar e remover volumes (CUIDADO: apaga os dados do banco)
docker-compose down -v
```

## 📝 Funcionalidades Principais

1. **Monitoramento de Consumo**
   - Registro de consumo em tempo real
   - Histórico por empresa/equipamento
   - Cálculo de custos

2. **Alertas Automáticos**
   - Notificações quando o consumo ultrapassa limites
   - Diferentes níveis de severidade
   - Resolução de alertas

3. **Gestão de Equipamentos**
   - Cadastro de equipamentos e sensores IoT
   - Desligamento automático de equipamentos ociosos
   - Monitoramento por localização

4. **Metas de Eficiência**
   - Definição de metas de redução de consumo
   - Acompanhamento de progresso
   - Relatórios de atingimento

5. **Relatórios de Sustentabilidade**
   - Geração automática de relatórios
   - Cálculo de redução de CO2
   - Indicadores de eficiência

## 🔒 Segurança

- Autenticação via JWT
- Senhas criptografadas com BCrypt
- Endpoints protegidos por role (USER/ADMIN)
- Validação de dados com Bean Validation
- Tratamento global de exceções

## 👥 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT.

## 📧 Contato

Equipe ESG - contato@energiasustentavel.com

## 🙏 Agradecimentos

- Spring Framework Team
- Oracle Database
- Comunidade Open Source
