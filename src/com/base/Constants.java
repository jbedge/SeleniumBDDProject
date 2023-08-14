package com.base;


import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Constants {

    public static final String CONFIG_PATH="config/config.properties";
    public static final String INI_PATH="config/config.ini";
    public static final String INI_GENERIC= Paths.get(System.getProperty("user.dir")).getParent().toString()+"/browserDrivers/config.ini";
    public static final String YAML_PATH="config/config.yaml";
    public static final String DRIVER_PATH="browserDrivers/chromedriver.exe";

//    in seconds
    public static final int PAGE_TIME=80;
    public static final int SCRIPT_TIME_OUT=120;
    public static final int MAX_TIMEOUT=80;
    public static final long POLL_TIME=1;
    public static final long IMPLICIT_WAIT = 10;

    public static final String SCREENSHOT_PATH = "screenshot/";
    public static final String TEST_DATA = "testdata/RuntimeData.csv";

    public static final List<String> RETAIL_GROUP = Arrays.asList("New Retailer Group","ILTuesday","ILWednesday","ILThursday","ILFriday");

    public static final List<String> EMAIlGROUP_NEWRETAIL = Arrays.asList("ILMon1@mailinator.com","ILMon2@mailinator.com","ILMon3@mailinator.com","ILMon4@mailinator.com","ILMon5@mailinator.com");
    public static final List<String> EMAIlGROUP_ILTuesday = Arrays.asList("ILTue1@mailinator.com","ILTue2@mailinator.com","ILTue3@mailinator.com","ILTue4@mailinator.com","ILTue5@mailinator.com");
    public static final List<String> EMAIlGROUP_ILWednesday = Arrays.asList("ILWed1@mailinator.com","ILWed2@mailinator.com","ILWed3@mailinator.com","ILWed4@mailinator.com","ILWed5@mailinator.com");
    public static final List<String> EMAIlGROUP_ILThursday = Arrays.asList("ILThu1@mailinator.com","ILThu2@mailinator.com","ILThu3@mailinator.com","ILThu4@mailinator.com","ILThu5@mailinator.com");
    public static final List<String> EMAIlGROUP_ILFriday = Arrays.asList("ILFri1@mailinator.com","ILFri2@mailinator.com","ILFri3@mailinator.com","ILFri4@mailinator.com","ILFri5@mailinator.com");

    public static String getRandomRetailGroup(){
        String random = RETAIL_GROUP.get(new Random().nextInt(RETAIL_GROUP.size()));
        return random;
    }

    public static List<String> getEmailGroup(String retailGroup){
        switch(retailGroup) {
            case "New Retailer Group":
                return EMAIlGROUP_NEWRETAIL;
            case "ILTuesday":
                return EMAIlGROUP_ILTuesday;
            case "ILWednesday":
                return EMAIlGROUP_ILWednesday;
            case "ILThursday":
                return EMAIlGROUP_ILThursday;
            case "ILFriday":
                return EMAIlGROUP_ILFriday;
            default:
                throw new RuntimeException("Retail Group Not Found");
        }
    }



}