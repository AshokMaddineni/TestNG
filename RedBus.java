package com.practise;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class RedBus {
	
WebDriver driver;



@Test
public void Setup()
{
	ChromeOptions options = new ChromeOptions();
	options.addArguments("--disable-notifications");
	System.setProperty("webdriver.chrome.driver", "D:/AM00478506/TECHM/chromedriver.exe");
    driver= new ChromeDriver(options);
	driver.get("https://www.redbus.in/");
	driver.manage().window().maximize();
	System.out.println("Setup has been started..........");
	
	
}

@Test(dependsOnMethods={"Setup"})
public void Login() throws InterruptedException
{
	System.out.println("Login to Redbus");
	
	
	Thread.sleep(5000);
	driver.findElement(By.xpath("//div[@id='signin-block']/div[2]")).click();
		
	WebElement signin=driver.findElement(By.xpath("//li[@id='signInLink']"));
	signin.click();
	Thread.sleep(5000);
	WebDriver frame = driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class='modalIframe']")));
	WebElement lgmode=frame.findElement(By.xpath("(//div[contains(text(),'SIGN IN using Email')])[2]"));
	lgmode.click();
	WebElement username=frame.findElement(By.xpath("//input[@id='email-mobile']"));
	username.sendKeys("ashokkumarmaddineni@gmail.com");
	WebElement pasw=frame.findElement(By.xpath("//input[@id='password']"));
	pasw.sendKeys("ashok1249");
	WebElement login=frame.findElement(By.xpath("//button[@id='doSignin']"));
	login.click();
	Thread.sleep(5000);
	driver.navigate().refresh();
	
}
@Test(dependsOnMethods={"Login"})
public void ticketSearch() throws InterruptedException
{
	Thread.sleep(5000);
		driver.findElement(By.id("src")).sendKeys("Hyderabad Airport");
Thread.sleep(2000);
WebDriverWait waitsrc=new WebDriverWait(driver,2);
WebElement autosrcOptions=driver.findElement(By.className("autoFill"));
waitsrc.until(ExpectedConditions.visibilityOf(autosrcOptions));
String textAtSource="Hyderabad Airport";
List<WebElement> optionsAtSource=autosrcOptions.findElements(By.tagName("li"));
for(WebElement option : optionsAtSource){
	if(option.getText().equals(textAtSource))
	{
		System.out.println("Select To: "+textAtSource);
		option.click();
	}
										}
driver.findElement(By.id("dest")).sendKeys("Nagpur");
Thread.sleep(10000);
WebDriverWait wait = new WebDriverWait(driver, 2);
WebElement autoOptions = driver.findElement(By.className("autoFill"));
wait.until(ExpectedConditions.visibilityOf(autoOptions));

String textToSelect = "Nagpur";
List<WebElement> optionsToSelect = autoOptions.findElements(By.tagName("li"));
for(WebElement option : optionsToSelect){
if(option.getText().equals(textToSelect)) {
System.out.println("Select To: "+textToSelect);
option.click();
break;
											}
										}
	}
@Test(dependsOnMethods={"ticketSearch"})
public void DateSelection() throws InterruptedException
{
	//driver.findElement(By.xpath("//*[@id='search']/div/div[3]/div/label")).click();
	String ExpectedMonth = "Oct 2018";


	String OriginalMonth = driver.findElement(By.xpath("//*[@id='rb-calendar_onward_cal']/table/tbody/tr[1]/td[2]"))
			.getText();


	if (ExpectedMonth.equals(OriginalMonth)) {
	WebElement dateTableDate = driver.findElement(By.xpath("//*[@id='rb-calendar_onward_cal']/table/tbody"));

	List<WebElement> columns = dateTableDate.findElements(By.tagName("td"));
	for(WebElement cell: columns){
	if(cell.getText().equals("30")){
	cell.click();
	break;
	}
	}

	} else {

	while (!ExpectedMonth.equals(OriginalMonth)) {

	driver.findElement(By.xpath("//*[@id='rb-calendar_onward_cal']/table/tbody/tr[1]/td[3]/button"))
	.click();
	OriginalMonth = driver
	.findElement(By.xpath("//*[@id='rb-calendar_onward_cal']/table/tbody/tr[1]/td[2]")).getText();

	}
	Thread.sleep(2000);
	WebElement dateTableDate = driver.findElement(By.xpath("//*[@id='rb-calendar_onward_cal']/table/tbody"));

	List<WebElement> columns = dateTableDate.findElements(By.tagName("td"));

	Thread.sleep(3000);

	for(WebElement cell: columns){
	if(cell.getText().equals("30")){
		System.out.println("Available Dates"+columns+"/n");
	cell.click();
	

	break;
									}
								   }
}
	

}
@Test(dependsOnMethods={"DateSelection"})
public void returnTicket() throws InterruptedException
{ 
	Thread.sleep(3000);
	driver.findElement(By.xpath("//div[@class='fl search-box date-box gtm-returnCalendar']")).click();
	String OrireturnDate="Nov 2018";
	String returnDate=driver.findElement(By.xpath("(//td[@class='monthTitle'])[2]")).getText();
	if(OrireturnDate.equals(returnDate))
	{
		WebElement dateTable=driver.findElement(By.xpath("//*[@id='rb-calendar_return_cal']"));
		List<WebElement>cols= dateTable.findElements(By.tagName("td"));
		for(WebElement cell:cols)
		{
			if(cell.getText().equals("1")){
				cell.click();
			 break;
			}
	   }
	}
	else
	{
		while(!OrireturnDate.equals(returnDate)){
			driver.findElement(By.xpath("(//td[@class='next'])[2]")).click();
			returnDate=driver.findElement(By.xpath("(//td[@class='monthTitle'])[2]")).getText();			
		}
		Thread.sleep(3000);
		WebElement cols=driver.findElement(By.xpath("//*[@id='rb-calendar_return_cal']"));
		List<WebElement> rows=cols.findElements(By.tagName("td"));
		for(WebElement cell:rows)
		{
			if(cell.getText().equals("1"))
			{
				cell.click();
			    break;
			}
		}
		
	}
	Thread.sleep(3000);
     driver.findElement(By.id("search_btn")).click();
	
}
@Test(dependsOnMethods={"returnTicket"})
public void showBuses()
{
	WebElement showavabus=driver.findElement(By.xpath("//div[contains(text(),'Show buses')]"));
	List<WebElement> count=showavabus.findElements(By.xpath("//div[@class='w-18 fr t-right']"));
	System.out.println("Show Buses Buttons"+count.size());
	
	
}

}

