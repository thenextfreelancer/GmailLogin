/**
 * 
 */
package com.test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Moksh
 *
 */
public class GmailUtil
{
   private final By userNameBy = By.id("identifierId");
   
   private final By userNextButtonBy = By.id("identifierNext");
   
   private final By passwordBy = By.name("password");
   
   private final By passwordNextButtonBy = By.id("passwordNext");
   
   public void fillUserNameAndNext(WebDriver driver, String userName) {
      try
      {
         driver.findElement(userNameBy).sendKeys(userName);
         Thread.sleep(1000);
         driver.findElement(userNextButtonBy).click();
      }
      catch (Exception e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   
   public void fillPassword(WebDriver driver, String password) {
      try
      {
         WebDriverWait wait = new WebDriverWait(driver, 20);
         
         try {
            WebElement element1 = wait.until(ExpectedConditions.elementToBeClickable(passwordBy));
            element1.sendKeys(password);
         } catch(Exception e) {
            WebElement ele = driver.findElement(passwordBy);
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0].setValue("+password+");", ele);
         }
         
        
         driver.findElement(passwordNextButtonBy).click();
      }
      catch (Exception e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   
}
