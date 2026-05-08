Test Scenario: Checkout information form validation

Test Case 1: Valid checkout information
Preconditions:
- The user is logged in and has items in the cart.
- The checkout page is accessible.
- The user is on the checkout page.

- Steps:
- Enter valid first name, last name, and postal code.
- Click on the "Continue" button.
- - Verify that the user is navigated to the next step of the checkout process.
- Click on "Finish" button.

- Expected Result:
- The user is successfully made purchase and is navigated to the order confirmation page.
- User get message "Thank you for your order!" on order confirmation page.

Test Case 2: Checkout with empty first name field
Preconditions:
- The user is logged in and has items in the cart.
- The checkout page is accessible.
- The user is on the checkout page.
- 
- Steps:
- Leave the first name field empty.
- Enter valid last name and postal code.
- Click on the "Continue" button.
- 
- Expected Result:
- An error message is displayed: "Error: First Name is required."
- The user remains on the checkout page.

Test Case 3: Checkout with empty last name field
Preconditions:
- The user is logged in and has items in the cart.
- The checkout page is accessible.
- The user is on the checkout page.
- 
- Steps:
- Enter valid first name.
- Leave the last name field empty.
- Enter valid postal code.
- Click on the "Continue" button.
- 
- Expected Result:
- An error message is displayed: "Error: Last Name is required."
- The user remains on the checkout page.
- 
- Test Case 4: Checkout with empty postal code field
- Preconditions:
- The user is logged in and has items in the cart.
- The checkout page is accessible.
- The user is on the checkout page.
- 
- Steps:
- Enter valid first name and last name.
- Leave the postal code field empty.
- Click on the "Continue" button.
- 
- Expected Result:
- An error message is displayed: "Error: Postal Code is required."
- The user remains on the checkout page.
- 
- Test Case 5: Checkout with all fields empty
- Preconditions:
- The user is logged in and has items in the cart.
- The checkout page is accessible.
- The user is on the checkout page.
- 
- Steps:
- Leave the first name, last name, and postal code fields empty.
- Click on the "Continue" button.
- 
- Expected Result:
- An error message is displayed: "Error: First Name is required."
- The user remains on the checkout page.
- 
- 