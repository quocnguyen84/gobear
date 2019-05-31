package mainpage;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class HomePageTest {
	
	@Test
	public void testHomePage() throws InterruptedException {
		System.out.println("Prepare Test");
		System.setProperty("webdriver.chrome.driver",
				"C:/Users/Quoc Nguyen/Documents/workspace-sts-3.8.2.RELEASE/gobear/driver/chromedriver.exe");

		WebDriver driver = new ChromeDriver();
		driver.get("http://www.google.com");
		System.out.println("Before sleep");
		Thread.sleep(10000);
		System.out.println("After sleep");
		driver.close();
//		assertEquals("1", "2", "This one not equals");
	}

}
