# Wolt 2025 Backend Engineering Internship implementation in Java Spring Boot

## Overview

This project is a Java Spring Boot implementation of the Delivery Order Price Calculator (DOPC) assignment originally created by Wolt for backend engineering internship candidates: https://github.com/woltapp/backend-internship-2025.

The original task specifies several preferred languages (e.g. Kotlin, Python, Go) depending on location and Java / Spring Boot is intentionally not listed. This implementation was created outside the official submission context and exists solely as a personal project to practice backend engineering skills, system design, and code quality in a familiar production-grade framework.

## Original Assignment

The assignment requires implementing a backend service called Delivery Order Price Calculator (DOPC).

The service exposes a single HTTP endpoint that:

1. Accepts venue information, cart value, and user coordinates
2. Fetches venue data from an external API (static + dynamic endpoints)
3. Calculates:
   - Delivery distance
   - Delivery fee
   - Small order surcharge
   - Total order price
4. Returns a structured JSON price breakdown

## Why Java Spring Boot for this task

I chose Java to use for this task for the sole reason that I am the most fluent with Java and enjoy coding with it the most. 

Spring boot also provides a clean and efficient way to design REST APIs and divide the functionalities to their respective classes, thus keeping code clean and readable.

After each request to the supplied `/static` and `/dynamic` REST API endpoints, the results are cached for 60 seconds in a Redis cache. This is to limit unnecessary repetitive requests to the same addresses because the resulting data is not changing that often.

## How to use

1. Clone the repository

   ```
   git clone https://github.com/Eikkaaaaa/dopcapi
   cd dopcapi
   ```

2. Start the service

   ```
   docker-compose up --build
   ```

## Disclaimer

This project:
- Is not an official submission
- Is not intended to be evaluated by Wolt recruiters