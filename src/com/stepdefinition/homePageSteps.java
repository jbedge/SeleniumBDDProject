package com.stepdefinition;

import com.base.TestConfiguration;
import com.base.TestContext;
import com.pages.HomePage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

public class homePageSteps {

    private HomePage homePage;
    private TestContext testContext;
    private TestConfiguration testConfiguration;

    public homePageSteps(TestContext testContext){
//        PropertyConfigurator.configure(System.getProperty("user.dir")+"\\resources\\log4j.properties");
        this.testContext=testContext;
        testConfiguration=testContext.getTestConfiguration();
        homePage =testContext.getPageObjectManager().getHomePage();
    }

    @Given("^I launch the URL$")
    public void i_am_on_google_page() throws Throwable {
        homePage.getURL();
    }

    @Given("^I start from last execution$")
    public void i_start_from_last_execution() throws Throwable {
        homePage.getURL();
    }

    @Given("^I extract all urls from homepage$")
    public void i_extractURL() throws Throwable {
        homePage.extractURLs();
    }

    @And("User fills in the required information fields for adding a contest in State")
    public void userFillsInTheRequiredInformationFieldsForAddingAContestInState(DataTable dataTable) throws Exception {
//        homePage.enterTextInInputFields(dataTable);
        List<Map<String, String>> data = dataTable.asMaps();
        WebElement input;
        // Iterate over each row in the DataTable
        for (Map<String, String> row : data) {
            String text = row.get("Text");
            String field = row.get("Field");
            String type = row.get("Type");
            System.out.println("Adding data for :"+field +" -> "+text);
            switch (type) {

                case "dropdown":
                    input = homePage.findElement(By.xpath("//label[normalize-space()= \"" + field + "\"]//following-sibling::*"));
                    if(input.getAttribute("class").contains("dropdownlist")){
                        input = homePage.findElement(By.xpath("//label[normalize-space()= \"" + field + "\"]//following-sibling::*[contains(@class,'dropdown')]//button"));
                        input.click();
                        homePage.waitfor(2);
                        WebElement loctext = homePage.findElement(By.xpath("//li[@role='option']//*[normalize-space()='" + text + "']"));
                        loctext.click();
                        homePage.waitfor(2);
                    }
                    else {
                        By loc = By.xpath("//label[normalize-space()= \"" + field + "\"]//following-sibling::select");
                        homePage.selectByVisibleText(loc, text);
                    }
                    break;

                case "input":
                    input = homePage.findElement(By.xpath("//label[contains(text(), '" + field + "')]/following-sibling::*"));
                    if(!input.getTagName().equals("input")){
                        input = homePage.findElement(By.xpath("//label[contains(text(), '" + field + "')]/following-sibling::*/*"));
                    }
                    input.click();
                    input.clear();
                    input.sendKeys(text);
                    break;

                default:
                    throw new RuntimeException("No element found->" + type);
            }
        }
    }


    @And("I load test {string} data")
    public void iLoadTestData(String arg0) throws Exception  {
        testConfiguration.loadJSONFile(arg0);
    }

    @Given("I Click on {string} Button")
    public void i_click_on_button(String string) throws Exception {
        homePage.clickOnButtonwithButtonTag(string);
    }

    @And("I wait for {int} sec to complete the background process")
    public void iWaitForSecToCompleteTheBackgroundProcess(int arg0) {
        homePage.waitfor(arg0*1000);
    }

    @And("I click on edit button")
    public void iClickOnEditButton() throws Exception{
        homePage.clickOnEdit();
    }

    @And("I search and selects {string}")
    public void iSearchAndSelects(String arg0) throws Exception{
        homePage.searchAndSelectDevice((String) testConfiguration.getJsonData(arg0));
    }

    @And("I navigate to home screen")
    public void iNavigateToHomeScreen() throws Exception {
        homePage.iNavigateToHomeScreen();
    }

    @And("I verify the {string} Added")
    public void iVerifyTheAdded(String arg0) throws Exception{
        homePage.verifyDeviceAdded((String) testConfiguration.getJsonData(arg0));
    }

    @And("I click on alert Ok button")
    public void iClickOnAlertOkButton() throws Exception{
        homePage.clickOnButtonwithButtonTag("Ok");
    }

    @And("I delete the added record")
    public void iDeleteTheAddedRecord() throws Exception{
        homePage.deleteAddedRecord();
    }

    @Then("I verify the {string} deleted")
    public void iVerifyTheDeleted(String arg0) throws Exception{
        homePage.iVerifyTheDeleted((String) testConfiguration.getJsonData(arg0));
    }

    @And("I click on blank space")
    public void iClickOnBlankSpace() throws Exception{
        homePage.clickOntheLabel();
    }

    @Then("I change the color of a device")
    public void iChangeTheColorOfADevice() throws Exception{
        homePage.iChangeTheColorOfADevice();
    }

    @Then("I verify the new color selected")
    public void iVerifyTheNewColorSelected() throws Exception{
        homePage.verifyTheColorDisplayed();
    }

    @And("i verify the list count before add")
    public void iVerifyTheCountBeforeAdd() throws Exception{
        homePage.iVerifyTheCountBeforeAdd();
    }

    @And("i verify the list count after add")
    public void iVerifyTheCountAfterAdd() throws Exception{
        homePage.iVerifyTheCountAfterAdd();
    }

    @Then("I verify the list displayed")
    public void iVerifyTheListDisplayed() throws Exception{
        homePage.iVerifyTheListDisplayed();
    }

    @Then("I modify the name with {string}")
    public void iModifyTheNameWith(String arg0)  throws Exception{
        homePage.iModifyTheName((String) testConfiguration.getJsonData(arg0));
    }

    @Then("I verify the list displayed {string}")
    public void iVerifyTheListDisplayed(String arg0) throws Exception{
        homePage.iVerifyTheListDisplayed((String) testConfiguration.getJsonData(arg0));
    }

    @And("I search and selects all matching devices with name {string}")
    public void iSearchAndSelectsAllMatchingDevicesWithName(String arg0) throws Exception{
        homePage.searchAndSelectAllmatchingDevices((String) testConfiguration.getJsonData(arg0));
    }

    @And("I search and selects {string} and verify data not displayed")
    public void iSearchAndSelectsAndVerifyDataNotDisplayed(String arg0) throws Exception{
        homePage.verifyDataNotDisplayed((String) testConfiguration.getJsonData(arg0));
    }
}