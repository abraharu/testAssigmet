# User Controller Documentation
## Endpoints
### Create User
* URL: /users
* Method: POST
* Description: Creates a new user with the given details.
* Request Body: Required, JSON format
* Validations: Checks for valid email, required fields (email, first name, last name, birth date).
* Success Response:
    + Code: 201 (CREATED)
    + Content: JSON representing the created user.
* Error Response:
   + Code: 400 (BAD REQUEST)
   + Content: Error message indicating what went wrong (e.g., validation error).
### Update User (Complete Update)
* URL: /users/{id}
* Method: PUT
* Description: Updates an existing user's details.
* Path Variable: id - ID of the user to update.
* Request Body: Required, JSON format
* Validations: All user fields must be provided and validated.
* Response:
   + Code: 200 (OK)
   + Content: JSON representing the updated user.
### Update User Field (Partial Update)
* URL: /users/{id}
* Method: PATCH
* Description: Partially updates an existing user's details.
* Path Variable: id - ID of the user to update.
* Request Body: Required, JSON format
* Response:
   + Code: 200 (OK)
   + Content: JSON representing the updated user.
### Delete User
* URL: /users/{id}
* Method: DELETE
* Description: Deletes the specified user.
* Path Variable: id - ID of the user to delete.
* Response:
   + Code: 204 (NO CONTENT)
### Find Users by Birth Date Range
* URL: /users/search
* Method: GET
* Parameters:
   + from: Start date (ISO date format, e.g., yyyy-MM-dd)
   + to: End date (ISO date format, e.g., yyyy-MM-dd)
* Description: Retrieves a list of users born between the specified dates.
* Response:
   + Code: 200 (OK)
   + Content: Array of users matching the criteria.
