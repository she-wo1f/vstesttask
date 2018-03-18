package com.datalore.functesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;

public class WorkBookPage extends AbstractPage {

    @FindBy(xpath = "//span[@class='cr-menu-section_title ts-menu-section-text' and contains(text(), 'Tools')]")
    WebElement toolsMenu;

    WebElement libraryManagerItem;

    HomePage homePage;

    public WorkBookPage(HomePage homePage){
        this.homePage = homePage;
    }

    public LibraryManagerPage openLibraryManager(){
        toolsMenu.click();
        libraryManagerItem = driver.findElement(By.xpath("//span[@class='ts-menu-item-text' and contains(text(), 'Library Manager')]"));
        libraryManagerItem.click();
        return new LibraryManagerPage();
    }

    public ConfirmationDialog getConfirmationDialog(){
        return new ConfirmationDialog();
    }

    public HomePage close(){
        driver.close();
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
        return this.homePage;
    }
}