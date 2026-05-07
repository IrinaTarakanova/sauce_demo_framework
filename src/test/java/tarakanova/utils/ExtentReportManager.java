package tarakanova.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

    private static ExtentReports extent;

   public static ExtentReports getReport(){
        if(extent == null) {
            String path = System.getProperty("user.dir") + ("/reports/ExtentReport.html");
            ExtentSparkReporter spark = new ExtentSparkReporter(path);
            spark.config().setReportName("saucedemo automation test report");
            spark.config().setDocumentTitle("Test Execution Results");
            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Tester", "Irina Tarakanova");
        }
        return extent;
    }
}
