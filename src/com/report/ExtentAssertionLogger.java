//package com.report;
//
//import org.testng.asserts.IAssert;
//import org.testng.asserts.SoftAssert;
//
//public class ExtentAssertionLogger extends SoftAssert {
//
//    private static final String ASSERTION_MESSAGE_OUTCOME_DELIMITER = " - ";
//    private static final String NO_ASSERTION_ERROR_MESSAGE = "No Assertion Message Not Provided";
//
//    @Override
//    public void onAssertFailure(IAssert<?> assertCommand) {
//        logAssertion(assertCommand, LogStatus.FAIL);
//        super.onAssertFailure(assertCommand);
//    }
//
//    @Override
//    public void onAssertSuccess(IAssert<?> assertCommand) {
//        logAssertion(assertCommand, LogStatus.PASS);
//        super.onAssertSuccess(assertCommand);
//    }
//
//    private void logAssertion(IAssert<?> assertCommand, LogStatus status) {
//        StringBuilder log = new StringBuilder();
//
//        Object expected = assertCommand.getExpected();
//        Object actual = assertCommand.getActual();
//        String message = assertCommand.getMessage();
//        message = (message == null || message.trim().isEmpty()) ? NO_ASSERTION_ERROR_MESSAGE : message.trim();
//
//        log.append(message).append(ASSERTION_MESSAGE_OUTCOME_DELIMITER);
//        if (expected != null || actual != null) {
//            log.append("Expected [").append(expected).append("] but found [").append(actual).append("]");
//        }
//
//        if (ExtentTestHolder.getTest() != null) {
//            ExtentTestHolder.getTest().log(status, log.toString());
//        }
//    }
//}
