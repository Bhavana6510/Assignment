Feature: IndusMobile app automation demo 

  @IndusMobile
  Scenario: Verificaiton of IndusMobile app launch
    Given user launch app with appPackage "com.fss.indus" and activity ".IndusMobile" on mobile device
    When user clicks on 'BhimUPI' button
    And user closes "app" on mobile device
