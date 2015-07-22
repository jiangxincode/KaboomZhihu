package edu.jiangxin.zhihu;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Follow {

	public static void main(String[] args) {

		WebDriver driver = new FirefoxDriver();
		driver.get("http://www.zhihu.com");
		
		driver.manage().window().maximize(); //maximize the window
		
		CookieWrapper cookieWrapper = new CookieWrapper();
		cookieWrapper.setCookieList("./tmp/Cookie/cookie.txt");
		List<Cookie> cookieList = cookieWrapper.getCookieList();
		for(Cookie cookie : cookieList) {
			driver.manage().addCookie(cookie);
		}

		//driver.get("http://www.zhihu.com/topic/19627873/followers"); //按照话题进行关注
		//driver.get("http://www.zhihu.com/question/19644659/followers"); //按照问题进行关注
		//driver.get("http://www.zhihu.com/people/YenvY/followees"); //某人关注的人
		//driver.get("http://www.zhihu.com/people/YenvY/followers"); //关注某人的人
		driver.get("http://www.zhihu.com/people/jiangxinnju/followees"); //我关注的人
		
		
		List<WebElement> follow = driver.findElements(By.className("zg-btn-follow"));
		List<WebElement> unfollow = driver.findElements(By.className("zg-btn-unfollow"));
		
		int duplicate = 0;
		int sum_follow_unfollow = follow.size() + unfollow.size();
		
		/**
		 * 如果下拉三次滚动条，没有变化就停止循环，该方法不用定位页面上的一些参考值（这些值不太好定位，而且适应情况有限，而且对于程序执行者是否关注需要分情况讨论）
		 * 曾经的方法是：
		 * String followNumber = driver.findElement(By.tagName("strong")).getText(); //定位总人数
		 * while(follow.size() + unfollow.size() < Integer.parseInt(followNumber)-1) { //程序执行者可能在关注者之中
		 */
		while(duplicate < 3) {
			
			WebElement loadMore;
			
			try {
				loadMore = driver.findElement(By.id("zh-load-more")); //如果不存在该元素会抛出异常，而不是直接返回null
			} catch (NoSuchElementException e) {
				loadMore = null;
			}
			if((loadMore != null) && loadMore.isDisplayed()) {
				loadMore.click();
			} else {
				String js="var q=document.documentElement.scrollTop=document.body.scrollHeight";
				((JavascriptExecutor)driver).executeScript(js);
				//((HasInputDevices) driver).getKeyboard().sendKeys(Keys.PAGE_DOWN); // inefficiency
			}
			follow = driver.findElements(By.className("zg-btn-follow"));
			unfollow = driver.findElements(By.className("zg-btn-unfollow"));
			if(follow.size() + unfollow.size() > sum_follow_unfollow) {
				duplicate = 0;
				sum_follow_unfollow = follow.size() + unfollow.size();
			} else {
				duplicate++;
			}
			System.out.println(follow.size() + " " + unfollow.size() + " " + duplicate);
		}
		
		if(args[0].equals("follow")) {
			for(WebElement we : follow) {
				we.click();
			}
		} else if(args[0].equals("unfollow")) {
			for(WebElement we : unfollow) {
				we.click();
			}
		}
		driver.close();
	}

}
