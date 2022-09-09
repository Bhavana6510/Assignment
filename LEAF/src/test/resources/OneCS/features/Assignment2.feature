@td_OneCS_TD
Feature: To create Joint Application for IMS

  @Assign2
  Scenario: To verify user is able to create Joint Application and fill all details and Save Application
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
    #Then user enters 'C2_TOWN_OF_BIRTH' in 'TOWNOFBIRTH_FOR_CLIENT2' field
    Then user selects 'COUNTRY_OF_BIRTH' from the 'COUNTRY_OF_BIRTH_FOR_CLIENT1' dropdown
   # Then user selects 'COUNTRY_OF_BIRTH' from the 'COUNTRY_OF_BIRTH_FOR_CLIENT2' dropdown
    Then user selects 'NATIONALITY' from the 'NATIONALITY_FOR_CLIENT1' dropdown
   # Then user selects 'NATIONALITY' from the 'NATIONALITY_FOR_CLIENT2' dropdown
    Then user enters 'NIN' in 'NIN_FOR_CLIENT1' field
    Then user chooses 'DUAL_NATIONALITY_NO' option
    Then user enters 'TAX_RESIDENCY' in 'TAX_RESIDENCY_FOR_CLIENT1' field
   # Then user enters 'TAX_RESIDENCY' in 'TAX_RESIDENCY_FOR_CLIENT2' field
    Then user enters 'TAX_ID' in 'TAX_ID_FOR_CLIENT1' field
    #Then user enters 'C2_TAX_ID' in 'TAX_ID_FOR_CLIENT2' field
    Then user chooses 'POLITICALLY_EXPOSED_NO' option
    Then user chooses 'C2_POLITICALLY_EXPOSED_NO' option
    Then user chooses 'PASSPORT_OPTION' option
    Then user chooses 'DRIVING_LIC_OPTION' option
    Then user enters 'PASSPORT_NUMBER' in 'PASSPORT_FOR_CLIENT1' field
    Then user enters 'DRIVING_LIC_NUM' in 'DRIVING_LIC_FIELD' field
    Then user enters 'EXPIRY_DATE' in 'EXPIRY_DATE_FOR_CLIENT1' field
    Then user enters 'EXPIRY_DATE' in 'EXPIRY_DATE_FOR_CLIENT2' field
    Then user enters 'BANK_NAME' in 'BANK_NAME_FOR_CLIENT1' field
    Then user enters 'C2_BANK_NAME' in 'BANK_NAME_FOR_CLIENT2' field
    Then user enters 'SORT_CODE' in 'SORT_CODE_FOR_CLIENT1' field
    Then user enters 'C2_SORT_CODE' in 'SORT_CODE_FOR_CLIENT2' field
    Then user enters 'ACC_NUM' in 'ACC_NUM_FOR_CLIENT1' field
    Then user enters 'JNT_BNAME' in 'JOINT_BANK_NAME' field
    Then user enters 'JNT_ACC_NAME' in 'JOINT_ACC_NAME' field
    Then user enters 'JNT_SORTCODE' in 'JOINT_SORT_CODE' field
    Then user enters 'JNT_ACCNUM' in 'JOINT_ACC_NUM' field
    Then user enters 'JNT_PAYREF' in 'JOINT_PAYMENT_REF' field    
    Then user clicks on 'EMPLOYED' button
    Then user clicks on 'C2_EMPLOYED' button
    Then user enters 'RETIREMENT_AGE' in 'EXP_RETIREMENT_AGE_FOR_CLIENT1' field
    Then user enters 'RETIREMENT_AGE' in 'EXP_RETIREMENT_AGE_FOR_CLIENT2' field
    Then user enters 'OCCUPATION' in 'OCCUPATION/JOB_TITLE_FOR_CLIENT1' field
    Then user enters 'C2_OCCUPATION' in 'OCCUPATION/JOB_TITLE_FOR_CLIENT2' field
    Then user enters 'COMPANY_NAME' in 'COMPANY_NAME_FOR_CLIENT1' field
    Then user enters 'C2_COMPANY_NAME' in 'COMPANY_NAME_FOR_CLIENT2' field
    Then user selects 'COUNTRY_OF_INCORP' from the 'COUNTRY_OF_INCORP_FOR_CLIENT1' dropdown
    Then user selects 'C2_COUNTRY_OF_INCORP' from the 'COUNTRY_OF_INCORP_FOR_CLIENT2' dropdown
    Then user selects 'SAVINGS_FROM_EMPLOYEMENT' checkbox
    Then user selects 'C2_SAVINGS_FROM_EMPLOYEMENT' checkbox
    Then user enters 'FURTHER_DETAILS' in 'SOURCE_OF_WEALTH_FOR_CLIENT1' field
    Then user enters 'FURTHER_DETAILS' in 'SOURCE_OF_WEALTH_FOR_CLIENT2' field
    Then user selects 'COUNTRY_OF_DER_WEALTH' from the 'COUNTRY_OF_DER_WEALTH_FOR_CLIENT1' dropdown
    Then user selects 'COUNTRY_OF_DER_WEALTH' from the 'COUNTRY_OF_DER_WEALTH_FOR_CLIENT2' dropdown
    Then user clicks on 'INV_EXP_UNDER_2YEARS' button
    Then user clicks on 'C2_INV_EXP_2TO5YEARS' button
    Then user chooses 'FINANCIAL_DEP_NO' option
    Then user chooses 'C2_FINANCIAL_DEP_NO' option
    #FINANCIAL DETAILS
    Then user clicks on 'FINANCIAL_DETAILS_TAB' button
    Then user enters 'INV_HELD_VALUE' in 'INV_HELD_FOR_CLIENT1' field
    Then user enters 'INV_HELD_VALUE' in 'INV_HELD_FOR_CLIENT2' field
    Then user enters 'INV_HELD__ELSEWHERE_VALUE' in 'INV_HELD__ELSEWHERE_FOR_CLIENT1' field
    Then user enters 'INV_HELD__ELSEWHERE_FIELD' in 'INV_HELD__ELSEWHERE_FOR_CLIENT2' field
    Then user enters 'CASH_DEPOSITS_VALUE' in 'CASH_DEPOSITS_FOR_CLIENT1' field
    Then user enters 'CASH_DEPOSITS_VALUE' in 'CASH_DEPOSITS_FOR_CLIENT2' field
    Then user enters 'NATIONAL_SAVINGS' in 'NATIONAL_SAVINGS_FOR_CLIENT1' field
    Then user enters 'NATIONAL_SAVINGS' in 'NATIONAL_SAVINGS_FOR_CLIENT2' field
    Then user enters 'PENSION_FUNDS_CS' in 'PENSION_FUNDS_CS_FOR_CLIENT1' field
    Then user enters 'PENSION_FUNDS_CS' in 'PENSION_FUNDS_CS_FOR_CLIENT2' field
    Then user chooses 'MEMBER_OF_DEF_BEN_NO' option
    Then user chooses 'C2_MEMBER_OF_DEF_BEN_YES' option
    Then user enters 'MARKET_VALUE' in 'MARKET_VALUE_FIELD' field
    Then user enters 'PERSONAL_EFFECTS_VALUE' in 'PERSONAL_EFF_FOR_CLIENT1' field
    Then user enters 'PERSONAL_EFFECTS_VALUE' in 'PERSONAL_EFF_FOR_CLIENT2' field
    Then user enters 'BUSINESS_INTEREST' in 'BUSINESS_INT_FOR_CLIENT1' field
    Then user enters 'BUSINESS_INTEREST' in 'BUSINESS_INT_FOR_CLIENT2' field
    Then user enters 'OTHER_ASSETS_VALUE' in 'OTHER_ASSETS_FOR_CLIENT1' field
    Then user enters 'OTHER_ASSETS_VALUE' in 'OTHER_ASSETS_FOR_CLIENT2' field
    Then user enters 'OTHER_BORROWINGS_VALUE' in 'OTHER_BORROWINGS_FOR_CLIENT1' field
    Then user enters 'OTHER_BORROWINGS_VALUE' in 'OTHER_BORROWINGS_FOR_CLIENT2' field
    Then user enters 'EARNED_INCOME' in 'EARNED_INCOME_FOR_CLIENT1' field
    Then user enters 'PENSION_INCOME' in 'PENSION_INCOME_FOR_CLIENT1' field
    Then user enters 'BANK_INTEREST_VALUE' in 'BANK_INTEREST_FOR_CLIENT1' field
    Then user enters 'REG_EXP' in 'REG_EXP_FOR_CLIENT1' field
    Then user enters 'C2_REG_EXP' in 'C2_REG_EXP_FIELD' field
    Then user chooses 'INCOME_CHANGE_NO' option
    Then user chooses 'CAPITAL_GAINS_OPT1' option
    #PORTFOLIO ACCOUNTS
    Then user clicks on 'PORTFOLIO_DETAILS_TAB' button
    Then user clicks on 'ADD_ACCOUNT' button
    Then user selects 'Alpha SIPP' from the 'ACCOUNT_TYPE_DRPDWN' Dropdown
    Then user chooses 'C1_ACC_RADIO_BTN' optionn
    Then user clicks on 'ADD_ACCOUNT' button
    Then user selects 'Bond' from the 'ACCOUNT_TYPE_DRPDWN' Dropdown
    Then user chooses 'C2_ACC_CHECKBOX' optionn
    Then user clicks on 'ADD_ACCOUNT' button
    Then user selects 'GIA/Investment' from the 'ACCOUNT_TYPE_DRPDWN' Dropdown
    Then user chooses 'C1_ACC_CHECKBOX' optionn
    Then user clicks on 'ADD_ACCOUNT' button
    Then user selects 'ISA' from the 'ACCOUNT_TYPE_DRPDWN' Dropdown
    Then user chooses 'C2_ACC_RADIO_BTN' optionn
    Then user clicks on 'ADD_ACCOUNT' button
    Then user selects 'JISA' from the 'ACCOUNT_TYPE_DRPDWN' Dropdown
    Then user chooses 'C1_ACC_RADIO_BTN' optionn
    Then user clicks on 'ADD_ACCOUNT' button
    Then user selects 'SIPP' from the 'ACCOUNT_TYPE_DRPDWN' Dropdown
    Then user chooses 'C2_ACC_RADIO_BTN' optionn
    Then user clicks on 'SUMMARY_TAB' button
    Then user clicks on 'SAVE_APPLICATION' button
    And user closes the browser
