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
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
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
		driver.manage().window().maximize(); 
		System.out.println("Step 01: Navigate to gobear.com");
		driver.get("https://www.gobear.com/ph?x_session_type=UAT");
		Thread.sleep(5000);
		takeSnapShot(driver, "./screenshots/Step01.png");
		System.out.println("Step02: Click on href with '#Insurance' value to ensure we are at Insurance section");
		WebElement insuranceHref = driver.findElement(By.xpath("//a[@href=\"#Insurance\"]"));
		
		if (insuranceHref != null) {
			insuranceHref.click();
		}
		Thread.sleep(2000);
		takeSnapShot(driver, "./screenshots/Step02.png");
		System.out.println("Step03: click on href with '#Travel' value to ensure we are at Travel Insurance section");
		WebElement travelHref = driver.findElement(By.xpath("//a[@href=\"#Travel\"]"));
		if (travelHref != null) {
			travelHref.click();
		}
		Thread.sleep(2000);
		takeSnapShot(driver, "./screenshots/Step03.png");
		System.out.println("Step04: Keep default value and click on 'Show My Result' button");
		// We could argue that we should use text 'Show My Result' instead but I believe that button name is more stable way
		WebElement btnShowResult = driver.findElement(By.name("product-form-submit"));
		if (btnShowResult != null) {
			btnShowResult.click();
		}
		Thread.sleep(4000);
		takeSnapShot(driver, "./screenshots/Step04.png");
		// Quick and dirty way to go directly to Travel section
//		driver.get("https://www.gobear.com/ph/insurance/travel/quote-online?triptype=single&covertype=individual&destination=Hong%20Kong&adults=1&children=&startdate=2019-06-07&enddate=2019-06-16");
//		Thread.sleep(3000);
		
		// Verify 'Promotions' filter
		// TODO: Get card result should be a common function
		// Get list of card result
		List<WebElement> cardList=driver.findElements(By.cssSelector(".col-sm-4.card-full"));
		if (cardList != null) {
			assertTrue(cardList.size() >= 3, "There are less than 3 displayed cards");
		}
		
		// TODO: The result is empty. The verify logic needs to be re-checked later.
		System.out.println("Step05: Click on 'Promos only' option");
		selectPromotionTypeFilter(driver, "Promos only");
		Thread.sleep(3000);
		takeSnapShot(driver, "./screenshots/Step05.png");
		System.out.println("Step06: 'Clear All' filter");
		clearAllFilter(driver);
		Thread.sleep(3000);
		takeSnapShot(driver, "./screenshots/Step06.png");
		
		System.out.println("Step07: Click on filter insurer 'Malayan Insurance' option");
		String filterOption = "Malayan Insurance";
		filterByInsurer(driver, filterOption);
		Thread.sleep(3000);
		// Ensure that every card has title 'Malayan Insurance' in order to verify that filter is worked!
		verifyAllCardHeader(driver, filterOption);
		takeSnapShot(driver, "./screenshots/Step07.png");
		System.out.println("Step08: Clear the filter 'Malayan Insurance' by clicking 'Clear All' filter");
		clearAllFilter(driver);
		Thread.sleep(3000);
		takeSnapShot(driver, "./screenshots/Step08.png");
		
		System.out.println("Step09: Click on 'See More' button to have additional conditional");
		WebElement seeMoreBtn=driver.findElement(By.cssSelector(".btn-ripple.more"));
		assertNotNull(seeMoreBtn, "Can not find 'See More' button");
		seeMoreBtn.click();
		Thread.sleep(3000);
		takeSnapShot(driver, "./screenshots/Step09.png");
		System.out.println("Step09A: Move left slider of 'Medical expenses while traveling' to 30 offset");
		String sliderText = "Medical expenses while traveling";
		// Move left slider 30 offset
		int minValue = scrollPromotionMinSlider(driver, sliderText, 30);
		takeSnapShot(driver, "./screenshots/Step09A.png");
		verifyMinMedicalExpense(driver, minValue);
		clearAllFilter(driver);
		Thread.sleep(3000);
		System.out.println("Step09B: Click on 'See Less' button");
		WebElement seeLessBtn=driver.findElement(By.cssSelector(".btn-ripple.less"));
		assertNotNull(seeLessBtn, "Can not find 'See Less' button");
		seeLessBtn.click();
		Thread.sleep(3000);
		takeSnapShot(driver, "./screenshots/Step09B.png");
		
		// Verify the 'Detail' filter
		System.out.println("Step10: Click on 'annual trip' detail option");
		selectPolicyTypeDetail(driver, "annual trip");
		Thread.sleep(3000);
		takeSnapShot(driver, "./screenshots/Step10.png");
		// Actually, we should verify ALL results
		// For this test, just try to verify the 1st result for demo purpose
		goToReadMoreOfInsurer(driver, 0);
		takeSnapShot(driver, "./screenshots/Step10A.png");
		verifyInsurerSupportAnnual(driver);
		// Quick and dirty to go bank to previous page (result page).
		// Not really sure if it should be used here, it is enough for now.
		driver.navigate().back();
		takeSnapShot(driver, "./screenshots/Step10B.png");

		System.out.println("Step11: Click on '2 persons' detail option");
		selectTravellerDetail(driver, "2 persons");
		Thread.sleep(3000);
		takeSnapShot(driver, "./screenshots/Step11.png");
		// TODO: Not really sure how to verify the result with '2 persons' filter. Need
		// check the logic here
		System.out.println("Step12: 'Clear All' filter");
		clearAllFilter(driver);
		Thread.sleep(3000);
		takeSnapShot(driver, "./screenshots/Step12.png");
		
		// Verify 'Sort' filter
		System.out.println("Step13: Select 'Price: High to low' sort option");
		selectSortOption(driver, "Price: High to low");
		Thread.sleep(3000);
		takeSnapShot(driver, "./screenshots/Step13.png");
		List<Integer> result = getAllCardValue(driver);
		boolean isSorted = isDescSorted(result);
		assertTrue(isSorted, "The result is not desc sorted");
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
	 * This function will scroll a promotion slider filter 
	 * 
	 * @param webdriver
	 * @param sliderText slider text to locate the slider
	 * @param offSet how many space needs to be scrolled? TODO: This needs to checked later to scroll to exact value
	 * 
	 * @return int current min value
	 * @throws InterruptedException 
	 * @throws ParseException 
	 * 
	 */
	private int scrollPromotionMinSlider(WebDriver driver, String sliderText, int offSet) throws InterruptedException, ParseException {
		int result = 0;
		WebElement labelSliderEl = driver.findElement(By.xpath("//div[@data-type='Number']//label[contains(text(),'" + sliderText + "')]"));
		assertNotNull(labelSliderEl, "Can not find label slider element");
		WebElement sliderParentEl = labelSliderEl.findElement(By.xpath(".."));
		assertNotNull(sliderParentEl, "Can not find slider parent element");
		WebElement sliderEl = sliderParentEl.findElement(By.xpath(".//div[@class='bootstrap-slider']"));
		assertNotNull(sliderEl, "Can not find slider element");
		// Get element of min slider (left slider)
		WebElement minSlider = sliderParentEl.findElement(By.xpath(
				".//div[@class='bootstrap-slider']//div[contains(@class, 'slider-horizontal')]//div[contains(@class, 'min-slider-handle')]"));
		// Move min slider to the desired offset
		Actions act = new Actions(driver);
		act.dragAndDropBy(minSlider, offSet, 0).build().perform();
		Thread.sleep(2000);
		WebElement minValueEl = sliderEl.findElement(By.cssSelector(".value"));
		assertNotNull(minValueEl, "Can not find min value element");
		String strValue = minValueEl.getText();
		String valueNumber = strValue.replaceAll("[^0-9]", "");
		return Integer.parseInt(valueNumber);
	}
	
