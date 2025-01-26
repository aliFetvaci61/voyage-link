## voyage-link
  This project implements a system for the aviation industry, designed to calculate possible routes between locations. The system includes a backend REST API built with Spring Boot, utilizing Java 17, PostgreSQL for data storage, and Redis for caching. Additionally, Depth First Search (DFS) algorithm is used to calculate routes efficiently by storing node and edge information in a graph structure.

# Features
- Locations Management: Create, Read, Update, Delete (CRUD) locations for airports, cities, and other locations.
- Transportation Management: CRUD operations for transportation entities (e.g., FLIGHT, BUS, SUBWAY, UBER).
- Route Calculation: Find valid routes between two locations considering transportation availability and transfer requirements.
  This system helps users find **valid routes** between two locations by calculating available transportation options based on specific rules, such as flights, transfers, and operating days.

1. **Route Finding**:
   - The system finds **all valid routes** between two locations (e.g., from one airport to another).
   - A valid route must include **at least one flight** and can have **up to 3 modes of transportation** (e.g., Uber → Flight → Bus).

2. **Valid Route Conditions**:
   - A route cannot contain **multiple flights**.
   - There can be **only one transfer before and one after the flight**.
   - Each mode of transportation must be **available on the selected date**.

3. **DFS Algorithm**:
   - The code uses the **DFS (Depth-First Search)** algorithm to explore all possible routes.
   - **Visited locations** are tracked to avoid revisiting the same places during the search.

4. **Final Output**:
   - Once a valid route is found (when the destination is reached with a valid flight), it is saved.
   - **All valid routes** are returned at the end.

5. **Business Value**:

   - **Improved User Experience**: Users can find the **best travel routes** that meet their needs.
   - **Dynamic and Efficient**: The system dynamically calculates available routes based on specific conditions and date availability, ensuring that only valid options are   considered.

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

## Conclusion
This system aims to provide an efficient and user-friendly way to calculate routes between locations in the aviation industry. With the use of in memory access, Depth First Search (DFS) algorithm for graph traversal, and a responsive frontend built in React, this solution ensures a seamless experience for users booking flights.

## Contributing

- Fork the project.
- Create a new branch: git checkout -b new-feature
- Make your changes and commit them: git commit -am 'Add new feature'
- Push to the branch: git push origin new-feature
- Create a new Pull Request.

