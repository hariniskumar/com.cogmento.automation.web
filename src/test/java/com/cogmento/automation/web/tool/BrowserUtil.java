/**
 * 
 */
package com.cogmento.automation.web.tool;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

/**
 * @author Harini
 *
 */
public class BrowserUtil {

	private static String webDriverRepositoryPath;
	private static long pageTimeOutSec;
	private static long implicitWaitMilliSec;
	private static boolean enableScreenshot;

	private static int counterForScreenshots;

	static {
		webDriverRepositoryPath = ConfigUtil.getProperty("webDriverRepositoryPath");
		if (webDriverRepositoryPath == null) {
			webDriverRepositoryPath = System.getProperty("user.dir") + "/webdriver/";
		}

		System.setProperty("webdriver.gecko.driver", webDriverRepositoryPath + ConfigUtil.getProperty("firefoxDriverPath"));

		System.setProperty("webdriver.chrome.driver", webDriverRepositoryPath + ConfigUtil.getProperty("chromeDriverPath"));

		System.setProperty("webdriver.ie.driver", webDriverRepositoryPath + ConfigUtil.getProperty("ieDriverPath"));

		System.setProperty("webdriver.edge.driver", webDriverRepositoryPath + ConfigUtil.getProperty("edgeDriverPath"));

		pageTimeOutSec = Long.parseLong(ConfigUtil.getProperty("pageLoadTimeOutSec"));
		implicitWaitMilliSec = Long.parseLong(ConfigUtil.getProperty("implicitWaitMilliSec"));
		if (ConfigUtil.getProperty("enableScreenshot").toLowerCase().equals("true")) {
			enableScreenshot = true;
			File screenshotDirectory = new File(System.getProperty("user.dir") + "/screenshot");
			try {
				FileUtils.cleanDirectory(screenshotDirectory);
			} catch (IOException e) {
				System.out.println("unable to clean screenshot directory");
				e.printStackTrace();
			}
		}
	}

	public static WebDriver getWebDriver(String browserName) {
		WebDriver driver = null;
		switch (browserName.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "ie":
			driver = getIeDriver();
			break;
		case "edge":
			driver = getEdgeDriver();
			break;
		default:
			driver = new ChromeDriver();
		}
		initializeBrowserConfig(driver);
		driver = convertToEventFiringWebDriver(driver);
		return driver;
	}

	private static void initializeBrowserConfig(WebDriver driver) {
		driver.manage().timeouts().pageLoadTimeout(pageTimeOutSec, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(implicitWaitMilliSec, TimeUnit.MILLISECONDS);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
	}

	private static WebDriver convertToEventFiringWebDriver(WebDriver driver) {
		EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(driver);
		eventFiringWebDriver.register(new WebDriverEventListenerImpl());
		return eventFiringWebDriver;
	}

	public static void sleep(long sleepMilliSec) {
		try {
			Thread.sleep(sleepMilliSec);
		} catch (InterruptedException e) {
		}
	}

	private static WebDriver getIeDriver() {
		/*
		 * IE needs additional settings to work properly:
		 * 
		 * 1. add the capability to ignore zoom settings - through code
		 * 
		 * 2. Cookies are not deleted when driver.manage().deleteAllCookies() is
		 * invoked. Workaround is adopted below to add the capability to ensure clean
		 * session
		 * 
		 * 3. MANUAL: set the add the domain freecrm.com to the list of trusted sites in
		 * IE. Open IE and manually to the list of trusted sites under IE -> Internet
		 * Options -> Security -> Trusted Sites --> Sites
		 * 
		 * 4. Also use the 32 bit version of IE even if running on 64bit OS/machine. The
		 * sendkeys() method is super slow with the 64 bit driver. It are normal in 32
		 * bit version of IE
		 */
		InternetExplorerOptions ieOptions = new InternetExplorerOptions();
		ieOptions.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
		ieOptions.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
		WebDriver driver = new InternetExplorerDriver(ieOptions);
		return driver;
	}

	private static WebDriver getEdgeDriver() {
		/*
		 * Edge needs additional settings to work properly. Cookies are not deleted when
		 * driver.manage().deleteAllCookies() is invoked. So the workaround below is
		 * adopted to open browser in private mode.
		 */
		EdgeOptions edgeOptions = new EdgeOptions();
		edgeOptions.setCapability("InPrivate", true);
		WebDriver driver = new EdgeDriver(edgeOptions);
		return driver;
	}

	public static void takeScreenshot(WebDriver driver, String url) {
		if (enableScreenshot) {
			// SimpleDateFormat simpleDateFormat = new
			// SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
			String screenShotFileName = System.getProperty("user.dir") + "/screenshot/" + generateFileNameFromUrl(url)
					+ "_" + (++counterForScreenshots) + ".png";
			File screenshotFile = new File(screenShotFileName);
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			try {
				// The source file is automatically removed by the OS since it is created in
				// Temp directory
				FileUtils.moveFile(scrFile, screenshotFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static String generateFileNameFromUrl(String url) {
		String uniqueName = url.replace("http://", "").replace("https://", "").replace("/", ".").replace(":", ".");
		if (uniqueName.endsWith(".")) {
			uniqueName = uniqueName.substring(0, uniqueName.length() - 1);
		}
		return uniqueName;
	}

	public static String getBrowserName(WebDriver driver) {
		Capabilities cap = ((EventFiringWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName().toLowerCase();
		return browserName;
	}
}
