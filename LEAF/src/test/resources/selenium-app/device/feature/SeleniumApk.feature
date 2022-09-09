Feature: Device automation Demo feature

  @AndroidDemo
  Scenario: Verfication of amazon web site search functionality
    # Provide by Framework
    Given user launch browser on mobile device
    And user open amazon application
    When user search "Mobile" on search box
    Then user verifies the search result
    # Provide by Framework
    And user closes "browser" on mobile device

  @AndroidDemo
  Scenario: Verification of user registration functionality
    # Provide by Framework
    Given user launch app "selendroid-test-app-0.17.0.apk" on mobile device
    When navigate to registration page on the app
    And user fill registration form and submit
    Then user verifies the regiestration
    # Provide by Framework
    And user closes "app" on mobile device

  @AndroidDemo
  Scenario: Verification of user registration functionality
    # Provide by Framework
    Given user launch app with appPackage "io.selendroid.testapp" and activity ".HomeScreenActivity" on mobile device
    When user enter text "Android APP Demo!!" on the app
    And user enable dispaly text
    Then user verified display text as "Text is sometimes displayed"
    When user enable popup on the app
    Then user should be able to disable the popup on the app
    # Provide by Framework
    And user closes "app" on mobile device
  
