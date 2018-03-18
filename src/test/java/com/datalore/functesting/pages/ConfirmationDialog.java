package com.datalore.functesting.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ConfirmationDialog extends AbstractPage {

    @FindBy(xpath = "//span[@class='popup_title' and contains(text(), 'Confirmation')]")
    private WebElement dialogTitle;

    @FindBy(xpath = "//div[@class='simple-alert_text']")
    private WebElement dialogText;

    @FindBy(xpath = "//span[@class='button_label' and contains(text(), 'OK')]")
    private WebElement okButton;

    @FindBy(xpath = "//span[@class='button_label' and contains(text(), 'Cancel')]")
    private WebElement cancelButton;

    public String getDialogText(){
        wait.until(ExpectedConditions.visibilityOf(dialogText));
        return dialogText.getText();
    }

    public void sendOk(){
        wait.until(ExpectedConditions.elementToBeClickable(okButton)).click();
    }

    public void sendCancel(){
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
    }
}
