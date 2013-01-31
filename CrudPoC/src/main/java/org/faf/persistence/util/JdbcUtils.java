package org.faf.persistence.util;

import static org.faf.config.AppConfiguration.DB_PASSWORD;
import static org.faf.config.AppConfiguration.DB_URI;
import static org.faf.config.AppConfiguration.DB_USER;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtils {

	public static boolean executeScript(String scriptName) throws SQLException {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection conn = DriverManager.getConnection(DB_URI, DB_USER, DB_PASSWORD);
		try {
			Statement stm = conn.createStatement();
			Reader r = new InputStreamReader(JdbcUtils.class.getClassLoader().getResourceAsStream(scriptName));
			LineNumberReader lnr = new LineNumberReader(r);
			String line="";
			String stmStr="";
			while (line!=null) {
				line = lnr.readLine();
				if(line!=null && !line.trim().startsWith("--")){
					stmStr+=line;
				}
				if(line!=null && line.trim().endsWith(";")){
					stm.execute(stmStr);
					stmStr="";
				}
			}
			stm.close();
		} catch (IOException e) {
			conn.close();
			e.printStackTrace();
			return false;
		}
		conn.close();
		return true;
	}
	
}
