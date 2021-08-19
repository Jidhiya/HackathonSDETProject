package hackathonIndeedJobScraping;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openxmlformats.schemas.drawingml.x2006.chart.impl.STRadarStyleImpl;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;

public class ScrapingIndeedJobs {
	private static final long TIME_TO_WAIT = 0;
	WebDriver driver = null;
	String job_Search;
	@Test
	public void scrape_data() throws InterruptedException, IOException {
		
	    Scanner myObj = new Scanner(System.in);  // Create a Scanner object
	    System.out.println("What jobs are you looking for ? ");

	    job_Search = myObj.nextLine();
		// Sending keys api testing
		driver.findElement(By.id("text-input-what")).sendKeys(job_Search);
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);

		// Clearing the where field

		driver.findElement(By.xpath("//input[@id='text-input-where']")).sendKeys(Keys.CONTROL + "a");
		driver.findElement(By.xpath("//input[@id='text-input-where']")).sendKeys(Keys.BACK_SPACE);
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);

		// click find
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		// Selecting option as Last 24 hours
		WebElement select = driver.findElement(By.xpath("//*[@id='filter-dateposted']/div[1]"));
		select.click();
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);

		List<WebElement> ulList = select.findElements(By.xpath("//*[@id='filter-dateposted-menu']"));
		List<WebElement> liList = ulList.get(0).findElements(By.tagName("li"));

		liList.get(0).click();
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		Thread.sleep(3000);

		// handling alert
		driver.findElement(By.xpath("//*[@id=\"popover-x\"]/button")).click();
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);

		System.out.println("Inside Main function");

		get_JobCardDetails();
	}

	public void get_JobCardDetails() throws InterruptedException, IOException {
		Thread.sleep(1000);
		List<WebElement> pagination = driver.findElements(By.xpath("//ul[@class='pagination-list']/li"));
		int pgSize = pagination.size();

		FileWriter csvWriter = new FileWriter(job_Search.replace(" ","")+".csv");
		csvWriter.append("JobTitle");
		csvWriter.append(",");
//		csvWriter.append("JobCategory");
//		csvWriter.append(",");
		csvWriter.append("JobCompanyName");
		csvWriter.append(",");
		csvWriter.append("JobLocation");
		csvWriter.append(",");
		csvWriter.append("JobPostedDate");
		csvWriter.append(",");
		csvWriter.append("PositonID");
		csvWriter.append(",");
		csvWriter.append("JobLink");
		csvWriter.append(",");
		csvWriter.append("JobDesc");
		csvWriter.append(",");
		csvWriter.append("DateScrapedOn");
		csvWriter.append(",");
		

		csvWriter.append("\n");

		System.out.println("No of Pages :" + pgSize);
		// checkif pagination link exists
		for (int j = 1; j < pgSize; j++) {
			Thread.sleep(1000);
			WebElement pagei = driver.findElement(By.xpath("(//ul[@class='pagination-list']/li)[" + j + "]"));
			pagei.click();
			Thread.sleep(1000);
			WebElement jobCard = driver.findElement(By.id("mosaic-provider-jobcards"));
			List<WebElement> jobCardWitha = jobCard.findElements(By.tagName("a"));
			int totcount = jobCardWitha.size();
			System.out.println(jobCardWitha.size());

			try {
				for (int i = 0; i < totcount; i++) {

					IndeedJob iJob = new IndeedJob();
					List<WebElement> jobTitle = driver.findElements(
							By.xpath("//h2[@class='jobTitle jobTitle-color-purple jobTitle-newJob']/span"));
					System.out.println("Job Title : " + jobTitle.get(i).getText());
					// iJob.JobTitle = jobTitle.get(i).getText().replace(",", " ").replace("\n", "
					// ");
					iJob.setJobTitle(jobTitle.get(i).getText().replace(",", " ").replace("\n", " "));

//					List<WebElement> jobCategory = driver.findElements(By.xpath("(//div[@class='companyLocation']/span)[3]"));
//					System.out.println("Job Category : "+jobCategory.get(i).getText());
//					iJob.setJobCategory(jobCategory.get(i).getText().replace(",", " ").replace("\n", " "));

					List<WebElement> jobCompName = driver.findElements(By.xpath("//span[@class='companyName']"));
					System.out.println("Job Company Name : " + jobCompName.get(i).getText());
					iJob.setJobCompName(jobCompName.get(i).getText().replace(",", " ").replace("\n", " "));

					List<WebElement> jobLocation = driver.findElements(By.xpath("//div[@class='companyLocation']"));
					System.out.println("Job Location : " + jobLocation.get(i).getText());
					iJob.setJobLocation(jobLocation.get(i).getText().replace(",", " ").replace("\n", " "));

					List<WebElement> jobPostedDate = driver.findElements(By.xpath("//span[@class='date']"));
					System.out.println("Job Posted Date : " + jobPostedDate.get(i).getText());
					iJob.setJobPostedDate(jobPostedDate.get(i).getText().replace(",", " ").replace("\n", " "));

					List<WebElement> jobId = driver.findElements(By.xpath("//div[@id='mosaic-provider-jobcards']/a"));
					System.out.println("Position ID :" + jobId.get(i).getAttribute("id"));
					iJob.setPositionId(jobId.get(i).getAttribute("id").replace(",", " ").replace("\n", " "));

					List<WebElement> jobLink = driver.findElements(By.xpath("//div[@id='mosaic-provider-jobcards']/a"));
					System.out.println("Job Link :" + jobLink.get(i).getAttribute("href"));
					iJob.setJobLink(jobLink.get(i).getAttribute("href").replace(",", " ").replace("\n", " "));

					List<WebElement> jobDesc = driver.findElements(By.xpath("//div[@class='job-snippet']"));
					System.out.println("Job Description : " + jobDesc.get(i).getText());
					iJob.setJobDesc(jobDesc.get(i).getText().replace(",", " ").replace("\n", " "));

					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
					LocalDateTime now = LocalDateTime.now();
					System.out.println("Data Scraped Date and Time : " + dtf.format(now));
					iJob.setDateScrapedOn(dtf.format(now));

					System.out.println(iJob);

					System.out.println("\n");
//					if ((!iJob.getJobTitle().isBlank()) || (!iJob.getJobCompName().isBlank())
//							|| (!iJob.getJobLocation().isBlank()) || (!iJob.getJobPostedDate().isBlank())
//							|| (!iJob.getJobDesc().isBlank())) {
					if ((!iJob.getJobTitle().isBlank())) {
						// System.out.println("writing to new") ;
						csvWriter.append(iJob.toString());
						csvWriter.append("\n");
					}
				}

				csvWriter.flush();
				csvWriter.close();
			}

			catch (Exception e) {
//		// TODO Auto-generated catch block
//e.printStackTrace();
//	System.out.println("Inside catch block");
				// get_NextJobDetails();
			}
		}
	}

	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\jidhi\\OneDrive\\Desktop\\SDET training\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.navigate().to("https://www.indeed.com/");
		driver.manage().window().maximize();
	}

	@AfterTest
	public void afterTest() {
		driver.close();
		driver.quit();

	}

}
