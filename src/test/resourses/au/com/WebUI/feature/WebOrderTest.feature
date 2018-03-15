@WebOrderTest
Feature: User login to place order, update order and delete order then logout

  As a User, I want to create a new order, update or delete it.

  Background:
    Given user is on home page

# test login and log out
  Scenario Outline: Successful Login with valid credentials and Logout
    When user login by <username> and <password>
    Then user is on landing page
    When user clicks on Logout
    Then user is on home page

    Examples:
      | username | password |
      | Tester   | test     |

    # test place a new order
  Scenario Outline: User can place a new order
    When user login by <username> and <password>
    Then user is clicking on Order
    And user selects product <product>
    And user updates Quantity <quantity>
    And user provide address infomation including <name> and <state>
    And user provide payment infomation including <cardnumber>
    And user clicks on Process button
    And user clicks on View all Orders
    Then verify order exist by checking <product>, <name>, <state> and <cardnumber> from the order list
    When user clicks on Logout
    Then user is on home page
    Examples:
      | username | password | product     | quantity | name    | state | cardnumber |
      | Tester   | test     | ScreenSaver | 5        | Jing001 | VIC   | 1234567890 |
      | Tester   | test     | MyMoney     | 8        | Jing002 | NSW   | 8748548372 |
      | Tester   | test     | FamilyAlbum | 2        | Jing003 | VIC   | 7575747475 |


     # test update an existing order
  Scenario Outline: User can update an existing order
    Given user login by <username> and <password>
    When user is clicking on Order
    And user selects product <product>
    And user updates Quantity <quantity>
    And user provide address infomation including <name> and <state>
    And user provide payment infomation including <cardnumber>
    And user clicks on Process button
    And user clicks on View all Orders
    Then verify order exist by checking <product>, <name>, <state> and <cardnumber> from the order list
    Then user select to edit the new order by checking <product>, <name>, <state> and <cardnumber> from the order list
    And user update payment infomation including <updatedCardnumber>
    And user clicks on Updated button
    Then verify order exist by checking <product>, <name>, <state> and <updatedCardnumber> from the order list
    When user clicks on Logout
    Then user is on home page
    Examples:
      | username | password | product     | quantity | name    | state | cardnumber | updatedCardnumber |
      | Tester   | test     | ScreenSaver | 5        | Jing001 | VIC   | 1234567890 | 12345654321       |
      | Tester   | test     | MyMoney     | 8        | Jing002 | NSW   | 8748548372 | 78436383493       |
      | Tester   | test     | FamilyAlbum | 2        | Jing003 | VIC   | 7575747475 | 30392394354       |


     # test delete an existing order
  Scenario Outline: User can delete an existing order
    Given user login by <username> and <password>
    When user is clicking on Order
    And user selects product <product>
    And user updates Quantity <quantity>
    And user provide address infomation including <name> and <state>
    And user provide payment infomation including <cardnumber>
    And user clicks on Process button
    And user clicks on View all Orders
    Then verify order exist by checking <product>, <name>, <state> and <cardnumber> from the order list
    Then user select order by checking <product>, <name>, <state> and <cardnumber> from the order list
    And user clicks on Delete Selected button
    Then verify order does not exist by checking <product>, <name>, <state> and <cardnumber> from the order list
    When user clicks on Logout
    Then user is on home page

    Examples:
      | username | password | product     | quantity | name    | state | cardnumber |
      | Tester   | test     | ScreenSaver | 5        | Jing001 | VIC   | 1234567890 |
      | Tester   | test     | MyMoney     | 8        | Jing002 | NSW   | 8748548372 |
      | Tester   | test     | FamilyAlbum | 2        | Jing003 | VIC   | 7575747475 |