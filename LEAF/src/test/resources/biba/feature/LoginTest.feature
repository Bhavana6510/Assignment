@td_UI_testData
Feature: Biba functionality verification

  @SmokeUI
  Scenario Outline: Verification of invalid login error message
    Given biba application is available
    When User login with invalid username as <user_name> and password as <password>
    Then application should show error message as "Your username or password is incorrect"

    Examples: 
      | user_name                   | password |
      | varsha.chhabria98@gmail.com | 1234asdf |
