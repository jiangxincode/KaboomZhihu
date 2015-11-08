package edu.jiangxin.zhihu.core;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "config")
public class Config {

	private Cookie cookie; //Cookie信息保存路径
	
	private User user; //用户名
	
	private List<Target> targets; //任务列表
	
	public Cookie getCookie() {
		return cookie;
	}

	@XmlElement
	public void setCookie(Cookie cookie) {
		this.cookie = cookie;
	}

	public User getUser() {
		return user;
	}

	@XmlElement
	public void setUser(User user) {
		this.user = user;
	}

	public List<Target> getTargets() {
		return targets;
	}
	
	@XmlElementWrapper(name="targets")  
    @XmlElement(name="target")
	public void setTargets(List<Target> targets) {
		this.targets = targets;
	}
	
	
}
