package com.test;

import java.util.Scanner;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AutoLogin
{
   private WebDriver driver;

   private final String baseUrl = "https://www.gmail.com";
   
   private GmailUtil gutil = null;
   
   private final int SECONDS_TO_WAIT = 5;  // in seconds
   
   @BeforeClass(alwaysRun = true)
   public void setUp() throws Exception
   {
      System.setProperty("webdriver.gecko.driver", "lib/geckodriver.exe");
      gutil = new GmailUtil();
   }

   @Test
   public void test() throws Exception
   {
      giveSpaceInLogs(5);
      System.out.println("/////////////////////////////////////////////////////////");
      System.out.println("            Welcome to the Gmail Utitlity                ");
      System.out.println("/////////////////////////////////////////////////////////");
      giveSpaceInLogs(2);
      startAutomation();
   }

  
   private void startAutomation() throws Exception {
      
      try {
         String fileName = getFileNameWithPath();
         
         if(fileName.isEmpty()) {
            throw new Exception("File Name is Empty.");
         }
         
         String[][] data =  ExcelDb.getInstance(fileName).get2DArray();
         
         for(int rowNum=0; rowNum < data.length; rowNum ++) {
            //Step 1: Open the URL
            driver = new FirefoxDriver();
            driver.get(baseUrl);
            
            //Step 2: Fill the data to Gmail login and proceed till OTP
            String[] row = data[rowNum];
            String userName = row[0];
            String password = row[1];
            gutil.fillUserNameAndNext(driver, userName);
            gutil.fillPassword(driver, password);
            
            //Step 3: Wait for a particular time
            Thread.sleep(SECONDS_TO_WAIT*1000);
            
            //Step 4: Close the Browser
            driver.quit();
         }
         retry();
         
      } catch(Exception e) {
         e.printStackTrace();
      }
      
      
   }
   
   @SuppressWarnings("resource")
   private String getFileNameWithPath() {
      giveSpaceInLogs(5);
      System.out.println("Please input File Name with with below(Example: /path/to/your/file/excel.xls):");
      Scanner s = new Scanner(System.in);
      return s.nextLine().trim();
   }

   
   @AfterClass(alwaysRun = false)
   public void tearDown() throws Exception
   {
      giveSpaceInLogs(5);
      System.out.print("Thank you!");
   }
   
   @SuppressWarnings("resource")
   private void retry() throws Exception {
      giveSpaceInLogs(5);
      System.out.print("WARNING: Pragram faced some issue (See logs in console). Do you want to retry?(y/n)?:");
      Scanner option = new Scanner(System.in);
      String switchOffOption = option.nextLine();
      
      if("y".equalsIgnoreCase(switchOffOption)) {
         startAutomation(); 
      } else if("n".equalsIgnoreCase(switchOffOption)){
         driver.quit();
      } else {
         tearDown();
      }
      
   }
   
   private static void giveSpaceInLogs(int count)
   {
      for (int ii = 0; ii < count; ii++)
      {
         System.out.println("  ");
      }

   }
   
}
