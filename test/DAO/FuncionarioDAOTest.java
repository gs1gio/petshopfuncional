package DAO;

import DTO.FuncionarioDTO;
import org.junit.BeforeClass;
import org.junit.Test;
import util.TestDatabaseUtil;

import static org.junit.Assert.*;

public class FuncionarioDAOTest {

    @BeforeClass
    public static void prepararBanco() {
        TestDatabaseUtil.criarEstrutura();
    }

    private FuncionarioDTO novoFuncionario() {
        String sufixo = String.valueOf(System.nanoTime());

        FuncionarioDTO funcionario = new FuncionarioDTO();
        funcionario.setNome("Funcionario Teste " + sufixo);
        funcionario.setEmail("funcionario." + sufixo + "@teste.com");
        funcionario.setSenha("123456");
        return funcionario;
    }

    private int idPorEmail(String email) {
        return TestDatabaseUtil.buscarId(
                "SELECT id_Funcionario FROM Funcionario "
                + "WHERE email_Funcionario = ?", email);
    }

    @Test
    public void deveCadastrarFuncionario() {
        FuncionarioDAO dao = new FuncionarioDAO();
        FuncionarioDTO funcionario = novoFuncionario();

        dao.cadastrar(funcionario);
        int id = idPorEmail(funcionario.getEmail());

        FuncionarioDTO salvo = dao.buscarPorId(id);

        assertNotNull(salvo);
        assertEquals(funcionario.getNome(), salvo.getNome());

        TestDatabaseUtil.excluirFuncionarioCompleto(id);
    }

    @Test
    public void deveAlterarFuncionario() {
        FuncionarioDAO dao = new FuncionarioDAO();
        FuncionarioDTO funcionario = novoFuncionario();

        dao.cadastrar(funcionario);
        int id = idPorEmail(funcionario.getEmail());

        funcionario.setIdFuncionario(id);
        funcionario.setNome("Funcionario Alterado");
        funcionario.setSenha("654321");

        dao.alterar(funcionario);

        FuncionarioDTO alterado = dao.buscarPorId(id);

        assertEquals("Funcionario Alterado", alterado.getNome());
        assertEquals("654321", alterado.getSenha());

        TestDatabaseUtil.excluirFuncionarioCompleto(id);
    }

    @Test
    public void deveListarFuncionarios() {
        FuncionarioDAO dao = new FuncionarioDAO();
        FuncionarioDTO funcionario = novoFuncionario();

        dao.cadastrar(funcionario);
        int id = idPorEmail(funcionario.getEmail());

        boolean encontrou = false;

        for (FuncionarioDTO item : dao.listar()) {
            if (item.getIdFuncionario() == id) {
                encontrou = true;
                break;
            }
        }

        assertTrue(encontrou);

        TestDatabaseUtil.excluirFuncionarioCompleto(id);
    }

    @Test
    public void deveBuscarFuncionarioPorId() {
        FuncionarioDAO dao = new FuncionarioDAO();
        FuncionarioDTO funcionario = novoFuncionario();

        dao.cadastrar(funcionario);
        int id = idPorEmail(funcionario.getEmail());

        FuncionarioDTO encontrado = dao.buscarPorId(id);

        assertNotNull(encontrado);
        assertEquals(id, encontrado.getIdFuncionario());

        TestDatabaseUtil.excluirFuncionarioCompleto(id);
    }

    @Test
    public void deveRetornarNullAoBuscarFuncionarioInexistente() {
        assertNull(new FuncionarioDAO().buscarPorId(-999999));
    }

    @Test
    public void deveRealizarLoginDoFuncionario() {
        FuncionarioDAO dao = new FuncionarioDAO();
        FuncionarioDTO funcionario = novoFuncionario();

        dao.cadastrar(funcionario);
        int id = idPorEmail(funcionario.getEmail());

        FuncionarioDTO login =
                dao.buscarLogin(funcionario.getEmail(), funcionario.getSenha());

        assertNotNull(login);
        assertEquals(id, login.getIdFuncionario());

        TestDatabaseUtil.excluirFuncionarioCompleto(id);
    }

    @Test
    public void naoDeveRealizarLoginComSenhaIncorreta() {
        FuncionarioDAO dao = new FuncionarioDAO();
        FuncionarioDTO funcionario = novoFuncionario();

        dao.cadastrar(funcionario);
        int id = idPorEmail(funcionario.getEmail());

        assertNull(
                dao.buscarLogin(funcionario.getEmail(), "senha_errada"));

        TestDatabaseUtil.excluirFuncionarioCompleto(id);
    }

    @Test
    public void deveExcluirFuncionario() {
        FuncionarioDAO dao = new FuncionarioDAO();
        FuncionarioDTO funcionario = novoFuncionario();

        dao.cadastrar(funcionario);
        int id = idPorEmail(funcionario.getEmail());

        dao.excluir(id);

        assertNull(dao.buscarPorId(id));
    }
}
