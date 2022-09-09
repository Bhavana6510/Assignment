@NotePad
Feature: Desktop application demo feature file

  
  Scenario: Verification of text insertion into Notepad
    # Generic statement provided by framework
    Given user start "notepad" application
    
    When user enter text "Hello World!! This is Desktop APP Automation." in notpad
    Then text "Hello World!! This is Desktop APP Automation." should get entered in notepad
    And user close the notepad application
    
    # Generic statement provided by framework
    And user close given application

  Scenario: Verification of Notepad version
    Given user start "notepad" application
    
    When user open 'About Notepad' dialog box
    Then notepad version should be "18363"
    When user close 'About Notepad' dialog box
    And user intentionally fail this scenario
    And user close the notepad application
    
    And user close given application
