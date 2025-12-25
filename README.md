# Retirement Monte Carlo Simulator

A prototype web application that estimates potential retirement outcomes using Monte Carlo simulation.  
Users provide basic financial inputs and a risk profile, and the system returns summary statistics along with a visualization of sample simulated wealth paths.

---

## Features

- Monte Carlo simulation of long-term retirement wealth
- Summary statistics:
  - Median wealth
  - 10th / 90th percentile wealth
  - Probability of reaching a target retirement amount
- Visualization of sample simulation paths (fixed number of representative paths)
- Clear separation between API boundary, business logic, and presentation
- API boundary validation with automated tests
- Lightweight frontend served directly by Spring Boot

---

## Architecture Overview

This project is implemented as a single Spring Boot application that serves both frontend and backend components.

### Components

1. **Frontend (Static Assets)**
  - Served from Spring Boot’s static resources directory
  - Collects user inputs and visualizes simulation results
  - Communicates with the backend via HTTP (JSON)

2. **Backend (REST API)**
  - Exposes a REST endpoint for running simulations
  - Validates input, executes Monte Carlo logic, and returns results as JSON

### Request Flow

1. Browser requests `/`  
   → Spring Boot serves `index.html`
2. User submits simulation inputs  
   → Frontend sends `POST /api/simulate`
3. Backend:
  - Deserializes JSON into a request object
  - Validates request at the API boundary
  - Executes simulation logic
  - Serializes results into JSON
4. Frontend renders summary statistics and sample path chart

---

## Technology Stack

- **Language**: Java 17
- **Backend Framework**: Spring Boot (embedded Tomcat)
- **Web Layer**: Spring MVC
- **JSON Processing**: Jackson
- **Validation**: Jakarta Bean Validation
- **Testing**: JUnit 5, MockMvc
- **Frontend**: HTML, CSS, JavaScript
- **Charting**: Chart.js (via CDN)

---

## Getting Started

### Prerequisites

- Java 17 or newer
- Maven Wrapper

### Run the application
```bash
./mvnw spring-boot:run
```
- open http://localhost:8080/

