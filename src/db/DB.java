package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

	private static Connection conn = null; // Declarar um objeto do tipo Connection, objeto de conexão do BD do JDBC

	public static Connection getConnection() { // Metódo para conectar o BD
		if (conn == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		
		return conn;
	}
	
	public static void closeConnection() { // Metódo para fechar a conexão
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
			
		}
	}

	private static Properties loadProperties() { // Método auxiliar para carregar as propriedades salvas no arquivo db.properties
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties(); // Estanciando um objeto do tipo Properties
			props.load(fs); // O comando 'load' faz a leitura do arquivo e guarda os dados dentro do objeto 'props'
			return props;
		} catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

}
