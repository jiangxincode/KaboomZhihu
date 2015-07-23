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


public class ConfigParser {
	
	String cookiePath;
	String username;
	String password;
	List<TargetConfig> targets;
	
	public ConfigParser() {
		targets = new ArrayList<TargetConfig>();
	}
	
	public void paser() {
		try {
			InputStream in = new FileInputStream("config.xml");
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
		System.out.println(configParser.username);
		System.out.println(configParser.password);
		System.out.println(configParser.targets.get(0).method);
		System.out.println(configParser.targets.get(0).url);
		
	}

}
