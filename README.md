# API Key Security System

This project demonstrates an API Key security system implemented using Spring Security, JWT token authentication, and Spring Data JPA.

## Overview

The application showcases an API Key system with JWT token-based authentication to secure specific endpoints and access JSON data.

## Functionality

### Authentication and Authorization

Endpoints for user authentication and authorization:

- `POST /api/v1/auth/register`: Registers a new user with provided details.
- `POST /api/v1/auth/login`: Authenticates a user using their credentials.
- `GET /api/v1/auth/hello`: A simple endpoint to check application status.

### API Key Management

Endpoints for API Key generation, access, and removal:

- `GET /api/v1/key/generate`: Generates an API Key for the authenticated user.
- `GET /api/v1/key/data`: Retrieves JSON data using the API Key for authentication.
- `GET /api/v1/key/keys`: Retrieves the API Key associated with the authenticated user.
- `DELETE /api/v1/key/remove`: Deletes the specified API Key.

## Usage

### Generating an API Key

To generate an API Key, make a `GET` request to `/api/v1/key/generate`. This will create a unique API Key associated with the authenticated user.

### Accessing Data

To access JSON data, use the generated API Key. Make a `GET` request to `/api/v1/key/data` and include the API Key in the request headers.

### Managing API Keys

To manage API Keys:

- `GET /api/v1/key/keys`: Retrieve the API Key associated with the user.
- `DELETE /api/v1/key/remove`: Delete an API Key by providing the key value.

## Technologies Used

- Spring Boot
- Spring Security
- Spring Data JPA
- JWT Token Authentication


