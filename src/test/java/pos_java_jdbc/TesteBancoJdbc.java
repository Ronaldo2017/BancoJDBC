package pos_java_jdbc;

import java.util.List;

import org.junit.Test;
import dao.UserPosDao;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

public class TesteBancoJdbc {

	@Test
	public void initBanco() {
		UserPosDao userPosDao = new UserPosDao();
		Userposjava userposjava = new Userposjava();

		userposjava.setNome("Joao");
		userposjava.setEmail("joao@gmail.com");

		userPosDao.salvar(userposjava);
	}

	@Test
	public void initListar() {
		UserPosDao dao = new UserPosDao();

		try {
			List<Userposjava> list = dao.listar();

			for (Userposjava userposjava : list) {
				System.out.println(userposjava);
				System.out.println("-------------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void initBuscar() {
		UserPosDao dao = new UserPosDao();

		try {
			Userposjava userposjava = dao.buscar(3L);
			System.out.println(userposjava);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void initAtualizar() {
		try {
			UserPosDao dao = new UserPosDao();

			Userposjava objetoBanco = dao.buscar(3L);
			objetoBanco.setNome("Modificado o nome com o metodo atualizar");
			dao.atualizar(objetoBanco);
			System.out.println(objetoBanco);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void initDeletar() {
		try {
			UserPosDao dao = new UserPosDao();
			dao.deletar(5L);
			System.out.println("Deletado");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInserTelefone() {
		Telefone telefone = new Telefone();
		telefone.setNumero("(45) 4549-5415");
		telefone.setTipo("casa");
		telefone.setUsuario(2L);

		UserPosDao dao = new UserPosDao();
		dao.salvarTelefone(telefone);
	}

	@Test
	public void testeCarregaFonesUser() {
		UserPosDao dao = new UserPosDao();

		List<BeanUserFone> beanUserFones = dao.listaUserFone(1L);

		for (BeanUserFone beanUserFone : beanUserFones) {
			System.out.println(beanUserFone);
			System.out.println("-------------------------------------------");
		}
	}

	@Test
	public void testeDeleteUserFone() {
		UserPosDao dao = new UserPosDao();
		dao.deleteFonePorUser(2L);
	}
}
