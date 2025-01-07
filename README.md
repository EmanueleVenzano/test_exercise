# Coding Exercises Repository

## Overview
This repository contains solutions to three coding exercises designed to evaluate problem-solving skills, coding style, and familiarity with Java. All exercises were implemented using **Java 17**, leveraging modern features and best practices.

## Exercises

### 1. Duplicate Detection
The goal was to write a function that detects all duplicate elements in a list. Given a list of objects, the function returns a new list containing the elements that are duplicated, preserving the order of their first appearance.

- **Example Input**: `["b", "a", "c", "c", "e", "a", "c", "d", "c", "d"]`
- **Expected Output**: `["a", "c", "d"]`

The solution avoids third-party libraries that directly solve the problem, focusing on core Java utilities for implementation.

---

### 2. Spreadsheet Engine
This exercise involved building a basic spreadsheet engine in Java. The task was to implement business logic to pass a predefined suite of unit tests. The engine supports the following functionalities:
- Accessing and modifying cell values.
- Exporting the spreadsheet as a structured representation.
- Handling different data types within the spreadsheet.

**Test-Driven Development (TDD)** principles were followed during implementation, with incremental development and refactoring.

---

### 3. Package Dependency Resolver
The task was to implement a small Java library capable of:
1. **Reading JSON** files containing package dependencies.
   - Example JSON:
     ```json
     {
       "pkg1": ["pkg2", "pkg3"],
       "pkg2": ["pkg3"],
       "pkg3": []
     }
     ```

2. Constructing a **full dependency graph** from the input.

3. Exposing a method to return the dependency graph as an object.

4. Providing a "pretty-printed" representation of the resolved dependency graph.

**Example Pretty Graph**:
  ```markdown
  - pkg1
    - pkg2
      - pkg3
    - pkg3
  - pkg2
    - pkg3
  - pkg3
  ```

## Technology Stack
- **Java 17**: For modern language features and improved performance.
- **JUnit 5**: Used for unit testing to ensure robustness.
- **Mockito**: Used to mock dependencies during testing.
- **Jackson**: For JSON parsing in the dependency resolver library.

## Repository Structure
```bash
.
├── src/
│   ├── main/
│   │   ├── java/         # Java source code
│   │   └── resources/    # Resources such as JSON files used in examples
│   └── test/
│       ├── java/         # Test code
│       └── resources/    # Test-related resources
├── README.md             # Documentation
└── pom.xml               # Maven configuration for dependencies and build
```

## Usage
1. **Clone the Repository**
```bash
git clone https://github.com/your-username/your-repository.git
cd your-repository
```

2. **Build and Run**: Ensure you have Java 17 and Maven installed.

```bash
mvn clean install
mvn test
```

3. **Run Specific Modules**: Each module (exercise) is implemented as a separate component. Refer to the individual class documentation for usage.

## Tests
Comprehensive unit tests are provided for each exercise to validate functionality. The tests are located in the src/test/java directory.
- Run the tests:
```bash
mvn test
```

## Author
This repository was created as part of an exercise evaluation. If you have any questions, feel free to reach out!