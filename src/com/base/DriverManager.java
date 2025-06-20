package com.base;

import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.*;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class DriverManager {
    public WebDriver driver;
    public ChromeDriver chromeDriver;
    public Boolean serverStarted  = false;
    public String dummyText;
    private String LOCAL_DIRECTORY_SOURCE = System.getProperty("user.dir") + File.separator + "browserDrivers" + File.separator;
    private String session_details = System.getProperty("user.dir") + File.separator + "tempFiles" + File.separator+"sessionDetails.tmp";
    TestConfiguration configuration;

    public DriverManager(TestConfiguration testConfiguration) {
        configuration= testConfiguration;
    }


    public WebDriver createDriver() throws Exception {
        System.out.println("Inside createDriver");
        String browser = configuration.getBrowser();
        switch (browser.toLowerCase()) {
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver","browserDrivers/chromedriver.exe");
                WebDriverManager.chromedriver().setup();
                ChromeOptions options1 = new ChromeOptions();
                options1.addArguments("--disable-extensions");
                options1.addArguments("disable-infobars");
                options1.addArguments("start-maximized");
                options1.addArguments("--disable-gpu");
//                options1.addArguments("--headless");

//                options1.setCapability("app", System.getProperty("user.dir")+"\\browserDrivers\\chromedriver.exe");
//                Runtime rt = Runtime.getRuntime();
//                Process p =rt.exec(new String[]{"cmd.exe","/c","start"});
//                p.waitFor();
//                p=rt.exec("taskkill /F /IM chrome.exe");
//                p.waitFor();
//                p.waitFor();
//                rt.exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe -remote-debugging-port=9222 -user-data-dir=C:\\ChromeData");
//                p.waitFor();
//                Thread.sleep(5000);
//                p.waitFor();
//                Map<String, Object> prefs = new HashMap<String, Object>();
//                Map<String, Object> langs = new HashMap<String, Object>();
//                options1.setExperimentalOption("debuggerAddress","localhost:9222");

//                String proxy = "127.0.0.1:5000";
//                options1.addArguments("--proxy-server=http://" + proxy);
//                chrome.exe -remote-debugging-port=9222 -user-data-dir=C:\ChromeData :command to run chrome in debugmode
//                options1.addArguments();
                 chromeDriver= new ChromeDriver(options1);
                String debugPort=(String)((Map)chromeDriver.getCapabilities().getCapability("goog:chromeOptions")).get("debuggerAddress");
                writeFile(session_details,debugPort);
//                System.out.println(debuggerport);
                driver=(WebDriver)chromeDriver;

//                using selenium grid using docker
//                driver = new RemoteWebDriver(new URL("http://localhost:5555/wd/hub"), options1);

                driver.manage().deleteAllCookies();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
                driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
                break;
            case "debugchrome":
                System.setProperty("webdriver.chrome.driver","browserDrivers/chromedriver.exe");
                options1= new ChromeOptions();
                String port=reafFile(session_details);
                options1.setExperimentalOption("debuggerAddress",port);
                chromeDriver=new ChromeDriver(options1);
                driver=(WebDriver)chromeDriver;
                chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
                chromeDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
                break;
        }
        return driver;
    }

    public void quitDriver() {
        driver.quit();
    }

    public void closeDriver() {
        driver.close();
    }

    public void getScreenshot(Scenario scenario, String udid) throws Exception {
        if (scenario.isFailed()) {
            try {
                    byte[] exception = (byte[]) ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    scenario.attach(exception, "image/jpeg",udid);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String reafFile(String filePath) throws Exception{
        FileInputStream fileInputStream=new FileInputStream(filePath);
        String fileString= IOUtils.toString(fileInputStream,"UTF-8");
        return fileString;
    }

    public void writeFile(String filePath,String dataToWrite) throws Exception{
        FileWriter fileWriter=new FileWriter(filePath);
        fileWriter.write(dataToWrite);
        fileWriter.close();
    }

}