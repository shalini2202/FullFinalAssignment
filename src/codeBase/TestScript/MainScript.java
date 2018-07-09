package codeBase.TestScript;


import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MainScript {

	Functions fun = new Functions();
	WebDriver driver = null;
	public String actual = null;
	public String expected = null;
	File src = null;
	public static String link = null;


	//Launching Browser Using @Parameter
	@Parameters({"Browser"})
	@BeforeTest
	public void launchBrowser(String browser) throws IOException {

		System.out.println("Select Browser Of Your Choice:");

		//Launch Chrome Browser
		if(browser.equalsIgnoreCase("chrome")) {
			System.out.println("Launching Chrome Browser!!!");
			System.setProperty("webdriver.chrome.driver", "../FinalAssignment/src/codeBase/Utils/chromedriver.exe");
			driver=new ChromeDriver();
			driver.get(fun.getUrl("URL"));
			driver.manage().window().maximize();
		}

		//Launch Firefox Browser
		else if(browser.equalsIgnoreCase("firefox")){
			System.out.println("Launching FireFox Browser!!!");
			System.setProperty("webdriver.firefox.marionette", "../FinalAssignment/src/codeBase/Utils/geckodriver.exe");
			driver = new FirefoxDriver();
			driver.get(fun.getUrl("URL"));
			driver.manage().window().maximize();
		}

		//Launch IE Browser
		else if(browser.equalsIgnoreCase("ie")){
			System.out.println("Launching IE Browser!!!");	
			System.setProperty("webdriver.ie.driver","../FinalAssignment/src/codeBase/Utils/IEDriverServer.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true); 
			capabilities.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP,true);
			capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,true);   
			capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL,fun.getUrl("URL"));
			capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
			capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS,true);                      
			driver = new InternetExplorerDriver(capabilities);
			driver.get(fun.getUrl("URL"));
			driver.manage().window().maximize();
		}

		else {
			System.out.println("No Browser Found!!!");
		}


	}


	//Login InTo Application
	@Test(priority = 0)
	public void login() throws IOException {
		System.out.println("Login InTo Application:");

		//Write Header In Result.xlsx
		Object[] detailsData = {"TestCaseID", "TestCaseName", "Status", "Link"} ;
		fun.writeInResult(detailsData);
		
		//First Verification: Application_Launch
		actual = driver.findElement(By.xpath(fun.getObjectRepositoryValue("signIn", "xpath"))).getText();
		expected = "Sign in";
		Assert.assertTrue(actual.equalsIgnoreCase(expected));
		System.out.println("Application Launch Verification Successful!!!");
		link = "C:/Users/a631020/eclipse-workspace/FinalAssignment/src/codeBase/Result/ScreenShot/Application_Launch.png";
		//Take Screenshot
		src = (File)((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(src, new File(link));
		//Write First Verification Details In Result.xlsx
		Object[] detailsData1 = {"TC01", "Applicatin_Launch", "Pass", link} ;
		fun.writeInResult(detailsData1);

		//Click The SignIn HyperLink
		driver.findElement(By.xpath(fun.getObjectRepositoryValue("signIn", "xpath"))).click();
		
		//Enter Email From TestData.xlsx
		driver.findElement(By.xpath(fun.getObjectRepositoryValue("email", "xpath"))).sendKeys(fun.getTestData("emailData"));
		
		//Enter Password From TestData.xlsx
		driver.findElement(By.xpath(fun.getObjectRepositoryValue("pwd", "xpath"))).sendKeys(fun.getTestData("pwdData"));
		
		//Click On LogIn Button
		driver.findElement(By.xpath(fun.getObjectRepositoryValue("signInButton", "xpath"))).click();
		
		//Second Verification: Login_InTo_Application
		actual = driver.findElement(By.xpath(fun.getObjectRepositoryValue("signOut", "xpath"))).getText();
		expected = "Sign out";
		Assert.assertTrue(actual.equalsIgnoreCase(expected));
		System.out.println("Login InTo Application Verification Successful!!!");
		link = "C:/Users/a631020/eclipse-workspace/FinalAssignment/src/codeBase/Result/ScreenShot/Login_InTo_Application.png";
		//Take Screenshot
		src = (File)((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(src, new File(link));
		//Write Second Verification Details In Result.xlsx
		Object[] detailsData2 = {"TC02", "Login_InTo_Application", "Pass", link} ;
		fun.writeInResult(detailsData2);

	}


	//Add To Cart
	@Test(priority = 1)
	public void addToCart() throws IOException, InterruptedException {
		System.out.println("Add To Cart:");

		//Click On Cart
		driver.findElement(By.xpath(fun.getObjectRepositoryValue("cart", "xpath"))).click();

		//Third Verification: Initial_Cart_Status_Empty
		actual = driver.findElement(By.xpath(fun.getObjectRepositoryValue("cartEmpty", "xpath"))).getText();
		expected = "Your shopping cart is empty.";
		Assert.assertTrue(actual.equalsIgnoreCase(expected));
		System.out.println("Initial Cart Status Empty Verification Successful!!!");
		link = "C:/Users/a631020/eclipse-workspace/FinalAssignment/src/codeBase/Result/ScreenShot/Initial_Cart_Status_Empty.png";
		//Take Screenshot
		src = (File)((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(src, new File(link));
		//Write Third Verification Details In Result.xlsx
		Object[] detailsData3 = {"TC03", "Initial_Cart_Status_Empty", "Pass", link} ;
		fun.writeInResult(detailsData3);
		
		//Click On Dresses HyperLink
		driver.findElement(By.xpath(fun.getObjectRepositoryValue("dresses", "xpath"))).click();
		
		//Click On CasualDresses HyperLink
		driver.findElement(By.xpath(fun.getObjectRepositoryValue("casualDresses", "xpath"))).click();

		//Mousehover The Printed Dress
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.xpath(fun.getObjectRepositoryValue("printedDress", "xpath")))).build().perform();

		//Click On More Button
		driver.findElement(By.xpath(fun.getObjectRepositoryValue("more", "xpath"))).click();
		
		//Clear The Intial Quantity & Enter Quantity From TestData.xlsx
		driver.findElement(By.xpath(fun.getObjectRepositoryValue("quantity", "xpath"))).clear();
		driver.findElement(By.xpath(fun.getObjectRepositoryValue("quantity", "xpath"))).sendKeys(fun.getTestData("quantityData"));

		//Enter Size From TestData.xlsx
		Select s1 = new Select(driver.findElement(By.xpath(fun.getObjectRepositoryValue("size", "xpath"))));
		s1.selectByVisibleText(fun.getTestData("sizeData"));

		//Click On AddToCart Button
		driver.findElement(By.xpath(fun.getObjectRepositoryValue("addToCartButton", "xpath"))).click();

		
		//Fourth Verification: Size_Quantity
		Thread.sleep(2000);
		actual = driver.findElement(By.id(fun.getObjectRepositoryValue("sizeVerification", "id"))).getText();
		expected = fun.getTestData("sizeData");
		Assert.assertTrue(actual.contains(expected));
		System.out.println("Size Verification Successful!!!"); 

		actual = driver.findElement(By.id(fun.getObjectRepositoryValue("quantityVerification", "id"))).getText();
		expected = fun.getTestData("quantityData");
		Assert.assertTrue(actual.equalsIgnoreCase(expected));
		System.out.println("Quantity Verification Successful!!!"); 
		link = "C:/Users/a631020/eclipse-workspace/FinalAssignment/src/codeBase/Result/ScreenShot/Size_Quantity.png";
		//Take Screenshot
		src = (File)((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(src, new File(link));
		//Write Fourth Verification Details In Result.xlsx
		Object[] detailsData4 = {"TC04", "Size_Quantity", "Pass", link} ;
		fun.writeInResult(detailsData4);

		//Close The Dress Confirmation Window
		driver.findElement(By.xpath(fun.getObjectRepositoryValue("close", "xpath"))).click();		

	}


	//Delete From Cart
	@Test(priority = 2)
	public void deleteFromCart() throws IOException, InterruptedException {
		System.out.println("Delete From Cart:");

		//Click On Cart
		Thread.sleep(2000);
		driver.findElement(By.xpath(fun.getObjectRepositoryValue("cart", "xpath"))).click();

		//Fifth Verification: Product_In_Cart
		actual = driver.findElement(By.xpath(fun.getObjectRepositoryValue("cartProduct", "xpath"))).getText();
		expected = "2 Products";
		Assert.assertTrue(actual.equalsIgnoreCase(expected));
		System.out.println("Product In Cart Verification Successful!!!");
		link = "C:/Users/a631020/eclipse-workspace/FinalAssignment/src/codeBase/Result/ScreenShot/Product_In_Cart.png";
		//Take Screenshot
		src = (File)((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(src, new File(link));
		//Write Fifth Verification Details In Result.xlsx
		Object[] detailsData5 = {"TC05", "Product_In_Cart", "Pass", link} ;
		fun.writeInResult(detailsData5);

		//Click On Delete Button
		driver.findElement(By.xpath(fun.getObjectRepositoryValue("delete", "xpath"))).click();

		//Sixth Verification: Again_Cart_Status_Empty
		Thread.sleep(2000);
		actual = driver.findElement(By.xpath(fun.getObjectRepositoryValue("cartEmpty", "xpath"))).getText();
		expected = "Your shopping cart is empty.";
		Assert.assertTrue(actual.equalsIgnoreCase(expected));
		System.out.println("Again Cart Status Empty Verification Successful!!!");
		link = "C:/Users/a631020/eclipse-workspace/FinalAssignment/src/codeBase/Result/ScreenShot/Again_Cart_Status_Empty.png";
		src = (File)((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//Take Screenshot
		FileHandler.copy(src, new File(link));
		//Write Sixth Verification Details In Result.xlsx
		Object[] detailsData6 = {"TC06", "Again_Cart_Status_Empty", "Pass", link} ;
		fun.writeInResult(detailsData6);

		//Click On SignOut HyperLink
		Thread.sleep(2000);
		driver.findElement(By.xpath(fun.getObjectRepositoryValue("signOut", "xpath"))).click();

		//Seventh Verification: Logout_From_Application
		actual = driver.findElement(By.xpath(fun.getObjectRepositoryValue("signIn", "xpath"))).getText();
		expected = "Sign in";
		Assert.assertTrue(actual.equalsIgnoreCase(expected));
		System.out.println("Logout From Application Verification Successful!!!");	
		link = "C:/Users/a631020/eclipse-workspace/FinalAssignment/src/codeBase/Result/ScreenShot/Logout_From_Application.png";
		//Take Screenshot
		src = (File)((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(src, new File(link));
		//Write Seventh Verification Details In Result.xlsx
		Object[] detailsData7 = {"TC07", "Logout_From_Application", "Pass", link} ;
		fun.writeInResult(detailsData7);

	}


    //Closing Browser
	@AfterTest
	public void afterTest() {
		System.out.println("Closing Browser:");
		
		driver.close();
	}
}
