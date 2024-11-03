package com.cogmento.automation.web.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GenericPage {
	private WebDriver driver;

	public GenericPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public boolean isElementPresent(By locator) {
		return (driver.findElements(locator).size() > 0) ? true : false;
	}

	public boolean isElementDisplayed(WebElement element) {
		return element.isDisplayed();
	}

	public boolean isElementEnabled(WebElement element) {
		return element.isEnabled();
	}

	public boolean isElementPresentDisplayedAndEnabled(By locator, WebElement element) {
		return isElementPresent(locator) && isElementDisplayed(element) && isElementEnabled(element);
	}
}
