package mainpage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertNotNull;

import java.io.File;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
		
		System.out.println("Step05: Click on 'Promos only' option");
		selectPromotionTypeFilter(driver, "Promos only");
		Thread.sleep(2000);
		takeSnapShot(driver, "./screenshots/Step05.png");
		System.out.println("Step06: 'Clear All' filter");
		clearAllFilter(driver);
		Thread.sleep(2000);
		takeSnapShot(driver, "./screenshots/Step06.png");
		
		System.out.println("Step07: Click on filter insurer 'Malayan Insurance' option");
		String filterOption = "Malayan Insurance";
		filterByInsurer(driver, filterOption);
		Thread.sleep(2000);
		// Ensure that every card has title 'Malayan Insurance' in order to verify that filter is worked!
		verifyAllCardHeader(driver, filterOption);
		takeSnapShot(driver, "./screenshots/Step07.png");
		System.out.println("Step08: Clear the filter 'Malayan Insurance' by clicking 'Clear All' filter");
		clearAllFilter(driver);
		Thread.sleep(2000);
		takeSnapShot(driver, "./screenshots/Step08.png");
		
//		System.out.println("Step09: Click on 'See More' button to have additional conditional");
//		WebElement seeMoreBtn=driver.findElement(By.cssSelector(".btn-ripple.more"));
//		assertNotNull(seeMoreBtn, "Can not find 'See More' button");
//		seeMoreBtn.click();
//		Thread.sleep(2000);
//		takeSnapShot(driver, "./screenshots/Step09.png");
//		
		System.out.println("Step10: Select 'Price: High to low' sort option");
		selectSortOption(driver, "Price: High to low");
		Thread.sleep(2000);
		takeSnapShot(driver, "./screenshots/Step10.png");
		List<Integer> result = getAllCardValue(driver);
		boolean isSorted = isDescSorted(result);
		assertTrue(isSorted, "The result is not desc sorted");
		
		
