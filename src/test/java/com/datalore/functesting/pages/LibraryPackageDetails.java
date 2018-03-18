package com.datalore.functesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LibraryPackageDetails extends LibraryManagerPage {

    @FindBy(xpath = "//span[@class='library-info-name']")
    protected WebElement packageName;

    @FindBy(xpath = "//button[@class='button action-remove is-action']")
    protected WebElement removeButton;

    @FindBy(xpath = "//button[@class='button button-type-action is-action action-install']")
    protected WebElement installButton;

    @FindBy(xpath = "//button[@class='button button-type-action is-action action-update']")
    protected WebElement updateButton;

    @FindBy(xpath = "//span[@class='select_button']")
    protected WebElement changePackageVersion;

    private String version;
    private String source;
    private LibraryActionReport actionReport;

    public String getPackageName(){
        return wait.until(ExpectedConditions.visibilityOf(packageName)).getText();
    }

    public String getVersion(){
        return version;
    }

    public String getSource(){
        return source;
    }

    public LibraryActionReport getActionReport(){
        return actionReport;
    }

    public void installPackage(){
        readVersion();
        readSource();
        wait.until(ExpectedConditions.elementToBeClickable(installButton)).click();
        new ConfirmationDialog().sendOk();
        actionReport = new LibraryActionReport();
    }

    public void removePackage(){
        wait.until(ExpectedConditions.elementToBeClickable(removeButton)).click();
        new ConfirmationDialog().sendOk();
        actionReport = new LibraryActionReport();
    }

    public void updatePackage(){
        wait.until(ExpectedConditions.elementToBeClickable(changePackageVersion)).click();
        WebElement newVersion = driver.findElement(By.xpath("//li[@class='dropdown_item']"));
        newVersion.click();

        readVersion();
        readSource();

        wait.until(ExpectedConditions.elementToBeClickable(updateButton)).click();

        new ConfirmationDialog().sendOk();
        actionReport = new LibraryActionReport();
    }

    public boolean canBeUpdated(){
        wait.until(ExpectedConditions.elementToBeClickable(changePackageVersion)).click();
        List<WebElement> availableVersions = driver.findElements(By.xpath("//li[@class='dropdown_item']"));
        changePackageVersion.click();
        return availableVersions.size() != 0;
    }

    public boolean isRemoveButtonDisplayed(){
        try {
            Thread.sleep(2000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return removeButton.isDisplayed();
    }

    public boolean isInstallButtonDisplayed(){
        try {
            Thread.sleep(2000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return installButton.isDisplayed();
    }

    public boolean isUpdateButtonDisplayed(){
        try {
            Thread.sleep(2000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return updateButton.isDisplayed();
    }

    private void readVersion(){
        wait.until(ExpectedConditions.visibilityOf(changePackageVersion));
        version = changePackageVersion.getText().split(" ")[0];
    }

    private void readSource(){
        wait.until(ExpectedConditions.visibilityOf(changePackageVersion));
        Pattern pattern = Pattern.compile("(conda|pip)");
        Matcher matcher = pattern.matcher(changePackageVersion.getText());
        if (matcher.find()){
            source = matcher.group(0);
        }
    }
}