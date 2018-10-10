package com.practise;




import java.util.List;


import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;


import org.openqa.selenium.support.ui.WebDriverWait;


public class RedBus {

public static void main(String[] args) throws InterruptedException {
	
System.setProperty("webdriver.chrome.driver", "D:/AM00478506/TECHM/chromedriver.exe");
ChromeOptions options= new ChromeOptions();
options.addArguments("--disable-notifications");
WebDriver driver = new ChromeDriver(options);
driver.get("http://www.redbus.in");
driver.manage().window().maximize();
driver.findElement(By.id("dest")).sendKeys("Nashik");
Thread.sleep(2000);
driver.findElement(By.id("src")).sendKeys("Nagpur");
Thread.sleep(10000);
WebDriverWait wait = new WebDriverWait(driver, 5);
WebElement autoOptions = driver.findElement(By.className("autoFill"));
wait.until(ExpectedConditions.visibilityOf(autoOptions));

String textToSelect = "Nagpur";
List<WebElement> optionsToSelect = autoOptions.findElements(By.tagName("li"));
for(WebElement option : optionsToSelect){
if(option.getText().equals(textToSelect)) {
System.out.println("Trying to select: "+textToSelect);
option.click();
break;
}
}





// -----------------------------------------Onward Date--------------------------------------------------------


driver.findElement(By.xpath("//*[@id='search']/div/div[3]/div/label")).click();
String ExpectedMonth = "Jan 2019";


String OriginalMonth = driver.findElement(By.xpath("//*[@id='rb-calendar_onward_cal']/table/tbody/tr[1]/td[2]"))
.getText();


if (ExpectedMonth.equals(OriginalMonth)) {
WebElement dateTableDate = driver.findElement(By.xpath("//*[@id='rb-calendar_onward_cal']/table/tbody"));

List<WebElement> columns = dateTableDate.findElements(By.tagName("td"));
for(WebElement cell: columns){
if(cell.getText().equals("15")){
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
if(cell.getText().equals("15")){
cell.click();

break;
}
}


}

Thread.sleep(3000);

driver.findElement(By.id("search_btn")).click();



}
}



/*import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class RedBus{
	WebDriver driver;
	
	List<String> monthList = Arrays.asList("Jan","Feb","Mar","Apr","May","Jun","July","Aug","Sept","Oct","Nov","Dec");
	String expDate = null;
	int expMonth;
	int expYear;
	
	String calDate = null;
	int calMonth;
	int calYear;
	
	boolean dateNotFound;
	
	@BeforeTest
	public void loadRedBus(){
		//for Mac OS
		System.setProperty("webdriver.chrome.driver","D:/AM00478506/TECHM/chromedriver.exe");
		// For Window OS
		//System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/drivers/geckodriver.exe");
		ChromeOptions options= new ChromeOptions();
		options.addArguments("--disable-notifications");
		driver = new ChromeDriver(options);

		//FirefoxProfile profile = new FirefoxProfile();
		//profile.setPreference("dom.webnotifications.enabled", false);
		//driver = new FirefoxDriver(profile);
	
		driver.get("https://www.redbus.in/");
		driver.manage().window().maximize();
		//driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.MILLISECONDS);
	}
	
	@Test(priority=1)
	public void searchBus(){
		WebElement srcElement = driver.findElement(By.id("src"));
		srcElement.clear();
		srcElement.sendKeys("Pune");
		srcElement.sendKeys(Keys.ARROW_DOWN);
		
		
		WebElement dstElement = driver.findElement(By.id("dest"));
		dstElement.clear();
		dstElement.sendKeys("Bangalore");
		dstElement.sendKeys(Keys.ARROW_DOWN);
	}
	
	@Test(priority=2)
	public void selectDateCal() throws InterruptedException{
		Thread.sleep(10000);
		WebElement calendar = driver.findElement(By.xpath("//input[@id='onward_cal']"));
		calendar.click();
		
		expDate = "20";
		expMonth = 10;
		expYear=2018;
		dateNotFound = true;
		
		/*WebElement monthYearEle = driver.findElement(By.xpath(".//*[@id='rb-calendar_onward_cal']//table//td[@class='monthTitle']"));
		String monthYear= monthYearEle.getAttribute("innerHTML");
		
		String[] s = monthYear.split(" ");
		String calMonth = s[0];
		int calYear = Integer.parseInt(s[1]);*/
		
	/*	while(dateNotFound){
			WebElement monthYearEle = driver.findElement(By.xpath(".//*[@id='rb-calendar_onward_cal']//table//td[@class='monthTitle']"));
			String monthYear= monthYearEle.getAttribute("innerHTML");
			
			String[] s = monthYear.split(" ");
			String calMonth = s[0];
			int calYear = Integer.parseInt(s[1]);
			
			
			////If current selected month and year are same as expected month and year then go Inside this condition.
			if(monthList.indexOf(calMonth)+1 ==expMonth && expYear==calYear){
				selectDate(expDate);
				dateNotFound = false;
			}
			
			//If current selected month and year are less than expected month and year then go Inside this condition
			else if(monthList.indexOf(calMonth)+1 <expMonth && expYear==calYear||expYear>calYear){
				//Click on next button of date picker.
				calendar.findElement(By.xpath(".//*[@id='rb-calendar_onward_cal']//button[.='>']")).click();
			}
			//If current selected month and year are greater than expected month and year then go Inside this condition.
			else if(monthList.indexOf(calMonth)+1 >expMonth && expYear==calYear||expYear<calYear){
				calendar.findElement(By.xpath(".//*[@id='rb-calendar_onward_cal']//button[.='<']")).click();
			}
		}
		
		//driver.findElement(By.xpath(".//*[@id='search_btn']")).click();
		
	}
	
	public void selectDate(String date){
		WebElement datePicker = driver.findElement(By.xpath(".//*[@id='rb-calendar_onward_cal']"));
		List<WebElement> dates = datePicker.findElements(By.tagName("td"));
		for(WebElement temp:dates){
			if(temp.getText().equals(date)){
				temp.click();
				break;
			}
		}
	}
	
	

}*/

/*import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
	/*WebDriver frame2 = driver.switchTo().frame(driver.findElement(By.xpath("(//iframe[@class='modalIframe'])[2]")));
	frame2.findElement(By.xpath("(//i[@class='icon-close'])[2]"));
	
	driver.navigate().refresh();
	
}
@Test(dependsOnMethods={"Login"})
public void ticketSearch() throws InterruptedException
{
	Thread.sleep(5000);
	WebElement from =driver.findElement(By.xpath("//input[@id='src']"));
	from.clear();
	from.sendKeys("Vijayawada");
	from.sendKeys(Keys.ARROW_DOWN);
	
	
	WebElement to=driver.findElement(By.xpath("//input[@id='dest']"));
	to.clear();
	to.sendKeys("Hyderabad");
	to.sendKeys(Keys.ARROW_DOWN);
	
	WebElement trdate =driver.findElement(By.xpath("//*[contains(text(),'Onward Date')]"));
	trdate.click();
	Thread.sleep(5000);
	WebElement next=driver.findElement(By.xpath("(//td[@class='next'])[2]"));
	next.click();
	Thread.sleep(5000);
	WebElement day=driver.findElement(By.xpath("(//td[@class='wd day'])[8]"));
	day.click();
	Thread.sleep(5000);
	WebElement searchbus =driver.findElement(By.xpath("//button[@class='fl button']"));
	searchbus.click();
	
}
}
*/
