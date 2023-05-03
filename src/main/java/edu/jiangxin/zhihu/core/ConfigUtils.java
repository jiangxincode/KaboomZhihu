package edu.jiangxin.zhihu.core;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigUtils {

	private static final Logger LOGGER = LogManager.getLogger(Login.class.getSimpleName());
	
	public static Config getConfig(String configPath) {
		
		Config config = null;
		
		File file = new File(configPath);
		
		if(!(file.isFile() && file.exists())) {
			LOGGER.error("Configuation file doesn't exist.");
		}
		
		
        JAXBContext jaxbContext = null;
		try {
			jaxbContext = JAXBContext.newInstance(Config.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	        config = (Config) jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException e2) {
			LOGGER.error("Can't parser the Configuration file");
		}
		
		return config;
	}
	
	public static Config getConfig() {
		
		return getConfig("config.xml");
	}

}
