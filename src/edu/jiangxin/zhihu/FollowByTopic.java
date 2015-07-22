package edu.jiangxin.zhihu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FollowByTopic {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		WebDriver driver = new FirefoxDriver();
		driver.get("http://www.zhihu.com");
		
		driver.manage().window().maximize(); //maximize the window
        try {
            File cookieFile = new File("./tmp/Cookie/zhihu.cookie.txt");
            FileReader fr = new FileReader(cookieFile);
            BufferedReader bufferedReader = new BufferedReader(fr);
            String line;
            while((line = bufferedReader.readLine()) != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(line, ";");
                while(stringTokenizer.hasMoreTokens()) {
                    String name = stringTokenizer.nextToken();
                    String value = stringTokenizer.nextToken();
                    String domain = stringTokenizer.nextToken();
                    String path = stringTokenizer.nextToken();
                    Date expiry = null;
                    String dt;
                    if(!(dt = stringTokenizer.nextToken()).equals("null")) {
                        expiry = new Date(dt);
                    }

                    boolean isSecure = new Boolean(stringTokenizer.nextToken()).booleanValue();
                    Cookie cookie = new Cookie(name,value,domain,path,expiry,isSecure);
                    driver.manage().addCookie(cookie);
                }
            }
            bufferedReader.close();
        }catch(Exception ex) {
                ex.printStackTrace();
        }

		driver.get("http://www.zhihu.com/topic/19835412/followers");
		
		
		List<WebElement> follow = driver.findElements(By.className("zg-btn-follow"));
		List<WebElement> unfollow = driver.findElements(By.className("zg-btn-unfollow"));
		String followNumber = driver.findElement(By.tagName("strong")).getText();
		System.out.println(followNumber);
		while(follow.size() + unfollow.size() < Integer.parseInt(followNumber)-1) { // in case of the ower of the program has followed this topic
			WebElement loadMore = driver.findElement(By.id("zh-load-more"));
			if((loadMore != null) && loadMore.isDisplayed()) {
				loadMore.click();
			} else {
				String js="var q=document.documentElement.scrollTop=document.body.scrollHeight";
				((JavascriptExecutor)driver).executeScript(js);
				//((HasInputDevices) driver).getKeyboard().sendKeys(Keys.PAGE_DOWN); // inefficiency
			}
			follow = driver.findElements(By.className("zg-btn-follow"));
			unfollow = driver.findElements(By.className("zg-btn-unfollow"));
			System.out.println(follow.size() + " " + unfollow.size());
		}
		
		for(WebElement we : follow) {
			we.click();
		}
		driver.close();
	}

}
