package com.cogmento.automation.web.test;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cogmento.automation.web.page.CompanyAddOrEditPage;
import com.cogmento.automation.web.page.HomePage;
import com.cogmento.automation.web.page.LoginPage;
import com.cogmento.automation.web.page.UserPageTemplate;
import com.cogmento.automation.web.tool.BrowserUtil;
import com.cogmento.automation.web.tool.ConfigUtil;
import com.cogmento.automation.web.tool.ExcelUtil;

public class MyTest {
	WebDriver driver;

	@BeforeMethod
	@Parameters({ "browser", "baseUrl" })
	public void setup(String browser, String baseUrl) {
		System.out.println(baseUrl);
		driver = BrowserUtil.getWebDriver(browser);
		driver.get(baseUrl);
	}

	@Test(dataProvider = "companyDataProvider")
	public void addCompanyTest(Map<String, String> company) {
		HomePage homePage = new HomePage(driver);
		homePage.clickLogin();
		LoginPage loginPage = new LoginPage(driver);
		loginPage.setTextUsername(ConfigUtil.getProperty("default.username"));
		loginPage.setTextPassword(ConfigUtil.getProperty("default.password"));
		loginPage.clickLogin();
		UserPageTemplate userPageTemplate = new UserPageTemplate(driver);

		userPageTemplate.clickCompanyAdd();
		BrowserUtil.sleep(3000);

		// Actions action = new Actions(driver);
		// action.moveByOffset(500, 500).contextClick().perform();

		CompanyAddOrEditPage companyAddOrEditPage = new CompanyAddOrEditPage(driver);
		companyAddOrEditPage.goToCancel();// to move away from dynamic left menu
		//companyAddOrEditPage.setTextName("NewCompany");
		companyAddOrEditPage.setTextWebsite("www.google.com");
		BrowserUtil.sleep(3000);

		// companyAddOrEditPage.initialize(company);
		// BrowserUtil.sleep(3000);
		// companyAddOrEditPage.clickSave();
	}

	@DataProvider
	public Object[][] companyDataProvider() {
		return ExcelUtil.getTestDataMaps("company");
	}

	@DataProvider
	public Object[][] someEntityDataProvider() {
		return ExcelUtil.getTestDataMaps("someEntity");
	}

	// @Test(dataProvider = "someEntityDataProvider")
	public void someEntityTest(Map<String, String> someEntity) {
		// TODO
		// reference code only.
	}

	@AfterMethod(alwaysRun = true)
	public void teardown() {
		// BrowserUtil.sleep(5000);
		driver.quit();
	}

}