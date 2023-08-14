package com.base;


import io.cucumber.java.Scenario;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.joda.time.LocalDate;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;




public class UIAction implements Action {

    private static final String DEFAULT_DOWNLOAD_DIRECTORY = "";
    Logger log = LoggerFactory.getLogger(UIAction.class);
    public WebDriver driver;
    private final TestConfiguration config;
    private WebDriverWait wait;
    private WebDriverWait waitWithZeroPollingTime;
    private WebElement webElement;
    private By alltext=By.xpath("//*[normalize-space(text()) and not(ancestor-or-self::*[contains(@style, 'display:none') or contains(@style, 'display: none')])]");

    public synchronized JavascriptExecutor getJs() {
        return js;
    }

    public void setJs(WebDriver webDriver) {
        this.js =  (JavascriptExecutor) webDriver;;
    }

    public WebElement findLocatorbytext(String textToMatch){
        AtomicReference<WebElement> loc = new AtomicReference<>();

        Optional<WebElement> firstMatchingElement = findElements(alltext).stream()
                .filter(element -> element.getText().trim().equals(textToMatch))
                .findFirst();
        firstMatchingElement.ifPresent(element -> {
            log.info("Found matching element with text: " + element.getText());
            loc.set(element);
        });
        return loc.get();
    }

    public List<WebElement> findAllMatchingLocatorbytext(String textToMatch){
        return findElements(alltext).stream()
                .filter(element1 -> element1.getText().trim().equals(textToMatch))
                .collect(Collectors.toList());
    }

    public List<String> getTextAll(By loc){
        return findElements(loc).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    private JavascriptExecutor js;

    public WebElement getElement() {
        return webElement;
    }

    public void setElement(WebElement element) {
        this.webElement = element;
    }


    public UIAction(TestContext context) {
        this.driver = context.getDriver();
        this.setJs(driver);
        this.config = context.getTestConfiguration();
//        wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.PAGE_TIME), Duration.ofSeconds(Constants.POLL_TIME));
//        waitWithZeroPollingTime = new WebDriverWait(driver, Duration.ofSeconds(Constants.PAGE_TIME), Duration.ofSeconds(0));
    }



    public void highlight(WebElement element) {
        getJs().executeScript("arguments[0].style.border='solid 2px orange'", element);
    }

    public String highlightAndGetText(WebElement element) {
        getJs().executeScript("arguments[0].style.border='solid 2px orange'", element);
        return element.getText().trim();
    }

    public String getText(By element) {
        try {
            WebElement webElement=waitForVisibilityOfElement(element);
            return webElement.getText().trim();
        }
        catch (StaleElementReferenceException e){
            webElement=waitForVisibilityOfElement(element);
            return webElement.getText().trim();
        }
    }

    public void getURL(String url){
        try {
            driver.manage().window().maximize();
            driver.get(url);
            this.waitForPageToLoad();
        }
        catch (TimeoutException e){
            driver.get(url);
            waitForPageToLoad();
        }
    }


    public  String getDateAndTime(String pattern){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone("EST"));
        String dateTime =sdf.format(calendar.getTime());
        return dateTime;
    }

    public static void main(String[] args) {
        String da=getDateAndTime("Mmm/d/yyyy",2);
        System.out.println();
    }

