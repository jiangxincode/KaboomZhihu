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
		int followCount = 0;
		WebDriver driver = new FirefoxDriver();
		driver.get("http://www.zhihu.com");

		driver.findElement(By.linkText("µÇÂ¼")).click();
		driver.findElement(By.name("account")).sendKeys(args[0]);
		driver.findElement(By.name("password")).sendKeys(args[1]);
		if (driver.findElement(By.name("remember_me")).isSelected()) {
			driver.findElement(By.name("remember_me")).click();
		}
		driver.findElement(By.className("sign-button")).click();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		driver.get("http://www.zhihu.com/topic/19674381/followers");
		List<WebElement> a = driver.findElements(By.className("zg-btn-follow"));

		for (WebElement we : a) {
			we.click();
			followCount++;
		}
		while (true) {
			WebElement loadMore = driver.findElement(By.id("zh-load-more"));
			if ((loadMore != null) && (loadMore.isDisplayed())) {
				driver.findElement(By.id("zh-load-more")).click();
			}
			((HasInputDevices) driver).getKeyboard().sendKeys(Keys.PAGE_DOWN);
			a = driver.findElements(By.className("zg-btn-follow"));
			for (WebElement we : a) {
				we.click();
				followCount++;
				if(followCount == 1413) {
					System.exit(1);
				}
			}
		}
	}

	// File cookieFile = new File("cookie.txt");

	// driver.close();

}
