package edu.jiangxin.zhihu.crawler;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.jiangxin.zhihu.core.ConfigParser;

public class RecommendationCrawler {

	// 获取推荐内容详细内容url
	public static ArrayList<GetRecommendationList> GetRecommendations(String content) {

		ArrayList<GetRecommendationList> results = new ArrayList<GetRecommendationList>();
		Document doc = Jsoup.parse(content);
		Elements items = doc.getElementsByClass("zm-item"); // 推荐内容元素
		for (Element item : items) {
			String href = item.getElementsByTag("h2").first().getElementsByTag("a").first().attr("href"); // 推荐内容url
			if (href.contains("question")) { // 去除不规范url
				results.add(new GetRecommendationList(href));
			}
		}
		return results;
	}

	public static void main(String[] args) {

		ConfigParser configParser = new ConfigParser();
		configParser.paser();
		for (int i = 0; i < configParser.targets.size(); i++) {
			if (configParser.targets.get(i).method.equals("recommendations")) {
				String savePath = configParser.targets.get(i).path;
				String content = GetUrlContent.getContent("http://www.zhihu.com/explore/recommendations"); // 获取知乎推荐首页
				ArrayList<GetRecommendationList> list = RecommendationCrawler.GetRecommendations(content); // 获取推荐内容详细内容
				for (GetRecommendationList zhihu : list) { // 写入文档
					
					FileReaderWriter.writeIntoFile(zhihu.writeString(), savePath, true);
				}
			}
		}
	}

}
