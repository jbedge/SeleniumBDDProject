package com.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface Action {
     List<WebElement> findElements(By loc);
     WebElement waitForVisibilityOfElement(By loc);
     WebElement waitForVisibilityOfElement(By loc,long timeout);
     WebElement waitForPresenceOfElement(By loc);
     WebElement waitForPresenceOfElement(By loc,long timeout);
     WebElement findElement(By loc);
     void click(By loc);
     void clickUsingJS(By loc);
     void clickUsingAction(By loc);
     void waitForPageToLoad();
     void waitFor(double sec);



}
