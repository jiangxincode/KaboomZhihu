package edu.jiangxin.zhihu;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 配置解析器
 * @author Aloys
 * 
 */
public class ConfigParser {
	
	String cookiePath; //Cookie信息保存路径
	String username; //用户名
	String password; //密码
	List<TargetConfig> targets; //任务列表
	
	String configPath = null;
	
	public ConfigParser() {
		targets = new ArrayList<TargetConfig>();
	}
	public ConfigParser(String configPath) {
		targets = new ArrayList<TargetConfig>();
		this.configPath = configPath;
	}
	
	public void paser() {
		try {
			if(configPath == null) {
				configPath = "config.xml";
			}
			InputStream in = new FileInputStream(configPath);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(in);
			
			NodeList nl;
			Element ele;
			
			nl = doc.getElementsByTagName("cookie");
			ele = (Element) nl.item(0);
			this.cookiePath = ele.getElementsByTagName("path").item(0).getFirstChild().getNodeValue();
			
			nl = doc.getElementsByTagName("user");
			ele = (Element) nl.item(0);
			this.username = ele.getElementsByTagName("username").item(0).getFirstChild().getNodeValue();
			this.password = ele.getElementsByTagName("password").item(0).getFirstChild().getNodeValue();
			
			nl = doc.getElementsByTagName("target");
			for(int i=0; i<nl.getLength(); i++) {
				ele = (Element) nl.item(i);
				TargetConfig targetConfig = new TargetConfig();
				targetConfig.method = ele.getElementsByTagName("method").item(0).getFirstChild().getNodeValue();
				targetConfig.url = ele.getElementsByTagName("url").item(0).getFirstChild().getNodeValue();
				if(ele.getElementsByTagName("number").getLength() != 0) {
					targetConfig.operated_num = Integer.parseInt(ele.getElementsByTagName("number").item(0).getFirstChild().getNodeValue());
				}
				if(ele.getElementsByTagName("shutdown").getLength() != 0) {
					targetConfig.shutdown = Boolean.parseBoolean(ele.getElementsByTagName("shutdown").item(0).getFirstChild().getNodeValue());
				}
				
				String url = targetConfig.url;
				if(url.matches("http://www.zhihu.com/topic/[0-9]+/followers")) {
					targetConfig.kind = Kind.SOMETOPIC_FOLLOWERS;
				} else if(url.matches("http://www.zhihu.com/question/[0-9]+/followers")) {
					targetConfig.kind = Kind.SOMEQUESTION_FOLLOWERS;
				} else if(url.matches("http://www.zhihu.com/people/[0-9a-zA-z\\-]+/followees")) {
					targetConfig.kind = Kind.SOMEPEOPLE_FOLLOWEES;
				} else if(url.matches("http://www.zhihu.com/people/[0-9a-zA-z\\-]+/followers")) {
					targetConfig.kind = Kind.SOMEPEOPLE_FOLLOWERS;
				} else if(url.matches("http://www.zhihu.com/people/[0-9a-zA-z\\-]+/columns/followed")) {
					targetConfig.kind = Kind.SOMEPEOPLE_COLUMNS;
				} else if(url.matches("http://www.zhihu.com/people/[0-9a-zA-z\\-]+/topics")) {
					targetConfig.kind = Kind.SOMEPEOPLE_TOPICS;
				} else {
					targetConfig.kind = Kind.UNKNOWN;
				}
				
				this.targets.add(targetConfig);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ConfigParser configParser = new ConfigParser();
		configParser.paser();
		System.out.println(configParser.cookiePath);
		//System.out.println(configParser.username);
		//System.out.println(configParser.password);
		System.out.println(configParser.targets.get(0).method);
		System.out.println(configParser.targets.get(0).url);
		System.out.println(configParser.targets.get(0).operated_num);
		System.out.println(configParser.targets.get(0).shutdown);
		
	}

}
