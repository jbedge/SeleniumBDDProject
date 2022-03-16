package com.pages;

import com.base.ActionMethods;
import com.base.TestConfiguration;
import com.base.UIElement;
import com.base.UILocatorType;
import com.utilities.CSVWriterClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.utilities.CSVWriterClass.createCSVFIle;

public class HomePage extends ActionMethods {

    private WebDriver driver;
    private TestConfiguration testConfig;
//    Logger logger= LogManager.getLogger();
    Logger logger= LogManager.getLogger(this.getClass().getCanonicalName());

    public HomePage(WebDriver driver, TestConfiguration configuration) {
        super(driver, configuration);
        this.driver = driver;
        this.testConfig = configuration;
    }

    private By expandLeft=By.xpath("//a[@icon='expandLeft']");
    private By editIcon=By.xpath("//h2[starts-with(text(),'Osparad lista')]");
    private By inpSearchDevice=By.xpath("//*[@id='select-organizations-input']");
    private By colorChange=By.xpath("//*[starts-with(@id,\"select-color-for\")]");
    private By newColor=By.xpath("//*[@color=\"#BF3E05\"]");
    private By confirmPopUp=By.xpath("//*[@id=\"leave-popup\"]");
    private By list=By.xpath("//a[@icon='pen']");
    private By newlist=By.xpath("//h2[starts-with(text(),'Min lista')]");
    private By inplistName=By.xpath("//*[@id=\"unitlist-name\"]");


    public synchronized void  getURL() throws Exception {
        logger.info("Started:getURL");
        driver.manage().window().maximize();
        driver.get(testConfig.getcurrentURL());
        waitForPageLoad();
        logger.info("Completed:getURL");
    }

    public void closeCurrentWindow() throws Exception{
        driver.close();
    }

    public void clickOnButtonwithButtonTag(String btnname) throws Exception {
        By btn=By.xpath("//button[normalize-space()='"+btnname+"']");
        waitForVisibilityOfElement(btn);
        click(btn);
    }

    public void clickOnEdit() throws Exception{
        waitForVisibilityOfElement(editIcon);
        click(editIcon);
    }


    public void searchAndSelectDevice(String deviceName) throws Exception{
        try {
            waitForVisibilityOfElement(inpSearchDevice);
            clearAndEnterValue(inpSearchDevice,deviceName);
            By devicename=By.xpath("//label[text()='"+deviceName+"']//preceding-sibling::input");
            waitForVisibilityOfElement(devicename);
            if(!isElementSelected(devicename)){
                click(devicename);}
        }
        catch (Exception e){
            Assert.assertTrue(false,"This_unit_doesn’t_exist");
        }
    }

    public void verifyDataNotDisplayed(String deviceName) throws Exception{
        try {
            waitForVisibilityOfElement(inpSearchDevice);
            clearAndEnterValue(inpSearchDevice,deviceName);
            By devicename=By.xpath("//label[text()='"+deviceName+"']//preceding-sibling::input");
            waitForVisibilityOfElement(devicename);
            if(!isElementSelected(devicename)){
                click(devicename);}
            Assert.assertTrue(false,"This_unit_doesn’t_exist");
        }
        catch (Exception e){
            Assert.assertTrue(true,"This_unit_doesn’t_exist");
        }
    }

    public void searchAndSelectAllmatchingDevices(String deviceName) throws Exception{
        waitfor(5000);
        waitForVisibilityOfElement(inpSearchDevice);
        enterValue(inpSearchDevice,deviceName);
        waitfor(5000);
        By devicename=By.xpath("//label[contains(text(),'"+deviceName+"')]//preceding-sibling::input");
        List<WebElement> list=findElements(devicename);
        for (WebElement ele:list){
            waitfor(2);
            if (!ele.isSelected()) {
                ele.click();
            }
        }
    }



    public void iNavigateToHomeScreen()  throws Exception{
        waitForVisibilityOfElement(expandLeft);
        click(expandLeft);
    }

    public void verifyDeviceAdded(String data) throws Exception{
        By loc=By.xpath("//p[text()='"+data+"']");
        waitForVisibilityOfElement(loc);
    }

    public void deleteAddedRecord() throws Exception{
        By loc=By.xpath("(//button[contains(@aria-label,'Ta bort')])[last()]");
        waitForVisibilityOfElement(loc);
        click(loc);
    }

    public void iVerifyTheDeleted(String data) throws Exception{
        By loc=By.xpath("//p[text()='"+data+"']");
        waitForInvisibilityOfElement(loc);
    }

    public void clickOntheLabel() throws Exception{
        By loc=By.xpath("//label[text()='Namnet på listan']");
        waitForVisibilityOfElement(loc);
        click(loc);
    }



    public void iChangeTheColorOfADevice() throws Exception{
        waitForVisibilityOfElement(colorChange);
        click(colorChange);
        waitForVisibilityOfElement(newColor);
        click(newColor);
//        waitForVisibilityOfElement(confirmPopUp);
//        click(confirmPopUp);
    }

    public void verifyTheColorDisplayed() throws Exception{
        waitForVisibilityOfElement(newColor);
    }
    private int before ;
    private int after ;

    public void iVerifyTheCountBeforeAdd() throws Exception{
        waitForVisibilityOfElement(list);
        before=findElements(list).size();
    }

    public void iVerifyTheCountAfterAdd() throws Exception{
        waitForVisibilityOfElement(list);
        after =findElements(list).size();
        Assert.assertEquals(before+1,after,"failed to add new list");
    }

    public void iVerifyTheListDisplayed() throws Exception{
        waitForVisibilityOfElement(newlist);
    }

    public void iVerifyTheListDisplayed(String name) throws Exception{
        By newlist=By.xpath("//h2[text()='"+name+"']");
        waitForVisibilityOfElement(newlist);
    }

    public void iModifyTheName(String jsonData) throws Exception{
        waitForVisibilityOfElement(inplistName);
        clearAndEnterValue(inplistName,jsonData);
    }

    public void extractURLs() throws Exception{
        logger.info("started:extractURLs");
        System.out.println(driver.getCurrentUrl());
        String url;
        String title;
        String description;
        By allurls=By.xpath("//a");
        UIElement signIn=new UIElement(UILocatorType.xpath,"//a[normalize-space()='Sign in']");
        driver.findElement(findBy(signIn)).click();
        List<WebElement> webElements=driver.findElements(By.tagName("a"));
        String filename="TestData/BBCData"+getDateAndTime()+".csv";
        CSVWriterClass.createCSVFIle(filename,new String[]{"URL","Title","Description"});
        for(WebElement el:webElements){
            logger.info("Extracting data...");
            url=el.getAttribute("href");
            title=el.getText();
//            List<WebElement> eleDesc=el.findElements(By.xpath("ancestor::div[@class='media__content']//p"));
            List<WebElement> eleDesc=el.findElements(By.xpath("following::p"));
            description=(eleDesc.size()>0?eleDesc.get(0).getText():"NA");
            CSVWriterClass.addRowsinCSV(new String[]{url,title,description});
            break;
        }
        logger.info("completed:extractURLs");
    }

    public static String getDateAndTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH_mm");
        sdf.setTimeZone(TimeZone.getTimeZone("JST"));
        String dateTime =sdf.format(calendar.getTime());
        return dateTime;
    }
}