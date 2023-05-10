package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.jdbc.SingleConnection;
import model.Userposjava;

public class UserPosDao {

	// estabelece a conexao
	private Connection conn;

	public UserPosDao() {
		conn = SingleConnection.getConnection();
	}

	// insert
	public void salvar(Userposjava userposjava) {
		String sql = "INSERT INTO userposjava(nome, email) values (?,?)";
		try {
			PreparedStatement insert = conn.prepareStatement(sql);
			// recupera e insere os dados no bd
			insert.setString(1, userposjava.getNome());// Parâmetro sendo adicionado
			insert.setString(2, userposjava.getEmail());
			insert.execute();// SQL sendo excutado no banco de dados
			conn.commit();// salva no bd
		} catch (SQLException e) {
			try {
				conn.rollback();// reverte a operação
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	// listar os usuarios
	public List<Userposjava> listar() throws Exception {
		List<Userposjava> list = new ArrayList<Userposjava>();

		String sql = "SELECT * FROM userposjava";

		PreparedStatement statement = conn.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();// Executa a consulta ao banco de dados

		while (resultado.next()) {// Itera percorrendo o objeto ResultSet que
			// tem os dados
			
			// Cria um novo obejtos para cada linha
			Userposjava userposjava = new Userposjava();
			userposjava.setId(resultado.getLong("id"));
			userposjava.setNome(resultado.getString("nome"));// Seta os valores para
			// o objeto
			userposjava.setEmail(resultado.getString("email"));
			// adiciona na lista
			list.add(userposjava);
		}

		return list;
	}

	// listar um usuario
	public Userposjava buscar(Long id) throws Exception {
		Userposjava retorno = new Userposjava();

		String sql = "SELECT * FROM userposjava WHERE id = " + id;

		PreparedStatement statement = conn.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			// retorna somente um ou nenhum
			retorno.setId(resultado.getLong("id"));// Capturando os dados e jogando no objeto
			retorno.setNome(resultado.getString("nome"));
			retorno.setEmail(resultado.getString("email"));
		}

		return retorno;
	}

	public void atualizar(Userposjava userposjava) {
		try {
			String sql = "UPDATE userposjava SET nome = ? WHERE id = " + userposjava.getId();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, userposjava.getNome());

			statement.execute(); // Executando a atualização
			conn.commit();// Comitando/Gravando no banco de dados

		} catch (SQLException e) {
			try {
				conn.rollback();// Reverte caso dê algum erro
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	
	public void deletar(Long id) {
		try {
			String sql = "DELETE FROM userposjava WHERE id = " + id;
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.execute();
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}
