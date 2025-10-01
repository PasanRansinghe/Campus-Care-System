Feature: Issue creation
  As a user
  I want to add a new issue
  So that I can track campus problems

  Scenario: Create a valid issue
    Given an issue titled "Window broken" in category "Broken" with status "Pending"
    When I submit the issue
    Then the issue should be saved successfully