Feature: Common gherkin steps/statement provided for JSON Schema Validation

  Scenario Outline: JSON Schema Validation
    Given verification of json schema for <endpoint> endpoint
    # Static JSON
    And user have json response <json> available
    # Live JSON Response
    #When user set api response as input for json schema validation
    And user have swagger yaml <swagger_yaml> doc available
    And user convert swagger yaml to json schema for object to validate <object_name> and of object type <object_type>
    And user add validation options "NO_ADDITIONAL_PROPERTIES,ALL_PROPERTIES_REQUIRED,IGNORE_NULL_VALUES,FAIL_EMPTY_ARRAYS" to existing json schema
    Then user perform json validation and results are logged
