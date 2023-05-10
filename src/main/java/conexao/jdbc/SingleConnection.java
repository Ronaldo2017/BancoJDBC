package conexao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {
	
	private static String url = "jdbc:postgresql://localhost:5432/posjava";
	private static String password = "admin";
	private static String user = "postgres";
	private static Connection conn = null;
	
	
	//quando invocar de qualquer lugar a classe
	static {
		conectar();
	}
	
	
	public SingleConnection() {
		conectar();
	}
	
	private static void conectar() {
		try {
			//verifica na primeira vez
			if(conn == null) {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection(url, user, password);
				conn.setAutoCommit(false);//decide operaçoes manual para fazer
				System.out.println("Conectou com sucesso");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//retorna a conexao
	public static Connection getConnection() {
		return conn;
	}
	
	
	

}
