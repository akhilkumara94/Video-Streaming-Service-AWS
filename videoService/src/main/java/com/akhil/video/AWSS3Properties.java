package com.akhil.video;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class AWSS3Properties {
	final static Logger logger = Logger.getLogger(AWSS3Properties.class);
	Properties properties;

	@Autowired
	public AWSS3Properties()
	{
		this.properties = new Properties();
		try (InputStream fileInputStream = AWSS3Properties.class.getClassLoader()
				.getResourceAsStream("configuration.properties")) {
			properties.load(fileInputStream);
		} catch (Exception exception) {
			logger.error(exception);
		}
	}
	
	public Properties getAWSProperties() {
		return properties;
	}
}
