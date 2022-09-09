@td_PTS_API_Pet
Feature: API operations Testing Calls Api for all basic operations

  @Smoke
  Scenario Outline: Verification of Pet Store rest end points.
    # POST
    Given user set base uri for "Pet-BaseUri" api
    And user set base path for "CreatePet-BasePath" endpoint
    When user form request json "CreatePet.json" with test data in excel "CreatePet.xlsx" and sheet "Valid" for scneario <Create_Scenario>
    And user update pet Id in request json
    
    And user execute "POST" request "with" json
    Then user verifies response code as "200"
    And user reset rest assured parameters
    
    #And user set relaxed HTTPS for current endpoint
    #And user set header "headerName" as "headerValue" for current endpoint
    #And user set "PATH" parameter "parameterName" as "parameterValue" for current endpoint
    #And user set "QUERY" parameter "parameterName" as "parameterValue" for current endpoint
    #And user set "FORM" parameter "parameterName" as "parameterValue" for current endpoint
    #And user set proxy details "10.10.89.98" as host and "8080" as port
    
    #And user add json "json file name" to request body
    
    # GET
    Given user set base uri for "Pet-BaseUri" api
    And user set base path for "GetPet-BasePath" endpoint
    And user provide pet ID to retrive employee information
    When user execute "GET" request "without" json
    Then user verifies response code as "200"
    Then user verify pet name
    And user reset rest assured parameters
    
    # PUT
    Given user set base uri for "Pet-BaseUri" api
    And user set base path for "UpdatePet-BasePath" endpoint
    When user form request json "UpdatePet.json" with test data in excel "UpdatePet.xlsx" and sheet "Valid" for scneario <Update_Scenario>
    And user update existing pet Id in request json
    And user execute "PUT" request "with" json
    Then user verifies response code as "200"
    And user reset rest assured parameters
    
    # GET
    Given user set base uri for "Pet-BaseUri" api
    And user set base path for "GetPet-BasePath" endpoint
    And user provide pet ID to retrive employee information
    When user execute "GET" request "without" json
    Then user verifies response code as "200"
    Then user verify pet name
    And user reset rest assured parameters
    
    # DELETE
    Given user set base uri for "Pet-BaseUri" api
    And user set base path for "DeletePet-BasePath" endpoint
    And user provide pet ID to retrive employee information
    When user execute "Delete" request "without" json
    Then user verifies response code as "200"
    And user reset rest assured parameters
    
    # GET
    Given user set base uri for "Pet-BaseUri" api
    And user set base path for "GetPet-BasePath" endpoint
    And user provide pet ID to retrive employee information
    When user execute "GET" request "without" json
    Then user verifies response code as "404"
    And verify response message as "Pet not found"
    And user reset rest assured parameters

    Examples: 
      | Create_Scenario    | Update_Scenario    |
      | Valid_CreatePet_01 | Valid_UpdatePet_01 |
      | Valid_CreatePet_02 | Valid_UpdatePet_02 |

  @Smoke-Pet
  Scenario Outline: Verification of Pet Store rest end points for invalid data.
    # POST
    Given user set base uri for "Pet-BaseUri" api
    And user set base path for "CreatePet-BasePath" endpoint
    When user form request json "CreatePet.json" with test data in excel "CreatePet.xlsx" and sheet "Valid" for scneario <Create_Scenario>
    And user update invalid pet Id in request json
    And user execute "POST" request "with" json
    Then user verifies response code as "400"
    And user reset rest assured parameters

    Examples: 
      | Create_Scenario    |
      | Valid_CreatePet_01 |
