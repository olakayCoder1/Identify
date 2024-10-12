Here’s a basic `README.md` file for your `INDENTIFY` API based on the Postman collection data you provided:

```markdown
# INDENTIFY API

## Overview
The **INDENTIFY** API provides a set of endpoints for user authentication, identity management, and sharing features. This API allows users to register, activate their accounts, log in, manage identities, and share information securely.

### Base URL
```
http://localhost:8080/api/v1
```

---

## Authentication

### 1. **Register a New User**
   **Endpoint**: `/auth/register`  
   **Method**: `POST`

   #### Request Body:
   ```json
   {
       "firstName": "AbdulKabeer",
       "lastName": "Olanrewaju",
       "password": "olakay@pyDev1",
       "email": "olakaycoder1@gmail.com"
   }
   ```

   #### Response (200 OK):
   ```json
   {
       "message": "Account created",
       "data": {
           "id": 2,
           "email": "olakaycoder1@gmail.com",
           "firstName": "AbdulKabeer",
           "lastName": "Olanrewaju",
           "isVerify": false,
           "isActive": false,
           "createdAt": "2024-08-25T11:45:37.692465",
           "updatedAt": "2024-08-25T11:45:37.692566",
           "role": "USER",
           "username": "olakaycoder1@gmail.com",
           "authorities": [
               {
                   "authority": "USER"
               }
           ]
       }
   }
   ```

### 2. **Activate User Account**
   **Endpoint**: `/auth/verify`  
   **Method**: `POST`

   #### Request Body:
   ```json
   {
       "code": "m0O15T",
       "email": "olakaycoder1@gmail.com"
   }
   ```

   #### Response (200 OK):
   ```json
   {
       "message": "Your account has been verified successfully",
       "data": null
   }
   ```

### 3. **Login**
   **Endpoint**: `/auth/login`  
   **Method**: `POST`

   #### Request Body:
   ```json
   {
       "email": "olakaycoder1@gmail.com",
       "password": "olakay@pyDev1"
   }
   ```

   #### Response (200 OK):
   ```json
   {
       "access_token": "YOUR_ACCESS_TOKEN",
       "refresh_token": null
   }
   ```

---

## Identities

### 1. **Get Identity by ID**
   **Endpoint**: `/identity/{id}`  
   **Method**: `GET`

   #### Headers:
   - `Authorization`: Bearer `{{ACCESS_TOKEN}}`

   #### Response (200 OK):
   ```json
   {
       "id": 1,
       "idType": "PHONE",
       "idNumber": "08082838283",
       "record": "eyJkYXRlT2ZCaXJ0aCI6IjE5OTktMTItMjMiLCJmaXJzdE5hbWUiOiJ0ZXN0Iiwic3VybmFtZSI6InRlc3QiLCJtaWRkbGVuYW1lIjoiVGVzdCIsInBob25lTnVtYmVyIjoiMDgwODI4MzgyODMifQ==",
       "createdAt": "2024-10-07T16:25:57.914388",
       "updatedAt": "2024-10-07T16:25:57.914575",
       "active": true
   }
   ```

### 2. **Update Identity Status**
   **Endpoint**: `/identity/{id}`  
   **Method**: `PUT`

   #### Headers:
   - `Authorization`: Bearer `{{ACCESS_TOKEN}}`

   #### Request Body:
   ```json
   {
       "active": false
   }
   ```

   #### Response (200 OK):
   ```json
   {
       "id": 1,
       "idType": "PHONE",
       "idNumber": "08082838283",
       "active": false
   }
   ```

---

## Shares

### 1. **Get Share by ID**
   **Endpoint**: `/shares/{id}`  
   **Method**: `GET`

   #### Headers:
   - `Authorization`: Bearer `{{ACCESS_TOKEN}}`

   #### Response (200 OK):
   ```json
   {
       "id": 102,
       "emails": ["programmerolakay@gmail.com"],
       "createdAt": "2024-10-09T16:03:05.175001",
       "identity": {
           "id": 1,
           "idType": "PHONE",
           "idNumber": "08082838283"
       },
       "active": true
   }
   ```

### 2. **Create New Share Link**
   **Endpoint**: `/shares`  
   **Method**: `POST`

   #### Headers:
   - `Authorization`: Bearer `{{ACCESS_TOKEN}}`
   - `Content-Type`: `application/json`

   #### Request Body:
   ```json
   {
       "viewOnce": false,
       "identityId": 1,
       "emails": ["programmerolakay@gmail.com"]
   }
   ```

   #### Response (200 OK):
   ```json
   {
       "id": 103,
       "emails": ["programmerolakay@gmail.com"],
       "createdAt": "2024-10-12T16:03:05.175001",
       "identity": {
           "id": 1,
           "idType": "PHONE",
           "idNumber": "08082838283"
       },
       "active": true
   }
   ```

---

## Error Codes
- **200 OK**: Successful requests.
- **204 No Content**: Successful request but no content to return.
- **401 Unauthorized**: Authentication failure.
- **400 Bad Request**: Malformed request or missing parameters.

## Authentication
All endpoints (except register and login) require an `Authorization` header with a valid JWT access token.

---

## Development

To run the API locally, make sure to configure your environment and start the server on port `8080`.

---
