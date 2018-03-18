package com.datalore.functesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;

public class HomePage extends AbstractPage {

    @FindBy(xpath="//*[@class='button_icon icon icon-light icon icon-document new_document new_workbook_item']")
    WebElement newWorkbookButton;

    @FindBy(xpath = "//*[@class='button_icon icon icon-trash delete_item']")
    WebElement deleteButton;

    MainPage mainPage;

    public HomePage(MainPage mainPage){
        this.mainPage = mainPage;
    }

    public HomePage createNewWorkBook(String workbookName){
        newWorkbookButton.click();
        WebElement newWorkBook = driver.findElement(By.xpath("//*[@title='New Workbook' and contains(@style, 'auto')]"));
        newWorkBook.clear();
        newWorkBook.sendKeys(workbookName);
        newWorkBook.sendKeys(Keys.ENTER);
        return this;
    }

    public HomePage deleteWorkBook(String workbookName){
        WebElement workbook = findWorkbook(workbookName);
        action.click(workbook).perform();
        deleteButton.click();
        return this;
    }

    public WorkBookPage openWorkBook(String workbookName){
        WebElement workbook = findWorkbook(workbookName);
        action.doubleClick(workbook).perform();

        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        return new WorkBookPage(this);
    }

    private WebElement findWorkbook(String workbookName){
        WebElement workbook = driver.findElement(By.xpath("//*[@title='"+workbookName+"']"));
        return workbook;
    }
}