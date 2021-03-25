	/*
	 *******************************************************************
	 *                                                                 *
	 *  Programmer: Andrew McCormick                                   *
	 *  Program:    HeroKuapp.java                                     *
	 *  Package:    SeleniumExamples                                   *  
	 *  Last Edit:  3.24.2021                                          *
	 *                                                                 *
	 *  This program uses herokaupp website to demonstrate multiple	   *  
	 *  Selenium tools in action.  Multiple different Web Elements     *
	 *  and methods are used to get a hold of items and manipulate     *
	 *  those items.                                                   *
	 *                                                                 *
	 *******************************************************************
	 */

package SeleniumExamples;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class Herokuapp 
{
	public static void main(String[] args) throws InterruptedException 
	{
		int pause = 1000; // Standard amount of time for Thread.sleep() statments
		
		// Information to get Chrome WebDriver 
		//System.setProperty("webdriver.chrome.driver", "C:\\Users\\aj300\\Documents\\Selenium\\chromedriver.exe");
		//WebDriver driver = new ChromeDriver();
		
		// Information to get Firefox WebDriver 
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\aj300\\Documents\\Selenium\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		
		// Creating an action object with the driver object
		Actions action = new Actions(driver);
		
		driver.manage().window().maximize(); // Maximizing browser window
		driver.manage().deleteAllCookies(); // Deleting the cookies in browser history
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS); // Creating a timeout of 30 seconds if page does not load
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // Creating implicit wait of 20 seconds for this project
		
		// Navigating to the main page of herokuapp
		driver.get("https://the-internet.herokuapp.com");
		
		AddRemoveElements(driver, pause); // Call to method addRemoveElements 

		Thread.sleep(pause);
		
		checkBoxes(driver, pause); // Call to method checkBoxes 
	
		Thread.sleep(pause);
		
		formAuth(driver, pause); // Call to method formAuth 
		
		Thread.sleep(pause);
		
		dropDown(driver, action, pause); // Call to method dropDown 
		
		Thread.sleep(pause);
		
		contextMenu(driver, action, pause); // Call to method contextMenu
		
		Thread.sleep(pause);

		// Closing out the driver and ending the program
		driver.quit();

	}
	
	/*
	 *******************************************************************************
	 *                                                                             *
	 *                      Method AddRemoveElements                               *
	 *                                                                             *
	 *	This method navigates to the Add/Remove Elements page on Herokuapp.        *
	 *	This program uses a for loop and a xpath link to click on the              *
	 *	addElement button four times.  The program then uses a four different	   *	
	 *	Selenium web elements to click the four delete buttons that appeared       *
	 *	after clicking the addElement button four times.  The method then          *  
	 *	navigates back to the main page of herokuapp.                              *
	 *                                                                             *
	 *******************************************************************************
	 */
	
	public static void AddRemoveElements(WebDriver driver, int pause) throws InterruptedException 
	{	
		// Navigating by xpath to the Add/Remove page
		driver.findElement(By.xpath("//a[contains(text(),'Add/Remove Elements')]")).click();
		
		Thread.sleep(pause);
		
		// Obtaining the add button with xpath
		WebElement addButton = driver.findElement(By.xpath("//button[@onclick='addElement()']"));
		
		// Clicking the add button four times to create four delete buttons
		for(int i = 0; i < 4; i++) 
		{
			addButton.click();
		}
		
		Thread.sleep(pause);
		
		// Four different methods to obtain the delete buttons and click them, thus removing the button from the webpage
		driver.findElement(By.xpath("//div[@id='elements']//child::button[2]")).click(); // Using xpath with a parent id element and finding the the second child button element 
		driver.findElement(By.xpath("//button[@onclick='deleteElement()'][3]")).click(); // Using xpath to find the third button element with a onclick attribute set to 'deleteElement()' 
		driver.findElement(By.cssSelector(".added-manually")).click(); // Using cssSelector to find the added-manually class of the button elements
		driver.findElement(By.className("added-manually")).click(); // Using class name to find the added-manually class of the button elements
		
		Thread.sleep(pause);
		
		// Returning to the main herokuapp page
		driver.navigate().back();
	}
	
	/*
	 *******************************************************************************
	 *                                                                             *
	 *                         Method checkBoxes                                   *
	 *                                                                             *
	 *	This method navigates to the Checkboxes page on Herokuapp.                 *
	 *	This program pulls all the checkboxes currently on the page and saves      *
	 *  them to a list object.  It then goes through a for loop to see if the      *
	 *  checkbox is checked by checking if its checked attribute is set to         *
	 *  null.  If it is set to null, the program checks the box. The method        *
	 *  then navigates back to the main page of herokuapp.                         *
	 *                                                                             *
	 *******************************************************************************
	 */
	
	public static void checkBoxes(WebDriver driver, int pause) throws InterruptedException 
	{
		// Navigating by cssSelector to the Checkboxes page
		driver.findElement(By.cssSelector("a[href='/checkboxes']")).click();
		
		Thread.sleep(pause);
		
		// Obtaining all the checkbox elements with a cssSelector and assigning them to a List object
		List<WebElement> check = driver.findElements(By.cssSelector("input[type='checkbox']")); 
		
		// Looping through the List object and checking if the checkbox has been checked; if not, check the checkbox
		for(int i = 0; i < check.size(); i++) 
		{
			if(check.get(i).getAttribute("checked") == null) 
			{	
				driver.findElement(By.xpath("//input[@type='checkbox'][" + i + 1 + "]")).click(); // Checking a checkbox using xpath and the counter variable i to locate the correct checkbox
			}	
		}
		
		Thread.sleep(pause);
		
		// Returning to the main herokuapp page
		driver.navigate().back();	
	}
	
	/*
	 *******************************************************************************
	 *                                                                             *
	 *                           Method formAuth                                   *
	 *                                                                             *
	 *	This method navigates to the form authentication page on Herokuapp.        *
	 *	This method first creates two string variables to save the username        *
	 *  and password meant for a login.  The password is purposefully incorrect    *
	 *  at the start.  It also creates a boolean variable to run a loop that       *
	 *  allows the username and password to be inputed again if wrong the first    *
	 *  time.  The method then enters the username, password, and clicks the       *
	 *  submit button.  The flash message that appears on top of the screen        *
	 *  regardless of sign-in fail or success is then printed to the console.      *
	 *  The method then tries to press the logout button on the successful login   *
	 *  page.  A try-catch block is used to see if the the button can be found     *
	 *  or not.  After the initial sign-in failure, the password is changed to     *
	 *  the correct one and the sign-in is tried again.  This time it succeeds.    *
	 *  The method then navigates back to the main page of herokuapp.              *
	 *                                                                             *
	 *******************************************************************************
	 */
	
	public static void formAuth(WebDriver driver, int pause) throws InterruptedException 
	{
		String userName = "tomsmith",
			   password = "SuperSecretPassword";
		
		boolean exceptionPresent = true; // variable for the while loop
		
		// Navigating by xpath to the loginPage page
		driver.findElement(By.xpath("//a[contains(text(),'Form Authentication')]")).click();

		// While loop to for rerunning sign-in code upon failure
		while(exceptionPresent)
		{
			exceptionPresent = false; // Change boolean variable to false at start of each loop
		
			driver.findElement(By.id("username")).sendKeys(userName); // Sending the username to the username box
					
			Thread.sleep(pause);
			
			driver.findElement(By.id("password")).sendKeys(password); // Sending the password to the password box
			
			Thread.sleep(pause);
		
			driver.findElement(By.cssSelector("button[type='submit']")).click(); // Clicking the submit button
				
			Thread.sleep(2000);
		
			System.out.println(driver.findElement(By.id("flash")).getText()); // Outputting the flash bar text to the console
				
			// Trying to press the logout button the successful login page
			try 
			{
				driver.findElement(By.cssSelector("a[class='button secondary radius']")).click();
			}
			
			// Catch the exception if the logout button is not on the current page
			catch(NoSuchElementException e) 
			{
				System.out.println("Login Failed");
				
				exceptionPresent = true; // Change boolean variable to true to loop again
				
				password += "!"; // Change the password so it is correct
			}
		}
		
		Thread.sleep(pause);
		
		// Returning to the main herokuapp page 		
		driver.navigate().to("https://the-internet.herokuapp.com");
	}
	
	/*
	 *******************************************************************************
	 *                                                                             *
	 *                           Method dropDown                                   *
	 *                                                                             *  
	 *	This method navigates to the Dropdown page on Herokuapp.                   *
	 *	This method first saves the dropdown menu element to a variable to be      *
	 *  be used multiple times.  The drop down bar is then clicked using an        *
	 *  action object.  The two options in the dropdown menu are then located      *
	 *  by using different methods and set to variables.  Option1 is then          *
	 *  clicked using an action object.  The dropdown menu is then clicked         *
	 *  again.  Option2 is then clicked using an action object.  The method        *
	 *  then navigates back to the main page of herokuapp.                         *
	 *                                                                             *
	 *******************************************************************************
	 */
	
	public static void dropDown(WebDriver driver, Actions action, int pause) throws InterruptedException 
	{
		// Navigating by partialLinkText to the Dropdown page
		driver.findElement(By.partialLinkText("Dropdown")).click();
		
		Thread.sleep(pause);
		
		// Finding the drop down bar by id and saving it to a variable
		WebElement dropDownBar = driver.findElement(By.id("dropdown")); 
		
		action.moveToElement(dropDownBar).build().perform(); // Using action object to move to the drop down bar
		dropDownBar.click(); // Clicking on the drop down bar
		
		// Chrome can't find the dropdown options; Catch the exception to bypass for chrome
		try
		{
		WebElement option1 = driver.findElement(By.cssSelector("option[value='1']")); // Finding option1 by cssSelector and saving it to a variable
		WebElement option2 = driver.findElement(By.xpath("//option[@value='2']")); // Finding option2 by xpath and saving it to a variable
		
		 
		action.moveToElement(option1).build().perform(); // Using action object to move to option1
		option1.click(); // Clicking on option1
		
		Thread.sleep(pause);
		
		action.moveToElement(dropDownBar).build().perform(); // Using action object to move back to the drop down bar
		dropDownBar.click(); // Clicking on the drop down bar again
		
		Thread.sleep(pause);
		
		action.moveToElement(option2).build().perform(); // Using action object to move to option2		
		option2.click(); // Clicking on option2
		}
		
		catch(ElementNotInteractableException e) 
		{
			System.out.println("Not able to get into the iFrame");
		}
		
		finally
		{
			Thread.sleep(pause);
			
			// Returning to the main herokuapp page
			driver.navigate().back();
		}
	}
	
	/*
	 *******************************************************************************
	 *                                                                             *
	 *                         Method contextMenu                                  *
	 *                                                                             *
	 *	This method navigates to the Context Menu page on Herokuapp.               *
	 *	This program finds the box that needs to be right clicked with the ID      *
	 *  Web Element.  It then uses an action object to right click the object      *
	 *  which causes an alert box to appear.  The driver then switches to the      *
	 *  alert box and uses the accept() method to check the ok button.  The        *
	 *  method then navigates back to the main page of herokuapp.                  *
	 *                                                                             *
	 *******************************************************************************
	 */
	
	public static void contextMenu(WebDriver driver, Actions action, int pause) throws InterruptedException 
	{
		// Navigating by linkText to the Context Menu page
		driver.findElement(By.linkText("Context Menu")).click();
		
		Thread.sleep(pause);
		
		// Finding the box to be right clicked with the id web element
		WebElement box = driver.findElement(By.id("hot-spot"));
		
		// Using an action object to right click the box
		action.contextClick(box).build().perform();
		
		Thread.sleep(pause);
		
		// Try-catch-finally block to see if the alert shows up; throwing exception if not; navigating back to the main herokuapp page regardless
		try 
		{
			driver.switchTo().alert().accept(); // Clicking the ok button in the alert box
		}
		
		catch(NoAlertPresentException e) 
		{
			// Displaying exception thrown
			e.printStackTrace();
			System.out.println("No alert present");
		}
		
		finally 
		{
			Thread.sleep(pause);
			
			// Returning to the main herokuapp page
			driver.navigate().back();
		}
	}
}
