package edu.jiangxin.zhihu.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

public class WebDriverWrapper {
	public static WebDriver getInstance(Config config) {
		
		String browserType = config.getBrowser().getType().toLowerCase();
		
		if(browserType.contains("firefox")) { //FireFox浏览器
			return new FirefoxDriver();
		} else if(browserType.contains("edge")) { //Chrome浏览器
			System.setProperty("webdriver.edge.driver", "C:\\Program Files (x86)\\Microsoft Web Driver\\MicrosoftWebDriver.exe");
			return new EdgeDriver();
		} else if(browserType.contains("ie")) { //IE浏览器
			return new InternetExplorerDriver();
		} else if(browserType.contains("chrome")) { //Chrome浏览器
			return new ChromeDriver();
		} else if(browserType.contains("safari")) { //Safari浏览器
			return new SafariDriver();
		} else { //其它不支持的浏览器
			return null;
		}
	}
}
