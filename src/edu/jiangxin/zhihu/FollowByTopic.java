package edu.jiangxin.zhihu;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FollowByTopic {

	public static void main(String[] args) {

		WebDriver driver = new FirefoxDriver();
		driver.get("http://www.zhihu.com");
		
		driver.manage().window().maximize(); //maximize the window
		
		CookieWrapper cookieWrapper = new CookieWrapper();
		cookieWrapper.setCookieList("./tmp/Cookie/cookie.txt");
		List<Cookie> cookieList = cookieWrapper.getCookieList();
		for(Cookie cookie : cookieList) {
			driver.manage().addCookie(cookie);
		}

		driver.get("http://www.zhihu.com/topic/19627873/followers");
		
		
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
