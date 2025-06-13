package com.report.extent;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestResult;

import java.io.File;
import java.util.*;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static final Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
    private static final String REPORT_DIR = "test-output/";
    private static final String REPORT_NAME = "ExtentReport.html";

    public synchronized static ExtentReports getReporter() {
        if (extent == null) {
            setup();
        }
        return extent;
    }

    private static void setup() {
        String reportPath = REPORT_DIR + REPORT_NAME;
        new File(REPORT_DIR).mkdirs(); // Create directory if it doesn't exist
        
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(reportPath);
        htmlReporter.config().setDocumentTitle("Automation Test Report");
        htmlReporter.config().setReportName("Test Execution Report");
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setTimelineEnabled(true);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        
        // System information
        extent.setSystemInfo("Organization", "Your Company");
        extent.setSystemInfo("Automation Framework", "Selenium WebDriver");
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));
    }

    public synchronized static ExtentTest createTest(String testName, String description) {
        ExtentTest test = getReporter().createTest(testName, description);
        extentTestMap.put((int) Thread.currentThread().getId(), test);
        return test;
    }

    public synchronized static ExtentTest getTest() {
        return extentTestMap.get((int) Thread.currentThread().getId());
    }

    public synchronized static void logInfo(String message) {
        getTest().info(message);
    }

    public synchronized static void logPass(String message) {
        getTest().pass(MarkupHelper.createLabel(message, ExtentColor.GREEN));
    }

    public synchronized static void logFail(String message) {
        getTest().fail(MarkupHelper.createLabel(message, ExtentColor.RED));
    }

    public synchronized static void logSkip(String message) {
        getTest().skip(MarkupHelper.createLabel(message, ExtentColor.ORANGE));
    }

    public synchronized static void logWarning(String message) {
        getTest().warning(MarkupHelper.createLabel(message, ExtentColor.YELLOW));
    }

    public synchronized static void addScreenshot(String base64Image, String title) {
        getTest().addScreenCaptureFromBase64String(base64Image, title);
    }

    public synchronized static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    // Add this method to handle String arrays
    public synchronized static void assignCategory(String[] categories) {
        if (categories != null && categories.length > 0) {
            for (String category : categories) {
                getTest().assignCategory(category);
            }
        }
    }

    // Keep the existing single category method
    public synchronized static void assignCategory(String category) {
        getTest().assignCategory(category);
    }

    public synchronized static void assignAuthor(String author) {
        getTest().assignAuthor(author);
    }

    public static void logTestResult(ITestResult result) {
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                logPass("Test Passed");
                break;
            case ITestResult.FAILURE:
                logFail("Test Failed");
                if (result.getThrowable() != null) {
                    logFail(result.getThrowable().getMessage());
                }
                break;
            case ITestResult.SKIP:
                logSkip("Test Skipped");
                break;
            default:
                logWarning("Test Status Unknown");
        }
    }
}