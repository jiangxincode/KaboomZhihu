package edu.jiangxin.zhihu.core;


public class TargetConfig {
	public String method;
	public String url;
	public String path;
	public int operated_num;
	public boolean shutdown;
	public Kind kind;
	
	public TargetConfig () {
		this.method = null;
		this.url = null;
		this.path = null;
		this.operated_num = Integer.MAX_VALUE;
		this.shutdown = false;
		this.kind = Kind.UNKNOWN;
	};
	
}
