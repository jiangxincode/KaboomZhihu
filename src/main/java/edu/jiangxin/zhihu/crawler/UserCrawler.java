package edu.jiangxin.zhihu.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import edu.jiangxin.zhihu.core.ConfigParser;

public class UserCrawler {

	public static void main(String[] args) {

		ConfigParser configParser = new ConfigParser();
		configParser.paser();
		
		for (int i = 0; i < configParser.targets.size(); i++) {
			if (configParser.targets.get(i).method.equals("users")) {
				//String savePath = configParser.targets.get(i).path;

				LinkQueue.addUnvisitedUrl("http://www.zhihu.com/people/jiangxinnju/followers");

				while (!LinkQueue.unVisitedUrlsEmpty()) {
					// 队头URL出队列
					String visitUrl = (String) LinkQueue.unVisitedUrlDeQueue();
					String content = GetUrlContent.getContent(visitUrl);
					System.out.println(content);
					Document doc = Jsoup.parse(content);
					Elements items = doc.getElementsByClass("zg-link");
					for (Element item : items) {
						String href = item.attr("href");
						System.out.println(href);
						LinkQueue.addUnvisitedUrl(href + "/followers");
					}
					LinkQueue.addVisitedUrl(visitUrl); // 该 url 放入到已访问的 URL 中
				}
			}

		}

	}

}
