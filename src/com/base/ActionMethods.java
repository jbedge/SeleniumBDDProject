package com.base;


import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Reporter;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class ActionMethods  {
    public WebDriver driverObj;
    private TestConfiguration testConfiguration;
    private String winHandleBefore;


    public ActionMethods (WebDriver driver,TestConfiguration testConfiguration) {
        this.driverObj = driver;
        this.testConfiguration=testConfiguration;
    }
    protected Logger logger = LogManager.getLogger();

    public void windowMinimize() throws Exception {
        driverObj.manage().window().setPosition(new Point(-2000, 0));

    }

    protected By findBy(UIElement element) throws Exception{
        By locator=null;
        switch (element.getLocatorType()){
            case id:
                locator=By.id(element.getLocator());
                break;
            case css:
                locator=By.cssSelector(element.getLocator());
                break;
            case xpath:
                locator= By.xpath(element.getLocator());
                break;
            case text:
                locator=By.partialLinkText(element.getLocator());
                break;
            case name:
                locator=By.name(element.getLocator());
                break;
            default:
                throw new  Exception("Locator type is invalid");
        }
        return locator;
    }

    private By findBy(By locator) throws Exception {
        try {
            return locator;
        } catch (Exception e) {
            logger.error("findBy method execution failed: " + e.getMessage());
            throw e;
        }
    }
    public void quitDriver() {
        driverObj.quit();
    }

    public void closeDriver() {
        driverObj.close();
    }

    public void goToUrl(String url) throws Exception {
        try {
            logger.info("goToUrl method execution started");
            driverObj.get(url);
            waitForPageLoad();
            logger.info("goToUrl findBy method execution completed");
        } catch (Exception e) {
            logger.error("goToUrl method execution failed: " + e.getMessage());
            throw e;
        }
    }

    public boolean retryingFindClick(By by) {
        boolean result = false;
        int attempts = 0;
        while(attempts < 2) {
            try {
                driverObj.findElement(by).click();
                result = true;
                break;
            } catch(StaleElementReferenceException e) {
            }
            attempts++;
        }
        return result;
    }

    public void click(By locator) throws Exception {
        try {
            logger.info("Started Click method execution");
            findElement(locator).click();
            logger.info("Successfully Clicked on Element - " + locator);
            Thread.sleep(500);
        } catch (Exception e) {
            logger.error("Failed to Click on Element - " + locator + e.getMessage());
            throw e;
        }
    }


    public void waitAndClick(By locator) throws Exception {
        try {
            logger.info("Started waitAndClick method execution");
            try {
                waitOnlocator(locator);
            }catch (Exception e){}
            scrollDown(locator);
            driverObj.findElement(this.findBy(locator)).click();
            waitForPageLoad();
            logger.info("Successfully Clicked on Element - " + locator);
        } catch (Exception e) {
            logger.error("Failed to Click on Element - " + locator + e.getMessage());
            throw e;
        }
    }
    public void waitAndClickWithJavaScriptExecutor(By locator) throws Exception {
        try {
            try {
                waitOnlocator(locator);
            }
            catch (Exception e){}
            scrollDown(locator);
            JavascriptExecutor js = (JavascriptExecutor) driverObj;
            js.executeScript("arguments[0].click();", driverObj.findElement(findBy(locator)));
        } catch (Exception e) {
            logger.error("Failed to Click on Element - " + locator + e.getMessage());
            throw e;
        }
    }

    public int getXcoordinate(By locator) throws Exception {
        Point p = driverObj.findElement(findBy(locator)).getLocation();
        return p.getX();
    }

    public int getYcoordinate(By locator) throws Exception {
        Point p = driverObj.findElement(findBy(locator)).getLocation();
        return p.getY();
    }

    public void waitAndClickWithJavaScriptExecutor(WebElement locator) throws Exception {
        try {
            scrollDown(locator);
            JavascriptExecutor js = (JavascriptExecutor) driverObj;
            js.executeScript("arguments[0].click();", locator);
        } catch (Exception e) {
            logger.error("Failed to Click on Element - " +e.getMessage());
            throw e;
        }
    }
    public void scrollDown(By locator) throws Exception{
        JavascriptExecutor js = (JavascriptExecutor) driverObj;
        js.executeScript("arguments[0].scrollIntoView(true);", findElement(locator));
    }

    public void scrollDownUsingJS() throws Exception{
        JavascriptExecutor js = (JavascriptExecutor) driverObj;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(1000);
    }

    public void scrollIntoViewIfNeeded(By locator) throws Exception{
        JavascriptExecutor js = (JavascriptExecutor) driverObj;
        js.executeScript("document.querySelector(\"td\").scrollIntoViewIfNeeded();");
        Thread.sleep(1000);
    }

    public void scrollDown(WebElement locator) throws Exception{
        JavascriptExecutor js = (JavascriptExecutor) driverObj;
        js.executeScript("arguments[0].scrollIntoView();", locator);

    }

    public void fireChangeEvent(By locator) throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driverObj;
        js.executeScript("$(arguments[0]).change();", driverObj.findElement(this.findBy(locator)));
    }


    public void clickRadioButton(By locator) throws Exception {
        WebElement element;
        try {
            logger.info("Started clickRadioButton method execution");
            element = driverObj.findElement(this.findBy(locator));
            if(!element.isSelected()) {
                element.click();
                logger.info("Successfully Clicked on Radio Button - " + locator);
            } else
                logger.info("Already clicked Radio Button - " + locator);
        } catch (Exception e) {
            logger.error("Failed to Click on Radio Button " + locator + e.getMessage());
            throw e;
        }
    }

    public void uncheckCheckbox(By locator) throws Exception {
        logger.info("Started uncheckCheckbox method execution");
        WebElement element = driverObj.findElement(this.findBy(locator));
        try {
            if (element.getAttribute("checked") != null) {
                element.click();
                logger.info("Successfully Unchecked Checkbox - " + locator);
            } else
                logger.info("Checkbox was allready Unchecked - " + locator);
        } catch (Exception e) {
            logger.error("Failed to Click on Element - " + locator + e.getMessage());
            throw e;
        }
    }

    public void checkCheckbox(By locator) throws Exception {
        logger.info("Started checkCheckbox method execution");
        WebElement element = driverObj.findElement(this.findBy(locator));
        try {
            if (element.getAttribute("checked") == null) {
                waitAndClickWithJavaScriptExecutor(locator);
                logger.info("Successfully checked Checkbox - " + locator);
            } else
                logger.info("Checkbox was already checked - " + locator);
        } catch (Exception e) {
            logger.error("Failed to Click on Element - " + locator + e.getMessage());
            throw e;
        }

    }

    public boolean isVerticalScrollBarDisplayed() throws Exception {
        try {
            logger.info("Started isVerticalScrollBarDisplayed method execution");
            JavascriptExecutor javascript = (JavascriptExecutor) driverObj;
            boolean VertscrollStatus = (boolean) javascript.executeScript("return document.documentElement.scrollHeight>document.documentElement.clientHeight;");
            logger.info("isVerticalScrollBarDisplayed method execution completed");
            return VertscrollStatus;
        } catch (Exception e) {
            logger.error("checkScrollBar method execution failed - " + e.getMessage());
            return false;
        }
    }

    public void enterValue(By locator, CharSequence... value) throws Exception {
        try {
            logger.info("Started enterValue method execution");
            waitForVisibilityOfElement(locator);
            findElement(locator).sendKeys(value);
            Thread.sleep(500);
            logger.info("Successfully Entered" + value + "in Textbox - " + locator);
        } catch (Exception e) {
            logger.error("Failed to find Element - " + locator + "in enterValue method" + e.getMessage());
            throw e;
        }

    }

    public void enterValue(By locator, Keys keys) throws Exception {
        try {
            logger.info("Started enterValue method execution");
            waitForVisibilityOfElement(locator);
            driverObj.findElement(locator).sendKeys(keys);
            logger.info("Successfully Entered" + keys + "in Textbox - " + locator);
        } catch (Exception e) {
            logger.error("Failed to find Element - " + locator + "in enterValue method" + e.getMessage());
            throw e;
        }
    }

    public WebElement webElement(By locator) throws Exception{
        try {
            waitForVisibilityOfElement(locator);
            return driverObj.findElement(locator);
            } catch (Exception e) {
        logger.error("Failed to find Element - " + locator + "in enterValue method" + e.getMessage());
        throw e;
    }
 }

    public void waitForVisibilityOfElement(By locator) throws Exception {
        logger.info("Started waitForVisibilityOfElement method execution");
        Wait wait = new FluentWait(driverObj).withTimeout(Duration.ofSeconds(25)).pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            logger.info("Element is visible");
        } catch (Exception e) {
            logger.error("waitForVisibilityOfElement method failed: " + e.getMessage());
            throw e;
        }
    }

    public void waitForVisibilityOfElement(By locator,int timeinSec) throws Exception {
        logger.info("Started waitForVisibilityOfElement method execution");
        Wait wait = new FluentWait(driverObj).withTimeout(Duration.ofSeconds(timeinSec)).pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            logger.info("Element is visible");
        } catch (Exception e) {
            logger.error("waitForVisibilityOfElement method failed: " + e.getMessage());
            throw e;
        }
    }

    public boolean waitForVisibilityOfElementBoolean(By locator,int timeinSec) throws Exception {
        logger.info("Started waitForVisibilityOfElement method execution");
        Wait wait = new FluentWait(driverObj).withTimeout(Duration.ofSeconds(timeinSec)).pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
           return false;
        }
    }

    public void waitForPresenceOfElement(By locator,int timeinSec) throws Exception {
        logger.info("Started waitForVisibilityOfElement method execution");
        Wait wait = new FluentWait(driverObj).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            logger.info("Element is visible");
        } catch (Exception e) {
            logger.error("waitForVisibilityOfElement method failed: " + e.getMessage());
            throw e;
        }
    }

    public void waitForElementToBeClickable(By locator) throws Exception {
        logger.info("Started waitForVisibilityOfElement method execution");
        Wait wait = new FluentWait(driverObj).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(findBy(locator)));
            logger.info("Element is visible");
        } catch (Exception e) {
            logger.error("waitForVisibilityOfElement method failed: " + e.getMessage());
            throw e;
        }
    }

    public boolean isElementNotDisplayed(By locator) {
        try {
            logger.info("Started waitForVisibilityOfElement method execution");
            boolean elementState = findElement(locator).isDisplayed();
            if (elementState) {
                logger.info("Successfully verified Element is not Displayed - " + locator);
                return false;
            } else {
                logger.info("Element is Displayed - " + locator);
                return true;
            }
        } catch (Exception e) {
            logger.error("Failed , identify Element is present - " + locator + e.getMessage());
            return true;
        }

    }

    public boolean isElementDisplayed(By locator) {
        try {
            waitForVisibilityOfElement(locator,30);
            return driverObj.findElement(this.findBy(locator)).isDisplayed();
        } catch (Exception e) {
            logger.error("Failed to identify Element - " + locator + e.getMessage());
            return false;
        }
    }
    public boolean isElementDisplayed1(By locator) {
        try {
            driverObj.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            return driverObj.findElement(this.findBy(locator)).isDisplayed();
        }catch(Exception e) {
            return false;
        } finally {
            driverObj.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        }
    }

    public void clearTextBox(By locator) throws Exception {
        try {
            logger.info("Started clearTextBox method execution");
            findElement(locator).clear();
            logger.info("Successfully Cleared value from Text box - " + locator);
        } catch (Exception e) {
            logger.error("failed to find the element - " + locator + e.getMessage());
            throw e;
        }
    }

    public boolean isElementEnabled(By locator) throws Exception {
        try {
            waitForVisibilityOfElement(locator);
            return findElement(locator).isEnabled();
        } catch (Exception e) {
            logger.error("Failed to identify Element - " + locator + e.getMessage());
            throw e;
        }
    }

    public boolean isElementDisabled(By locator) throws Exception {
        try {
            logger.info("Started isElementDisabled method execution");
            boolean elementState = findElement(locator).isEnabled();
            if (!elementState) {
                logger.info("Successfully Verified Element - " + locator + "is disabled.");
                return true;
            } else {
                logger.info("Successfully Verified Element - " + locator + "is enabled.");
                return false;
            }

        } catch (Exception e) {
            logger.error("Failed to Verify on Element - " + e.getMessage());
            throw e;
        }
    }
    private Duration timeoutInSeconds;
    public void waitForPageLoad() throws Exception{
        logger.info("Started waitForPageLoad method execution");
        Thread.sleep(5000);
        timeoutInSeconds = Duration.ofSeconds(60);
//        long timeoutInSeconds = 60;
        long sleepTimeInMilliSeconds = 200;
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
            }
        };
        try {
            Thread.sleep(sleepTimeInMilliSeconds);
            WebDriverWait wait = new WebDriverWait(driverObj, timeoutInSeconds);
            wait.until(expectation);
            logger.info("Completed waitForPageLoad method execution");
        } catch (Exception e) {
            logger.error("Failed to Verify on Element - " + e.getMessage());
            throw e;
        }
    }

    public void moveToElement(By locator) throws Exception {
        try {
            logger.info("Started moveToElement method execution");
            Actions mouse = new Actions(driverObj);
            mouse.moveToElement(driverObj.findElement(this.findBy(locator))).perform();
            logger.info("Successfully moved to Element - " + locator);
        } catch (Exception e) {
            logger.error("Failed to move to Element - " + locator + e.getMessage());
            throw e;
        }
    }

    public void moveToElementAndClick(int x, int y) throws Exception {
        Actions actions = new Actions(driverObj);
        actions.moveToElement(driverObj.findElement(By.tagName("body")), 0, 0);
        actions.moveByOffset(x,y).click().build().perform();
    }

    public void moveToElementAndClick(By locator) throws Exception {
        try {
            logger.info("Started moveToElementAndClick method execution");
            moveToElement(locator);
            new Actions(driverObj).click();
            logger.info("Successfully moved and clicked Element - " + locator);
        } catch (Exception e) {
            logger.error("Failed to move to Element - " + locator + e.getMessage());
            throw e;
        }
    }

    public void clickOnLinkText(String str) throws Exception {
        try {
            logger.info("Started clickOnLinkText method execution");
            WebElement element = driverObj.findElement(By.linkText(str));
            if (element.isDisplayed())
                element.click();
            else
                throw new Exception();
            logger.info("Successfully Clicked on link - " + str);
        } catch (Exception e) {
            logger.error("Failed to Clicked on link - " + str + e.getMessage());
            throw e;
        }
    }


    public String clickAndSwitchToWindow(By locator) throws Exception {
        try {
            logger.info("Started clickAndSwitchToWindow method execution");
            long sleepTimeInMilliSeconds = 5000;
            winHandleBefore = driverObj.getWindowHandle();
            waitAndClick(locator);
            Thread.sleep(sleepTimeInMilliSeconds);
            Set<String> windows = driverObj.getWindowHandles();
            for (String winHandle : windows) {
                if(!winHandle.equals(winHandleBefore)) {
                    driverObj.switchTo().window(winHandle);
                }
            }
            logger.info("Successfully Clicked on element - " + locator + "and Switched to window");
            return winHandleBefore;
        } catch (Exception e) {
            logger.error("Failed to switch to window - " + e.getMessage());
            throw e;
        }
    }


    public String switchToChildWindow() throws Exception {
        try {
            winHandleBefore = driverObj.getWindowHandle();
            Set<String> windows = driverObj.getWindowHandles();
            for (String winHandle : windows) {
                if(!winHandle.equals(winHandleBefore)) {
                    driverObj.switchTo().window(winHandle);
                    System.out.println("Switched to new window");
                }
            }
            return winHandleBefore;
        } catch (Exception e) {
            logger.error("Failed to switch to window - " + e.getMessage());
            throw e;
        }
    }

    public void closeCurrentWindow() throws Exception {
        try {
            logger.info("closeCurrentWindow method execution started");
            int countWindows= driverObj.getWindowHandles().size();
            driverObj.close();
            waitForWindowCountToBe(countWindows-1);
            logger.info("closeCurrentWindow method execution completed");
        } catch (Exception e) {
            logger.error("closeCurrentWindow method execution failed: " + e.getMessage());
            throw e;
        }

    }

    public void switchToWindow(String windowTitle) throws Exception {
        try {
            logger.info("Started switchToWindow method execution");
            Set<String> winHandles = driverObj.getWindowHandles();
            boolean isSwitched = false;
            for (String winHandle : winHandles) {
                driverObj.switchTo().window(winHandle);
                String title = driverObj.getTitle();
                if (title.equals(windowTitle)) {
                    logger.info("successfully switched to the window: " + windowTitle);
                    isSwitched = true;
                    break;
                }
            }
            if(!isSwitched) {
                throw new Exception("Unable to switch to new window. New window may not be loaded properly");
            }
            logger.info("Successfully Switched to window - " + windowTitle);
        } catch (Exception e) {
            logger.error("Failed to switch to window - " + windowTitle + e.getMessage());
            throw e;
        }
    }

    public void switchToWindowAndClose(String windowTitle) throws Exception {
        try {
            logger.info("Started switchToWindowAndClose method execution");
            Set<String> windows = driverObj.getWindowHandles();
            for (String winHandle : windows) {
                driverObj.switchTo().window(winHandle);
                if (driverObj.getTitle().contains(windowTitle)) {
                    driverObj.close();
                    break;
                }
            }
            logger.info("Successfully switched to window and closed - " + windowTitle);
        } catch (Exception e) {
            logger.error("Failed to switched to window and closeCurrentWindow - " + windowTitle + e.getMessage());
            throw e;
        }
    }

    public void switchToFrame() throws Exception {
        try {
            logger.info("Started switchToFrame method execution");
            driverObj.switchTo().activeElement();
            logger.info("Successfully switched to frame");
        } catch (Exception e) {
            logger.error("Failed to switched to window and closeCurrentWindow - " + e.getMessage());
            throw e;
        }
    }

    public void switchToFrameByID(By locator) throws Exception {
        try {
            logger.info("Started switchToFrame method execution");
            WebElement fr = driverObj.findElement(findBy(locator));
            driverObj.switchTo().frame(fr);
            logger.info("Successfully switched to frame");
        } catch (Exception e) {
            logger.error("Failed to switched to window and closeCurrentWindow - " + e.getMessage());
            throw e;
        }
    }

    public void switchToActiveElement() throws Exception {
        try {
            logger.info("Started switchToFrame method execution");
            driverObj.switchTo().activeElement();
            logger.info("Successfully Activated popup");
        } catch (Exception e) {
            logger.error("Failed to switched to window and closeCurrentWindow - " + e.getMessage());
            throw e;
        }
    }

    public Select findSelect(By locator) throws Exception {
        try {
            logger.info("findSelect method execution started");
            WebElement listElement = driverObj.findElement(findBy(locator));
            Select select = new Select(listElement);
            logger.info("findSelect method execution completed");
            return select;
        } catch (Exception e) {
            logger.error("findSelect method execution failed: " + e.getMessage());
            throw e;
        }
    }

    public void selectByValue(By locator, String value) throws Exception {
        try {
            logger.info("Started selectByValue method execution");
            Select select = findSelect(locator);
            select.selectByValue(value);
            logger.info("Successfully Selected value - " + value + " from element " + locator);
        } catch (Exception e) {
            logger.error("Failed to Select value - " + value + " from element " + locator + e.getMessage());
            throw e;
        }
    }

    public void selectByVisibleText(By locator, String value) throws Exception {
        try {
            logger.info("Started selectByVisibleText method execution");
            click(locator);
            Select select = findSelect(locator);
            select.selectByVisibleText(value);
            List<WebElement> opt=select.getOptions();
            opt.stream().forEach(i->{if(i.getText().contains(value));i.click();return;});
            Thread.sleep(2000);
            logger.info("Successfully Selected value - " + value + " from element " + locator);
        } catch (Exception e) {
            logger.error("Failed to Select value - " + value + " from element " + locator + e.getMessage());
            throw e;
        } finally {
            if(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browser").equalsIgnoreCase("android")) {
                click(locator);
                Thread.sleep(1000);
            }
        }
    }


    public void selectByPartialText(By locator, String value) throws Exception {
        try {
            click(locator);
            Thread.sleep(1000);
            Select select = findSelect(locator);
            List<WebElement> opt=select.getOptions();
            opt.stream().forEach(i->{
                if(i.getText().contains(value)){
                    select.selectByVisibleText(i.getText());
                    return;
                }});
            Thread.sleep(500);
        } catch (Exception e) {
            logger.error("Failed to Select value - " + value + " from element " + locator + e.getMessage());
            throw e;
        }
    }

    public void waitForPageLoad(WebDriver driver1) throws Exception{
        logger.info("Started waitForPageLoad method execution");
//        long timeoutInSeconds = 60;
        long sleepTimeInMilliSeconds = 200;
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
            }
        };
        try {
            Thread.sleep(sleepTimeInMilliSeconds);
            WebDriverWait wait = new WebDriverWait(driver1, timeoutInSeconds);
            wait.until(expectation);
            logger.info("Completed waitForPageLoad method execution");
        } catch (Exception e) {
            logger.error("Failed to Verify on Element - " + e.getMessage());
            throw e;
        }
    }

    public boolean waitOnlocator(WebElement locator, WebDriver driver) throws Exception {
        try {
            logger.info("Started waitOnlocator method execution");
//            long timeoutInSeconds = 50;
            WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            if (element != null) {
                logger.info("Completed waitOnlocator method execution");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("Failed  method" + e.getMessage());
            throw e;
        }
    }

    public void selectByIndex(By locator, int index) throws Exception {
        try {
            logger.info("Started selectByIndex method execution");
            Select select = findSelect(locator);
            select.selectByIndex(index);
            logger.info("Successfully Selected value at index - " + index + " from element " + locator);
        } catch (Exception e) {
            logger.error("Failed to select value at index - " + index + " from element " + locator + e.getMessage());
            throw e;
        }
    }

    public String getText(By locator) throws Exception {
        try {
            logger.info("Started getText method execution");
            waitForVisibilityOfElement(locator);
            scrollDown(locator);
            String elementText = driverObj.findElement(this.findBy(locator)).getText().trim();
            logger.info("Successfully returned text - " + elementText + " from element " + locator);
            return elementText;
        } catch (Exception e) {
            logger.error("Failed to get text from element - " + locator + e.getMessage());
            throw e;
        }
    }

    public String getTextAfterSwitchingToDefaultContent(By locator) throws Exception {
        try {
            logger.info("Started getText method execution");
            driverObj.switchTo().defaultContent();
            waitForVisibilityOfElement(locator);
            String elementText = driverObj.findElement(this.findBy(locator)).getText();
            logger.info("Successfully returned text - " + elementText + " from element " + locator);
            return elementText;
        } catch (Exception e) {
            logger.error("Failed to get text from element - " + locator + e.getMessage());
            throw e;
        }
    }

    public void waitfor(long millisec) {
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isElementSelected(By locator) throws Exception {
        try {
            logger.info("Started isElementSelected method execution");
            waitForVisibilityOfElement(locator);
            boolean isSelected = findElement(locator).isSelected();
            logger.info("Successfully returned value - " + isSelected + " from element " + locator);
            return isSelected;
        } catch (Exception e) {
            logger.error("Failed to get value from element - " + locator + e.getMessage());
            throw e;
        }
    }

    public void clearAndEnterValue(By locator, String strg) throws Exception {
        try {
            logger.info("Started clearAndEnterValue method execution");
            WebElement element = driverObj.findElement(this.findBy(locator));
            element.clear();
            element.sendKeys(strg);
            logger.info("Successfully set text - " + strg + " to element " + locator);
        } catch (Exception e) {
            logger.error("Failed to set text - " + strg + " to element " + locator + e.getMessage());
            throw e;
        }
    }

    public void setTextUsingJS(By locator, String strg) throws Exception {
        try {
            WebElement element = driverObj.findElement(this.findBy(locator));
            JavascriptExecutor js = (JavascriptExecutor) driverObj;
            js.executeScript("arguments[0].setAttribute('value', '" + strg +"')", element);

        } catch (Exception e) {
            logger.error("Failed to set text - " + strg + " to element " + locator + e.getMessage());
            throw e;
        }
    }

    public String getPageTitle() {
        logger.info("Started getPageTitle method execution");
        String title = driverObj.getTitle();
        logger.info("Successfully returned page title - " + title);
        return title;
    }

    public void acceptAlert() throws Exception {
        try {
            logger.info("Started acceptAlert method execution");
            Alert alert = driverObj.switchTo().alert();
            alert.accept();
            logger.info("Successfully clicked OK on Alert.");
        } catch (Exception e) {
            logger.error("Failed to click OK on Alert - " + e.getMessage());
            throw e;
        }
    }

    public String getTextOfAlert() throws Exception {
        try {
            logger.info("Started getTextOfAlert method execution");
            Alert alert = driverObj.switchTo().alert();
            String alertText = alert.getText();
            logger.info("Successfully get alert text - " + alertText);
            return alertText;
        } catch (Exception e) {
            logger.error("Failed to get text from Alert - " + e.getMessage());
            throw e;
        }
    }

    public void switchBackToDefaultWindow() throws Exception {
        try {
            logger.info("Started switchBackToDefaultWindow method execution");
            driverObj.switchTo().defaultContent();
            logger.info("Successfully switched to default content");
        } catch (Exception e) {
            logger.error("Failed to switch to default content - " + e.getMessage());
            throw e;
        }
    }

    public List<WebElement> findElements(By locators) throws Exception {
        try {
            logger.info("Started findElements method execution");
            return driverObj.findElements(this.findBy(locators));
        } catch (Exception e) {
            logger.error("Failed to find elements: " + e.getMessage());
            throw e;
        }
    }

    public void dismissAlert() throws Exception {
        try {
            logger.info("Started closeWindowsSecurityPopup method execution");
            Alert alert = driverObj.switchTo().alert();
            alert.dismiss();
            logger.info("Successfully closed Windows Security Popup");
        } catch (Exception e) {
            logger.error("Failed to switch to default content - " + e.getMessage());
            throw e;
        }
    }

    public boolean waitOnlocator(By locator) throws Exception {
        try {
            logger.info("Started waitOnlocator method execution");
            WebDriverWait wait = new WebDriverWait(driverObj, timeoutInSeconds);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(this.findBy(locator)));
            if (element != null) {
                logger.info("Completed waitOnlocator method execution");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("Failed  method" + e.getMessage());
            throw e;
        }
    }

    public String getSelectedOption(By locator) throws Exception {
        try {
            logger.info("Started getSelectedOption method execution");
            Select select = findSelect(locator);
            String selectedOption = select.getFirstSelectedOption().getText();
            logger.info("Successfully retured selected option - " + selectedOption + " from " + locator);
            return selectedOption;

        } catch (Exception e) {
            logger.error("Failed to return selected option from " + locator + e.getMessage());
            throw e;
        }
    }

    public String getEnteredText(By locator) throws Exception {
        try {
            logger.info("Started getTextEntered method execution");
            String enteredText = driverObj.findElement(findBy(locator)).getAttribute("value");
            logger.info("Successfully retured selected option - " + enteredText + " from " + locator);
            return enteredText;

        } catch (Exception e) {
            logger.error("Failed to return selected option from " + locator + e.getMessage());
            throw e;
        }
    }

    public boolean isAlertPresent() throws Exception {
        try {
            logger.info("Started isAlertPresent method execution");
            WebDriverWait wait = new WebDriverWait(driverObj, timeoutInSeconds);
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (Exception e) {
            logger.info("No alert");
            return false;
        }
    }

    public String getAttributeValue(By locator, String attributeName) throws Exception {
        try {
            logger.info("Started getAttributeValue method execution");
            String getAttributeName = driverObj.findElement(this.findBy(locator)).getAttribute(attributeName);
            logger.info("Successfully returned selected option - " + getAttributeName + " from " + locator);
            return getAttributeName;
        } catch (Exception e) {
            logger.error("Failed to return selected option - " + locator + e.getMessage());
            throw e;
        }
    }

    public int[] getElementCoordinates(By locator) throws Exception {
        try {
            logger.info("Started getCoordinates method execution");
            Point coordinate = driverObj.findElement(this.findBy(locator)).getLocation();
            int xCordinate = coordinate.getX();
            int yCordinate = coordinate.getY();
            int[] coordinates = new int[2];
            coordinates[0] = xCordinate;
            coordinates[1] = yCordinate;
            logger.info("Successfully returned  the X and Y coordinates of the Element" + "X coordinate = "+xCordinate +" and yCoordinate = "+ yCordinate );
            return coordinates;
        } catch (Exception e) {
            logger.error("Failed to return the X and Y coordinate " + locator + e.getMessage());
            throw e;
        }
    }

    public int[] getElementSize(By locator) throws Exception {
        try {
            logger.info("Started getElementSize method execution");
            Dimension sizeOfElement = driverObj.findElement(this.findBy(locator)).getSize();
            int[] elementSize = new int[2];
            elementSize[0] = sizeOfElement.getHeight();
            elementSize[1] = sizeOfElement.getWidth();
            logger.info("Successfully returned element height and width" + "height = " +elementSize[0] +"Width = " + elementSize[1]);
            return elementSize;
        } catch (Exception e) {
            logger.error("Failed to return element height and width" + locator + e.getMessage());
            throw e;
        }
    }

    public boolean waitForInvisibilityOfElement(By locator) throws Exception {
        logger.info("Started waitForInvisibilityOfElement method execution");
        long timeoutInSeconds = 20;
        long pollingTimeoutInMilliSeconds = 200;
        Wait wait = new FluentWait(driverObj).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(findBy(locator)));
            logger.info("Successfully wait for invisibility of element - " + locator);
            return true;
        } catch (Exception e) {
            logger.error("Failed to wait on element - " + locator + e.getMessage());
            throw e;
        }
    }

    public boolean waitForInvisibilityOfElement(By locator, long timeOut) throws Exception {
        logger.info("Started waitForInvisibilityOfElement method execution");
        try {
            Duration duration=Duration.ofSeconds(timeOut);
            WebDriverWait wd = new WebDriverWait(driverObj,duration);
            wd.until(ExpectedConditions.invisibilityOfElementLocated(findBy(locator)));
            return true;
        } catch (Exception e) {
            logger.error("Failed to wait on element - " + locator + e.getMessage());
            return false;

        }
    }
    public boolean waitForInvisibilityOfElement1(By locator, long timeOut) throws Exception {
        logger.info("Started waitForInvisibilityOfElement method execution");
        try {
            Duration duration=Duration.ofSeconds(timeOut);
            driverObj.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            WebDriverWait wd = new WebDriverWait(driverObj, duration);
            wd.until(ExpectedConditions.invisibilityOfElementLocated(findBy(locator)));
            return true;
        } catch (Exception e) {
            logger.error("Failed to wait on element - " + locator + e.getMessage());
            return false;
        } finally {
            driverObj.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        }
    }

    public List<WebElement> getDropdownOptions(By locator) throws Exception {
        try {
            Select select = findSelect(locator);
            return select.getOptions();
        } catch (Exception e) {
            logger.error("Failed to get dropdown values - " + locator + " " + e.getMessage());
            throw e;
        }

    }

    public List<String> getDropdownValues(By locator) throws Exception {
        try {
            logger.info("Started getDropdownValues method execution");
            List<String> options = new ArrayList<>();
            List<WebElement> optionList = getDropdownOptions(locator);
            for (WebElement option : optionList) {
                options.add(option.getText());
            }
            logger.info("Completed getDropdownValues method execution");
            return options;
        } catch (Exception e) {
            logger.error("Failed to get dropdown values - " + locator + " " + e.getMessage());
            throw e;
        }
    }

    public void enterKeyUsingRobot() throws Exception {
        logger.info("Started enterKeyUsingRobot method execution");
        Robot robot;
        int milliSeconds = 400;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(milliSeconds);
            logger.info("Successfully pressed Enter Key from keyboard");
        } catch (Exception e) {
            logger.error("Failed to execute enterKeyUsingRobot method - " + e.getMessage());
            throw e;
        }
    }

    public WebElement findElement(By locator) throws Exception {
        try {
            return driverObj.findElement(this.findBy(locator));
        } catch (Exception e) {
            logger.error("findElement method executino failed: " + e.getMessage());
            throw e;
        }
    }

    public WebElement findElementStream(By locator)  {
        try {
            return driverObj.findElement(this.findBy(locator));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //***** Trigger a change event on textbox. Sometimes textbox doesn't hold the value entered by webxriver, in that case we need to use this method *****
    public void triggerChangeEventOnTextbox(By locator) throws Exception {
        try {
            logger.info("Started triggerChangeEventOnTextbox method execution");
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driverObj;
            jsExecutor.executeScript("$(arguments[0]).change();", findElement(locator));
            logger.info("Completed triggerChangeEventOnTextbox method execution");
        } catch (Exception e) {
            logger.error("Failed to execute triggerChangeEventOnTextbox method - " + e.getMessage());
            throw e;
        }
    }

    public String getCssValue(By locator, String cssAttribute) throws Exception {
        try {
            return findElement(locator).getCssValue(cssAttribute);
        } catch (Exception e) {
            logger.error("getCssValue method execution failed: " + e.getMessage());
            throw e;
        }
    }

    public void clickUsingActionClass(By locator) throws Exception{
        WebElement ele=findElement(locator);
        Actions ob = new Actions(driverObj);
//        ob.moveToElement(ele);
        ob.click(ele).build().perform();
    }

    public void clickandHoldUsingActionClass(By locator) throws Exception{
        WebElement ele=findElement(locator);
        Actions ob = new Actions(driverObj);
        ob.clickAndHold(ele).perform();
        waitfor(5000);
        ob.release(ele).perform();
    }

    public void clickUsingJavaScriptExcuter1(By locator) throws Exception {
        try {
            logger.info("Started clickUsingJavaScriptExcuter method execution");
            JavascriptExecutor jse = (JavascriptExecutor) driverObj;
            jse.executeScript("arguments[0].click();", findElement(locator));
            logger.info("Successfully clicked on element");
        } catch (Exception e) {
            logger.error("Failed to switch to default content - " + e.getMessage());
            throw e;
        }
    }

    public boolean waitForAlert() throws Exception {
        try {
            logger.info("Started isAlertPresent method execution");
            WebDriverWait wait = new WebDriverWait(driverObj, timeoutInSeconds);
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (Exception e) {
            logger.info("waitForAlert method failed: "+e.getMessage());
            throw e;
        }
    }


    public boolean waitForWindowCountToBe(int count) throws Exception {
        try {
            logger.info("Started isAlertPresent method execution");
            WebDriverWait wait = new WebDriverWait(driverObj, timeoutInSeconds);
            wait.until(ExpectedConditions.numberOfWindowsToBe(count));
            return true;
        } catch (Exception e) {
            logger.info("waitForAlert method failed: "+e.getMessage());
            throw e;
        }
    }


    public void switchToWindowS(String windowTitle) throws Exception {
        try {
            logger.info("Started switchToWindow method execution");
            String windowHandle = driverObj.getWindowHandle();
            Set<String> winHandles = driverObj.getWindowHandles();
            winHandles.remove(windowHandle);
            for (String winHandle : winHandles) {
                driverObj.switchTo().window(winHandle);
                if (driverObj.getTitle().contains(windowTitle))
                    break;
            }
            logger.info("Successfully Switched to window - " + windowTitle);
        } catch (Exception e) {
            logger.error("Failed to switch to window - " + windowTitle + e.getMessage());
            throw e;
        }
    }

    public boolean waitForFrameToBeAvailableAndSwitchToIt(By locator) throws Exception {
        logger.info("Started waitForInvisibilityOfElement method execution");
        long timeoutInSeconds = 50;
        long pollingTimeoutInMilliSeconds = 300;
        Wait wait = new FluentWait(driverObj).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(findBy(locator)));
            logger.info("Successfully wait for invisibility of element - " + locator);
            return true;
        } catch (Exception e) {
            logger.error("Failed to wait on element - " + locator + e.getMessage());
            throw e;
        }
    }

    public String getTagName(By locator) throws Exception {
        try {
            return findElement(locator).getTagName();
        } catch (Exception e) {
            logger.error("getTagName method execution failed: "+ e.getMessage());
            throw  e;
        }
    }

    public String getPageUrl() throws Exception {
        try {
            return driverObj.getCurrentUrl();
        } catch (Exception e) {
            logger.error("getPageUrl method execution failed: "+ e.getMessage());
            throw  e;
        }
    }

    public void presenceOfElementLocated(By locator) throws Exception {
        logger.info("Started waitForInvisibilityOfElement method execution");
        long timeoutInSeconds = 50;
        long pollingTimeoutInMilliSeconds = 300;
        Wait wait = new FluentWait(driverObj).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(findBy(locator)));
            logger.info("Successfully wait for invisibility of element - " + locator);
        } catch (Exception e) {
            logger.error("Failed to wait on element - " + locator + e.getMessage());
            throw e;
        }
    }



    public void captureScreenshot (Scenario scenario, String udid){
        try {
                byte[] exception = (byte[]) ((TakesScreenshot) driverObj).getScreenshotAs(OutputType.BYTES);
                scenario.attach(exception, "image/jpeg",udid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Object> captureXYCoordinatesOfEndCallBtn(){
        Point p = driverObj.findElement(By.xpath("(//label[text()='中断']//preceding::button)[5]")).getLocation();
        int x_coordinate = p.getX();
        int y_coordinate = p.getY();
        ArrayList<Object> al = new ArrayList<Object>();
        al.add(x_coordinate);
        al.add(y_coordinate);
        return al;
    }

    public void performClickOperation(int x, int y) {
        new Actions(driverObj).moveByOffset(x, y).click().build().perform();
    }
    public void openNewWindowInWeb(String windowUrl) throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driverObj;
        js.executeScript("window.open('"+windowUrl+"')");
    }

    public void switchToDefaultWindow(){
        driverObj.switchTo().window(winHandleBefore);
        driverObj.switchTo().defaultContent();
    }



}