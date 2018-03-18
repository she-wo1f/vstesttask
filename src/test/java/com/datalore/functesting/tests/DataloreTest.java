package com.datalore.functesting.tests;

import com.datalore.functesting.pages.HomePage;
import com.datalore.functesting.pages.MainPage;
import com.datalore.functesting.pages.WorkBookPage;
import com.datalore.functesting.utils.TestLogger;
import org.hamcrest.Matcher;
import org.junit.*;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

public abstract class DataloreTest {

    static WebDriver driver;
    static HomePage homePage;
    static WorkBookPage workBookPage;
    static String testReportPath;
    static {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
        testReportPath = "test-reports/"+dateFormat.format(new Date());
    }

    @Rule
    public final TestName testName = new TestName();

    @Rule
    public final TestLogger watchman = new TestLogger();

    @BeforeClass
    public static void setUpClass(){
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        homePage = new MainPage()
                .signIn();
    }

    @AfterClass
    public static void cleanUpClass(){
        driver.quit();
    }

    public static String getTestReportPath(){
        return testReportPath;
    }

    public static WebDriver getDriver(){
        return driver;
    }

    public <T> void validateResult(String message, T actual, Matcher<T> matcher){
        try{
            assertThat(message, actual, matcher);
        }
        catch (AssertionError e){
            watchman.makeScreenshot(testName);
            throw e;
        }
    }

    public <T> void validatePrecondition(String message, T actual, Matcher<T> matcher){
        try{
            assumeThat(message, actual, matcher);
        }
        catch (AssumptionViolatedException e){
            watchman.makeScreenshot(testName);
            throw e;
        }
    }
}