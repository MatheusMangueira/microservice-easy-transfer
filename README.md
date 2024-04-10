<h1 align="center">💸 Microservice Easy Transfer </h1>

</br>

<div align="center">
  <img src="https://skillicons.dev/icons?i=git,docker,ts,js,nodejs,postgres,rabbitmq,spring,java,idea,vscode" />
</div>

</br>
</br>

Recursos disponíveis para acesso via API:
* [**Resumo Geral do Projeto**](#resumo)
* [**Regras de Negócio**](#regras)
* [**Tecnologias Utilizadas**](#Tecnologias)
* [**Métodos**](#Métodos)
* [**Respostas**](#Respostas)
* [**Listar**](#Listar)
* [**Usuários**](#Usuários)
* [**Transfêrencia**](#Transfêrencia)

## Resumo Geral do Projeto:
<p id="resumo"> </p>

<p> Este projeto consiste em um sistema de transferência de valores, desenvolvido com a arquitetura de microserviços, composto por três microserviços interconectados. Lembrando que a comunicação entre nossos microsserviços é efetuada via <a href="https://rabbitmq.com" target="_blank">RabbitMQ</a>
</p>

1) 🟢 Microserviço de Usuário:
   <p> Este microserviço foi criado com Spring Boot, Java na sua versão 21 e é responsável por gerenciar as informações dos usuários, incluindo nome, email e saldo disponível em sua conta. Ao ser instanciado, o microserviço realiza a criação da fila de transferência utilizando <a href="https://rabbitmq.com" target="_blank">RabbitMQ</a>. Essa fila será utilizada para enviar os dados necessários para o serviço de transferência. </p>

2) 🟢 Microserviço de Transferência:
   <p> O microserviço de transferência foi criado com Spring Boot, Java na sua versão 21. Esse serviço recebe e processa as solicitações de transferência dos usuários, calcula o valor da transação e assegura a integridade das operações. Após a conclusão, retorna informações relevantes sobre a transação para o usuário através da fila ```transferUserBack-row```, que é consumida pelo microserviço de usuários para atualização do banco de dados. Essa abordagem assíncrona garante eficiência e escalabilidade, mantendo a operação do sistema suave e confiável.  </p>

3) 🟢 Microserviço de Notificação:
   <p> Este microserviço foi realizado com NodeJs e é responsável por enviar notificações por e-mail aos destinatários de uma transferência de valores. Ele é acionado após a conclusão de uma transferência bem-sucedida e envia uma mensagem de notificação para o destinatário, informando sobre a transação. </p>


## Regras de Negócio:
<p id="regras"> </p>

1) O usuário poderá realizar uma transferência de valor para um destinatário.
2) A transferência ocorrerá subtraindo o valor da conta do usuário que solicitou a transferência e adicionando-o à conta do usuário destinatário. 
3) A notificação enviará um e-mail para o e-mail do usuário com o e-mail do remetente e do destinatário, assim como o valor da transferência.
4) O sistema está desenhado da seguinte forma:
  </br>
  
   ![c34c17b8-e3d0-4aed-b028-8bf0e9eae338](https://github.com/MatheusMangueira/microservice-transfer/assets/98111351/d9b48277-1cd9-413b-9a90-46eeb535fe7b)


## Tecnologias Utilizadas:
<p id="Tecnologias"> </p>

| microservice-users                     | microservice-transfer                 | microservice-notification  |  
| --------------------------------------| --------------------------------------| ----------------------------|
| ✅ Spring Boot                        | ✅ Spring Boot                       | ✅ NodeJs                  |
| ✅ Docker                             | ✅ Java 21                           | ✅ Typescript              | 
| ✅ RabbitMq                           | ✅ Docker                            | ✅ RabbitMq                |
| ✅ PostgreSQL                         | ✅ RabbitMq                          | ✅ Docker                  |
| ✅ JPA                                | ✅ PostgreSQL                        | ✅ Nodemailer              |
| ✅ Validation                         | ✅ JPA                               | -                          |
| ✅ Lombok                             | ✅ Lombok                            | -                          |
| ✅ Java 21                            | -                                     | -                          |


## Como rodar ?

1) Para testar a API, Faça um clone do repósitorio https://github.com/MatheusMangueira/microservice-easy-transfer.git
2) Configure as variaveis de ambiente de acordo com o arquivo ```.env.example``` de cada microserviço.
3) Configure o docker conforme o especificado no [**Docker**](#Docker).
4) Acesse http://localhost:8080 para o ```microserviço de usres```
5) Acesse http://localhost:9090 para o ```microserviço de transfer```
6) Acesse http://localhost:3000 para o ```microserviço de notification```
7) Aceese http://localhost:15672 para o ```Login do RabbitMq``` conforme suas credenciais

## Docker
1) Na raiz do projeto vá até o diretório ```devops/docker/docker-compose.yml``` e atente-se as variáveis de ambiente (ENV).
2) Inicialize os serviços com o comando ```docker-compose up``` assim você irá executar a aplicação.
 * OBS: Irá inicializar o serviços do  ```rabbitmq | ports: - "5432:5432"```  ```postgres-user | ports: - "5432:5432"``` ``` postgres-transfer | ports: - "5433:5432" ```
   
