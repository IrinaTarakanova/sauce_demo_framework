Test Scenario: User login functionality

Test Case 1: Valid User Login
Preconditions:
- The user has a registered account with valid credentials.
- The login page is accessible.
- The user is on the login page.
-  Steps:
- Enter valid username and password.
- Click on the "Login" button.
Expected Result:
- The user is successfully logged in and redirected to product page
Perform by:
- Irina
Execution time:
- 1 minute

Test Case 2: Locked user login
Preconditions:
- The user has a registered account that is locked.
- The login page is accessible.
- The user is on the login page.
Steps:
- Enter locked username.
- Enter valid password.
- Click on the "Login" button.
Expected Result:
- An error message is displayed: "Epic sadface: Sorry, this user has been locked out."
- The user remains on the login page.
  Perform by:
- Irina
  Execution time:
- 1 minute
- 
Test Case 3: Empty fields login
Preconditions:
- The login page is accessible.
- The user is on the login page.
Steps:
- Leave the username and password fields empty.
- Click on the "Login" button.
- Expected Result:
- An error message is displayed: "Epic sadface: Username is required."
- The user remains on the login page.
  Perform by:
- Irina
  Execution time:
- 1 minute

Test case 4: Login with wrong password
Preconditions:
- The login page is accessible
- The user is on the login page 
Steps:
- Enter valid username "standard_user"
- Enter wrong password "testing_password"
- Click on the "Login" button
Expected result:
- An error mesage is displayed: "Epic sadface: Username and password 
do not match any user is this service "
- The user remains on the login page
Perform by:
- Irina
Execution time:
- 1 minute

Test Case 5: Login with wrong username
Preconditions:
- The login page is accessible
- The user is on the login page
  Steps:
- Enter wrong username "invalid_user"
- Enter valid password "secret_sauce"
- Click on the "Login" button
  Expected result:
- An error message is displayed: "Epic sadface: Username and password
  do not match any user is this service "
- The user remains on the login page
  Perform by:
- Irina
  Execution time:
- 1 minute