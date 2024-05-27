# Bank API

## Descrição

Trata-se de uma API Rest que simula o funcionamento básico de um banco digital, sendo possível criar conta, fazer login, cadastrar chaves pix, realizar transferências entre contas, tirar extrato bancário, entre outras funcionalidades. 
Essa aplicação foi desenvolvida com Java 17, Spring Boot e Spring Security.Como banco de dados foi utilizado o MySQL. Além disso, utilizei serviços externos como o SMTP do Gmail para envio de e-mails e o serviço de CEP da ViaCEP para busca de endereços.
O principal motivo da criação desse serviço foi a necessidade de aprimorar meus conhecimentos em Java, além de ser um projeto que me desafiou a desenvolver uma aplicação completa, desde a modelagem do banco de dados até a documentação da API.

## Instrução de instalação

### Pré-requisito
- Java 17
- MySQL
- Chave do gmail para aplicações externas
- Arquivo application.properties com as seguintes propriedades:
```
api.security.token.secret=${JWT_SECRET:sua-chave-secreta}

spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/Bank
spring.datasource.username=__altere-para-seu-usuario-do-mysql__
spring.datasource.password=sua-senha-do-mysql
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql: true

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=seu-gmail
spring.mail.password=sua-chave-de-aplicativo-do-gmail
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```
### Etapas:

1. Clone o repositório por meio do terminal com o comando abaixo :
```ddd
git clone https://github.com/kenedygondim/bank-api.git
```
2. Abra o projeto em sua IDE de preferência
3. Entre no arquivo pom.xml e recarregue as dependências do Maven
4. Crie o arquivo application.properties no caminho 
``` 
bank\target\classes
```
5. Adicione as propriedades citadas nos pré-requisitos com as informações do seu banco de dados e do seu gmail.
7. Execute a aplicação
---
## Instruções de uso

