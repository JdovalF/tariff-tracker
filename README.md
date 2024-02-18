# tariff-tracker
This microservice is designed for price management and consultation in an e-commerce environment. 
It handles dynamic pricing scenarios based on date and time ranges, providing accurate price information for products. 
The service features a clean, modern architecture utilizing Domain-Driven Design (DDD) principles, and is built with Spring Boot.

# post-man collection
in the resources package there is a postman collection with a few requests to play with the controller

# Unit testing
PricingServiceImpl is fully tested covering all scenarios

# Integration Testing
 PriceController is fully tested covering all scenarios given by recruiter
    
    - Test 1: Request at 10:00 on the 14th for product 35455 for brand 1 (ZARA)
    - Test 2: Request at 16:00 on the 14th for product 35455 for brand 1 (ZARA)
    - Test 3: Request at 21:00 on the 14th for product 35455 for brand 1 (ZARA)
    - Test 4: Request at 10:00 on the 15th for product 35455 for brand 1 (ZARA)
    - Test 5: Request at 21:00 on the 16th for product 35455 for brand 1 (ZARA)