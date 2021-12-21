package com.base;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestContext {
    private DriverManager webDriverManager;
    private PageObjectManager pageObjectManager;
    private TestConfiguration testConfiguration;
    public ScenarioContext scenarioContext;
    private Logger logger;

    public TestContext() throws Exception {
        testConfiguration = new TestConfiguration();
        webDriverManager = new DriverManager(testConfiguration);
        pageObjectManager = new PageObjectManager(webDriverManager.createDriver(), testConfiguration);
        scenarioContext = new ScenarioContext();
        logger = LogManager.getLogger();
    }

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