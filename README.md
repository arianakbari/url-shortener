# A simple URL shortener with clean architecture in Kotlin + Spring Boot

![Class Digram](/draw.png)

The entire domain layer is organized into the `domain` package. It has no dependency on Spring or any other library.

The folder structure of the project follows *Screaming or Hexagonal architecture*.

Swagger API docs for this project has been configured and is accessible at `/api-docs/swagger-ui`.

I also configured a sample workflow for Github actions in order to run tests and act as a CI.

In order to run the project, run `docker-compose up --build`

It can be argued that a NoSQL implementation like MongoDB might bring slight performance gains and higher availability,
especially because, for this particular solution, there are no relations and no need for ACID compliance.
On the other hand, one trade-off is that if we want to achieve horizontal scaling, for example through partitioning, it
could increase overall complexity.

Bottlenecks:

- Database:
    - Solution:
        - Using cache first approach for resolving the full url.
        - Horizontal scaling:
            - Using replication (Master-Slave model).
            - Using partitioning