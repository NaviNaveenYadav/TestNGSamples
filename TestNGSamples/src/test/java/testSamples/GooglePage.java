package testSamples;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.collections.functors.WhileClosure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class GooglePage {
	
	WebDriver driver;
	Properties prop;
	
	@BeforeMethod
	public void setup() {
		
		prop = new Properties();
		String path = System.getProperty("user.dir")+"//src//test//resources//configFiles//config.properties";
		
		FileInputStream fin;
		
		try {
			fin = new FileInputStream(path);
			prop.load(fin);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	
	 @Test(dataProvider = "loginData")
	  public void cyPressSearchTest(String strUser, String strPassword) {
		  
		  driver.get(prop.getProperty(prop.getProperty("URL")));
		  driver.findElement(By.id("username")).sendKeys(strUser);
		  driver.findElement(By.id("password")).sendKeys(strPassword);
		  driver.findElement(By.cssSelector("i.fa.fa-2x.fa-sign-in")).click();
		  
	  }
	 
	 
	 @DataProvider(name="loginData")
	 public Object[][] getData() throws CsvValidationException, IOException {
		 
		 String path = System.getProperty("user.dir") +
					"//src//test//resources//testData//loginData.csv";
		 CSVReader reader = new CSVReader(new FileReader(path));
		 String str[];
		 
		 ArrayList<Object> dataList = new ArrayList<Object>();
		 while((str = reader.readNext()) != null) {
			 Object[] record = {str[0],str[1]};
			 dataList.add(record);
		 
	 }
	 reader.close();
	 return dataList.toArray(new Object[dataList.size()][]);
	
	
}
	 
	 @AfterMethod
	 public void tearDown() {
		 driver.close();
	 }
	 
}	 
