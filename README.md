# quake-3-log - Victor Alcântara 

## Introdução

Este projeto é a construção de uma API para realizar um parser de um arquivo log gerado pelo quake.

Detalhes:
  - Java 11
  - Spring Boot v. 2.6.2
  - Banco de dados em memória H2
  - Maven


## API

A api consiste em duas rotas:
 
 - POST /file -> recebe o arquivo log para realizar a análise
 - GET /games -> retorna o resumo dos jogos em um arquivo json


## Exemplo

Request para enviar o arquivo
![image](https://user-images.githubusercontent.com/27792114/149003033-1216a0ea-b6de-4575-a680-78a0de5fe505.png)


Request para buscar os reumos dos jogos
![image](https://user-images.githubusercontent.com/27792114/149003331-37e249fc-0726-43ac-aed1-4dc9f0a8d226.png)




