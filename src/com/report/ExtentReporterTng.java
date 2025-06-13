//package com.report;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.testng.IClass;
//import org.testng.IExecutionListener;
//import org.testng.IReporter;
//import org.testng.IResultMap;
//import org.testng.ISuite;
//import org.testng.ISuiteResult;
//import org.testng.ITestContext;
//import org.testng.ITestListener;
//import org.testng.ITestResult;
//import org.testng.xml.XmlSuite;
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.markuputils.ExtentColor;
//
////import com.**.atmn.aon.ta.assertion.tng.AonAssertion;
////import com.**.atmn.aon.ta.extent.ExtentAssertionListener;
////import com.**.atmn.aon.ta.extent.ExtentReportsManager;
////import com.**.atmn.aon.ta.extent.ExtentTestManager;
////import com.**.atmn.aon.ta.extent.markup.AnchorLink;
////import com.**.atmn.aon.ta.extent.markup.ExtentMarkupHelper;
////import com.**.atmn.aon.ta.log.MdcTestController;
////import com.**.atmn.aon.ta.metatest.tng.MetaTestManagerTng;
////import com.**.atmn.aon.ta.metatest.tng.MetaTestTng;
////import com.**.atmn.aon.ta.report.tng.AonReporterUtils;
//
//
//public class ExtentReporterTng implements IExecutionListener, ITestListener, IReporter {
//
//    private static final Logger logger = LoggerFactory.getLogger(ExtentReporterTng.class);
//    private static Boolean onExecutionStartDone = false;
//
//    @Override
//    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
//        logger.trace("Generating Extent report for suites: {}", xmlSuites);
//
//        Set<ExtentTest> extentTests = ExtentTestManager.getInstance().getAllTests();
//        int testNgTestResultCountTotal = 0;
//
//        for (ISuite suite : suites) {
//            Map<String, ISuiteResult> suiteResultMap = suite.getResults();
//            int i = 0;
//            for (ISuiteResult r : suiteResultMap.values()) {
//                i++;
//                ITestContext context = r.getTestContext();
//                IResultMap failedWithinSuccessPercentages = context.getFailedButWithinSuccessPercentageTests();
//                IResultMap passed = context.getPassedTests();
//                IResultMap failed = context.getFailedTests();
//                IResultMap skipped = context.getSkippedTests();
//
//                int suiteResultTestResultCount = 0;
//                suiteResultTestResultCount += failedWithinSuccessPercentages.getAllResults().size();
//                suiteResultTestResultCount += passed.getAllResults().size();
//                suiteResultTestResultCount += failed.getAllResults().size();
//                suiteResultTestResultCount += skipped.getAllResults().size();
//
//                testNgTestResultCountTotal += suiteResultTestResultCount;
//
//                logger.trace("TestNg suite result map counts: " + "Suite [{}] ({}/{}), Context [{}], Total [{}], Passed [{}], "
//                        + "Failed But Within Sucess Percentage [{}], Failed [{}], Skipped [{}].",
//                        suite.getName(), i, suiteResultMap.size(), context.getName(), suiteResultTestResultCount,
//                        passed.getAllResults().size(), failedWithinSuccessPercentages.getAllResults().size(),
//                        failed.getAllResults().size(), skipped.getAllResults().size());
//            }
//        }
//
//        if (extentTests.size() != testNgTestResultCountTotal) {
//            logger.error("The number of Extent test results [{}] does not match " + "TestNg result total [{}]. Extent report will not be generated.",
//                    extentTests.size(), testNgTestResultCountTotal);
//        } else {
//            ExtentReports extent = ExtentReportsManager.getInstance().getExtentReports();
//            extent.flush();
//        }
//    }
//
//    @Override
//    public void onTestStart(ITestResult result) {
//        getOrCreateExtentTest(result);
//    }
//
//    @Override
//    public void onTestSuccess(ITestResult result) {
//        ExtentTest extentTest = getOrCreateExtentTest(result);
//        Throwable t = result.getThrowable();
//        if (extentTest != null) {
//            if (t != null) {
//                extentTest.pass(t); // how a test can pass with a throwable I don't know
//            } else {
//                extentTest.pass("TestNG status 'success'");
//            }
//        }
//        ExtentTestManager.getInstance().unsetCurrentThreadTest();
//    }
//
//    @Override
//    public void onTestFailure(ITestResult result) {
//        ExtentTest extentTest = getOrCreateExtentTest(result);
//        Throwable t = result.getThrowable();
//        if (extentTest != null) {
//            if (t != null) {
//                extentTest.fail(t);
//            } else {
//                extentTest.fail("TestNG status 'fail' (no throwable attached)");
//            }
//        }
//        ExtentTestManager.getInstance().unsetCurrentThreadTest();
//    }
//
//    @Override
//    public void onTestSkipped(ITestResult result) {
//        ExtentTest extentTest = getOrCreateExtentTest(result);
//        Throwable t = result.getThrowable();
//        if (extentTest != null) {
//            if (t != null) {
//                extentTest.skip(t);
//            } else {
//                extentTest.skip("TestNG status 'skip' (no throwable attached)");
//            }
//        }
//        ExtentTestManager.getInstance().unsetCurrentThreadTest();
//    }
//
//    @Override
//    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
//        ExtentTest extentTest = getOrCreateExtentTest(result);
//        Throwable t = result.getThrowable();
//        if (extentTest != null) {
//            if (t != null) {
//                extentTest.fail(t);
//                extentTest.pass("TestNG status 'fail but within success percentage'");
//            } else {
//                extentTest.fail("TestNG status 'fail but within success percentage'");
//                extentTest.pass("TestNG status 'fail but within success percentage'");
//            }
//        }
//        ExtentTestManager.getInstance().unsetCurrentThreadTest();
//    }
//
//    @Override
//    public void onStart(ITestContext context) {
//        // nothing to do here
//    }
//
//    @Override
//    public void onFinish(ITestContext context) {
//        // nothing to do here
//    }
//
//    private ExtentTest getOrCreateExtentTest(ITestResult result) {
//        assert result != null : "Parameter should never be null.";
//        assert result.getTestContext() != null : "Result Test Context should never be null.";
//        assert result.getTestContext().getSuite() != null : "Result Test Context Suite should never be null.";
//        assert result.getMethod() != null : "Result TestNGMethod should never be null.";
//        assert result.getMethod().getConstructorOrMethod() != null : "Result ConstructorOrMethod holder should never be null.";
//        assert result.getMethod().getConstructorOrMethod().getMethod() != null : "Result Method should never be null.";
//
//        MetaTestTng essTest = MetaTestManagerTng.getInstance().getOrAddByTestResult(result);
//        ExtentTestManager manager = ExtentTestManager.getInstance();
//
//        /*
//         * If an extent test has been created already, return it immediately
//         */
//        if (manager.hasTestWithKey(essTest)) {
//            return manager.getTestWithKey(essTest);
//        }
//
//        /*
//         * Test does not exist, create test and add to manager
//         */
//        // TODO: load config here that can be used to determine the hierarchy?
//        boolean useSuiteParent = false;
//        boolean useContextParent = false;
//        boolean useClassParent = false;
//        Object parentKey = null;
//
//        if (useSuiteParent) {
//            ISuite suite = result.getTestContext().getSuite();
//            String name = suite.getName() == null ? "-NULL-" : suite.getName();
//            manager.addHierarchyNodeIfNotExisting(suite, parentKey, name, "");
//            parentKey = suite;
//        }
//        if (useContextParent) {
//            ITestContext context = result.getTestContext();
//            String name = context.getName() == null ? "-NULL-" : context.getName();
//            manager.addHierarchyNodeIfNotExisting(context, parentKey, name, "");
//            parentKey = context;
//        }
//        if (useClassParent) {
//            IClass clazz = result.getTestClass();
//            String name = clazz.getRealClass().getSimpleName() == null ? "-NULL-" : clazz.getRealClass().getSimpleName();
//            manager.addHierarchyNodeIfNotExisting(clazz, parentKey, name, clazz.getName());
//            parentKey = clazz;
//        }
//
//        boolean added = manager.addTestIfNotExisting(essTest, parentKey);
//        // If test existed as we would have returned immediately above, so new test should always be added
//        assert added : "Unknown error, a new extent test was created but could not be added to manager.";
//
//        ExtentTest test = manager.getTestWithKey(essTest);
//
//        boolean tagTestWithSuite = true;
//        boolean tagTestWithContext = true;
//        boolean tagTestWithClassName = true;
//
//        if (tagTestWithSuite) {
//            test.assignCategory("Suite::" + result.getTestContext().getSuite().getName());
//        }
//        if (tagTestWithContext) {
//            test.assignCategory("Context::" + result.getTestContext().getName());
//        }
//        if (tagTestWithClassName) {
//            test.assignCategory("Class::" + result.getTestClass().getName());
//        }
//
//        List<String> groups = Arrays.asList(result.getMethod().getGroups());
//        for (String group : groups) {
//            test.assignCategory("Group::" + group);
//        }
//
//        List<String> tags = AonReporterUtils.collectTestTags(essTest);
//        tags.removeAll(groups);
//        for (String tag : tags) {
//            test.assignCategory("Tag::" + tag);
//        }
//
//        StringBuilder almIdsMarkup = new StringBuilder();
//        for (String almId : AonReporterUtils.collectTestAlmIds(essTest)) {
//            if (!StringUtils.isBlank(almId)) {
//                AnchorLink almIdLink = new AnchorLink();
//                almIdLink.setHref("https://rally1.rallydev.com/#/46772661628d/search?keywords=" + almId);
//                almIdLink.setInnerText(almId);
//                almIdLink.setAttribute("style", "color: inherit;");
//                almIdsMarkup.append(ExtentMarkupHelper.createAonLabel(almIdLink.getMarkup(), ExtentColor.PURPLE).getMarkup());
//            }
//        }
//        if (!StringUtils.isBlank(almIdsMarkup)) {
//            test.info(ExtentMarkupHelper.createNoOpDetails(almIdsMarkup.toString()));
//        }
//
//        // TODO: resolve file name vs mdcKey, LogMangager provide file name from key?
//        String logHref = "logs/" + MdcTestController.MDC_TEST_LOG_SIFTING_VALUE_PREFIX + essTest.getMdcKey() + ".html";
//        test.info(ExtentMarkupHelper.createLink(logHref, "Test Log"));
//
//        return test;
//    }
//
//    @Override
//    public void onExecutionStart() {
//        synchronized (onExecutionStartDone) {
//            if (!onExecutionStartDone) {
//                logger.trace("Extent 'on execution start', registering assertion listener...");
//                AonAssertion.registerGlobalAssertionListener(new ExtentAssertionListener());
//                onExecutionStartDone = true;
//            }
//        }
//    }
//
//    @Override
//    public void onExecutionFinish() {
//        logger.trace("Extent 'on execution finish'.");
//    }
//}