package com.stepdefinition;

import com.base.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;

public class Hooks {
    private TestContext testContext;

    public Hooks(TestContext testContext) {
        this.testContext=testContext;
    }

    @After
    public void tearDown(Scenario scenario) throws Exception{
        try {
            testContext.getWebDriverManager().getScreenshot(scenario,testContext.getTestConfiguration().getUdid());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            testContext.getWebDriverManager().quitDriver();
        }catch (Exception e){
            e.printStackTrace();
        }
//        if(System.getProperty("os.name").contains("Windows")){
//            Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
//        }
    }

}
