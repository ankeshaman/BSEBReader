package com.bseb.reading.bseb.service;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BsebWait {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void checkCorporateActions(String fromDate, String toDate) {
		
		// Set path for Edge driver (Download EdgeDriver from Microsoft website if not available)
        System.setProperty("webdriver.edge.driver", "C:\\Users\\ankesh.aman\\OneDrive - Accenture\\Documents\\Edge Driver\\edgedriver_win64\\msedgedriver.exe");

        WebDriver driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        try {
             driver.get("https://www.bseindia.com/corporates/ann.html");
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Select From Date
            //WebElement fromDateInput = driver.findElement(By.id("txtFromDt")); // Change ID as per actual page
           /* WebElement fromDateInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("fromDate")));
            fromDateInput.clear();
            fromDateInput.sendKeys(fromDate);

            // Select To Date
           // WebElement toDateInput = driver.findElement(By.id("txtToDt")); // Change ID as per actual page
            WebElement toDateInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("toDate")));
            toDateInput.clear();
            toDateInput.sendKeys(toDate);*/

            // Select Equity from Segment Dropdown
            WebElement segmentDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("ddlAnnType")));
            new Select(segmentDropdown).selectByVisibleText("Equity");

            // Select Corp. Action from Category Dropdown
            WebElement categoryDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("ddlPeriod")));
            new Select(categoryDropdown).selectByVisibleText("Corp. Action");
            
            
         // Select Dividend from Category Dropdown
            WebElement subCategoryDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("ddlsubcat")));
            new Select(subCategoryDropdown).selectByVisibleText("Dividend");

            // Click Submit button
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSubmit")));
            submitButton.click();

            // Wait for table to load (Explicit Wait if needed)
            Thread.sleep(5000);

            // Check if table contains data
            //List<WebElement> tableRows = driver.findElements(By.xpath("//table[@id='corporateTable']/tbody/tr"));
            
         // Extract required data          
            WebElement resultElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(text(),'Total No of Announcements')]")
            ));

            String resultText = resultElement.getText();
            System.out.println("Extracted Text: " + resultText);
            
            //return !tableRows.isEmpty(); // Return true if there is data, otherwise false

        } catch (Exception e) {
            e.printStackTrace();
            //return false;
        } finally {
            driver.quit();
        }
    }
	
	public static String extractAnnouncementCount(String html) {
        Document doc = Jsoup.parse(html);
        WebElement announcementElement = (WebElement) doc.selectFirst("div.col-lg-6.text-right.ng-binding b.ng-binding");
        return (announcementElement != null) ? announcementElement.getText() : "0";
    }



}
