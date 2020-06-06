About
---
This repository is experimental and created for fun to answer typical test-assignment interview - design and implement money transfer system.  

Next steps
--- 
- [ ] Add ci with github actions
- [ ] Migrate to kotlin
- [ ] Add persistence implementation 

Requirements
---
1. Java 11 used for implementation
2. To keep it simple avoided data-transfer layer and mappings from entity
3. Netty was used as non-blocking http server
4. Frameworks and libraries:	
	- Micronaut for web and dependency injection
	- Logback for logs
	- Junit 5 for tests
	- Gradle 5 as a build tool
5. No database was used due to simplifications, persistence implemented with ConcurrencyHashMap
6. Gradle builds standalone executable jar, which we can run as usual
	- java -jar build/libs/money-transfers-0.1.jar
7. Tests included

How to run
---
- ./gradlew build run - to start app on default 8080 port
- ./gradlew test - to run tests 

Endpoints
---
- POST /api/accounts - create new account
- GET /api/accounts - get all accounts
- GET /api/accounts/{id} - get account by id
- GET /api/accounts/{id}/balance - get account balance

- POST /api/transfers - create new transfer
- GET /api/transfers - get all transfers
- GET /api/transfers/{id} - get transfer by id
- GET /api/transfers/?from={from}&to={to} - find transfers for particular sender and receiver or only sender and only receiver

Load tests
--- 
Requests 100k, concurrent users 20, timeout 2s, keepalive true  
- Good old single-threaded Apache Bench 
```bash
ab -n 100000 -c 20 -k -r -s 2 http://localhost:8080/api/accounts
```
- Fashionable multi-threaded K6 https://k6.io/docs/ 
```bash 
k6 run loadtest.js --iterations 100000 --vus 20 --no-connection-reuse=false (keepalive = true)
```