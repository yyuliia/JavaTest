package pages;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;


public class HomePage {
	
	WebDriver driver;
	

	@FindBy(xpath="//div[@class='age-popup visible']")
	WebElement isPresent;
	
	@FindBy(xpath="//li[@id='menu-item-11956']/a")
	WebElement btnGrocery;

	@FindBy(xpath="//li[@id='menu-item-11960']/a")
	WebElement linkBingo;
	
	@FindBy(xpath="//h2[text()='Наши варианты Бинго']")
	WebElement customBingoTitle;
	
	@FindBy(xpath = "//div[starts-with(@class,'slick-track')]/div/div/div")
	List<WebElement> BingoTitles;
	
	@FindBy(xpath="//li[@id='menu-item-11956']/ul/li/a")
	List<WebElement> subMenuElements;
	  
	@FindBy(xpath="//ul[@id='menu-top-menu-ru']/li/a[not(descendant::img)]") 
	List<WebElement> headerElements;
	
	public HomePage(WebDriver driver) {
		
		this.driver = driver;
	}

	
	public void AgeCheck()
	{
			
	try {
		if (isPresent !=null)
		{
			 driver.findElement(By.xpath("//a[@data-age-check-confirm]")).click();
		 } 
		}
	 catch(NoSuchElementException e)
	  {
		  e.printStackTrace();
	  }
		
		
	}
	  
	  public Hashtable<String, String> HeaderMainMenuElements() { 
		  Hashtable<String, String> menuMain_dict = new Hashtable<String, String>();
		  try {
		  for (WebElement iterator: headerElements) {
			  CurrentAction().moveToElement(iterator).build().perform(); 
			  String contents = (String)
			((JavascriptExecutor) driver).executeScript("return window.getComputedStyle(arguments[0]).getPropertyValue('color');", iterator); 
			  String colorValue = Color.fromString(contents).asHex();
			  menuMain_dict.put(iterator.getAttribute("text"), colorValue);
		  }
		  }
		  catch(NoSuchElementException e)
		  {
			  e.printStackTrace();
		  }
		  catch(NullPointerException e)
		  {
			  e.printStackTrace();
		  }
		  return menuMain_dict;
	  
	  }
	  
	  public Hashtable<String, String> HeaderSubMenuElements() { 
		  Hashtable<String, String> menuSub_dict = new Hashtable<String, String>();
		 try {
		  for (WebElement iterator: subMenuElements) {
			  CurrentAction().moveToElement(btnGrocery).moveToElement(iterator).build().perform(); 
			  String contents = (String)
			((JavascriptExecutor) driver).executeScript("return window.getComputedStyle(arguments[0]).getPropertyValue('color');", iterator); 
			  String colorValue = Color.fromString(contents).asHex();
			  menuSub_dict.put(iterator.getAttribute("text"), colorValue);
	  
		  }
	  } 
		  catch(NoSuchElementException e)
		  {
			  e.printStackTrace();
		  }
		  catch(NullPointerException e)
		  {
			  e.printStackTrace();
		  }
		  return menuSub_dict;
	  
	  }
	 
	private Actions CurrentAction()
	{
		Actions action = new Actions(driver);
		return action;
	}
	
	
	public void ChooseBingo()
	
	{
		
		try {
			CurrentAction().moveToElement(btnGrocery).moveToElement(linkBingo).click().build().perform();
		}
		
		  catch(NoSuchElementException e)
		  {
			  e.printStackTrace();
		  }
	  catch(NullPointerException e)
	  {
		  e.printStackTrace();
	  }
	}
	
	public void ScrollPage()
	{
		try {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", customBingoTitle);
		}
		 catch(NoSuchElementException e)
		  {
			  e.printStackTrace();
		  }
		 catch(NullPointerException e)
		  {
			  e.printStackTrace();
		  }
	}
	public String[][] GetBingoGames()
	{
		String[][] allTitlesImages = new String[BingoTitles.size()][2];
	try {
		 for (int i=0; i<BingoTitles.size(); i++) { 
			 String document = BingoTitles.get(i).getAttribute("innerHTML");
			 Document doc = Jsoup.parse(document);
			 allTitlesImages[i][0]=(doc.select("p").first().text()).toString();
			 allTitlesImages[i][1]=(doc.select("img").first().absUrl("src")).toString();
		  }
		 
		
	}
	catch(IndexOutOfBoundsException e)
	{
		e.printStackTrace();
	}
	catch(NullPointerException e)
	  {
		  e.printStackTrace();
	  }
	return allTitlesImages;
	}
	
	public boolean CheckUnique(String[][] items)
	{
		    for (int i = 0; i < items.length; i++) {
		        for (int j = i + 1; j < items.length; j++) {
		            if (items[j][0].toString().equals(items[i][0].toString())) {
		                return false;
		            }
		        }
		    }
		return true;
		
	}
	
	  public Map<Object, Object> UniqueTitles() 
	  { 
		  Map<Object, Object> uniqueTitles_dict = new HashMap<>();
		  try {
		  
		  uniqueTitles_dict = ArrayUtils.toMap(GetBingoGames());
		  }
		
		  catch(IndexOutOfBoundsException e)
			{
				e.printStackTrace();
			}
		  return uniqueTitles_dict;
	  }
	  
	  public boolean MatchImgTitle(Object key, Object values)
	  {
		  try {
				String[] q = (key.toString().split("\\s|\\'"));
				for(String s:q) {
					 if(values.toString().toLowerCase().contains(s.toLowerCase())) {
					 return true;
					 }
				 }
		  }
		  catch(NullPointerException e)
		  {
			  e.printStackTrace();
		  }
				
			return false;
	  } 
	 
}
	
