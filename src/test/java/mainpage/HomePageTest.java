package mainpage;

import static org.testng.Assert.assertEquals;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class HomePageTest {
	
	@Test
	public void testHomePage() throws InterruptedException {
		System.out.println("Prepare Test");
		System.setProperty("webdriver.chrome.driver",
				"./src/test/resources/driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		System.out.println("Prepare navigating to gobear.com");
		driver.get("https://www.gobear.com/ph?x_session_type=UAT");
		Thread.sleep(5000);
		System.out.println("At gobear.com");
		WebElement btnShowResult = driver.findElement(By.name("product-form-submit"));
		if (btnShowResult != null) {
			btnShowResult.click();
		}
		
		System.out.println("Before sleep");
		Thread.sleep(10000);
		System.out.println("After sleep");
		driver.close();
	}
	
	/**
	 * 
	 * This function will take screenshot
	 * 
	 * @param webdriver
	 * 
	 * @param fileWithPath
	 * 
	 * @throws Exception
	 * 
	 */

//	public static void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception {
//
//		// Convert web driver object to TakeScreenshot
//		TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
//		// Call getScreenshotAs method to create image file
//		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
//		// Move image file to new destination
//		File DestFile = new File(fileWithPath);
//
//		// Copy file at destination
//
//		FileUtils.copyFile(SrcFile, DestFile);
//
//	}

}
