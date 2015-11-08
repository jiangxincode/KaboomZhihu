package edu.jiangxin.zhihu.core;

import javax.xml.bind.annotation.XmlElement;

public class Cookie {
	
	private String path;

	public String getPath() {
		return path;
	}

	@XmlElement
	public void setPath(String path) {
		this.path = path;
	}
	
	

}
