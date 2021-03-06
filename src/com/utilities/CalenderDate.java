package com.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CalenderDate {
    WebDriver driver = null;
  

    public  void selectStartDate(String date){

        driver.findElement(By.xpath("(//form//mat-dialog-content//button[@*='Open calendar'])[1]")).click();
        Object ecUtil;
//        ecUtil.waitUntilElementFound(2);
        driver.findElement(By.xpath("//button[@*='Choose month and year']")).click();
//        ecUtil.waitUntilElementFound(2);
        String[] caldate=date.toUpperCase().split("/");
        for(int i=caldate.length-1;i>=0;i--){
            By locYear=By.xpath("//div[contains(@class,'mat-calendar-body') and normalize-space()='"+caldate[i]+"']");
//            ecUtil.waitUntilElementFound(1);
            WebElement el=driver.findElement(locYear);
            el.click();
        }
    }

    public void selectEndDate(String date){
//        driver.findElement(By.xpath("(//form//mat-dialog-content//button[@*='Open calendar'])[2]")).click();
//        ecUtil.javascriptClick(driver.findElement(By.xpath("(//form//mat-dialog-content//button[@*='Open calendar'])[2]")),driver);
//        ecUtil.waitUntilElementFound(2);
//        ecUtil.javascriptClick(driver.findElement(By.xpath("//button[@*='Choose month and year']")), driver);
//        driver.findElement(By.xpath("//button[@*='Choose month and year']")).click();
//        ecUtil.waitUntilElementFound(2);
        String[] caldate=date.toUpperCase().split("/");
        for(int i=caldate.length-1;i>=0;i--){
            By locYear=By.xpath("//div[contains(@class,'mat-calendar-body') and normalize-space()='"+caldate[i]+"']");
//            ecUtil.waitUntilElementFound(1);
            WebElement el=driver.findElement(locYear);
            el.click();
        }
    }

    public static String getDateAndTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("JST"));
        String dateTime =sdf.format(calendar.getTime());
        return dateTime;
    }


}
