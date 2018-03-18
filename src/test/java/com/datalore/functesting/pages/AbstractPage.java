package com.datalore.functesting.pages;

import com.datalore.functesting.tests.DataloreTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractPage {

    protected static WebDriver driver;
    protected static Actions action;
    protected static WebDriverWait wait;
    static {
        driver = DataloreTest.getDriver();
        action = new Actions(driver);
        wait = new WebDriverWait(driver, 20);
    }

    public AbstractPage(){
        PageFactory.initElements(driver, this);
    }
}
