package com.base;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class TestContext {
    private DriverManager webDriverManager;
    private PageObjectManager pageObjectManager;
    private TestConfiguration testConfiguration;
    public ScenarioContext scenarioContext;
    private Logger logger;
    private WebDriver driver;

    public TestContext() throws Exception {
        testConfiguration = new TestConfiguration();
        webDriverManager = new DriverManager(testConfiguration);
        driver = webDriverManager.createDriver();
        pageObjectManager = new PageObjectManager(driver, testConfiguration);
        scenarioContext = new ScenarioContext();
        logger = LogManager.getLogger();
        PropertyManager.initializePropertyManager();
    }

    public WebDriver getDriver(){return driver;}
    public TestConfiguration  getTestConfiguration() {
        return testConfiguration;
    }
    public DriverManager getWebDriverManager() {
        return webDriverManager;
    }
    public PageObjectManager getPageObjectManager() {
        return pageObjectManager;
    }
    public ScenarioContext getScenarioContext() {
        return scenarioContext;
    }
    public Logger getLogger() {
        return logger;
    }

}