package com.atlcc.single;


import java.io.IOException;
import java.util.Properties;

/**
 * 饿汉式
 */
public class Singleton3 {

	public static final Singleton3 INSTANCE;

	static {
		Properties properties = new Properties();
		try {
			properties.load(Singleton3.class.getClassLoader().getResourceAsStream("singleton3.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		INSTANCE = new Singleton3();
		INSTANCE.setName(properties.getProperty("name"));
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private Singleton3() {
	}

	@Override
	public String toString() {
		return "Singleton3{" +
				"name='" + name + '\'' +
				'}';
	}
}
