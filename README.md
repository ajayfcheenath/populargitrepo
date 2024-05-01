# GitHub Popularity Service

The GitHub Popularity Service is a Java-based RESTful API designed to check the popularity of GitHub repositories based on predefined criteria. This service interacts with the GitHub API to retrieve repository information and calculates a score based on the number of stars and forks. If the calculated score meets a certain threshold, the repository is considered popular.

## Service Description

The GitHub Popularity Service consists of several components:

1. PopularityController: REST controller for handling requests related to GitHub repository popularity.
2. GitHubPopularityCheckerService: Service class responsible for checking the popularity of GitHub repositories.
3. GitHubService: Service class for interacting with the GitHub API to fetch repository information.
4. GlobalExceptionHandler: Global exception handler for handling exceptions thrown by controllers.

The service is built using Spring Boot, which provides a lightweight framework for developing Java-based applications. It utilizes Spring's inversion of control (IoC) and dependency injection features to manage components and simplify configuration.

## Assumptions

- The service assumes that the GitHub API access token is provided and correctly configured in the application.properties file.
- The predefined criteria for repository popularity are based on a weighted sum of stars and forks, with a threshold score of 500.

## Tech Stack Used

- Java 17
- Spring Boot 3.2.3
- Maven for dependency management
- Docker for containerization
- Testing
  - JUnit 5
  - Mockito

## How to Build the Service

To build the GitHub Popularity Service, follow these steps:

1. Clone the repository to your local machine.
2. Navigate to the project directory.
3. Run the following Maven command to build the project:


## How to Run Automatic Tests

To run automated tests for the GitHub Popularity Service, execute the following Maven command:

> mvn test


This command will run all the unit tests defined in the project.

##  How to build the Service
> mvn clean install

## Run the Service Locally
>mvn spring-boot:run



## How to Run the Service with Docker

To run the GitHub Popularity with Docker, follow these steps:

* Ensure that Docker is installed on your machine. 
* Build the Docker image using the provided Dockerfile:

Build Docker image:

>docker build -t github-popularity-service .

Run the Docker container:
>docker run -p 8080:8080 github-popularity-service


The service will be accessible at 
>http://localhost:8080/popularity?owner=<owner_name>&repositoryName=<repo_name>

## What Improvements Would I make if You Had More Time

If more time were available, the following improvements could be made to enhance the GitHub Popularity Service:

1. Enhanced error handling: Implement more robust error handling and logging mechanisms to provide better feedback to users and developers.
2. Caching: Implement caching mechanisms to improve performance by reducing the number of requests made to the GitHub API.
3. Rate limiting: Implement rate limiting to prevent abuse and ensure fair usage of the service.
4. Authentication and authorization: Add support for user authentication and authorization to restrict access to certain endpoints or features.
5. Monitoring and metrics: Implement monitoring and metrics collection to track service performance and usage statistics.
6. API documentation: Generate comprehensive API documentation using tools like Swagger to facilitate integration and usage by external clients.



