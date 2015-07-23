package edu.jiangxin.zhihu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Login {
	public static void main(String[] args) {

		ConfigParser configParser = new ConfigParser();
		configParser.paser();
		
		WebDriver driver = new FirefoxDriver();
		
		driver.get("http://www.zhihu.com");
		
		driver.manage().window().maximize(); //maximize the window

		driver.findElement(By.linkText("登录")).click();
		driver.findElement(By.name("account")).sendKeys(configParser.username);
		driver.findElement(By.name("password")).sendKeys(configParser.password);
		if (driver.findElement(By.name("remember_me")).isSelected()) {
			driver.findElement(By.name("remember_me")).click();
		}
		driver.findElement(By.className("sign-button")).click();
		
		boolean isLogin = false;
		while(!isLogin) {
			
			try {
				driver.findElement(By.className("zg-icon-dd-logout")); //如果页面上没有该元素则抛出异常
				isLogin = true;
			} catch (Exception e) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}

		File cookieFile = new File(configParser.cookiePath);
		
		driver.close();

        try {
            cookieFile.delete();
            cookieFile.createNewFile();
            FileWriter fileWriter = new FileWriter(cookieFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for(Cookie cookie : driver.manage().getCookies()) {
                bufferedWriter.write((cookie.getName() + ";" + cookie.getValue() + ";" + cookie.getDomain() + ";" + cookie.getPath() + ";" + cookie.getExpiry() + ";" + cookie.isSecure()));
                bufferedWriter.newLine();
            }
            
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();

        } catch(Exception ex) {
            ex.printStackTrace();
        }
        driver.quit();
	}

}
