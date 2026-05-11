Test Scenario: User login functionality

Test Case 1: Valid Login
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