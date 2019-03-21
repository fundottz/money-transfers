How to run: 
---
- ./gradlew build run - to start app on default 8080 port
- ./gradlew test - to run tests 

Requirements:
---
1. Java 11 used for implementation
2. To keep it simple avoided data-transfer layer and mappings from entity
3. To meet this requirement Netty was used as non-blocking http server
4. Frameworks and libraries:	
	- Micronaut for web and dependency injection
	- Logback for logs
	- Junit 5 for tests
	- Gradle 5 as a build tool
5. No database was used due to #2 requirement, persistence implemented with ConcurrencyHashMap
6. Gradle builds standalone executable jar, which we can run as usual
	- java -jar build/libs/money-transfers.jar
7. Tests included

Endpoints: 
---
- POST /api/accounts - create new account
- GET /api/accounts - get all accounts
- GET /api/accounts/{id} - get account by id
- GET /api/accounts/{id}/balance - get account balance

- POST /api/transfers - create new transfer
- GET /api/transfers - get all transfers
- GET /api/transfers/{id} - get transfer by id
- GET /api/transfers/?from={from}&to={to} - find transfers for particular sender and receiver or only sender and only receiver