```bash
< PROJECT ROOT >
   |-- devops/                       # devops
   |   |-- docker/
   |   |-- docker-compose.yml        # Docker
   |   |-- .env.example              # arquivo ENV de exemplo
   |    
   |-- librabbitmq                   # lib BTOs
   |-- microservice-notification     # microserviço de notificação 
   |-- microservice-transfer         # microserviço de transferencia
   |-- microservice-users            # microserviço de usuario
   |
   |-- *************************************************      
```   

## Métodos

Requisições para a API devem seguir os padrões:
| Método | Descrição |
|---|---|
| `GET` | Retorna informações de um ou mais registros. |
| `POST` | Utilizado para criar um novo registro. |
| `PUT` | Atualiza dados de um registro ou altera sua situação. |
| `DELETE` | Remove um registro do sistema. |

## Respostas

| Código | Descrição |
|---|---|
| `200` | Requisição executada com sucesso (success).|
| `400` | Erros de validação ou os campos informados não existem no sistema.|
| `401` | Dados de acesso inválidos.|
| `404` | Registro pesquisado não encontrado (Not found).|
| `405` | Método não implementado.|
| `410` | Registro pesquisado foi apagado do sistema e não esta mais disponível.|
| `422` | Dados informados estão fora do escopo definido para o campo.|
| `429` | Número máximo de requisições atingido. (*aguarde alguns segundos e tente novamente*)|
| `500` | Internal Sesrver Error.|

## Listar
As ações de `listar todos (GET)` permitem o envio dos seguintes parâmetros:

| Parâmetro | Descrição |
|---|---|
| `limit` | Filtra dados pelo valor informado. |
| `page` | Informa qual página deve ser retornada. |

# Usuários

### Listar (List) [GET /users/all]

+ Request (application/json)

  + Parameters
      - page: 1
      - limit: 10
   
+ Response 200 (application/json)

      [{
        "id": "295083bf-9484-498f-95b4-5fa658c9f052",
        "name": "teste01",
        "email": "teste01@example.com",
        "balance": 1000.00
      }]
  
+ Response 500 (application/json)

      {
        message: 'Internal Server Error'
      }


### Detalhar (List) [GET /users/{id}]

+ Request (application/json)

+ Response 200 (application/json)

      {
        "id": "295083bf-9484-498f-95b4-5fa658c9f052",
        "name": "teste01",
        "email": "teste01@example.com",
        "balance": 1000.00
      }
  
+ Response 500 (application/json)

      {
        message: 'Internal Server Error'
      }

### Atualizar (update) [PUT /users/{id}]

+ Request (application/json)

  + Body
  
        {
          "balance": 5000.00
        }

+ Response 200 (application/json)

      {
        "id": "295083bf-9484-498f-95b4-5fa658c9f052",
        "name": "teste01",
        "email": "teste01@example.com",
        "balance": 5000.00
      }

+ Response 500 (application/json)

      {
        message: 'Internal Server Error'
      }

### Criar (create) [POST /users]

+ Request (application/json)

  + Body
  
        {
          "name": "teste01",
          "email": "teste01@example.com",
          "balance": 5000.00
        }
  
+ Response 201 (application/json)

      {
        "id": "295083bf-9484-498f-95b4-5fa658c9f052",
        "name": "teste01",
        "email": "teste01@example.com",
        "balance": 5000.00
      }

+ Response 500 (application/json)

      {
        message: 'Internal Server Error'
      }

### Deletar (delete) [DELETE /users/{id}]

+ Request (application/json)

+ Response 204 (application/json)
   + No body returned for response

+ Response 500 (application/json)

      {
        message: 'Internal Server Error'
      }


### Enviar Transferência (update) [PUT /users/transfer]

+ Request (application/json)

+ Response 200 (application/json)
  
  + Body
  
        {
          "senderID": {
            "id": "1f9c18d7-5308-4951-bb84-000d2806a2d5",
            "name": "teste01",
            "email": "teste01@hotmail.com",
            "balance": 5100.00
          },
          "recipientID": {
            "id": "122020e5-7641-468e-9afd-fdb4144ee3a8",
            "name": "teste02",
            "email": "teste02@hotmail.com",
            "balance": 1000.00
          },
          "value": 1000.00
        }
    
+ Response 500 (application/json)

      {
        message: 'Internal Server Error'
      }


# Transfêrencia

### Listar (List) [GET /transfers/all]

+ Request (application/json)
  
   + Parameters
      - page: 1
      - limit: 10

+ Response 200 (application/json)

      [
        {
          "id": "d18d5a69-c057-42a2-a441-6c06b3a2d9e5",
          "senderID": "d41c7c88-7697-44ea-9738-0d96e45c39e1",
          "recipientID": "9b33bfb3-1452-485e-9d66-9a22bd64ca67",
          "value": 500.00
        }
      ]

+ Response 500 (application/json)

        {
          message: 'Internal Server Error'
        }


### Detalhar (List) [GET /transfers/{id}]

+ Request (application/json)

+ Response 200 (application/json)

        {
          "id": "d18d5a69-c057-42a2-a441-6c06b3a2d9e5",
          "senderID": "d41c7c88-7697-44ea-9738-0d96e45c39e1",
          "recipientID": "9b33bfb3-1452-485e-9d66-9a22bd64ca67",
          "value": 500.00
        }
      
+ Response 500 (application/json)

        {
          message: 'Internal Server Error'
        }









  
