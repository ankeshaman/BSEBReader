package com.bseb.reading.bseb.service;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

@Service
//@Scope("Prototype")
public class BSEDataChecker {
	
	@Autowired
	private EmailService emailService;
	
	public static int checkCorporateActions(String fromDate, String toDate) {
		
		int record=0;
		// Set path to ChromeDriver
        /*System.setProperty("webdriver.chrome.driver", "C:\\Users\\ankesh.aman\\OneDrive - Accenture\\Documents\\Chrome Driver\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
		
		ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");  // Run without UI
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
		
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(options);*/
        //ChromeDriver driver = new ChromeDriver();
		
		// Set path for Edge driver (Download EdgeDriver from Microsoft website if not available)
        System.setProperty("webdriver.edge.driver", "C:\\Users\\ankesh.aman\\OneDrive - Accenture\\Documents\\Edge Driver\\edgedriver_win64\\msedgedriver.exe");

        WebDriver driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        try {
            //driver.get("https://www.bseindia.com/corporates/corporates_act.html");
            driver.get("https://www.bseindia.com/corporates/ann.html");
            
            //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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
            Select segmentDropdown = new Select(driver.findElement(By.id("ddlAnnType"))); // Change ID as per actual page
            segmentDropdown.selectByVisibleText("Equity");

            // Select Corp. Action from Category Dropdown
            Select categoryDropdown = new Select(driver.findElement(By.id("ddlPeriod"))); // Change ID as per actual page
            //categoryDropdown.selectByVisibleText("Corp. Action");
            categoryDropdown.selectByVisibleText("Company Update");
            
            
         // Select Dividend from Category Dropdown
            Select subCategoryDropdown = new Select(driver.findElement(By.id("ddlsubcat"))); // Change ID as per actual page
            //subCategoryDropdown.selectByVisibleText("Dividend");
            subCategoryDropdown.selectByVisibleText("Award of Order / Receipt of Order");

            // Click Submit button
            /*WebElement submitButton = driver.findElement(By.id("btnSubmit")); // Change ID as per actual page
            submitButton.click();*/
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSubmit")));

            // Using JavaScript Click
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

            // Wait for table to load (Explicit Wait if needed)
            Thread.sleep(5000);

            // Check if table contains data
            //List<WebElement> tableRows = driver.findElements(By.xpath("//table[@id='corporateTable']/tbody/tr"));
            
         // Extract required data
            String pageSource = driver.getPageSource();
            String extractedNumber = extractAnnouncementCount(pageSource);
            System.out.println("Total Announcements: " + extractedNumber);
            record = Integer.parseInt(extractedNumber);
         // Send Email
            //EmailService emailService = new EmailService();
            //emailService.sendEmail("ankeshaman404@gmail.com", "BSE Announcements", "Total Announcements: " + extractedNumber);
            //emailService.sendEmail("xpkunal@gmail.com", "BSE Announcements", "Hi Ayush Congratulations! Total Announcements: " + extractedNumber);
            
            //return !tableRows.isEmpty(); // Return true if there is data, otherwise false

        } catch (Exception e) {
            e.printStackTrace();
            //return false;
        } finally {
            driver.quit();
        }
		return record;
    }
	
	public static String extractAnnouncementCount(String html) {
        Document doc = Jsoup.parse(html);
        @Nullable Element announcementElement = doc.selectFirst("div.col-lg-6.text-right.ng-binding b.ng-binding");
        return (announcementElement != null) ? announcementElement.text() : "0";
    }



}
