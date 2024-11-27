# Sistema de Cadastro e Gerenciamento de Localidades Bootcamp New Thinkers

Este é um projeto de API RESTful para cadastro e gerenciamento de informações relacionadas a unidades federativas (UFs),
municípios, bairros e pessoas, com funcionalidades completas para manipulação e consulta dessas entidades. A aplicação
foi desenvolvida utilizando **Spring Boot**, com integração para banco de dados Oracle.

---

## Índice

- [Recursos Principais](#recursos-principais)
- [Instalação e Configuração](#instalação-e-configuração)
- [Endpoints](#endpoints)
    - [UFs](#ufs)
    - [Municípios](#municípios)
    - [Bairros](#bairros)
    - [Pessoas](#pessoas)
- [Como Contribuir](#como-contribuir)
- [Licença](#licença)

---

## Recursos Principais

- **Cadastro e edição** de UFs, municípios, bairros e pessoas.
- **Listagem** de entidades por parâmetros.
- Sistema robusto para vinculação de pessoas a múltiplos endereços.
- API estruturada com suporte para métodos **GET**, **POST** e **PUT**.
- Integração com banco de dados Oracle 21c.

---

## Instalação e Configuração

### Pré-requisitos

- **Java 17+**
- **Maven 3.8+**
- **Banco de Dados Oracle 21c**
- Ferramenta para requisições REST, como **Postman** ou **Insomnia**.

### Passos

1. Clone o repositório:
   ```bash
   git clone https://github.com/jailtonjuniordev/Bootcamp-New-Thinkers-Backend-Desafio-Final.git
   cd projeto
   ```

2. Configure as variáveis de ambiente no arquivo `.env` para conexão com o banco Oracle.

3. Ajuste as configurações do JPA em relação ao banco de dados como preferir no arquivo `application.yml` 

4. Compile e execute o projeto:
   ```bash
   mvn spring-boot:run
   ```

5. Utilize o Postman para interagir com os endpoints disponíveis.

---

## Endpoints

### UFs

#### Cadastro de UF

**POST** `/uf`  
**Body**:

```json
{
  "sigla": "PE",
  "nome": "PERNAMBUCO",
  "status": 1
}
```

#### Edição de UF

**PUT** `/uf`  
**Body**:

```json
{
  "codigoUF": 2,
  "status": 2
}
```

#### Listagem de UFs por Parâmetros

**GET** `/uf`  
**Exemplo de URL**: `/uf?codigoUF=1`

---

### Municípios

#### Cadastro de Município

**POST** `/municipio`  
**Body**:

```json
{
  "codigoUF": 1,
  "nome": "OLINDA",
  "status": 1
}
```

#### Edição de Município

**PUT** `/municipio`  
**Body**:

```json
{
  "codigoMunicipio": 1,
  "nome": "SANTOS"
}
```

#### Listagem de Municípios por Parâmetros

**GET** `/municipio`  
**Exemplo de URL**: `/municipio?nome=ouro preto`

---

### Bairros

#### Cadastro de Bairro

**POST** `/bairro`  
**Body**:

```json
{
  "codigoMunicipio": 1,
  "nome": "JABOTÁ",
  "status": 1
}
```

#### Edição de Bairro

**PUT** `/bairro`  
**Body**:

```json
{
  "codigoBairro": 1,
  "status": 2
}
```

#### Listagem de Bairros por Parâmetros

**GET** `/bairro`  
**Exemplo de URL**: `/bairro`

---

### Pessoas

#### Cadastro de Pessoa

**POST** `/pessoa`  
**Body**:

```json
{
  "nome": "JOÃO ANDRADE",
  "sobrenome": "DOS REIS",
  "idade": 12,
  "login": "joao.reis.dois",
  "senha": "senha",
  "status": 1,
  "enderecos": [
    {
      "codigoBairro": 1,
      "nomeRua": "RUA A",
      "numero": "123",
      "complemento": "MINHA CASA UM",
      "cep": "11111-678"
    }
  ]
}
```

#### Edição de Pessoa

**PUT** `/pessoa`  
**Body**:

```json
{
  "codigoPessoa": 1,
  "sobrenome": "DOS REIS",
  "idade": 12,
  "status": 2,
  "enderecos": [
    {
      "codigoEndereco": 2,
      "nomeRua": "RUA ALTERADA B"
    }
  ]
}
```

#### Listagem de Pessoas por Parâmetros

**GET** `/pessoa`  
**Exemplo de URL**: `/pessoa?codigoPessoa=1`

---

## Como Contribuir

1. Faça um fork do repositório.
2. Crie um branch para sua funcionalidade (`git checkout -b minha-nova-funcionalidade`).
3. Envie suas alterações para revisão (`git push origin minha-nova-funcionalidade`).

---

## Licença

Este projeto está licenciado sob a licença MIT. Consulte o arquivo [LICENSE](LICENSE) para obter mais detalhes.
