package tarakanova.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ExtentReportManager handles the configuration and management of ExtentReports
 * for generating detailed HTML test execution reports.
 *
 * Uses Singleton pattern to ensure only one ExtentReports instance exists.
 *
 * @author Irina Tarakanova
 * @version 1.0
 */
public class ExtentReportManager {
    private static final Logger logger = LoggerFactory.getLogger(ExtentReportManager.class);
    private static ExtentReports extent;

    /**
     * Gets the singleton ExtentReports instance.
     * Creates and configures the report if it doesn't exist.
     *
     * @return ExtentReports instance configured for the test suite
     */
    public static ExtentReports getReport(){
        logger.debug("Requesting ExtentReports instance");

        if(extent == null) {
            logger.info("Creating new ExtentReports instance");

            // Define report file path
            String path = System.getProperty("user.dir") + ("/reports/ExtentReport.html");
            logger.debug("Report will be saved to: {}", path);

            // Create ExtentSparkReporter for HTML reports
            ExtentSparkReporter spark = new ExtentSparkReporter(path);
            logger.debug("ExtentSparkReporter created");

            // Configure report metadata
            spark.config().setReportName("saucedemo automation test report");
            spark.config().setDocumentTitle("Test Execution Results");
            logger.debug("Report configuration set - Name: 'saucedemo automation test report', Title: 'Test Execution Results'");

            // Initialize ExtentReports and attach reporter
            extent = new ExtentReports();
            extent.attachReporter(spark);
            logger.debug("ExtentSparkReporter attached to ExtentReports");

            // Set system information
            extent.setSystemInfo("Tester", "Irina Tarakanova");
            logger.info("System info set - Tester: Irina Tarakanova");

            logger.info("ExtentReports instance created and configured successfully");
        } else {
            logger.debug("Returning existing ExtentReports instance");
        }

        return extent;
    }
}
