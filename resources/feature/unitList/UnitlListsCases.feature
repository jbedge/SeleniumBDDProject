
Feature: Search and select from unit list

  @TC001
  Scenario: Create new list
    Given I launch the URL
    And I load test "TC001.json" data
    And I click on alert Ok button
    And i verify the list count before add
    And I Click on "Skapa enhetslista" Button
    Then i verify the list count after add
    Then I verify the list displayed


#  @TC002
#  Scenario: Search and Add device in existing list
#    Given I launch the URL
#    And I load test "TC001.json" data
#    And I click on alert Ok button
#    And I click on edit button
#    And I search and selects "DeviceName2"
#    And I click on blank space

#  @TC003
#  Scenario: Rename existing list
#    Given I launch the URL
#    And I load test "TC001.json" data
#    And I click on alert Ok button
#    And I click on edit button
#    Then I modify the name with "newName"
#    And I navigate to home screen
#    Then I verify the list displayed "newName"
#
#  @TC004
#  Scenario: Add New Device
#    Given I launch the URL
#    And I load test "TC001.json" data
#    And I click on alert Ok button
#    And I click on edit button
#    And I search and selects "DeviceName"
#    And I navigate to home screen
#    Then I verify the "DeviceName" Added
#
#  @TC005
#  Scenario: Delete the List
#    Given I launch the URL
#    And I load test "TC001.json" data
#    And I click on alert Ok button
#    And I delete the added record
#    Then I verify the "DeviceName" deleted
#
#  @TC006
#  Scenario: Add Multiple Devices in the list
#    Given I launch the URL
#    And I load test "TC001.json" data
#    And I click on alert Ok button
#    And I Click on "Skapa enhetslista" Button
#    And I click on edit button
#    And I search and selects "DeviceName"
#    And I search and selects "DeviceName2"
#    And I navigate to home screen
#    Then I verify the "DeviceName" Added
#    Then I verify the "DeviceName2" Added
#
#  @TC007
#  Scenario: Change the color of the device
#    Given I launch the URL
#    And I load test "TC001.json" data
#    And I click on alert Ok button
#    And I Click on "Skapa enhetslista" Button
#    And I click on edit button
#    And I search and selects "DeviceName"
#    And I click on blank space
#    Then I change the color of a device
#    Then I verify the new color selected
#
#  @TC008
#  Scenario: select all alike devices
#    Given I launch the URL
#    And I load test "TC001.json" data
#    And I click on alert Ok button
#    And I Click on "Skapa enhetslista" Button
#    And I click on edit button
#    And I search and selects all matching devices with name "DeviceName"
#    And I click on blank space
#    And I navigate to home screen
#    Then I verify the "DeviceName" Added
#
#  @TC009
#  Scenario: Verify non existing unit in the list
#    Given I launch the URL
#    And I load test "TC001.json" data
#    And I click on alert Ok button
#    And I Click on "Skapa enhetslista" Button
#    And I click on edit button
#    And I search and selects "DeviceName3" and verify data not displayed