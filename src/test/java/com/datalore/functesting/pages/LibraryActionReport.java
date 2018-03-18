package com.datalore.functesting.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class LibraryActionReport extends AbstractPage {
    @FindBy(xpath = "//div[@class='dl-library-report_action-name']")
    private WebElement actionName;

    @FindBy(xpath = "//div[@class='dl-library-report_text']/span")
    private WebElement actionResult;

    @FindBy(xpath = "//span[@class='button' and contains(text(), 'Close')]")
    private WebElement closeButton;

    public String getReportActionName(){
        wait.until(visibilityOf(actionName));
        return actionName.getText();
    }

    public String getReportActionResult(){
        wait.until(visibilityOf(actionResult));
        return actionResult.getText();
    }

    public void close(){
        try {
            Thread.sleep(2000l); //it seems webdriver wait doesn't help here
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        closeButton.click();
    }
}
