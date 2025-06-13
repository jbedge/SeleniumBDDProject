package com.report.extent;

import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

public class ExtentAssertionLogger extends SoftAssert {

    @Override
    public void onAssertSuccess(IAssert<?> assertCommand) {
        logAssertion(assertCommand, true);
    }

    @Override
    public void onAssertFailure(IAssert<?> assertCommand) {
        logAssertion(assertCommand, false);
    }

    private void logAssertion(IAssert<?> assertCommand, boolean isSuccess) {
        String message = assertCommand.getMessage() == null ? "" : assertCommand.getMessage();
        String details = String.format("Expected: [%s] | Actual: [%s]", 
                                      assertCommand.getExpected(), 
                                      assertCommand.getActual());

        if (isSuccess) {
            ExtentReportManager.logPass(message + " - " + details);
        } else {
            ExtentReportManager.logFail(message + " - " + details);
        }
    }
}