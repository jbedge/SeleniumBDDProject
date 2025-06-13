//package com.report;
//
//import java.util.HashMap;
//import java.util.Map;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
//import com.aventstack.extentreports.reporter.configuration.Theme;
//
//public class ExtentReportsManager {
//    private static final Logger logger = LoggerFactory.getLogger(ExtentReportsManager.class);
//
//    private static ExtentReportsManager instance;
//    private static ExtentReports extent;
//
//    private static final String[] REPORT_ENV_KEYS = new String[] {
//        "GIT_BRANCH", "GIT_COMMIT", "GIT_URL", "JOB_NAME", "ENABLE_AIM_RESULT_UPLOAD", "RUN_SUITE"
//    };
//
//    private static final String[] REPORT_SYS_PROP_KEYS = new String[] {
//        "java.vendor", "java.version", "os.name", "os.version"
//    };
//
//    private ExtentReportsManager() {
//        // private constructor
//    }
//
//    public static synchronized ExtentReportsManager getInstance() {
//        if (instance == null) {
//            instance = new ExtentReportsManager();
//        }
//        return instance;
//    }
//
//    public synchronized ExtentReports getExtentReports() {
//        if (extent == null) {
//            extent = new ExtentReports();
//            initConfiguration();
//        }
//        return extent;
//    }
//
//    private void initConfiguration() {
//        String reportName = PropertiesUtils.getProperty("extent.report.name");
//        if (reportName == null || reportName.trim().isEmpty()) {
//            reportName = "ExtentReport";
//        }
//        String reportPath = PropertiesUtils.getProperty("extent.report.path");
//        if (reportPath == null || reportPath.trim().isEmpty()) {
//            reportPath = "test-output/ExtentReport.html";
//        }
//        ExtentHtmlReporter html = new ExtentHtmlReporter(reportPath);
//        html.config().setDocumentTitle(reportName);
//        html.config().setReportName(reportName);
//        // Example: set report theme based on property.
//        String theme = PropertiesUtils.getProperty("extent.report.theme");
//        if ("DARK".equalsIgnoreCase(theme)) {
//            html.config().setTheme(Theme.DARK);
//        } else {
//            html.config().setTheme(Theme.STANDARD);
//        }
//        extent.attachReporter(html);
//
//        // Attach system/environment info
//        Map<String, String> sysProps = System.getProperties()
//            .stringPropertyNames().stream()
//            .collect(HashMap::new,
//                     (m, key) -> m.put(key, System.getProperty(key)),
//                     Map::putAll);
//
//        for (String key : REPORT_SYS_PROP_KEYS) {
//            String val = System.getProperty(key);
//            if (val != null) {
//                extent.setSystemInfo(key, val);
//            }
//        }
//
//        for (String key : REPORT_ENV_KEYS) {
//            String val = System.getenv(key);
//            if (val != null) {
//                extent.setSystemInfo(key, val);
//            }
//        }
//    }
//}
