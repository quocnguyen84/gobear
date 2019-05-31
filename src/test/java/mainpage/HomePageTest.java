package mainpage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertNotNull;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class HomePageTest {
	
	@Test
	public void testHomePage() throws Exception {
		System.out.println("Prepare Test");
		System.setProperty("webdriver.chrome.driver",
				"./src/test/resources/driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
//		System.out.println("Step 01: Navigate to gobear.com");
//		driver.get("https://www.gobear.com/ph?x_session_type=UAT");
//		Thread.sleep(3000);
//		takeSnapShot(driver, "./screenshots/Step01.png");
//		System.out.println("Step02: Click on href with '#Insurance' value to ensure we are at Insurance section");
//		WebElement insuranceHref = driver.findElement(By.xpath("//a[@href=\"#Insurance\"]"));
		
//		if (insuranceHref != null) {
//			insuranceHref.click();
//		}
//		Thread.sleep(1000);
//		takeSnapShot(driver, "./screenshots/Step02.png");
//		System.out.println("Step03: click on href with '#Travel' value to ensure we are at Travel Insurance section");
//		WebElement travelHref = driver.findElement(By.xpath("//a[@href=\"#Travel\"]"));
//		if (travelHref != null) {
//			travelHref.click();
//		}
//		Thread.sleep(1000);
//		takeSnapShot(driver, "./screenshots/Step03.png");
//		System.out.println("Step04: Keep default value and click on 'Show My Result' button");
//		// We could argue that we should use text 'Show My Result' instead but I believe that button name is more stable way
//		WebElement btnShowResult = driver.findElement(By.name("product-form-submit"));
//		if (btnShowResult != null) {
//			btnShowResult.click();
//		}
//		Thread.sleep(2000);
//		takeSnapShot(driver, "./screenshots/Step04.png");
		// Quick and dirty way to go directly to Travel section
		driver.get("https://www.gobear.com/ph/insurance/travel/quote-online?triptype=single&covertype=individual&destination=Hong%20Kong&adults=1&children=&startdate=2019-06-07&enddate=2019-06-16");
		Thread.sleep(3000);
		// Get list of card result
		List<WebElement> cardList=driver.findElements(By.cssSelector(".col-sm-4.card-full"));
		if (cardList != null) {
			assertTrue(cardList.size() >= 3, "There are less than 3 displayed cards");
		}
		System.out.println("Step05: Click on filter insurer 'Malayan Insurance' option");
		String filterOption = "Malayan Insurance";
		filterByInsurer(driver, filterOption);
		Thread.sleep(2000);
		// Ensure that every card has title 'Malayan Insurance' in order to verify that filter is worked!
		verifyAllCardHeader(driver, filterOption);
		takeSnapShot(driver, "./screenshots/Step05.png");
		System.out.println("Step06: Clear the filter 'Malayan Insurance' by clicking the same option again");
		filterByInsurer(driver, filterOption);
		Thread.sleep(2000);
		takeSnapShot(driver, "./screenshots/Step06.png");
		driver.close();
	}
	
	/**
	 * This function will click on a Filter-Insurers option
	 * 
	 * @param webdriver
	 * @param label label of insurer option
	 * 
	 */
	private void filterByInsurer(WebDriver driver, String label) {
		if (driver != null && StringUtils.isNotEmpty(label)) {
			WebElement filterOption = driver.findElement(By.xpath("//label[contains(text(), '" + label +"')]"));
			if (filterOption != null) {
				filterOption.click();
			}
		}
	}
	
	/**
	 * This function will verify all current card header has the same as expected header
	 * 
	 * @param webdriver
	 * @param expectedHeader expected header
	 * 
	 */
	private void verifyAllCardHeader(WebDriver driver, String expectedHeader) {
		// Get all current card
		List<WebElement> cardList=driver.findElements(By.cssSelector(".col-sm-4.card-full"));
		assertNotNull(cardList, "Can not find current card list");
		assertTrue(cardList.size() > 0, "There should have at least one card");
		for (WebElement tempCard : cardList) {
			WebElement currentCardHead = tempCard.findElement(By.cssSelector(".card-wrapper"));
			assertNotNull(currentCardHead);
			assertEquals(currentCardHead.getAttribute("data-insuer-name"), expectedHeader);
		}
	}
	
	/**
	 * This function will take screenshot
	 * 
	 * @param webdriver
	 * @param fileWithPath
	 * 
	 * @throws Exception
	 * 
	 */
	public static void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception {
		// Convert web driver object to TakeScreenshot
		TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
		// Call getScreenshotAs method to create image file
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		// Move image file to new destination
		File DestFile = new File(fileWithPath);
		// Copy file at destination
		FileUtils.copyFile(SrcFile, DestFile);
	}
}
