# Social Memory Network

## Overview
Social Memory Network is a web application that allows users to share and cherish their memories. Built with Spring Boot, Spring Data Neo4j, and Amazon S3, this application provides a platform for users to connect, share, and relive their special moments through photos, stories, and more.


## Prerequisites
- Java JDK 17 or higher
- Maven
- Neo4j database
- AWS account

## Getting Started
Clone the repository:
```bash
git clone https://github.com/noureddine409/memories-backend
cd memories-backend
```

## Configuration
Update application properties in src/main/resources/application.properties:

```bash
# Spring Data Neo4j
spring.data.neo4j.uri=bolt://localhost:7687
spring.data.neo4j.username=neo4j
spring.data.neo4j.password=password
```

```bash
# Amazon S3
aws.accessKeyId=your-access-key-id
aws.secretKey=your-secret-key
aws.s3.bucket-name=your-s3-bucket-name
aws.s3.region=your-s3-region
```

Running the Application
Build and run the application using Maven:

```bash
mvn clean install
mvn spring-boot:run
```

The application will be accessible at http://localhost:8080.

