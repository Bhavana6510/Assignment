@td_OneCS_TD
Feature: To create Individual Application for IMS

  @Assign1
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
    Then user selects 'INDIVIDUAL' from the 'APPLICATION_TYPE_DRPDWN' dropdown
    Then user selects 'TITLE_MR' from the 'TITLE_FOR_CLIENT1' dropdown
    Then user enters 'FIRST_NAME' in 'FIRST_NAME_FOR_CLIENT1' field
    Then user enters 'LAST_NAME' in 'LAST_NAME_FOR_CLIENT1' field
    Then user enters 'DOB' in 'DOB_FOR_CLIENT1' field
    Then user chooses 'MALE' option
    Then user selects 'MARITAL_STATUS' from the 'MARITAL_STATUS_FOR_CLIENT1' dropdown
    Then user clicks on 'ENTER_ADDRESS_MANUALLY' button
    Then user enters 'ADDRESS_LINE1' in 'ADDRESS1_FOR_CLIENT1' field
    Then user enters 'TOWN' in 'TOWN_FOR_CLIENT1' field
    Then user enters 'POSTCODE' in 'POSTCODE_FOR_CLIENT1' field
    Then user selects 'COUNTRY' from the 'COUNTRY_DRPDWN' dropdown
    Then user enters 'EMAIL' in 'EMAIL_FOR_CLIENT1' field
    Then user enters 'MOBILE_NUMBER' in 'MOBILE_FOR_CLIENT1' field
    #RISK QUESTIONAIRE
    Then user clicks on 'PORTFOLIO_DETAILS_TAB' button
    Then user clicks on 'RISK_QUEST_SECTION' button
    Then user chooses 'RISK_QUEST1' option
    Then user chooses 'RISK_QUEST2' option
    Then user chooses 'RISK_QUEST3' option
    Then user chooses 'RISK_QUEST4' option
    Then user chooses 'RISK_QUEST5' option
    Then user chooses 'RISK_QUEST6' option
    Then user chooses 'RISK_QUEST7' option
    Then user chooses 'RISK_QUEST8' option
    Then user chooses 'RISK_QUEST9' option
    Then user selects 'SERVICE_TYPE' from the 'SERVICE_TYPE_DRPDWN' dropdown
    Then user clicks on 'INCOME_BTN' button
    Then user clicks on 'MEDIUM_LOW_BTN' button
    #ADDITIONAL CLIENT DETAILS
    Then user clicks on 'ADDITIONAL_DETAILS_TAB' button
    Then user enters 'TOWN_OF_BIRTH' in 'TOWNOFBIRTH_FOR_CLIENT1' field
    Then user selects 'COUNTRY_OF_BIRTH' from the 'COUNTRY_OF_BIRTH_FOR_CLIENT1' dropdown
    Then user selects 'NATIONALITY' from the 'NATIONALITY_FOR_CLIENT1' dropdown
    Then user enters 'NIN' in 'NIN_FOR_CLIENT1' field
    Then user chooses 'DUAL_NATIONALITY_NO' option
    Then user enters 'TAX_RESIDENCY' in 'TAX_RESIDENCY_FOR_CLIENT1' field
    Then user chooses 'POLITICALLY_EXPOSED_NO' option
    Then user chooses 'PASSPORT_OPTION' option
    Then user enters 'PASSPORT_NUMBER' in 'PASSPORT_FOR_CLIENT1' field
    Then user enters 'EXPIRY_DATE' in 'EXPIRY_DATE_FOR_CLIENT1' field
    Then user enters 'BANK_NAME' in 'BANK_NAME_FOR_CLIENT1' field
    Then user enters 'ACC_NAME' in 'ACC_NAME_FOR_CLIENT1' field
    Then user enters 'SORT_CODE' in 'SORT_CODE_FOR_CLIENT1' field
    Then user enters 'ACC_NUM' in 'ACC_NUM_FOR_CLIENT1' field
    Then user clicks on 'EMPLOYED' button
    Then user enters 'RETIREMENT_AGE' in 'EXP_RETIREMENT_AGE_FOR_CLIENT1' field
    Then user enters 'OCCUPATION' in 'OCCUPATION/JOB_TITLE_FOR_CLIENT1' field
    Then user enters 'COMPANY_NAME' in 'COMPANY_NAME_FOR_CLIENT1' field
    Then user selects 'COUNTRY_OF_INCORP' from the 'COUNTRY_OF_INCORP_FOR_CLIENT1' dropdown
    Then user selects 'SAVINGS_FROM_EMPLOYEMENT' checkbox
    Then user enters 'FURTHER_DETAILS' in 'SOURCE_OF_WEALTH_FOR_CLIENT1' field
    Then user selects 'COUNTRY_OF_DER_WEALTH' from the 'COUNTRY_OF_DER_WEALTH_FOR_CLIENT1' dropdown
    Then user clicks on 'INV_EXP_UNDER_2YEARS' button
    Then user chooses 'FINANCIAL_DEP_NO' option
    #FINANCIAL DETAILS
    Then user clicks on 'FINANCIAL_DETAILS_TAB' button
    Then user enters 'INV_HELD_VALUE' in 'INV_HELD_FOR_CLIENT1' field
    Then user clicks on 'CLIENT1_NO_BTN' button
    Then user enters 'INV_HELD__ELSEWHERE_VALUE' in 'INV_HELD__ELSEWHERE_FOR_CLIENT1' field
    Then user enters 'CASH_DEPOSITS_VALUE' in 'CASH_DEPOSITS_FOR_CLIENT1' field
    Then user enters 'NATIONAL_SAVINGS' in 'NATIONAL_SAVINGS_FOR_CLIENT1' field
    Then user enters 'PENSION_FUNDS_CS' in 'PENSION_FUNDS_CS_FOR_CLIENT1' field
    Then user enters 'PENSION_FUNDS_ELSEWHERE_VALUE' in 'PENSION_FUNDS_ELSEWHERE_FOR_CLIENT1' field
    Then user chooses 'CLIENT1_ADD2_BTN' option
    Then user chooses 'MEMBER_OF_DEF_BEN_NO' option
    Then user enters 'OTHER_PROPERTY_VALUE' in 'OTHER_PROPERTY_FOR_CLIENT1' field
    Then user enters 'PERSONAL_EFFECTS_VALUE' in 'PERSONAL_EFF_FOR_CLIENT1' field
    Then user enters 'BUSINESS_INTEREST_VALUE' in 'BUSINESS_INT_FOR_CLIENT1' field
    Then user enters 'OTHER_ASSETS_VALUE' in 'OTHER_ASSETS_FOR_CLIENT1' field
    Then user enters 'MAIN_HOME_VALUE' in 'MAIN_HOME_FOR_CLIENT1' field
    Then user enters 'OTHER_BORROWINGS_VALUE' in 'OTHER_BORROWINGS_FOR_CLIENT1' field
    Then user enters 'EARNED_INCOME' in 'EARNED_INCOME_FOR_CLIENT1' field
    Then user enters 'BANK_INTEREST_VALUE' in 'BANK_INTEREST_FOR_CLIENT1' field
    Then user enters 'ANNUAL_INCOME' in 'ANNUAL_INCOME_FOR_CLIENT1' field
    Then user enters 'REG_EXP' in 'REG_EXP_FOR_CLIENT1' field
    Then user chooses 'INCOME_CHANGE_NO' option
    Then user chooses 'CAPITAL_GAINS_OPT1' option
    #PORTFOLIO ACCOUNTS
    Then user clicks on 'PORTFOLIO_DETAILS_TAB' button
    Then user clicks on 'ADD_ACCOUNT' button
    Then user selects 'Alpha SIPP' from the 'ACCOUNT_TYPE_DRPDWN' Dropdown
    Then user clicks on 'ADD_ACCOUNT' button
    Then user selects 'Bond' from the 'ACCOUNT_TYPE_DRPDWN' Dropdown
    Then user clicks on 'ADD_ACCOUNT' button
    Then user selects 'GIA/Investment' from the 'ACCOUNT_TYPE_DRPDWN' Dropdown
    Then user clicks on 'ADD_ACCOUNT' button
    Then user selects 'ISA' from the 'ACCOUNT_TYPE_DRPDWN' Dropdown
    Then user clicks on 'ADD_ACCOUNT' button
    Then user selects 'JISA' from the 'ACCOUNT_TYPE_DRPDWN' Dropdown
    Then user clicks on 'ADD_ACCOUNT' button
    Then user selects 'SIPP' from the 'ACCOUNT_TYPE_DRPDWN' Dropdown
    Then user clicks on 'SUMMARY_TAB' button
    Then user clicks on 'SAVE_APPLICATION' button
    And user closes the browser
