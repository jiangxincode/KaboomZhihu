package edu.jiangxin.zhihu.core;

import javax.xml.bind.annotation.XmlElement;

public class Target {
	private String method;
	private String url;
	private String path;
	private int operated_num;
	private boolean shutdown;
	private Kind kind;
	
	public Target () {
		this.method = null;
		this.url = null;
		this.path = null;
		this.operated_num = Integer.MAX_VALUE;
		this.shutdown = false;
		this.kind = Kind.UNKNOWN;
	};

	public String getMethod() {
		return method;
	}

	@XmlElement
	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	@XmlElement
	public void setUrl(String url) {
		this.url = url;
		if(url == null) {
			this.kind = Kind.UNKNOWN;
		} else if(url.matches("http://www.zhihu.com/topic/[0-9]+/followers")) {
			this.kind = Kind.SOMETOPIC_FOLLOWERS;
		} else if(url.matches("http://www.zhihu.com/question/[0-9]+/followers")) {
			this.kind = Kind.SOMEQUESTION_FOLLOWERS;
		} else if(url.matches("http://www.zhihu.com/people/[0-9a-zA-z\\-]+/followees")) {
			this.kind = Kind.SOMEPEOPLE_FOLLOWEES;
		} else if(url.matches("http://www.zhihu.com/people/[0-9a-zA-z\\-]+/followers")) {
			this.kind = Kind.SOMEPEOPLE_FOLLOWERS;
		} else if(url.matches("http://www.zhihu.com/people/[0-9a-zA-z\\-]+/columns/followed")) {
			this.kind = Kind.SOMEPEOPLE_COLUMNS;
		} else if(url.matches("http://www.zhihu.com/people/[0-9a-zA-z\\-]+/topics")) {
			this.kind = Kind.SOMEPEOPLE_TOPICS;
		} else {
			this.kind = Kind.UNKNOWN;
		}
	}

	public String getPath() {
		return path;
	}


	@XmlElement
	public void setPath(String path) {
		this.path = path;
	}

	public int getOperated_num() {
		return operated_num;
	}


	@XmlElement
	public void setOperated_num(int operated_num) {
		this.operated_num = operated_num;
	}

	public boolean isShutdown() {
		return shutdown;
	}

	@XmlElement
	public void setShutdown(boolean shutdown) {
		this.shutdown = shutdown;
	}

	public Kind getKind() {
		return kind;
	}

	@XmlElement
	public void setKind(Kind kind) {
		this.kind = kind;
	};
	
	
}