//	public void verifySlider(WebDriver driver) throws Exception {
//		takeSnapShot(driver, "./screenshots/StepA.png");
//		// show list slider
//		WebElement showMoreBtn = driver.findElement(By.id("collapseSeemoreBtn"));
//		showMoreBtn.click();
//
//		takeSnapShot(driver, "./screenshots/StepB.png");
//		// find Medical expenses while traveling slider
//		WebElement medicalEl = driver.findElement(
//				By.xpath("//div[@data-type='Number']//label[contains(text(),'Medical expenses while traveling')]"));
//		System.out.println(medicalEl.getText());
//		WebElement sliderParent = medicalEl.findElement(By.xpath(".."));
//		System.out.println(sliderParent.getTagName());
//		WebElement slider = sliderParent.findElement(By.xpath(".//div[@class='bootstrap-slider']"));
//		int width = slider.getSize().width;
//		// TODO calculate xOfset to move
//		int xOfset = 30;
//
//		// move min slider
//		WebElement minSlider = sliderParent.findElement(By.xpath(
//				".//div[@class='bootstrap-slider']//div[contains(@class, 'slider-horizontal')]//div[contains(@class, 'min-slider-handle')]"));
//		Actions act = new Actions(driver);
//		act.dragAndDropBy(minSlider, xOfset, 0).build().perform();
//
//		// TODO move max slider
//		Thread.sleep(2000);
//
//		List<WebElement> cards = driver.findElements(By.xpath("//div[contains(@class, 'card-content-details')]"));
//		System.out.println(cards.size());
//		List<String> values = new ArrayList<String>();
//		for (WebElement card : cards) {
//			WebElement pEl = card
//					.findElement(By.xpath(".//div//p[contains(text(),'Medical expenses while traveling')]"));
//			WebElement parentEl = pEl.findElement(By.xpath(".."));
//			WebElement valueEl = parentEl.findElement(By.xpath(".//p//strong//span"));
//			String value = valueEl.getText();
//			String numberOnly = value.replaceAll("[^0-9]", "");
//			System.out.println(numberOnly);
//			values.add(numberOnly);
//		}
//
//		// TODO verify
//	}
	
	/**
	 * This function will go to "Read More" page of a result insurer
	 * 
	 * @param webdriver
	 * @param index result index
	 * @throws InterruptedException 
	 * 
	 * @throws Exception
	 * 
	 */
	private void goToReadMoreOfInsurer(WebDriver driver, int index) throws InterruptedException {
		// Get all current card result
		List<WebElement> cardList=driver.findElements(By.cssSelector(".col-sm-4.card-full"));
		assertNotNull(cardList, "Can not find current card list");
		assertTrue(cardList.size() > 0, "There should have at least one card");
		assertTrue(index < cardList.size(), "Insurer index should less than total card");
		WebElement indexCard = cardList.get(index);
		// Get 'Read More' element of index card
		WebElement readMoreLink = indexCard.findElement(By.cssSelector(".btn-ripple.btn.btn-read-more.pull-right"));
		assertNotNull(readMoreLink, "'Read more' link of current card is not found");
		readMoreLink.click();
		Thread.sleep(2000);
	}
	
	/**
	 * This function will verify if current insurer supports annual trip or not
	 * It will try to get the text value of 'Maximum days annual trip' element. If it is not empty (usually a number), it supports annual trip
	 * 
	 * Sample insurer support annual trip: https://www.gobear.com/ph/insurance/travel/detail/Standard-Insurance/Essential/3dd0c3ea-2534-4b02-8f71-bd72c2098e81?triptype=annual&covertype=individual&destination=Domestic&adults=4&children=&startdate=2019-06-07&enddate=2020-06-06&selectedCoverageOptionIDs=&coverageFiltrations=%5B%5D&insurers=&promotion=&premium=6048&coverageScore=2.4
	 * Sample insurer NOT support annual trip: https://www.gobear.com/ph/insurance/travel/detail/Malayan-Insurance/Travelite-Plan-200K-(Asia)/3d21c6fe-7805-4aa0-b0b1-6847822a0d66?triptype=single&covertype=individual&destination=Hong%20Kong&adults=4&children=&startdate=2019-06-07&enddate=2019-06-16&selectedCoverageOptionIDs=&coverageFiltrations=%5B%5D&insurers=&promotion=&premium=752&coverageScore=2.0
	 * 
	 * @param webdriver
	 * @param fileWithPath
	 * 
	 * @throws Exception
	 * 
	 */
	private void verifyInsurerSupportAnnual(WebDriver driver) {
		// Get the label element of 'Maximum days annual trip'
		WebElement maxDayAnnualTripLabelEl = driver.findElement(By.xpath("//div[@class='form-group']//label[contains(text(),'Maximum days annual trip')]"));
		assertNotNull(maxDayAnnualTripLabelEl, "Can not find 'Maximum days annual trip' label element");
		// Get the value element of 'Maximum days annual trip'
		WebElement maxDayAnnualTripValueEl = maxDayAnnualTripLabelEl.findElement(By.xpath("..//span"));
		assertNotNull(maxDayAnnualTripValueEl, "Can not find 'Maximum days annual trip' value element");
		String maxDayAnnualValue = maxDayAnnualTripValueEl.getText();
		assertTrue(!maxDayAnnualValue.isEmpty(), "Current insurer does not supports 'annual trip' which is not correct!");
	}
	
	/**
	 * This function will verify if insurer list item has medical expense while travelling more than a min number
	 * 
	 * @param webdriver
	 * @param fileWithPath
	 * @throws ParseException 
	 * 
	 * @throws Exception
	 * 
	 */
	private void verifyMinMedicalExpense(WebDriver driver, int minExpense) throws ParseException {
		// Get all current card
		List<WebElement> cardList = driver.findElements(By.cssSelector(".col-sm-4.card-full"));
		assertNotNull(cardList, "Can not find current card list");
		assertTrue(cardList.size() > 0, "There should have at least one card");
		for (WebElement tempCard : cardList) {
			WebElement medicalExpenseLabelEl = tempCard
					.findElement(By.xpath(".//div//p[contains(text(),'Medical expenses while traveling')]"));
			assertNotNull(medicalExpenseLabelEl, "Can not find medical expenses while traveling label element");
			WebElement medicalExpenseValueEl = medicalExpenseLabelEl.findElement(By.xpath("..//span"));
			assertNotNull(medicalExpenseValueEl, "Can not find medical expenses while traveling value element");
			String medicalExpenseValue = medicalExpenseValueEl.getText();
			String medicalExpenseNumber = medicalExpenseValue.replaceAll("[^0-9]", "");
			int intValue = Integer.parseInt(medicalExpenseNumber);
			assertTrue(intValue >= minExpense, "Current card has medical expenses less than min value");
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
