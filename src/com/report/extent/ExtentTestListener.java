package com.report.extent;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        ExtentReportManager.createTest(result.getMethod().getMethodName(),
                result.getMethod().getDescription());

        // Add categories from TestNG groups - this will now work with the array version
        String[] groups = result.getMethod().getGroups();
        ExtentReportManager.assignCategory(groups); // Now accepts String[]
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.logTestResult(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentReportManager.logTestResult(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.logTestResult(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Do nothing
    }

    @Override
    public void onStart(ITestContext context) {
        // Setup Extent before suite starts
        ExtentReportManager.getReporter();
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.flushReport();
    }
}