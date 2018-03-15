@quickQuote
Feature: generating quick quote for investor

  As a Advisor
  I want to generate quick quote for the investor
  so that investor gets the information regarding the product

  Background:

  Scenario Outline: Getting a quick quote for investor from composer UI
    Given The user is viewing the login page
    Then The user login by <username> and <password>
    And The user is clicking on Order
    And User selects product <product>
    And User updates Quantity <quantity>
    And User provide address infomation including <name> and <state>
    And User provide payment infomation including <cardnumber>
    And User clicks on Process button
    And User clicks on View all Orders
    And Verify order is exist by checking <product>, <name>, <state> and <cardnumber> form the order list
    And User select to edit the new order by checking <product>, <name>, <state> and <cardnumber> form the order list
    And User update payment infomation including <updatedCardnumber>
    And User clicks on Updated button
    And User select order by checking <product>, <name>, <state> and <updatedCardnumber> form the order list
    And User clicks on Delete Selected button
    And Verify order is not exist by checking <product>, <name>, <state> and <updatedCardnumber> form the order list
    And User clicks on Logout

    Examples: Order details
      | username | password | product     | quantity | name    | state | cardnumber | updatedCardnumber |
      | Tester   | test     | ScreenSaver | 5        | Jing001 | VIC   | 1234567890 | 12345654321       |
      | Tester   | test     | MyMoney     | 8        | Jing002 | NSW   | 8748548372 | 78436383493       |
      | Tester   | test     | FamilyAlbum | 2        | Jing003 | VIC   | 7575747475 | 30392394354       |

