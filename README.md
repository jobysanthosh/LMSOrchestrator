# LMSOrchestrator

A simple Orchestrator microservice to be the gateway for other microservices used in the application like Librarian, Borrower and Admin. This is also equipped with Spring Security for login credentials and for designating who gets access to which part of microservice.
For example, a Librarian wouldn't get access to Admin functionalities and a Borrower wouldn't get access to neither Admin or Librarian.
