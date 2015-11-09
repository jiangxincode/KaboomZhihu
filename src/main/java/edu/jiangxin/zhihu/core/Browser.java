package edu.jiangxin.zhihu.core;

import javax.xml.bind.annotation.XmlElement;

public class Browser {
	
	private String type;
	
	private String version;

	public Browser() {
		this.type = "firefox";
		this.version = "42.0";
	}
	
	public Browser(String type, String version) {
		this.type = type;
		this.version = version;
	}

	public String getType() {
		return type;
	}

	@XmlElement
	public void setType(String type) {
		this.type = type;
	}

	public String getVersion() {
		return version;
	}

	@XmlElement
	public void setVersion(String version) {
		this.version = version;
	}

}
