# Hoster ![Tests status](.github/badges/test.svg)![Build status](.github/badges/build.svg)![Coverage](.github/badges/jacoco.svg)
Manual access provider. Claims for access, accept or decline it manually and notifies him automatically.

A pet project by JAVO.

# Tech Stack
<img src="https://github.com/devicons/devicon/blob/master/icons/java/java-original-wordmark.svg" title="Java" alt="Java" width="40" height="40"/>&nbsp;
<img src="https://github.com/devicons/devicon/blob/master/icons/spring/spring-original-wordmark.svg" title="Spring" alt="Spring" width="40" height="40"/>&nbsp;
<img src="https://github.com/devicons/devicon/blob/master/icons/docker/docker-original.svg" title="Docker" alt="Docker" width="40" height="40"/>&nbsp;
<img src="https://github.com/devicons/devicon/blob/master/icons/kubernetes/kubernetes-plain-wordmark.svg" title="Kubernetes" alt="Kubernetes" width="40" height="40"/>&nbsp;
<img src="https://github.com/devicons/devicon/blob/master/icons/azure/azure-original.svg" title="Azure" alt="Azure" width="40" height="40"/>&nbsp;

- Java 11
- Spring Boot 2.7
- Spring Reactive Web (Project Reactor)
- Spring JPA
- PostgresSQL
- Maven Multi-module (Clean Architecture approach)

### Test Locally

First, we need a PostgresSQL instance.

```shell
docker run -d --name hoster-db-postgres -e POSTGRES_USER=javo -e POSTGRES_PASSWORD=javo -e POSTGRES_DB=hoster -p 5432:5432 postgres:14-alpine
```

Compile and package the project:

```shell
mvn -pl runner -am clean install
```

Run the application:

```shell
java -jar .\runner\target\runner-0.0.1-SNAPSHOT.jar
```

---
# What's next

- Learn ProjectReactor - SpringWebFlux
- Learn Testcontainers
- Learn Clean Architecture