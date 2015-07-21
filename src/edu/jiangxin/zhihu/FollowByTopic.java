package edu.jiangxin.zhihu;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.HasInputDevices;

public class FollowByTopic {

	public static void main(String[] args) {

		WebDriver driver = new FirefoxDriver();
		driver.get("http://www.zhihu.com");
		
		driver.manage().window().maximize(); //maximize the window

		driver.findElement(By.linkText("登录")).click();
		driver.findElement(By.name("account")).sendKeys(args[0]);
		driver.findElement(By.name("password")).sendKeys(args[1]);
		if (driver.findElement(By.name("remember_me")).isSelected()) {
			driver.findElement(By.name("remember_me")).click();
		}
		driver.findElement(By.className("sign-button")).click();
		try {
			String imageSrc = driver.findElement(By.className("js-captcha-img")).getAttribute("src");
			DownloadImage.download(imageSrc, "temp.gif", "./IdentifyingCode/");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		driver.get("http://www.zhihu.com/topic/19593327/followers");
		
		
		List<WebElement> follow = driver.findElements(By.className("zg-btn-follow"));
		List<WebElement> unfollow = driver.findElements(By.className("zg-btn-unfollow"));
		String followNumber = driver.findElement(By.tagName("strong")).getText();
		System.out.println(followNumber);
		while(follow.size() + unfollow.size() < Integer.parseInt(followNumber)-1) { //由于本人可能会关注此话题，所以应该比实际关注人数少1
			WebElement loadMore = driver.findElement(By.id("zh-load-more"));
			if((loadMore != null) && loadMore.isDisplayed()) {
				loadMore.click();
			} else {
				((HasInputDevices) driver).getKeyboard().sendKeys(Keys.PAGE_DOWN);
				((HasInputDevices) driver).getKeyboard().sendKeys(Keys.PAGE_DOWN);
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
