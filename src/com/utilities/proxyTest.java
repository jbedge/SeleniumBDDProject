package com.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class proxyTest {
    public static void main(String[] args) {
//    Proxy proxy = new Proxy();
//    proxy.setHttpProxy("14a06a809764d:a568e57dd6@195.210.106.59:12323");
    WebDriverManager.chromedriver()
            .browserVersion("104.0.5112.101")
            .arch32()
            .proxy("195.210.106.59:12323")
            .proxyUser("14a06a809764d")
            .proxyPass("a568e57dd6")
            .setup();

//        WebDriverManager.chromedriver().browserVersion("104.0.5112.101").setup();


//        ChromeOptions options = new ChromeOptions();
//        options.setCapability("proxy", proxy);
//        WebDriver driver = new ChromeDriver(options);
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com/");
        driver.findElement(By.xpath("//*/]")).sendKeys("adasd");
        driver.manage().window().maximize();
        driver.quit();
    }
}