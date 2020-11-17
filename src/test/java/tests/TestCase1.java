package tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.HomePage;


public class TestCase1 {

	static WebDriver driver;
    static String baseColor = "#ffffff";
	private static Logger logger = LogManager.getLogger(TestCase1.class);
	static HomePage searchObj;

	@BeforeTest
	public void setUpTest()
	{
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
		searchObj=PageFactory.initElements(driver, HomePage.class);
		driver.get("https://www.pragmaticplay.com/ru/");
	}
	
	@Test(priority=1)
	public static void testPositiveCheckAge() {
		searchObj.AgeCheck();
		logger.info("Age checker is passed");
		
	}
	@Test(priority=2)
	public static void testMenuMainColorCheck()
	{
		searchObj.HeaderMainMenuElements().forEach((key,values) -> {
			Assert.assertNotEquals(values, baseColor); 
				logger.info("Color is changed for " + key + " "+ values);});
		
	}
	@Test(priority=3)
	public static void testSubMenuColorCheck()
	{
		searchObj.HeaderSubMenuElements().forEach((key,values) -> {
			Assert.assertNotEquals(values, baseColor); 
				logger.info("Color is changed for " + key + " "+ values); });
		
	}

	@Test(priority=4)
	public static void testClickBingoDropDown()
	{
		searchObj.ChooseBingo();
		logger.info("Bingo is selected from dropdown");
		
	}
	@Test(priority=5)
	public static void testCountBingoSlides()
	{
		searchObj.ScrollPage(); 
		AssertJUnit.assertEquals(searchObj.GetBingoGames().length, 8);
		logger.info("Expected count of Bingo is 8, actual is "+searchObj.GetBingoGames().length);
	
	}
	
	@Test(priority=6)
	public static void testImgTitleCheck()
	{
		searchObj.UniqueTitles().forEach((key,values) -> {
			Assert.assertEquals(searchObj.MatchImgTitle(key,values), true);
			logger.info("Image links which are can be compared " + key + " "+ values);
			 });
	}
	
	@Test(priority=7)
	public static void testCheckDuplication()
	{
			AssertJUnit.assertEquals(searchObj.CheckUnique(searchObj.GetBingoGames()), true);
			logger.info("Items are not duplicated");
	}

	
	
	@AfterTest
	public void tearDown()
	{
		driver.close();
		driver.quit();
	}

}
