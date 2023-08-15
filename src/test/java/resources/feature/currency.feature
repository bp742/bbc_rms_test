Feature: Music tracks and Metadata Test

  Scenario: Verify the GET request response code is 200 and response time is below 1000 milliseconds
    Given User makes a GET request to 'https://testapi.io/api/rmstest/media'
    Then The HTTP status code should be 200
    And  The response time should be below 1000 milliseconds

  Scenario: User wants to verify the 'id' and 'segment_type' fields
    Given User makes a GET request to 'https://testapi.io/api/rmstest/media'
    Then  The user verifies that the 'id' field should not be null or empty for all items
    And   The 'segment_type' field should be "music" for all items

  Scenario: User wants to verify the 'primary' field in 'title_list'
    Given User makes a GET request to 'https://testapi.io/api/rmstest/media'
    Then  The 'primary' field in 'title_list' should not be null or empty for any track

  Scenario: User wants to verify the 'now_playing' field
    Given User makes a GET request to 'https://testapi.io/api/rmstest/media'
    Then  The user verifies that the only one track should have 'now_playing' field in 'offset' as true

  Scenario: User wants to verify the "Date" value in response headers
    Given User makes a GET request to 'https://testapi.io/api/rmstest/media'
    Then The 'Date' value in response headers should be valid

