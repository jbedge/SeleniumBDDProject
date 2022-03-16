package com.api;


import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONArray;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BaseAPIPage {

    Response response;
    RequestSpecification commonParam;
    String baseUri="https:/xxxxyyyy.com";
    String tokenurl;
    Map util;
    String env;

    By inpusername=By.xpath("//*[@id=\"IdPlogin\"]");
    By inpuserpwd=By.xpath("//*[@id=\"IdPpass\"]");
    By btnConnect=By.xpath("//*[@id=\"IdPsubmit\"]");
    By tokenbtn=By.xpath("(//input[@value='Control'])[last()]");
    By agree=By.xpath("//a[text()='Agree']");

    public BaseAPIPage() {
//        PropUtil propUtil=new PropUtil();
//        String env=propUtil.getPropertyValue("env");
//        util=(Map)propUtil.loadYaml().get(env);
        baseUri= (String) (util.get("baseuri"));
        System.out.println(baseUri);
        tokenurl= (String) util.get("tokenurl");
    }





    WebDriver driver;
    public static String token="";

    public void getAuthToken() throws InterruptedException {
        if (token.length()==0) {
            String path = System.getProperty("user.dir") + File.separator + "drivers" + File.separator + "chromedriver.exe";
            System.setProperty("webdriver.chrome.driver", path);
            ChromeOptions options=new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("start-maximized");
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
            driver.get(tokenurl);
            driver.findElement(inpusername).sendKeys((String) util.get("username"));
            driver.findElement(inpuserpwd).sendKeys((String) util.get("password"));
            driver.findElement(btnConnect).click();
            ((ChromeDriver)driver).executeScript("arguments[0].click();",  driver.findElement(tokenbtn));
            driver.findElement(agree).isEnabled();
            Thread.sleep(5000);
            String currenturl = driver.getCurrentUrl();
            int lastindex = currenturl.indexOf("&id_token");
            int index = currenturl.indexOf("#access_token=") + 14;
            token = currenturl.substring(index, lastindex);
            System.out.println("token:" + token);
            driver.close();
        }
    }



    public void getCommonParam2(){
        commonParam= RestAssured.given().
                relaxedHTTPSValidation() //java.security.cert.CertPathValidatorException: validity check failed
                .baseUri(baseUri)
                .header("authorization",token)
                .header("Content-Type","application/json")
                .when().log().all();
    }




    public void postJSOn(String basePath){
        response=commonParam.queryParam("countryCode","UK").body(jsonBody).basePath(basePath).post();
        logPostAPIDetails();
    }


    public JSONArray getResponseJSONArray() {
        JSONArray json = JsonPath.read(response.getBody().asString(),"$");
        return json;
    }

    public LinkedHashMap getResponseJsonObject() {
        LinkedHashMap json = JsonPath.read(response.getBody().asString(),"$");
        return json;
    }

    public void getResponseAndVerify(String expText) {
       String actual= JsonPath.read(response.getBody().asString(),"$");;
        Assert.assertEquals(expText,actual);
    }


    FileInputStream fis;
    String jsonBody="";
    public void loadJsonFile(String fileName) throws IOException {
        String path=System.getProperty("user.dir")+File.separator+"testdata"+File.separator+fileName;;
        fis= new FileInputStream(path);
        jsonBody= IOUtils.toString(fis, StandardCharsets.UTF_8);
    }

    public void modifyJson() throws IOException {
        LinkedHashMap json = JsonPath.read(jsonBody,"$");
        String currentDateAndTime=getCurrentDateAndTime("yyyy-MM-dd HH:mm:ss.SSS");
        json.put("signInDateTime",currentDateAndTime);
        jsonBody= new JSONObject(json).toJSONString();
    }

    public String getCurrentDateAndTime(String dateformat){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(dateformat);
        String dateFormat=simpleDateFormat.format(calendar.getTime());
        return dateFormat;
    }

    public void getCommonParam(){
        commonParam= RestAssured.given().
                relaxedHTTPSValidation() //java.security.cert.CertPathValidatorException: validity check failed
                .baseUri(baseUri)
//                .basePath(basePath)
                .header("x-api-key","12345678991234567800000")
                .when().log().all();
    }

    public void logPreAPIDetails(){
        commonParam.log().all();
    }

    public void logPostAPIDetails(){
        response.then().log().all();
    }

    public void getAPI(String basePath){
        response=commonParam.get(basePath);
        logPostAPIDetails();
    }

    public void verifyStatus(String statusCodeExpected){
        int statusCodeActual=response.getStatusCode();
        Assert.assertEquals(statusCodeActual,Integer.parseInt(statusCodeExpected),"failed to verify the status");
    }

    public static void main(String[] args)  {
        BaseAPIPage page=new BaseAPIPage();
        page.getCommonParam();
        page.getAPI("/v1/sites/getSites/UK/");
        page.verifyStatus("200");
        page.getResponseJSONArray();
    }

    public void get(){
        RequestSpecification requestSpecification= RestAssured.given().baseUri("")
                .basePath("").header("","").when();
    }

    public void getResponse(){
        response.then().log().all();
    }


    public void getResonseCode(){
        response.getStatusCode();
    }

}
