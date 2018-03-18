package com.datalore.functesting.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.datalore.functesting.tests.DataloreTest;
import org.apache.commons.io.FileUtils;
import org.junit.AssumptionViolatedException;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class TestLogger extends TestWatcher {

    static ExtentHtmlReporter htmlReporter;
    static ExtentReports testReport;
    static WebDriver driver;
    static String testReportPath;
    static {
        testReportPath = DataloreTest.getTestReportPath();
        driver = DataloreTest.getDriver();
        new File(testReportPath+"/screenshots").mkdirs();
        htmlReporter = new ExtentHtmlReporter(testReportPath
                + "/TestReport.html");
        htmlReporter.config().setDocumentTitle("Datalore test report");
        htmlReporter.config().setReportName("Datalore test report");
        testReport = new ExtentReports();
        testReport.attachReporter(htmlReporter);
    }

    ExtentTest test;

    @Override
    protected void starting(Description desc){
        test = testReport.createTest(desc.getTestClass().getSimpleName() + "." + desc.getMethodName());
    }

    @Override
    protected void succeeded(Description description) {
    }

    @Override
    protected void failed(Throwable e, Description desc) {
        test.fail(e);
        try {
              test.addScreenCaptureFromPath("./screenshots/"
                    + desc.getMethodName()
                    + ".jpg");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    protected void skipped(AssumptionViolatedException e, Description desc) {
        test.warning(e);
        try {
            test.addScreenCaptureFromPath("./screenshots/"
                    + desc.getMethodName()
                    + ".jpg");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    protected void finished(Description desc){
        testReport.flush();
    }

    public void makeScreenshot(TestName testName){
        String screenshotPath = testReportPath
                + "/screenshots/"
                + testName.getMethodName()
                + ".jpg";

        File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src,
                    new File(screenshotPath));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}