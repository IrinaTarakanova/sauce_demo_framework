Test Scenario: Verify that the product page is displayed, user can add products to the cart, 
can remove, and the product details are correct.

Test Case 1: Verify that the product page is displayed
Preconditions:
- The user is logged in and redirected to product page.
- The product page is accessible.

Steps:
- Verify that the product page is displayed
- Click on add to cart button for any  3 products

Expected Result:
- Product is added in cart.
- Cart icon shows the number of products added in cart.

Test Case 2: Verify that the user can remove products from the cart
Preconditions:
- The user is logged in and has products added in the cart.
- Cart icon shows the number of products added in cart.
Steps:
- Click in remove button for any  products in the cart what was added in previous test case
Expected Result:
- Product is removed from the cart.
- Cart icon shows the number of products added in cart after removing the product.
