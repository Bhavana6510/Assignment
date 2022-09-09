@td_GRAPHQL_API_SPACEX
Feature: API operations Testing Calls Api for all basic operations

  # @Hasura-Smoke
  # Scenario: Get the token Id
  # Given user launches the browser
  # And hasura application is avaialble
  # When user clicks on login button on home page
  # And user enters credentials and clicks on 'Submit' button
  # And user get the authentication id
  # Provided by framework
  # And user closes the browser
  
  @SpaceX-Post
  Scenario: Verification of Hasura graphQL end points
    Given user set base uri for "Space-BaseUri" api
    And user set base path for "CreateSpace-BasePath" endpoint
    When user provide the graphQl query from "CreateSpaceX.json" file
    And user set relaxed HTTPS for current endpoint
    And user execute "POST" request "with" json
    Then user verifies response code as "200"
    And user verifies the response content for "data.company.ceo" with "Elon Musk"
