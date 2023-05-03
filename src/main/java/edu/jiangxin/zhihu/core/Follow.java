package edu.jiangxin.zhihu.core;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.FindBy;

public class Follow {

	private static final Logger LOGGER = Logger.getLogger(Follow.class);

	public static void main(String[] args) throws InterruptedException {

		//Logger logger = Logger.getRootLogger();
		//DOMConfigurator.configure("log4j.xml");

		Config config = ConfigUtils.getConfig();

		if (config == null) {
			LOGGER.error("Can't find the configuation file.");
			return;
		}

		WebDriver driver = WebDriverWrapper.getInstance(config);

		if (driver == null) {
			LOGGER.error("Unsupported Browser");
			return;
		}

		driver.get("http://www.zhihu.com");

		driver.manage().window().maximize(); //maximize the window

		CookieWrapper cookieWrapper = new CookieWrapper();
		cookieWrapper.setCookieList(config.getCookie().getPath());
		List<Cookie> cookieList = cookieWrapper.getCookieList();
		for (Cookie cookie : cookieList) {
			driver.manage().addCookie(cookie);
		}

		for (int i = 0; i < config.getTargets().size(); i++) {
			String method = config.getTargets().get(i).getMethod();
			if ((!method.equals("follow")) && (!method.equals("unfollow"))) {
				continue;
			}

			driver.get(config.getTargets().get(i).getUrl());

			//int followees_num = Integer.parseInt(driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[1]/a[1]/strong")).getText());
			int operated_num = 0; //已经关注或者取消关注的数目

			List<WebElement> follow = driver.findElements(By.className("zg-btn-follow"));
			List<WebElement> unfollow = driver.findElements(By.className("zg-btn-unfollow"));

			//https://bitbucket.org/snippets/jiangxincode/Ro6XE

			int duplicate = 0;
			int sum_follow_unfollow = follow.size() + unfollow.size();

			/**
			 * 如果下拉三次滚动条，没有变化就停止循环，该方法不用定位页面上的一些参考值（这些值不太好定位，而且适应情况有限，而且对于程序执行者是否关注需要分情况讨论）
			 * 曾经的方法是：
			 * String followNumber = driver.findElement(By.tagName("strong")).getText(); //定位总人数
			 * while(follow.size() + unfollow.size() < Integer.parseInt(followNumber)-1) { //程序执行者可能在关注者之中
			 */
			while (duplicate < 5) {
				if ((method.equals("follow")) && (follow.size() > config.getTargets().get(i).getOperated_num())) {
					break;
				} else if ((method.equals("unfollow"))
						&& (unfollow.size() > config.getTargets().get(i).getOperated_num())) {
					break;
				}

				WebElement loadMore;

				try {
					loadMore = driver.findElement(By.id("zh-load-more")); //如果不存在该元素会抛出异常，而不是直接返回null
				} catch (NoSuchElementException e) {
					loadMore = null;
				}
				if ((loadMore != null) && loadMore.isDisplayed()) {
					loadMore.click();
				} else {
					String js = "var q=document.documentElement.scrollTop=document.body.scrollHeight";
					((JavascriptExecutor) driver).executeScript(js);
					try {
						Thread.sleep(1000); //防止加载过慢
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//((HasInputDevices) driver).getKeyboard().sendKeys(Keys.PAGE_DOWN); // inefficiency
				}
				follow = driver.findElements(By.className("zg-btn-follow"));
				unfollow = driver.findElements(By.className("zg-btn-unfollow"));
				if (follow.size() + unfollow.size() > sum_follow_unfollow) {
					duplicate = 0;
					sum_follow_unfollow = follow.size() + unfollow.size();
				} else {
					duplicate++;
				}
				LOGGER.warn("[Follow:" + follow.size() + "] " + "[Unfollow:" + unfollow.size() + "] " + "[Duplicate:"
						+ duplicate + "]");
			}


			if (method.equals("follow")) {
				for (WebElement we : follow) {
					System.out.println("after sleep");

					((Locatable) we).getCoordinates().inViewPort();
				    try {
				    	we.click();
				    } catch (Exception e) {
				    	new Actions(driver).sendKeys(Keys.HOME).perform();
				        new Actions(driver).sendKeys(Keys.PAGE_DOWN).perform();
				        we.click();
				    }

					operated_num++;
					if (operated_num >= config.getTargets().get(i).getOperated_num()) {
						break;
					}
				}

			} else if (method.equals("unfollow")) {
				for (WebElement we : unfollow) {
					we.click();
					operated_num++;
					if (operated_num >= config.getTargets().get(i).getOperated_num()) {
						break;
					}
				}
			}

			if (config.getTargets().get(i).isShutdown()) {

				try {
					//Runtime.getRuntime().exit(1);
					Runtime.getRuntime().exec("shutdown -s");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		driver.close();
	}

}
