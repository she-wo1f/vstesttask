package com.datalore.functesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class LibraryManagerPage extends AbstractPage {

    @FindBy(xpath = "//span[@class='popup_title']")
    protected WebElement pageTitle;

    @FindBy(xpath = "//span[@data-state='installed']")
    protected WebElement installedTab;

    @FindBy(xpath = "//span[@class='suggest_text_control' and contains(text(), 'show all installed')]")
    protected WebElement showAllInstalled;

    @FindBy(xpath = "//input[@class='suggest_input input']")
    protected WebElement searchField;

    @FindBy(xpath = "//span[@class='dl-library-item_name']")
    protected List<WebElement> installedPackageNames;

    @FindBy(xpath = "//span[@class='dl-library-item_version']")
    protected List<WebElement> installedPackageVersions;

    @FindBy(xpath = "//span[@class='dl-library-item_source']")
    protected List<WebElement> installedPackageSources;

    @FindBy(xpath = "//span[@class='popup-close iconic iconic-sm iconic-x-thin popup_close']")
    protected WebElement closeIcon;

    public LibraryManagerPage showAllInstalledPackages(){
        showAllInstalled.click();
        return this;
    }

    public int getPackageIndex(String packageName){
        for (WebElement element : installedPackageNames){
            if (element.getText().equals(packageName)){
                return installedPackageNames.indexOf(element);
            }
        }
        return -1;
    }

    public boolean isInstalledPackage(String packageName){
        return driver.findElements(By.xpath("//span[@class='dl-library-item_name' and text() = '"+packageName+"']")).size() != 0;
    }

    public String getPackageName(int index){
        return installedPackageNames.get(index).getText();
    }

    public String getPackageVersion(int index){
        return installedPackageVersions.get(index).getText();
    }

    public String getPackageSource(int index){
        return installedPackageSources.get(index).getText();
    }

    public LibraryPackageDetails openPackageDetails(int index){
        try {
            Thread.sleep(2000l); //to avoid StaleElementReferenceException in line 81
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.elementToBeClickable(installedPackageNames.get(index))).click();
        return new LibraryPackageDetails();
    }

    public LibraryPackageDetails searchForPackage(String name){
        searchField.sendKeys(name);
        try {
            Thread.sleep(2000l); //to avoid StaleElementReferenceException in line 81
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement searchItem = driver.findElement(By.xpath("//li[@class='dropdown_item']/span[text()='"+name+"']"));
        wait.until(ExpectedConditions.visibilityOf(searchItem)).click();
        return new LibraryPackageDetails();
    }

    public boolean isInitialized() {
        return wait.until(visibilityOf(pageTitle))
                .getText().equals("Library Manager")
                && wait.until(visibilityOf(installedTab))
                .getAttribute("class").equals("tab-header tab-header-selected");
    }

    public void close(){
        closeIcon.click();
    }
}