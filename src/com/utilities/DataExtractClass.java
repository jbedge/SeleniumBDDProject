package com.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.ArrayList;

public class DataExtractClass {
    static WebDriver driver;

    public static void main(String[] args) throws InterruptedException {

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("–disable-infobars");
        options.addArguments("–disable-extensions");
        driver = new ChromeDriver(options);
        listFilesForFolder(new File("C:\\TestData\\Downloads\\Indiana Lobbyists"));



        driver.quit();
    }

    public static void listFilesForFolder(final File folder) throws InterruptedException {

        By clientName= By.xpath("(//h6/strong)[last()]");
        By legalName= By.xpath("//h2[text()='Full Legal Name of Lobbyist']/following-sibling::div[1]/p");
        By address1= By.xpath("//label[text()='Address 1']//following-sibling::p[1]");
        By address2= By.xpath("//label[text()='Address 2']//following-sibling::p[1]");
        By city= By.xpath("//label[text()='City']//following-sibling::p[1]");
        By state= By.xpath("//label[text()='State']//following-sibling::p[1]");
        By zip= By.xpath("//label[text()='Zip']//following-sibling::p[1]");
        By email= By.xpath("//label[text()='Email']//following-sibling::p[1]");
        Lobbyist lobbyist=new Lobbyist();
        ArrayList<Lobbyist> details=new ArrayList<>();
        int cnt=0;
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                cnt++;
                driver.get(fileEntry.getAbsolutePath());
                Thread.sleep(1000);
                lobbyist.setFullName(driver.findElement(legalName).getText());
                lobbyist.setClientName(driver.findElement(clientName).getText());
                lobbyist.setAddress1(driver.findElement(address1).getText());
                lobbyist.setAddress2(driver.findElement(address2).getText());
                lobbyist.setCity(driver.findElement(city).getText());
                lobbyist.setState(driver.findElement(state).getText());
                lobbyist.setZip(driver.findElement(zip).getText());
                lobbyist.setEmail(driver.findElement(email).getText());
                details.add(lobbyist);
            }
        }
//        System.out.println(details);
    }

}
