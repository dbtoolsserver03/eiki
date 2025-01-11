package jp.co.eiki.common;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class MyDbConnection {

	public static Connection getConnection() throws Exception {
		Properties properties = new Properties();
		InputStream input = MyDbConnection.class.getClassLoader().getResourceAsStream("db.properties");

		if (input == null) {
			System.out.println("Sorry, unable to find db.properties");
			return null;
		}

		// 加载 properties 文件
		properties.load(input);

		// 从 properties 文件获取 MySQL 配置信息
		String url = properties.getProperty("db.url");
		String username = properties.getProperty("db.username");
		String password = properties.getProperty("db.password");

		// 建立连接
		return DriverManager.getConnection(url, username, password);
	}
}
