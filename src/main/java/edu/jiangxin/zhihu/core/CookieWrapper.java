package edu.jiangxin.zhihu.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.openqa.selenium.Cookie;

public class CookieWrapper {

	List<Cookie> cookieList;
	
	public CookieWrapper() {
		this.cookieList = new ArrayList<Cookie>();
	}
	
	@SuppressWarnings("deprecation")
	public void setCookieList(String filepath) {
		try {
            File cookieFile = new File(filepath);
            FileReader fr = new FileReader(cookieFile);
            BufferedReader bufferedReader = new BufferedReader(fr);
            String line;
            while((line = bufferedReader.readLine()) != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(line, ";");
                while(stringTokenizer.hasMoreTokens()) {
                    String name = stringTokenizer.nextToken();
                    String value = stringTokenizer.nextToken();
                    String domain = stringTokenizer.nextToken();
                    String path = stringTokenizer.nextToken();
                    Date expiry = null;
                    String dt;
                    if(!(dt = stringTokenizer.nextToken()).equals("null")) {
                        expiry = new Date(dt);
                    }

                    boolean isSecure = new Boolean(stringTokenizer.nextToken()).booleanValue();
                    this.cookieList.add(new Cookie(name,value,domain,path,expiry,isSecure));
                }
            }
            bufferedReader.close();
        }catch(Exception ex) {
                ex.printStackTrace();
        }
	}
	public List<Cookie> getCookieList() {
        return this.cookieList;
	}
}
