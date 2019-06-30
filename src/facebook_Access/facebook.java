package facebook_Access;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.fasterxml.jackson.databind.ObjectMapper;




public class facebook {
	
	public static WebDriver facebook;
	static String Username = "mrrobotmus@gmail.com";
	static String Password = "mus%^&4545";
	
	public static String  current_ele_id;
	
	static long timeout = 1000;
	
	facebook_parser parser;
	
	public  void getFacebookInstance() throws InterruptedException, IOException {
		
		//System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		System.setProperty("phantomjs.binary.path", "C:\\phantomjs.exe");
		
		
		//To avoid show notification from the browser
		//Create a map to store  preferences 
		Map<String, Object> prefs = new HashMap<String, Object>();

		//add key and value to map as follow to switch off browser notification
		//Pass the argument 1 to allow and 2 to block
		prefs.put("profile.default_content_setting_values.notifications", 2);

		//Create an instance of ChromeOptions 
		//ChromeOptions options = new ChromeOptions();

		// set ExperimentalOption - prefs 
		//options.setExperimentalOption("prefs", prefs);
		
		facebook = new PhantomJSDriver();
		
		parser = new facebook_parser();
		
		
		
		//navigate to login page
		facebook.get("https://www.facebook.com/login");
		Thread.sleep(timeout);
		
		//check if user already logged in
		if(facebook.findElements(By.xpath("//*[@id=\"js_t\"]/div/div/div[1]/div[1]/h1/a")).size()==0) {
			//user is not logged in...
			
			facebook.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys(Username);
			Thread.sleep(timeout);
			facebook.findElement(By.xpath("//*[@id=\"pass\"]")).sendKeys(Password);
			Thread.sleep(timeout);
			
			facebook.findElement(By.xpath("//*[@id=\"loginbutton\"]")).click();
			Thread.sleep(timeout+3000);
					
		}
		
		
		prepareSearchQuery("Computer Engineering 116");
			
		
	}
	
	
	public  void prepareSearchQuery(String GroupName) throws InterruptedException, IOException {
		

		facebook.get("https://www.facebook.com/groups/295009014182402/");
		Thread.sleep(timeout);
		
		facebook_parser.do_scrolling(4, 1200);
		
		List<WebElement> posts =  parser.get_story_elements();

		 StringBuilder Posts_JSON = new StringBuilder();
		 
		 
			ObjectMapper Obj = new ObjectMapper();
			

	
		 for(int i=0;i<posts.size();i++)
         {
			 current_ele_id="ele"+i;
			 
			 
			 try {
	 				
	 				
	 				Posts_JSON.append(Obj.writeValueAsString(parser.parse_post_data(posts.get(i), i)) + " , \n");
				 
				
	 			}catch (Exception e) {
				
	 				e.printStackTrace();
				}
			 
			 
		       			
             
         }
		 
		 facebook.close();
		 facebook.quit();
		 
			File json_out = new File("JSON.txt");
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(json_out), StandardCharsets.UTF_8);
			
			
			out.append(Posts_JSON);
			
			out.close();
		 
	
		
	}
	
	
	

}
