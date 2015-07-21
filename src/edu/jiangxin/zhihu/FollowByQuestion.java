package edu.jiangxin.zhihu;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.HasInputDevices;

public class FollowByQuestion {

	public static void main(String[] args) {
		int followCount = 0;
		WebDriver driver = new FirefoxDriver();
		driver.get("http://www.zhihu.com");
		
		driver.manage().window().maximize(); //maximize the window

		driver.findElement(By.linkText("µÇÂ¼")).click();
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

		driver.get("http://www.zhihu.com/question/19644659/followers");
		
		List<WebElement> a = null;
		boolean isFirstTime = true;
		
		while ((a == null) || isFirstTime) {

			for (WebElement we : a) {
				we.click();
				System.out.println(followCount++);
			}
			
			a = null;
			isFirstTime = false;
			
			while(a == null) {
				WebElement loadMore = driver.findElement(By.id("zh-load-more"));
				if ((loadMore != null) && (loadMore.isDisplayed())) {
					driver.findElement(By.id("zh-load-more")).click();
				}
				((HasInputDevices) driver).getKeyboard().sendKeys(Keys.PAGE_DOWN);
				a = driver.findElements(By.className("zg-btn-follow"));
			}
			
		}
	}

	// File cookieFile = new File("cookie.txt");

	// driver.close();

}
