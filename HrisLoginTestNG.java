package login;

import java.util.concurrent.TimeUnit;
import org.jboss.netty.channel.SucceededChannelFuture;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HrisLoginTestNG {
	WebDriver driver;
	
	public void HrisLoginTest(WebDriver driver)
	{
		this.driver = driver;
	}
	
  @BeforeClass
  public void beforeClass() 
  {
	  System.setProperty("webdriver.chrome.driver", "/home/qainfotech/Desktop/chromedriver");
	  driver = new ChromeDriver();
	  driver.get("https://hris.qainfotech.com/login.php");
	  driver.findElement(By.xpath("//*[@id=\"demo-2\"]/div/div[2]/ul/li[1]/a")).click();
  }

  @AfterClass
  public void afterClass() 
  {
	  //driver.quit();
  }
  
  //tc1 scrolling the login page
  @Test(priority=1)
  public void scroll() {
	  JavascriptExecutor je = (JavascriptExecutor) driver;
	  driver.manage().window().maximize();
	  je.executeScript("window.scroll(0,1000)");
  }
 
  //tc2- when username field is blank, username filed's border turns red
  @Test(priority=2)
  public void blankUsernameField() {
	  driver.findElement(By.cssSelector("#txtUserName")).clear();
	  driver.findElement(By.cssSelector("#txtUserName")).sendKeys("");
	  driver.findElement(By.cssSelector("#txtPassword")).clear();
	  driver.findElement(By.cssSelector("#txtPassword")).sendKeys("Kanika@321#");
	  driver.findElement(By.cssSelector("#txtPassword")).submit();
	  String usernameStyle = driver.findElement(By.id("txtUserName")).getAttribute("style");
	  Assert.assertTrue(usernameStyle.contains("red"));
  }

  //tc3 - when password field is blank, the password Field's border turns red
  @Test(priority=3)
  public void blackPasswordField() {
	  driver.findElement(By.cssSelector("#txtUserName")).clear();
	  driver.findElement(By.cssSelector("#txtUserName")).sendKeys("kanikagosain");
	  driver.findElement(By.cssSelector("#txtPassword")).clear();
	  driver.findElement(By.cssSelector("#txtPassword")).sendKeys("");
	  driver.findElement(By.cssSelector("#txtPassword")).submit();
	  String passwordStyle = driver.findElement(By.id("txtPassword")).getAttribute("style");
	  Assert.assertTrue(passwordStyle.contains("red"));
  }
   
  //tc4 - when username is incorrect, error message appears
  @Test(priority=4)
  public void incorrectUsername() {
	  driver.findElement(By.cssSelector("#txtUserName")).clear();
	  driver.findElement(By.cssSelector("#txtUserName")).sendKeys("kanika");
	  driver.findElement(By.cssSelector("#txtPassword")).clear();
	  driver.findElement(By.cssSelector("#txtPassword")).sendKeys("Kanika@321#");
	  driver.findElement(By.cssSelector("#txtPassword")).submit();  
	  driver.findElement(By.xpath("//*[@id=\"demo-2\"]/div/div[2]/ul/li[1]/a")).click();
	  Assert.assertTrue(driver.findElement(By.cssSelector
			  ("#login > form > div.loginTxt.txtHideDiv.alert.alert-error > div"))
			  .getText().contains("Invalid Login")); 
  }
  
  //tc5 - when password is incorrect, error message appears
  @Test(priority=5)
  public void incorrectPassword() {
	  driver.findElement(By.cssSelector("#txtUserName")).clear();
	  driver.findElement(By.cssSelector("#txtUserName")).sendKeys("kanikagosain");
	  driver.findElement(By.cssSelector("#txtPassword")).clear();
	  driver.findElement(By.cssSelector("#txtPassword")).sendKeys("Kanika");
	  driver.findElement(By.cssSelector("#txtPassword")).submit();  
	  driver.findElement(By.xpath("//*[@id=\"demo-2\"]/div/div[2]/ul/li[1]/a")).click();
	  Assert.assertTrue(driver.findElement(By.cssSelector
			  ("#login > form > div.loginTxt.txtHideDiv.alert.alert-error > div"))
			  .getText().contains("Invalid Login"));
  }

  //tc6 - remember me
  @Test(priority=6)
  public void rememberMe() {
	  driver.findElement(By.cssSelector("#txtUserName")).sendKeys("kanikagosain");
	  driver.findElement(By.cssSelector("#txtPassword")).sendKeys("Kanika@321#");
	  driver.findElement(By.name("txtSsi")).click();
	  driver.findElement(By.cssSelector("#txtPassword")).submit();  
	  Assert.assertEquals("https://hris.qainfotech.com:8086/empFeedback/yourFeedback", driver.getCurrentUrl());
  } 
  
  //tc7 - successful login
  @Test(priority=7)
  public void successfulLogin() {
	  driver.findElement(By.name("txtUserName")).sendKeys("kanikagosain");
	  driver.findElement(By.name("txtPassword")).sendKeys("Kanika@321#");
	  driver.findElement(By.name("txtPassword")).submit();  
	  Assert.assertEquals("https://hris.qainfotech.com:8086/empFeedback/yourFeedback", driver.getCurrentUrl());
  }
  
  //tc8
  @Test(priority=8)
  public void timeSheet_applyLeave() {
	  driver.findElement(By.className("time")).click();
	  driver.findElement(By.className("apply_leave")).click();
  }
 
  //tc9 - after login - logout
  @Test(priority=9)
  public void afterLogin_Logout() {
	  //successfulLogin();
	  driver.findElement(By.xpath("//*[@id=\"page\"]/div/div[1]/div[2]/ul/li/a/img")).click();
	  WebDriverWait wait=new WebDriverWait(driver,10); 
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("fa")));
	  driver.findElement(By.className("fa-sign-out")).click();
	  Assert.assertEquals("https://hris.qainfotech.com/login.php",driver.getCurrentUrl());
  } 
  
  //tc10 - forget Password
  @Test(priority=10)
  public void forgetPassword() {
	  driver.findElement(By.xpath("//*[@id=\"demo-2\"]/div/div[2]/ul/li[1]/a")).click();
	  driver.findElement(By.cssSelector("#login > form > div.loginTxtBtn.extraText > div > label:nth-child(2) > a")).click();
	  Assert.assertEquals("http://115.113.54.29/N9DLRfanX+PP6yPq5SqjXds5NYONBgzS6wV2zbDb8+I=NQGcQZpridnn8H8ErfRa65ReEJ+svJdDc0Y=/?action=sendtoken", driver.getCurrentUrl());
  }
  
//  //tc11
//  @Test
//  public void celebration() {
//	  //driver.findElement(By.)
//  }
  
}

