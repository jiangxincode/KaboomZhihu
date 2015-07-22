package edu.jiangxin.zhihu;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Unfollow {

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

		while (true) {
			driver.get(args[2]+"/followees");
			List<WebElement> buttons = driver.findElements(By.className("zg-btn-unfollow"));
			if(buttons == null) {
				break;
			}
			for (WebElement we : buttons) {
				we.click();
			}
		}
		driver.close();

	}

}
