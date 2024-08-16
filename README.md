# Fuel logging api
Spring Boot REST API for truck drivers who want to log their fuel consumption

# Features
- Users can register, login, update their passwords and delete their accounts
- Users can create/read/update/delete their fuel logs
- Users can organize logs into folders called sheets
- Users can create/read/update/delete their sheets
- Users can record country, postal code, door number of gas station
- Users can record local date/time of fuel stop, fuel amount and cost
- API security using JSON web tokens

# Technologies
- PostgreSQL
- Spring Boot

# Author
Prabhjot Aulakh

## API Documentation

### Register a New User

- **Endpoint**: `POST /public/user/register`
- **Description**: Registers a new user with a username and password.
- **Request Body**: 
  - **Type**: `AuthRequest`
  - **Details**:
    ```json
    {
      "username": "string",
      "password": "string"
    }
    ```
- **Response**: 
  - **Type**: `Void`
  - **Description**: The server responds with a 200 OK status if the registration is successful.

### Login a User

- **Endpoint**: `POST /public/user/login`
- **Description**: Authenticates a user and returns a JWT token if the credentials are valid.
- **Request Body**: 
  - **Type**: `AuthRequest`
  - **Details**:
    ```json
    {
      "username": "string",
      "password": "string"
    }
    ```
- **Response**: 
  - **Type**: `TokenResponse`
  - **Details**:
    ```json
    {
      "token": "string"
    }
    ```
  - **Description**: The server responds with a 200 OK status and a JWT token if the login is successful.

### Delete a User

- **Endpoint**: `DELETE /private/user/delete`
- **Description**: Deletes a user from the database.
- **Request Body**: 
  - **Type**: `AuthRequest`
  - **Details**:
    ```json
    {
      "username": "string",
      "password": "string"
    }
    ```
- **Response**: 
  - **Type**: `Void`
  - **Description**: The server responds with a 200 OK status if the user is successfully deleted.

### Update User Password

- **Endpoint**: `PUT /private/user/update-password`
- **Description**: Updates the password for an existing user.
- **Request Body**: 
  - **Type**: `ChangePasswordAuthRequest`
  - **Details**:
    ```json
    {
      "username": "string",
      "password": "string",
      "newPassword": "string"
    }
    ```
- **Response**: 
  - **Type**: `Void`
  - **Description**: The server responds with a 200 OK status if the password update is successful.

### Create a New Sheet

- **Endpoint**: `POST /private/sheet/create`
- **Description**: Creates a new sheet with the provided name.
- **Request Body**: 
  - **Type**: `SheetRequest`
  - **Details**:
    ```json
    {
      "sheetName": "string"
    }
    ```
- **Response**: 
  - **Type**: `SheetResponse`
  - **Details**:
    ```json
    {
      "sheetId": "integer",
      "sheetName": "string"
    }
    ```
  - **Description**: The server responds with a 200 OK status and the details of the newly created sheet.

### Get a Single Sheet by ID

- **Endpoint**: `GET /private/sheet/{sheetId}`
- **Description**: Retrieves a single sheet by its ID.
- **Path Variable**:
  - `sheetId`: The ID of the sheet to retrieve.
- **Response**: 
  - **Type**: `SheetResponse`
  - **Details**:
    ```json
    {
      "sheetId": "integer",
      "sheetName": "string"
    }
    ```
  - **Description**: The server responds with a 200 OK status and the details of the requested sheet.

### Get All Sheets

- **Endpoint**: `GET /private/sheet/all`
- **Description**: Retrieves a list of all sheets.
- **Response**: 
  - **Type**: `List<SheetResponse>`
  - **Details**:
    ```json
    [
      {
        "sheetId": "integer",
        "sheetName": "string"
      },
      ...
    ]
    ```
  - **Description**: The server responds with a 200 OK status and a list of all sheets.

### Delete a Sheet by ID

- **Endpoint**: `DELETE /private/sheet/delete/{sheetId}`
- **Description**: Deletes a sheet by its ID.
- **Path Variable**:
  - `sheetId`: The ID of the sheet to delete.
- **Response**: 
  - **Type**: `Void`
  - **Description**: The server responds with a 200 OK status if the sheet is successfully deleted.

### Update Sheet Name

