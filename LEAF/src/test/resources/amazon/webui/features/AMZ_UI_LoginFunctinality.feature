@td_AMZ_UI_LoginFunctinality
Feature: Verification of Amazon login funcinality

  @Amazon-Smoke @tb
  Scenario: Verification of Error message with invalid user id
    #Provided by framework
    Given user launches the browser
    And amazon application is avaialble
    When user clicks on sign button on home page
    And user enters invalid user id and clicks on 'Continue' button
    Then user verifies error message as "Incorrect phone number" on Login page
    And intentionally failing this step
    #Provided by framework
    And user closes the browser

  @Amazon-Smoke1
  Scenario: Verification of Error message with invalid user id to delete
    #Provided by framework
    Given user launches the browser
    And amazon application is avaialble
    When user clicks on sign button on home page
    And user enters invalid user id and clicks on 'Continue' button
    Then user verifies error message as "Incorrect phone number" on Login page
    And intentionally failing this step
    #Provided by framework
    And user closes the browser

  @Amazon-Smoke1
  Scenario: Verification of Error message with invalid user id to delete
    #Provided by framework
    Given user launches the browser
    And amazon application is avaialble
    When user clicks on sign button on home page
    And user enters invalid user id and clicks on 'Continue' button
    Then user verifies error message as "Incorrect phone number" on Login page
    And intentionally failing this step
    #Provided by framework
    And user closes the browser

