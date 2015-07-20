package edu.jiangxin.zhihu;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Unfollow {

	public static void main(String[] args) {
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

		while (true) {
			driver.get(args[2]+"/followees");
			List<WebElement> buttons = driver.findElements(By.tagName("button"));
			if(buttons == null) {
				
			}
			for (WebElement we : buttons) {
				if (we.getAttribute("class").contains("zg-btn-unfollow")) {
					System.out.println(we.getAttribute("id"));
					we.click();
				}
			}
		}

		// File cookieFile = new File("cookie.txt");

		// driver.close();

	}

}
