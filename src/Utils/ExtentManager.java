
package Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public class ExtentManager {

    private static ExtentReports extent;

    public static synchronized ExtentReports getReportObject() {

        if (extent == null) {

            String projectPath = System.getProperty("user.dir");

            File reportDir = new File(projectPath + "/reports");

            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }

            String path = projectPath + "/reports/ExtentReport.html";

            ExtentSparkReporter report =
                    new ExtentSparkReporter(path);

            report.config().setReportName(
                    "Demo Web Shop Automation Results");

            report.config().setDocumentTitle("Test Results");

            extent = new ExtentReports();

            extent.attachReporter(report);

            extent.setSystemInfo("Application", "Demo Web Shop");
            extent.setSystemInfo("Tester",
                    System.getProperty("user.name"));
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("Framework", "Selenium + TestNG + POM");
        }

        return extent;
    }
}
