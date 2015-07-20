package edu.jiangxin.zhihu;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.HasInputDevices;

public class FollowByTopicGo {

	public static void main(String[] args) {
		WebDriver driver = new FirefoxDriver();
		driver.get("http://www.zhihu.com");
		
		driver.findElement(By.linkText("µÇÂ¼")).click();
		driver.findElement(By.name("account")).sendKeys(args[0]);
		driver.findElement(By.name("password")).sendKeys(args[1]);
		if(driver.findElement(By.name("remember_me")).isSelected()) {
			driver.findElement(By.name("remember_me")).click();
		}
		driver.findElement(By.className("sign-button")).click();
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		driver.get("http://www.zhihu.com/topic/19574589/followers");
		List<WebElement> a = driver.findElements(By.tagName("a"));
		while(true) {
			for(WebElement we : a) {
				if(we.getAttribute("class").contains("zg-btn-follow")) {
					System.out.println(we.getAttribute("id"));
					we.click();
				}
			}
			((HasInputDevices) driver).getKeyboard().sendKeys(Keys.PAGE_DOWN);
			boolean has = false;
			a = driver.findElements(By.tagName("a"));
			for(WebElement we : a) {
				if(we.getAttribute("class").contains("zg-btn-follow")) {
					has = true;
				}
			}
			while(!has) {
				if(driver.findElement(By.id("zh-load-more")) != null) {
					driver.findElement(By.id("zh-load-more")).click();
				}
				((HasInputDevices) driver).getKeyboard().sendKeys(Keys.PAGE_DOWN);
				a = driver.findElements(By.tagName("a"));
				for(WebElement we : a) {
					if(we.getAttribute("class").contains("zg-btn-follow")) {
						has = true;
					}
				}
			}
		}
		
		
		//File cookieFile = new File("cookie.txt");
		

		//driver.close();

	}

}
