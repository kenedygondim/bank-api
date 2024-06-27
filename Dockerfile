#####################Etapa de Build#####################
#Usando a imagem pública do Maven (Diretamente do docker hub) para compilar o projeto
FROM maven:3.9.6-amazoncorretto-17-debian as build
#Copiando toda a pasta src do projeto para o container
COPY src /app/src
#Copiando o arquivo pom.xml para o container
COPY pom.xml /app
#Trocando o diretório de trabalho para /app (Semelhante ao comando cd)
WORKDIR /app
#Rodando o comando de build da aplicação
RUN mvn clean install
#Build realizado dentro do container

#####################Etapa de Configuração do projeto#####################
FROM amazoncorretto:17

#Adicionando as variáveis de ambiente para conexão com o banco de dados
ENV DB_URL=jdbc:mysql://db-bank-api.c1aqoywgymi0.us-east-1.rds.amazonaws.com/bankapidb
ENV DB_USER=kgondim
ENV DB_PASS=6GPMh3cyPfvZffunkwID

COPY --from=build /app/target/bank-0.0.1-SNAPSHOT.jar /app/api.jar

WORKDIR /app

EXPOSE 8080

CMD ["java", "-jar", "api.jar"]
