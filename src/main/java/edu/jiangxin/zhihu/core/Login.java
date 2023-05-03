package edu.jiangxin.zhihu.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

public class Login {

	private static final Logger LOGGER = LogManager.getLogger(Login.class.getSimpleName());
	
	public static void main(String[] args) {
		
		Config config = ConfigUtils.getConfig();
		
		if(config == null) {
			LOGGER.error("Can't find the configuation file.");
			return;
		}
		
		WebDriver driver = WebDriverWrapper.getInstance(config);
		
		if(driver == null) {
			LOGGER.error("Unsupported Browser");
			return;
		}
		
		driver.get("http://www.zhihu.com");
		
		driver.manage().window().maximize(); //maximize the window

		driver.findElement(By.linkText("登录")).click();
		driver.findElement(By.name("account")).sendKeys(config.getUser().getUsername());
		driver.findElement(By.name("password")).sendKeys(config.getUser().getPassword());
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

		File cookieFile = new File(config.getCookie().getPath());
		
		driver.close();

        try {
            if(cookieFile.exists()) {
            	cookieFile.delete();
            } else {
            	File parent = cookieFile.getParentFile();
            	if(!parent.exists()) {
            		parent.mkdirs();
            	}
            	cookieFile.createNewFile();
            }
            
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
