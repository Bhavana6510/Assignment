@td_OneCS_TD
Feature: To create Individual Application for IMS

  @Try
  Scenario: To verify user is able to create Individual Application and fill all details and Save Application
    Given user launches the url
    Then user verifies Application Dashboard screen is displayed
    Then user clicks on 'CREATE_NEW' button
    Then user selects 'IMS' from the 'CREATE_DRPDWN' dropdown
    Then user clicks on 'CREATE_NEW' button
    Then user verifies 'Summary' screen is displayed
    Then user enters BGCode
    #CLIENT DETAILS
    Then user clicks on 'CLIENT_DETAILS_TAB' tab
    Then user clicks on 'PORTFOLIO_DETAILS_TAB' tab and verfies error message is displayed
    Then user selects 'JOINT' from the 'APPLICATION_TYPE_DRPDWN' dropdown
    Then user selects 'TITLE_MR' from the 'TITLE_FOR_CLIENT1' dropdown
    Then user selects 'CLIENT2_TITLE_MR' from the 'TITLE_FOR_CLIENT2' dropdown
    Then user enters 'FIRST_NAME' in 'FIRST_NAME_FOR_CLIENT1' field
    Then user enters 'CLIENT2_FNAME' in 'FIRST_NAME_FOR_CLIENT2' field
    Then user enters 'LAST_NAME' in 'LAST_NAME_FOR_CLIENT1' field
    Then user enters 'CLIENT2_LNAME' in 'LAST_NAME_FOR_CLIENT2' field
    Then user enters 'DOB' in 'DOB_FOR_CLIENT1' field
    Then user enters 'CLIENT2_DOB' in 'DOB_FOR_CLIENT2' field
    Then user chooses 'MALE' option
    Then user chooses 'C2_MALE' option
    Then user selects 'MARITAL_STATUS' from the 'MARITAL_STATUS_FOR_CLIENT1' dropdown
    Then user selects 'C2_MARITAL_STATUS' from the 'MARITAL_STATUS_FOR_CLIENT2' dropdown
    Then user clicks on 'ENTER_ADDRESS_MANUALLY' button
    Then user enters 'ADDRESS_LINE1' in 'ADDRESS1_FOR_CLIENT1' field
    Then user enters 'TOWN' in 'TOWN_FOR_CLIENT1' field
    Then user enters 'POSTCODE' in 'POSTCODE_FOR_CLIENT1' field
    Then user selects 'COUNTRY' from the 'COUNTRY_DRPDWN' dropdown
    Then user chooses 'SAME_ADDRESS_AS_CLIENT1' option
    Then user enters 'EMAIL' in 'EMAIL_FOR_CLIENT1' field
    Then user enters 'C2_EMAIL' in 'EMAIL_FOR_CLIENT2' field
    Then user enters 'MOBILE_NUMBER' in 'MOBILE_FOR_CLIENT1' field
    Then user enters 'CLIENT2_MOBILE' in 'MOBILE_FOR_CLIENT2' field
  
   #RISK QUESTIONAIRE
   Then user clicks on 'ADDITIONAL_DETAILS_TAB' button
    Then user enters 'BANK_NAME' in 'BANK_NAME_FOR_CLIENT1' field
    Then user enters 'C2_BANK_NAME' in 'BANK_NAME_FOR_CLIENT2' field
    Then user enters 'SORT_CODE' in 'SORT_CODE_FOR_CLIENT1' field
    Then user enters 'C2_SORT_CODE' in 'SORT_CODE_FOR_CLIENT2' field
    Then user enters 'ACC_NUM' in 'ACC_NUM_FOR_CLIENT1' field
    Then user enters 'C2_ACC_NUM' in 'ACC_NUM_FOR_CLIENT2' field
   