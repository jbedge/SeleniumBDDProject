package com.base;

import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Map;

public class TestConfiguration {
    private String configName = "resources/config/config.yaml";
    Logger log = LogManager.getLogger();
    private Map config = null;
    private String browser;
    private String env;
    private String appiumPort;
    private String version;
    private String udid;
    private String deviceName;
    private String app = null;
    private Boolean sp;
    private Scenario scenario;
    private int systemPort;
    private int chromeDriverPort;
    public static final int WAIT_1_SECOND = 1000;
    public static final int WAIT_2_SECOND = 2000;
    public static final int WAIT_HALF_SECOND = 500;
    public static final int WAIT_3_SECOND = 3000;
    public static final int WAIT_5_SECOND = 5000;
    public static final int WAIT_10_SECOND = 10000;

    public TestConfiguration() throws Exception {
//        setBrowser(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browser"));
//        setEnv(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("env"));
//        setAppiumPort(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("port"));
//        setUdid(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("udid"));
//        setVersion(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("version"));
//        setDeviceName(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("deviceName"));
//        setSP(true);
//        setSystemPort(Integer.parseInt(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("systemPort")));
//        setChromeDriverPort(Integer.parseInt(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("chromedriverPort")));
        getConfig();
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getBrowser() {
        return config.get("browser").toString();
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getAppiumPort() {
        return appiumPort;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setAppiumPort(String appiumPort) {
        this.appiumPort = appiumPort;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Map getConfig() throws Exception {
        config = null;
        log.info("Inside getConfig");
        InputStream input = new FileInputStream(configName);
        log.info("file loaded");
        Yaml yaml = new Yaml();
        Map<String, Object> data1 = (Map<String, Object>) yaml.load(input);
        config = data1;
        input.close();
        return config;
    }

    public String getApplicationURl() {
        return config.get("url").toString();
    }

    public String getManagerName() {
        return config.get("user.manager").toString();
    }


    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public Boolean getSP() {
        return sp;
    }

    public void setSP(Boolean sp) {
        this.sp = sp;
    }

    public int getSystemPort() {
        return systemPort;
    }

    public void setSystemPort(int systemPort) {
        this.systemPort = systemPort;
    }

    public int getChromeDriverPort() {
        return chromeDriverPort;
    }

    public void setChromeDriverPort(int chromeDriverPort) {
        this.chromeDriverPort = chromeDriverPort;
    }

    public String getcurrentURL() {
        return config.get("url").toString();
    }

    private JSONObject jsonObject;

    public void loadJSONFile(String fileName) throws Exception{
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir")+"//TestData//"+fileName+""));
        jsonObject =  (JSONObject) obj;
    }

    public Object getJsonData(String key){
        return jsonObject.get(key);
    }



}