package com.cogmento.automation.web.page;

import com.cogmento.automation.web.page.GenericPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.NoSuchElementException;


/**
* @author  Code generated by ATFG (Automation Test Framework Generator) created by Harini Kumar <harini.kumar.email@gmail.com> & Shashikanth Rao <shashikanth.h.rao@gmail.com>
* @version 1.0
* @since   2021-09-20 
*/

public class HomePage extends GenericPage {
	
		//ElementType:def  Description:Default Element. Link, button etc.. That are clickable
	private By signUpLocator = By.xpath("//span[contains(text(),'sign up')]");

	//ElementType:def  Description:Default Element. Link, button etc.. That are clickable
	private By loginLocator = By.xpath("//span[contains(text(),'Log In')]");

	public HomePage(WebDriver driver) {
		super(driver);
	}
	//signUp methods
	 
	public By getSignUpLocator() {
		return signUpLocator;
	}
	
	public WebElement getSignUp() {
		return getDriver().findElement(signUpLocator);		
	}

	public void goToSignUp() {
		Actions actions = new Actions(getDriver());
		actions.moveToElement(getSignUp()).build().perform();					
	}
	
	public void clickSignUp() {
		getSignUp().click();
		//Alternative 1:  new Actions(getDriver()).moveToElement(getSignUp()).click().build().perform();
		//Alternative 2:  ((JavascriptExecutor)getDriver()).executeScript("arguments[0].click()", getSignUp());								
	}
	//login methods
	 
	public By getLoginLocator() {
		return loginLocator;
	}
	
	public WebElement getLogin() {
		return getDriver().findElement(loginLocator);		
	}

	public void goToLogin() {
		Actions actions = new Actions(getDriver());
		actions.moveToElement(getLogin()).build().perform();					
	}
	
	public void clickLogin() {
		getLogin().click();
		//Alternative 1:  new Actions(getDriver()).moveToElement(getLogin()).click().build().perform();
		//Alternative 2:  ((JavascriptExecutor)getDriver()).executeScript("arguments[0].click()", getLogin());								
	}

}