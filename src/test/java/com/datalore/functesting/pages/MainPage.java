package com.datalore.functesting.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MainPage extends AbstractPage {
    @FindBy(id="login-main-email")
    WebElement loginField;

    @FindBy(id="login-main-password")
    WebElement passwordField;

    @FindBy(id="login-submit-button")
    WebElement submitButton;

    public MainPage(){
        driver.get("https://datalore.io");
    }

    public HomePage signIn(){
        try (InputStream input = this.getClass().getClassLoader().getResourceAsStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            loginField.sendKeys(properties.getProperty("user.email"));
            passwordField.sendKeys(properties.getProperty("user.password"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        action.moveToElement(submitButton).click().perform();
        return new HomePage(this);
    }
}