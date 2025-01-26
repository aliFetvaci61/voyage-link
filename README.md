## voyage-link
  This project implements a system for the aviation industry, designed to calculate possible routes between locations. The system includes a backend REST API built with Spring Boot, utilizing Java 17, PostgreSQL for data storage, and Redis for caching. Additionally, Depth First Search (DFS) algorithm is used to calculate routes efficiently by storing node and edge information in a graph structure.

# Features
- Locations Management: Create, Read, Update, Delete (CRUD) locations for airports, cities, and other locations.
- Transportation Management: CRUD operations for transportation entities (e.g., FLIGHT, BUS, SUBWAY, UBER).
- Route Calculation: Find valid routes between two locations considering transportation availability and transfer requirements.
- Caching: Redis caching to optimize locations and transportations read performans.
- Graph Representation: Uses Depth First Search (DFS) for efficient route traversal.
- Swagger UI: API documentation available via Swagger for easy testing and exploration.

# Technologies Used
- Spring Boot - Java REST API framework
- Java 17 - The programming language version
- PostgreSQL - Relational database for storing locations, transportations, and routes
- Redis - Caching mechanism to improve performance
- Docker - Dockerized environment for development and deployment
- Swagger UI - API documentation interface
- Depth First Search (DFS) - Algorithm to efficiently calculate routes in the graph
- Hibernate - ORM for database interaction

# Requirements
- Java 17
- PostgreSQL Database
- Redis Server
- Docker (for running the application in containers)

# Software Architecture Design for voyage-link
![image](https://github.com/user-attachments/assets/3d91f3ec-4132-43d0-b5d5-881afe3dfb40)

## How to Run the Application
- git clone [https://github.com/yourusername/aviation-route-management.git](https://github.com/aliFetvaci61/voyage-link.git)
- cd voyage-link
- docker-compose up --build

Frontend (React)
- cd travel-management-web-app
- npm install
- npm start
  
The React app will run on http://localhost:3000. It includes the following pages:
User: Register, Login
Locations: List, create, update, and delete locations.
Transportations: Manage transportations (FLIGHT, BUS, etc.).
Routes: Find valid routes between two locations using dropdown menus.

Locations: Locations are represented by their name, country, city, and IATA code (for airports).
Transportations: A transportation entity connects two locations. It can represent a bus ride, subway, Uber, or a flight.
Routes: A valid route consists of connected transportations. The algorithm ensures that there is exactly one flight and at most one transportation before or after the flight.
Depth First Search (DFS) for Route Calculation
Every 10 seconds, the system updates its graph representation in memory, ensuring fast and efficient route calculations.
The DFS algorithm is employed to find all possible routes between origin and destination, adhering to the rules for valid routes (e.g., no more than three transportations, one flight, etc.).

Conclusion
This system aims to provide an efficient and user-friendly way to calculate routes between locations in the aviation industry. With the use of in memory access, Depth First Search (DFS) algorithm for graph traversal, and a responsive frontend built in React, this solution ensures a seamless experience for users booking flights.

