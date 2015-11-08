package edu.jiangxin.zhihu.core;

import javax.xml.bind.annotation.XmlElement;

public class User {
	
	private String username;
	
	private String password;

	public String getUsername() {
		return username;
	}

	@XmlElement
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	@XmlElement
	public void setPassword(String password) {
		this.password = password;
	}

}
