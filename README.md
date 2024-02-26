# tariff-tracker

## Overview
Tariff Tracker is a sophisticated microservice designed for the dynamic management and consultation of pricing in an 
e-commerce setting. It specializes in handling complex pricing scenarios that vary based on date and time ranges, 
ensuring that the most accurate and up-to-date price information is available for products. Developed using modern
software architecture practices, including Hexagonal Architecture, 
Tariff Tracker is built with Spring Boot for high performance and scalability.

## Architecture and Design
Design Principles: The service adheres to Hexagonal Architecture principles, ensuring that our business domain is 
properly encapsulated and modeled. By utilizing a Hexagonal Architecture (also known as Ports and Adapters), we create a
highly maintainable and loosely coupled system where business logic remains isolated from external concerns.

## Key Features
Dynamic Pricing: Ability to handle various pricing scenarios based on specific date and time ranges.

Accurate Price Information: Ensures real-time pricing accuracy for all products.

Scalable and Maintainable: Built with Spring Boot, allowing for easy scaling and maintenance.

## Getting Started
You can run this project in any IDE or a container, just need to be sure you are using JDK 21.

## Usage
Postman Collection: Located in the resources package, this collection contains several requests to interact with the controller,
providing a hands-on way to test and understand the service's functionality.

```
Endpoints: 
    - "/api/v1/prices"
Params:
    - applicationDate
    - productId
    - brandId
Example:
    - /api/v1/prices?applicationDate=2020-06-14-10.00.00&productId=35455&brandId=1
```

## Testing
Unit Testing: Comprehensive testing of PricingServiceImpl and PricingDataAdapter covering all scenarios.
Integration Testing: Detailed tests for PriceController, including scenarios provided by the recruiter:

      - Test 1: Request at 10:00 on the 14th for product 35455 for brand 1 (ZARA)
      - Test 2: Request at 16:00 on the 14th for product 35455 for brand 1 (ZARA)
      - Test 3: Request at 21:00 on the 14th for product 35455 for brand 1 (ZARA)
      - Test 4: Request at 10:00 on the 15th for product 35455 for brand 1 (ZARA)
      - Test 5: Request at 21:00 on the 16th for product 35455 for brand 1 (ZARA)

## Code Coverage
Jacoco: Jacoco is integrated for measuring test coverage and how to generate reports.

    ./gradlew test jacocoTestReport

## Contributing
Submitting pull requests, reporting bugs, and suggesting enhancements.

## License
No License

## Contact
To reach out for more details or support please contact j.doval.fraga@gmail.com