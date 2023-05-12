package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.jdbc.SingleConnection;
import model.BeanUserFone;
import model.Telefone;
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

	// salvar telefone
	public void salvarTelefone(Telefone telefone) {
		try {
			String sql = "INSERT INTO telefoneuser(numero, tipo, usuariopessoa)" + " VALUES (?, ?, ?);";

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, telefone.getNumero());
			statement.setString(2, telefone.getTipo());
			statement.setLong(3, telefone.getUsuario());
			statement.execute();
			conn.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
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

	public List<BeanUserFone> listaUserFone(Long idUser) {
		
		List<BeanUserFone> beanUserFones = new ArrayList<BeanUserFone>();

		String sql = " select nome, numero, email from telefoneuser as fone ";
		sql += " inner join userposjava as userp ";
		sql += " on fone.usuariopessoa = userp.id";
		sql += " where userp.id = " + idUser;
		
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				BeanUserFone userFone = new BeanUserFone();
				
				userFone.setEmail(resultSet.getString("email"));
				userFone.setNome(resultSet.getString("nome"));
				userFone.setNumero(resultSet.getString("numero"));
				
				beanUserFones.add(userFone);//adiciona na lista
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beanUserFones;
		
	}
	
	public void deleteFonePorUser(Long idUser) {
		try {
			String sqlFone = " DELETE FROM telefoneuser WHERE usuariopessoa = " + idUser;
			String sqlUser = " DELETE FROM userposjava WHERE id = " + idUser;
			
			PreparedStatement preparedStatement = conn.prepareStatement(sqlFone);
			preparedStatement.executeUpdate();
			conn.commit();
			
			preparedStatement = conn.prepareStatement(sqlUser);
			preparedStatement.executeUpdate();
			conn.commit();
			
		}catch(Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
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
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}
