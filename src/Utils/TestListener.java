
package Utils;

import Tests.MainTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class TestListener implements ITestListener {

    ExtentReports extent = ExtentManager.getReportObject();

    ExtentTest test;

    @Override
    public void onTestStart(ITestResult result) {

        test = extent.createTest(result.getMethod().getMethodName());

        test.info("Test Started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        test.pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        test.fail(result.getThrowable());

        WebDriver driver = MainTest.driver;

        if (driver != null) {

            try {

                File source = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.FILE);

                Path screenshotDir = Paths.get(
                        System.getProperty("user.dir"),
                        "screenshots");

                Files.createDirectories(screenshotDir);

                String fileName = result.getName()
                        + "_" + System.currentTimeMillis() + ".png";

                Path target = screenshotDir.resolve(fileName);

                Files.copy(
                        source.toPath(),
                        target,
                        StandardCopyOption.REPLACE_EXISTING
                );

                test.fail("Failure Screenshot",
                        MediaEntityBuilder
                                .createScreenCaptureFromPath(
                                        target.toAbsolutePath().toString())
                                .build());

            } catch (Exception e) {

                test.warning("Screenshot capture failed: "
                        + e.getMessage());
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        test.skip("Test Skipped");

        if (result.getThrowable() != null) {
            test.skip(result.getThrowable());
        }
    }

    @Override
    public void onFinish(ITestContext context) {

        extent.flush();
    }
}
