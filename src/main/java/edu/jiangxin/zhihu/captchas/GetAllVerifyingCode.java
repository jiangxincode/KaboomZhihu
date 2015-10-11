package edu.jiangxin.zhihu.captchas;

import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GetAllVerifyingCode {
	public static void main(String[] args) {

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
			while(true) {
				String imageSrc = driver.findElement(By.className("js-captcha-img")).getAttribute("src");
				String imageName = String.valueOf(new File("./IdentifyingCode/").listFiles().length + 1) + ".gif";
				DownloadImage.download(imageSrc, imageName, "./IdentifyingCode/");
				driver.findElement(By.className("js-refresh-captcha")).click();
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		driver.close();
	}
}