    public static String getDateAndTime(String format,int plusDays){
        Date date= LocalDate.now().plusDays(plusDays).toDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("EST"));
        String dateTime =sdf.format(calendar.getTime());
        return dateTime;
    }
    
    public void addScreenshot(){
        Scenario scenario=config.getScenario();
        byte[] img = (byte[]) ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        scenario.attach(img, "image/jpeg",scenario.getName());
    }
        

    public void selectDropDownWithDefaultValue(By locator) {
        waitFor(3);
        Random random=new Random();
        WebElement element=waitForVisibilityOfElement(locator);
        Select select=new Select(element);
        List<WebElement> opts=select.getOptions();
        opts.remove(0);
        int size=opts.size();
        int index=random.nextInt(size);
        config.getScenario().log("value selected from dropdown is : "+opts.get(index).getText());
        select.selectByVisibleText(opts.get(index).getText());
    }

    public void setText(By locator, CharSequence... value) {
        waitForVisibilityOfElement(locator).sendKeys(value);
    }

    public void clearText(By locator) {
        WebElement element=waitForVisibilityOfElement(locator);
        element.clear();
        element.sendKeys(Keys.CONTROL+"a"+Keys.BACK_SPACE);
    }

    public void selectDropDown(By locator, String  visibleText) {
        WebElement element=waitForVisibilityOfElement(locator);
        Select select=new Select(element);
        select.selectByVisibleText(visibleText);
    }

    public void selectDropDown(By locator) {
        Random random=new Random();
        WebElement element=waitForVisibilityOfElement(locator);
        Select select=new Select(element);
        int size=select.getOptions().size();
        int index=random.nextInt(size);
        select.selectByIndex(index);
    }

    public void selectDropDown(WebElement webElement) {
        Random random=new Random();
        Select select=new Select(webElement);
        int size=select.getOptions().size();
        int index=random.nextInt(size);
        select.selectByIndex(index);
    }

    public String getDefaultDropDownValue(By locator) {
        Random random=new Random();
        WebElement element=waitForVisibilityOfElement(locator);
        Select select=new Select(element);
        return select.getFirstSelectedOption().getText();
    }

    public void setTextUsingJS(By locator, CharSequence... value) {
        getJs().executeScript("arguments[0].value='"+value+"';", waitForPresenceOfElement(locator));
    }

    public void setText(By locator, Keys value) {
        waitForVisibilityOfElement(locator).sendKeys(value);
    }
    static int cnt=0;
    WebElement element;
    @Override
    public WebElement waitForVisibilityOfElement(By loc) {
        try {
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
            highlight(element);
            return element;
        } catch (StaleElementReferenceException e) {
            waitForVisibilityOfElement(loc);
            return element;
        } catch (TimeoutException e) {
            element = findElement(loc);
            return element;
        } catch (Exception e) {
            
            commonExceptions(e);
            return null;
        }
    }

    public WebElement waitForVisibilityOfElementWithZeroPollingTime(By loc) {
        WebElement element;
        try {
            element = waitWithZeroPollingTime.until(ExpectedConditions.visibilityOfElementLocated(loc));
            highlight(element);
            return element;
        } catch (TimeoutException e) {
            element = findElement(loc);
            return element;
        } catch (Exception e) {
            
            commonExceptions(e);
            return null;
        }
    }


    @Override
    public WebElement waitForVisibilityOfElement(By loc, long timeoutInSec) {
        WebElement element;
        try {
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
            highlight(element);
            return element;
        } catch (TimeoutException e) {
            element = findElement(loc);
            return element;
        } catch (Exception e) {
            
            commonExceptions(e);
            return null;
        }
    }

    @Override
    public WebElement waitForPresenceOfElement(By loc) {
        WebElement element;
        try {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(loc));
            highlight(element);
            return element;
        } catch (TimeoutException e) {
            element = findElement(loc);
            return element;
        } catch (Exception e) {
            
            commonExceptions(e);
            return null;
        }
    }

    @Override
    public WebElement waitForPresenceOfElement(By loc, long timeoutInSec) {
        WebElement element;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSec), Duration.ofSeconds(Constants.POLL_TIME));
            element = wait.until(ExpectedConditions.presenceOfElementLocated(loc));
            highlight(element);
            return element;
        } catch (TimeoutException e) {
            element = findElement(loc);
            return element;
        } catch (Exception e) {
            
            commonExceptions(e);
            return null;
        }
    }

    public boolean isElementPresence(By loc, long timeoutInSec) {
        try {
            clearImplicitWait(driver);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSec), Duration.ofSeconds(Constants.POLL_TIME));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(loc));
            setElement(element);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
        finally {
            resetImplicitWait(driver);
        }
    }

    public boolean isElementVisible(By loc, long timeoutInSec) {
        try {
            clearImplicitWait(driver);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSec), Duration.ofSeconds(Constants.POLL_TIME));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
            highlight(element);
            setElement(element);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
        finally {
            resetImplicitWait(driver);
        }
    }

    public void clearImplicitWait(WebDriver driver){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    }

    public void resetImplicitWait(WebDriver driver){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT));
    }

    WebElement popup;
    public boolean isElementPresenct1(By loc, long timeoutInSec) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSec), Duration.ofSeconds(Constants.POLL_TIME));
        try {
            popup = wait.until(ExpectedConditions.presenceOfElementLocated(loc));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public WebElement findElement(By loc) {
        try {
            
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
            return driver.findElement(loc);
        } catch (Exception e) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT));
            config.getScenario().log("Locator value:" + loc);
            commonExceptions(e);
            throw e;
        }
    }

    @Override
    public List<WebElement> findElements(By loc) {
        try {
            
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
            return driver.findElements(loc);
        } catch (Exception e) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT));
            config.getScenario().log("Locator value:" + loc);
            commonExceptions(e);
            throw e;
        }
    }


    public void verifyElementDisplayed(By loc, String log) {
        waitForVisibilityOfElement(loc);
        config.getScenario().log(log);
    }

    public void waitForFileToDownload(){
        File dir = new File(DEFAULT_DOWNLOAD_DIRECTORY.toString());
        File[] dirContents = dir.listFiles();
//        int size=dirContents.length;
        int size=0;
        boolean isDownloading = false;
        int cnt=0;
        while ((size<1)&&(cnt<15)){
            waitFor(1);
            dirContents = dir.listFiles();
            if(dirContents.length>=1){
                isDownloading = Arrays.stream(dirContents).anyMatch(i->(i.getName().contains(".crdownload")||i.getName().contains(".tmp")));
                log.info("file is downloading .."+dirContents[0]);
            }
            if(isDownloading){
                size=0;
            }
            else {
                size=dirContents.length;
            }
            cnt++;
        }
        waitFor(2);
        log.info("File downloaded successfully.");
    }

    public void verifyElementDisplayedWithZeroPollingTime(By loc, String log) {
        waitForVisibilityOfElementWithZeroPollingTime(loc);
        config.getScenario().log(log);
    }

    public void verifyElementDisplayed1(By loc, String log) {
        waitForVisibilityOfElement(loc);
    }

    public void assertTrue(boolean isDisplayed,String msg){
        try {
            Assert.assertTrue(isDisplayed);
            config.getScenario().log(msg);
        }
        catch (Exception e){
            config.getScenario().log(e.toString());
        }

    }

    public String getAttribute(By locator, String attribute){
        WebElement element = findElement(locator);
        return element.getAttribute(attribute).trim();
    }

    @Override
    public void click(By loc) {
       try {
           waitForVisibilityOfElement(loc).click();
       }
       catch (ElementClickInterceptedException | StaleElementReferenceException e){
           clickUsingJS(loc);
       }
    }

    public void moveSliderToPosition(By by, int percentage) {
        WebElement Slider = findElement(by);
        Actions slider = new Actions(driver);
        slider.dragAndDropBy(Slider,percentage,0).build().perform();
    }
    public WebElement mouseHover(By by) {
        WebElement element = findElement(by);
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();
        return element;
    }

    @SneakyThrows
    public void clearDownloadDirectory(){
        File dir = new File(DEFAULT_DOWNLOAD_DIRECTORY.toString());
        FileUtils.cleanDirectory(dir);
    }

    public void retryClick(By loc) {
        waitForVisibilityOfElement(loc).click();
        waitFor(1);
        if(isElementVisible(loc,6)){
            click(loc);
        }
    }

    public static File getLastModified(String directoryFilePath) {
        File directory = new File(directoryFilePath);
        File[] files = directory.listFiles(File::isFile);
        long lastModifiedTime = Long.MIN_VALUE;
        File chosenFile = null;
        if (files != null) {
            for (File file : files) {
                if (file.lastModified() > lastModifiedTime) {
                    chosenFile = file;
                    lastModifiedTime = file.lastModified();
                }
            }
        }
        return chosenFile;
    }

    public void selectCheckBox(By loc) {
        WebElement element=waitForVisibilityOfElement(loc);
        if(!element.isSelected()){
            element.click();
        }
    }

    public void selectAllCheckBox(By loc) {
        waitForVisibilityOfElement(loc);
        List<WebElement> webElements=findElements(loc);
        for (WebElement element:webElements){
            highlight(element);
            element.click();
        }

    }

    @Override
    public void clickUsingJS(By loc) {
        getJs().executeScript("arguments[0].click();", waitForPresenceOfElement(loc));
    }

    public void executeScript(String script) {
        getJs().executeScript(script);
    }

    public void executeScript(String script,String arg) {
        getJs().executeScript(script,arg);
    }

    @Override
    public void clickUsingAction(By loc) {
        Actions actions = new Actions(driver);
        actions.moveToElement(waitForVisibilityOfElement(loc)).click().build().perform();
    }


    public void setTextUsingAction(By loc,CharSequence... keys) {
        Actions actions = new Actions(driver);
        actions.moveToElement(waitForVisibilityOfElement(loc)).sendKeys(keys).build().perform();
    }

    public void doubleClick(By loc) {
        Actions actions = new Actions(driver);
        actions.moveToElement(waitForVisibilityOfElement(loc)).doubleClick().build().perform();
    }

    public void scrollToElement(By loc) {
        Actions actions = new Actions(driver);
        actions.moveToElement(waitForVisibilityOfElement(loc)).build().perform();
    }

    public void scrollTobottom() {
        String scrollToBottom="window.scrollTo(0, document.body.scrollHeight);";
        getJs().executeScript(scrollToBottom);
    }

    public void scrollToTop() {
        String scrollUp="document.body.scrollTop = document.documentElement.scrollTop = 0;";
        getJs().executeScript(scrollUp);
    }


    public void scrollElementJS(By loc) {
        String scrollUp="document.body.scrollTop = document.documentElement.scrollTop = 0;";
//        getJs().executeScript(scrollUp);
//        int Y =waitForPresenceOfElement(loc).getLocation().getY();
//        String script=String.format("window.scrollBy(0,%s)",Y-40);
//        getJs().executeScript(script);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoViewIfNeeded();", waitForPresenceOfElement(loc));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", waitForPresenceOfElement(loc));
        waitFor(1);
    }

    public void scrollToElementJS(By locator)
    {
        String script =
                "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                        + "var elementTop = arguments[0].getBoundingClientRect().top;"
                        + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(script, waitForPresenceOfElement(locator));
    }

    public void scrollClick(By locator)
    {
        String script =
                "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                        + "var elementTop = arguments[0].getBoundingClientRect().top;"
                        + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(script, waitForPresenceOfElement(locator));
        click(locator);
    }

    public void scrollWindow(By loc) {
        String scrollUp="document.body.scrollTop = document.documentElement.scrollTop = 0;";
        getJs().executeScript(scrollUp);
        int Y =waitForPresenceOfElement(loc).getLocation().getY();
        String script=String.format("window.scrollBy(0,%s)",Y);
        getJs().executeScript(script);
        waitFor(1);
    }


    public void quitDriver() {
        driver.quit();
    }

    public void closeDriver() {
        driver.close();
    }

    public String getValue(By locator) {
        WebElement element = findElement(locator);
        String value = element.getAttribute("value");
        return value;
    }

    public boolean isElementPresent(By loc, long timeoutInSec) {
        try {
            clearImplicitWait(driver);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSec), Duration.ofSeconds(Constants.POLL_TIME));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(loc));
            setElement(element);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
        finally {
            resetImplicitWait(driver);
        }
    }

    public void selectDropDownByIndex(By locator) {
        Random random=new Random();
        WebElement element=waitForVisibilityOfElement(locator);
        Select select=new Select(element);
        select.selectByIndex(1);
    }

    @Override
    public void waitForPageToLoad() {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
            }
        };
        try {
            log.info("waiting for page load...");
            wait.until(expectation);
        } catch (Exception e) {
            throw e;
        }
    }


    @Override
    public  void waitFor(double sec) {
        try {
            if (sec < 0 || sec > 500) {
                throw new IllegalArgumentException("Wait is specified is greater than 500 sec.");
            }
            log.info("waiting for "+(long) (sec*1000)+" sec...");
            Thread.sleep((long) (sec*1000));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getViewMethodNameInFailedTestScripts() {
        try {
            StackTraceElement[] viewMethodName = Thread.currentThread().getStackTrace();
            for (int k = 0; k < viewMethodName.length - 1; k++) {
                String method = Thread.currentThread().getStackTrace()[k].getMethodName();
                if (method.startsWith("RMID")) {
                    method = Thread.currentThread().getStackTrace()[k - 1].getMethodName();
                    return method;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Failed method not found.";
    }

    public void switchToIframe(By locator) {
        driver.switchTo().frame(waitForPresenceOfElement(locator));
    }

    public void switchToDefault() {
        driver.switchTo().defaultContent();
    }

    @SneakyThrows
    public void commonExceptions(Exception e) {
        int target=0;
        try {
            StackTraceElement[] viewMethodName = Thread.currentThread().getStackTrace();
            for (int i=0;i<viewMethodName.length;i++){
                if(viewMethodName[i].getClassName().contains("step")){
                    target=i;
                    break;
                }
            }
            config.getScenario().log("Failed methods :" + viewMethodName[target].getFileName() +" "+ viewMethodName[target].getMethodName());
            config.getScenario().log(viewMethodName[target-1].getFileName() + " " + viewMethodName[target-1].getMethodName());
            config.getScenario().log(viewMethodName[target-2].getFileName() + " " + viewMethodName[target-2].getMethodName());
            config.getScenario().log(viewMethodName[target-3].getFileName() + " " + viewMethodName[target-3].getMethodName());
            String exception=e.toString().substring(0, 250).split(":")[0];
            config.getScenario().log("Exception :" + exception);
            throw e;
        } catch (Exception exception) {
            throw exception;
        }
    }

    public void switchToNewWindow(String title) {
        waitForPageToLoad();
        if(driver.getTitle().equals(title)){
            return;
        }
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        Set<String> winids=driver.getWindowHandles();
        for (String winid:winids){
            if(getTitle(winid).equals(title)){
                return;
            }
        }
    }

    public String getTitle(String windID){
        driver.switchTo().window(windID);
        return driver.getTitle();
    }

    public void verifyNewTabDisplayedWithCreateAccountForm() {
//        switchToNewWindow(CREATE_ACCOUNT_TITLE.toString());
//        By hdrCreateAccount=H6_Header.setValue(HEADER_CREATE_ACCT.toString()).getLocator();
//        verifyElementDisplayed(hdrCreateAccount,"Header displayed successfully :"+HEADER_CREATE_ACCT);
    }

    public void verifyNewTabDisplayedWithSignIn(String title) {
        switchToNewWindow(title);
    }


    public void verifyPopUp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    getJs().executeScript("async function getLocator(text){\n" +
                            "    while(true){\n" +
                            "    var expLoc;\n" +
                            "    await new Promise(r=>setTimeout(r,200));\n" +
                            "    var loc=document.querySelectorAll('button');\n" +
                            "    for (let index = 0; index < loc.length ; index++) {\n" +
                            "//    console.log(loc[index].getAttribute('id'))\n" +
                            "       if(loc[index].getAttribute('id')===text){\n" +
                            "           expLoc=loc[index];\n" +
                            "           expLoc.click();\n" +
                            "           console.log(loc[index].getAttribute('id'))\n" +
                            "           break;\n" +
                            "    }\n" +
                            "  }\n" +
                            " }\n" +
                            "};\n" +
                            "\n" +
                            "getLocator('ip-no');");
                }
            }
        }).start();
        System.out.println("5.....");
    }

//    public void verifyPopUp1(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    getJs().executeScript("function myFunc() {\n" +
//                            "  var popup=document.querySelector(\"#ip-no\");\n" +
//                            "  if (popup ==null) {\n" +
//                            "  } else {\n" +
//                            "    popup.click();\n" +
//                            "  }\n" +
//                            "};\n" +
//                            "myFunc();");
//                }
//            }
//        }).start();
//        System.out.println("5.....");
//    }

    private By popupYes=By.xpath("//*[@id=\"ip-no\"]");
    private By iframe=By.xpath("//*[@id=\"iPerceptionsFrame\"]");
    public boolean selectPopUp(){
        if(isElementPresenct1(popupYes,2)){
            popup.click();
            return true;
        }
        return false;
    }

}