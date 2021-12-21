package com.base;

import com.pages.HomePage;
import org.openqa.selenium.WebDriver;

public class PageObjectManager {

    public WebDriver driver;
    private TestConfiguration testConfiguration;

    public PageObjectManager(WebDriver driver,TestConfiguration configuration){
        this.driver=driver;
        this.testConfiguration=configuration;
    }

    private HomePage homePage;

    public HomePage getHomePage(){
        return (homePage==null)?homePage=new HomePage(driver,testConfiguration):homePage;
    }
}
