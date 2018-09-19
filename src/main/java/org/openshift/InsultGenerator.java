package org.openshift;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class InsultGenerator {
public String generateInsult() {
String vowels = "AEIOU";
String article = "an";
String theInsult = "";
try {
	String databaseURL = "jdbc:postgresql://";
	databaseURL += System.getenv("POSTGRESQL_SERVICE_HOST");
	databaseURL += "/" + System.getenv("POSTGRESQL_DATABASE");
	System.out.println("Database URL = "+databaseURL);
	
	String username = System.getenv("POSTGRESQL_USER");
	System.out.println("Database username = "+username);
	String password = System.getenv("PGPASSWORD");
	System.out.println("Database password = "+password);
	Connection connection = DriverManager.getConnection(databaseURL, username,password);
	System.out.println("test1");
	if (connection != null) {
		String SQL = "select a.string AS first, b.string AS second, c.string AS noun from short_adjective a , long_adjective b, noun c ORDER BY random() limit 1";
		System.out.println("test2");
		Statement stmt = connection.createStatement();
		System.out.println("test3");
		ResultSet rs = stmt.executeQuery(SQL);
		System.out.println("test4");
		while (rs.next()) {
			System.out.println("test5");
			if (vowels.indexOf(rs.getString("first").charAt(0)) == -1) {
				article = "a";
			}
			theInsult = String.format("Thou art %s %s %s %s!", article,
			rs.getString("first"), rs.getString("second"), rs.getString("noun"));
		}
		rs.close();
		connection.close();
	}
} catch (Exception e) {
	return "Database connection problem!"+ e.getMessage();
}
return theInsult;
}
}