- **Endpoint**: `PUT /private/sheet/update/{sheetId}`
- **Description**: Updates the name of a sheet.
- **Path Variable**:
  - `sheetId`: The ID of the sheet to update.
- **Request Body**: 
  - **Type**: `SheetRequest`
  - **Details**:
    ```json
    {
      "sheetName": "string"
    }
    ```
- **Response**: 
  - **Type**: `Void`
  - **Description**: The server responds with a 200 OK status if the sheet name is successfully updated.

### Create a New Log

- **Endpoint**: `POST /private/log/create/{sheetId}`
- **Description**: Creates a new log entry for a specified sheet.
- **Path Variable**:
  - `sheetId`: The ID of the sheet to associate with the log.
- **Request Body**: 
  - **Type**: `LogRequest`
  - **Details**:
    ```json
    {
      "fuelAmount": 50,
      "fuelCost": 100,
      "localDateTime": "2023-08-15T14:30:00",
      "country": "Canada",
      "state": "Quebec",
      "postalCode": "H3A 1A1",
      "doorNumber": 101
    }
    ```
- **Response**: 
  - **Type**: `LogResponse`
  - **Details**:
    ```json
    {
      "logId": 1,
      "fuelAmount": 50,
      "fuelCost": 100,
      "localDateTime": "2023-08-15T14:30:00",
      "location": {
        "locationId": 123,
        "country": "Canada",
        "state": "Quebec",
        "postalCode": "H3A 1A1",
        "doorNumber": 101
      }
    }
    ```
  - **Description**: The server responds with a 200 OK status and the details of the newly created log entry.

### Get All Logs for a Sheet

- **Endpoint**: `GET /private/log/all/{sheetId}`
- **Description**: Retrieves all log entries associated with a specified sheet.
- **Path Variable**:
  - `sheetId`: The ID of the sheet for which to retrieve logs.
- **Response**: 
  - **Type**: `List<LogResponse>`
  - **Details**:
    ```json
    [
      {
        "logId": 1,
        "fuelAmount": 50,
        "fuelCost": 100,
        "localDateTime": "2023-08-15T14:30:00",
        "location": {
          "locationId": 123,
          "country": "Canada",
          "state": "Quebec",
          "postalCode": "H3A 1A1",
          "doorNumber": 101
        }
      },
      ...
    ]
    ```
  - **Description**: The server responds with a 200 OK status and a list of all logs associated with the specified sheet.

### Get a Single Log by ID

- **Endpoint**: `GET /private/log/{logId}`
- **Description**: Retrieves a single log entry by its ID.
- **Path Variable**:
  - `logId`: The ID of the log entry to retrieve.
- **Response**: 
  - **Type**: `LogResponse`
  - **Details**:
    ```json
    {
      "logId": 1,
      "fuelAmount": 50,
      "fuelCost": 100,
      "localDateTime": "2023-08-15T14:30:00",
      "location": {
        "locationId": 123,
        "country": "Canada",
        "state": "Quebec",
        "postalCode": "H3A 1A1",
        "doorNumber": 101
      }
    }
    ```
  - **Description**: The server responds with a 200 OK status and the details of the requested log entry.

### Update a Log Entry

- **Endpoint**: `PATCH /private/log/update/{logId}`
- **Description**: Updates an existing log entry.
- **Path Variable**:
  - `logId`: The ID of the log entry to update.
- **Request Body**: 
  - **Type**: `LogRequest`
  - **Details**:
    ```json
    {
      "fuelAmount": 50,
      "fuelCost": 100,
      "localDateTime": "2023-08-15T14:30:00",
      "country": "Canada",
      "state": "Quebec",
      "postalCode": "H3A 1A1",
      "doorNumber": 101
    }
    ```
- **Response**: 
  - **Type**: `Void`
  - **Description**: The server responds with a 200 OK status if the log entry is successfully updated.

### Delete a Log Entry

- **Endpoint**: `DELETE /private/log/delete/{logId}`
- **Description**: Deletes a log entry by its ID.
- **Path Variable**:
  - `logId`: The ID of the log entry to delete.
- **Response**: 
  - **Type**: `Void`
  - **Description**: The server responds with a 200 OK status if the log entry is successfully deleted.
