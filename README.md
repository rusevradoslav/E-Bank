# E-Bank
In this application I create a mini banking software- E- Bank, Online banking system.E-Bank have the following functionality:User register,login, logout,a logged user can create a bank account/s,deposit and withdraw a given money,transfer money to another user’s bank account and in the end get information about all transactions that he make.  

**Technologies:**

**Front-End:**

*   HTML
*   CSS
*   Bootstrap
*   Thymeleaf

 
**Back-End:**
*   Java
*   Spring Boot
*   Spring MVC
*   Spring Security
*   MySQL Database
*   JPA

**Without authentication** you can see : 

    - the index page
    - the login form
    - the user registration form
  
**With authentication** you can see :

    - the home page
    - the deposit page
    - the withdraw page
    - the transfer page
    - the crate-account page
    - the transactions page
    
  URLs | Description
  ---------|---------
   */* | Index page - login, register
   */register* | Register page -  page where user can register .
   */login* | Login page - page where user can login .
   */accounts/deposit/{id}* | Deposit page - page where user can deposit amount of money.
   */accounts/withdraw/{id}* | Withdraw page - page where user can withdraw amount of money.
   */accounts/transfer/{id}* | Transfer page - page where user can transfer amount of money.
   */transactions* | Transactions page - page where user can see all transactions that he make.
 
  Database Diagram Screenshot
  ---
  
  ![database](/src/main/resources/static/screens/databse.png)
  
 Website Screenshots
 ---
 
 - Index Page 
 ![homepage](/src/main/resources/static/screens/index.png)
 
 - Register Page
 ![addons](/src/main/resources/static/screens/register.png)
 
 - Login Page
 ![pending-addons](/src/main/resources/static/screens/login.png)
 
 - Home Page
 ![product-page](/src/main/resources/static/screens/home.png)
 
 - Crate Bank Account Page
  ![product-page](/src/main/resources/static/screens/create-account.png)
 
 - Deposit Page
 ![product-page](/src/main/resources/static/screens/deposit.png)
 
 - Withdraw Page
 ![product-page](/src/main/resources/static/screens/withdraw.png)
 
 - Transfer Page
 ![create-new-addon](/src/main/resources/static/screens/transfer.png)
 
 - All Transactions Page
 ![update](/src/main/resources/static/screens/transactions.png) 
 
 - Test
 ![update](/src/main/resources/static/screens/test1.png)
 