//		System.out.println("Step05: Click on 'annual trip' detail option");
//		selectPolicyTypeDetail(driver, "annual trip");
//		Thread.sleep(2000);
//		takeSnapShot(driver, "./screenshots/Step05.png");
//		System.out.println("Step06: 'Clear All' filter");
//		clearAllFilter(driver);
//		Thread.sleep(2000);
//		takeSnapShot(driver, "./screenshots/Step06.png");
//		
//		System.out.println("Step07: Click on '2 persons' detail option");
//		selectTravellerDetail(driver, "2 persons");
//		Thread.sleep(2000);
//		takeSnapShot(driver, "./screenshots/Step07.png");
//		System.out.println("Step08: 'Clear All' filter");
//		clearAllFilter(driver);
//		Thread.sleep(2000);
//		takeSnapShot(driver, "./screenshots/Step08.png");
		
		driver.close();
	}
	
	/**
	 * Get element of promotion filter
	 * 
	 * @param webdriver
	 * 
	 */
	private WebElement getPromotionFilterElement(WebDriver driver) {
		return driver.findElement(By.xpath("//div[@data-filter-by='promotion']"));
	}
	
	/**
	 * This function will select a promotion type filter
	 * 
	 * @param webdriver
	 * @param promotionType promotion type needs to be selected
	 * 
	 */
	private void selectPromotionTypeFilter(WebDriver driver, String promotionType) {
		WebElement promotionFilterElement= getPromotionFilterElement(driver);
		assertNotNull(promotionFilterElement, "Promotion Filter Element can not be found");
		clickOnLabelWithinElement(promotionFilterElement, promotionType);
	}
	
	/**
	 * This function will click on a Filter-Insurers option
	 * 
	 * @param webdriver
	 * @param label label of insurer option
	 * 
	 */
	private void clearAllFilter(WebDriver driver) {
		// This is arguable because this class could be used in many sections
		// For now, it is enough
		WebElement clearAllLink = driver.findElement(By.cssSelector(".btn-ripple.pull-right.clear-all.hidden-xs"));
		assertNotNull(clearAllLink, "Clear all link can not be found");
		clearAllLink.click();
	}
	
	/**
	 * This function will click on a Filter-Insurers option
	 * 
	 * @param webdriver
	 * @param label label of insurer option
	 * 
	 */
	private void filterByInsurer(WebDriver driver, String label) {
		// This is arguable because this class could be used in many sections
		// For now, it is enough
		WebElement insurerchkboxGroup = driver.findElement(By.cssSelector(".gb-checkbox-group"));
		assertNotNull(insurerchkboxGroup, "Insurer group check box can not be found");
		clickOnLabelWithinElement(insurerchkboxGroup, label);
	}
	
	/**
	 * This function will click on a label within a parent element
	 * 
	 * @param parentElement parent element
	 * @param label label needs to be clicked
	 * 
	 */
	private void clickOnLabelWithinElement(WebElement parentElement, String label) {
		if (parentElement != null && StringUtils.isNotEmpty(label)) {
			WebElement labelElement = parentElement.findElement(By.xpath("//label[contains(text(), '" + label +"')]"));
			if (labelElement != null) {
				labelElement.click();
			}
		}
	}
	
	/**
	 * This function will select a sort option
	 * 
	 * @param sortOption sort option needs to be selected
	 * 
	 */
	private void selectSortOption(WebDriver driver, String sortOption) {
		WebElement sortDiv=driver.findElement(By.cssSelector(".sort-detail.sidebar-item"));
		assertNotNull(sortDiv, "Sort Div can not be found");
		clickOnLabelWithinElement(sortDiv, sortOption);
	}
	
	/**
	 * This function will select a policy type detail option
	 * 
	 * @param value value needs to be selected
	 * 
	 */
	private void selectPolicyTypeDetail(WebDriver driver, String value) {
		WebElement policyTypeDiv=driver.findElement(By.xpath("//div[@data-gb-name='triptype']"));
		assertNotNull(policyTypeDiv, "policyTypeDiv can not be found");
		clickOnLabelWithinElement(policyTypeDiv, value);
	}
	
	/**
	 * This function will select a traveller detail option
	 * 
	 * @param value value needs to be selected
	 * 
	 */
	private void selectTravellerDetail(WebDriver driver, String value) {
		WebElement travellerDiv=driver.findElement(By.xpath("//div[@data-gb-name='traveller']"));
		assertNotNull(travellerDiv, "travellerDiv can not be found");
		clickOnLabelWithinElement(travellerDiv, value);
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
	 * This function will verify all current card header has the same as expected header
	 * 
	 * @param webdriver
	 * @param expectedHeader expected header
	 * @throws ParseException 
	 * 
	 */
	private List<Integer> getAllCardValue(WebDriver driver) throws ParseException {
		List<Integer> result = new ArrayList<Integer>();
		// Get all current card
		List<WebElement> cardList=driver.findElements(By.cssSelector(".col-sm-4.card-full"));
		assertNotNull(cardList, "Can not find current card list");
		assertTrue(cardList.size() > 0, "There should have at least one card");
		for (WebElement tempCard : cardList) {
			WebElement currentCardValue = tempCard.findElement(By.cssSelector(".value"));
			assertNotNull(currentCardValue);
			String valueText = currentCardValue.getAttribute("innerText");
			result.add(NumberFormat.getNumberInstance(Locale.UK).parse(valueText).intValue());
			
//			result.add(currentCardValue.getAttribute("innerText"));
		}
		return result;
	}
	
	/**
	 * This function to check if an array is sorted desc or not
	 * 
	 * @param a list needs to check sorted desc or not
	 * @return boolean true if array is desc sorted;otherwise, false
	 * 
	 */
	public boolean isDescSorted(List<Integer> a) {
		// Our strategy will be to compare every element to its successor.
		// The array is considered unsorted
		// if a successor has a greater value than its predecessor.
		// If we reach the end of the loop without finding that the array is unsorted,
		// then it must be sorted instead.

		// Note that we are always comparing an element to its successor.
		// Because of this, we can end the loop after comparing
		// the second-last element to the last one.
		// This means the loop iterator will end as an index of the second-last
		// element of the array instead of the last one.
		for (int i = 0; i < a.size() - 1; i++) {
			if (a.get(i).intValue() < a.get(i+1).intValue()) {
				return false; // It is proven that the array is not sorted.
			}
		}
		return true; // If this part has been reached, the array must be sorted.
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
