package DAO;

import DTO.TutorDTO;
import org.junit.BeforeClass;
import org.junit.Test;
import util.TestDatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import conexao.ConexaoSQLite;

import static org.junit.Assert.*;

public class TutorDAOTest {

    @BeforeClass
    public static void prepararBanco() {
        TestDatabaseUtil.criarEstrutura();
    }

    private TutorDTO novoTutor() {
        String sufixo = String.valueOf(System.nanoTime());

        TutorDTO tutor = new TutorDTO();
        tutor.setNome("Tutor Teste " + sufixo);
        tutor.setCpf("CPF" + sufixo);
        tutor.setTelefone("519" + sufixo);
        tutor.setEmail("tutor." + sufixo + "@teste.com");
        tutor.setSenha("123456");
        return tutor;
    }

    private int idPorEmail(String email) {
        return TestDatabaseUtil.buscarId(
                "SELECT id_Tutor FROM Tutor WHERE email_Tutor = ?", email);
    }

    @Test
    public void deveCadastrarTutor() {
        TutorDAO dao = new TutorDAO();
        TutorDTO tutor = novoTutor();

        dao.cadastrar(tutor);

        int id = idPorEmail(tutor.getEmail());
        assertTrue(id > 0);

        TutorDTO salvo = dao.buscarPorId(id);
        assertNotNull(salvo);
        assertEquals(tutor.getNome(), salvo.getNome());

        TestDatabaseUtil.excluirTutorCompleto(id);
    }

    @Test
    public void deveAlterarTutor() {
        TutorDAO dao = new TutorDAO();
        TutorDTO tutor = novoTutor();

        dao.cadastrar(tutor);
        int id = idPorEmail(tutor.getEmail());

        tutor.setIdTutor(id);
        tutor.setNome("Nome Alterado");
        tutor.setTelefone("51000000000");

        dao.alterar(tutor);

        TutorDTO alterado = dao.buscarPorId(id);
        assertEquals("Nome Alterado", alterado.getNome());
        assertEquals("51000000000", alterado.getTelefone());

        TestDatabaseUtil.excluirTutorCompleto(id);
    }

    @Test
    public void deveListarTutores() {
        TutorDAO dao = new TutorDAO();
        TutorDTO tutor = novoTutor();

        dao.cadastrar(tutor);
        int id = idPorEmail(tutor.getEmail());

        ArrayList<TutorDTO> lista = dao.listar();

        boolean encontrou = false;
        for (TutorDTO item : lista) {
            if (item.getIdTutor() == id) {
                encontrou = true;
                break;
            }
        }

        assertTrue(encontrou);
        TestDatabaseUtil.excluirTutorCompleto(id);
    }

    @Test
    public void deveBuscarTutorPorId() {
        TutorDAO dao = new TutorDAO();
        TutorDTO tutor = novoTutor();

        dao.cadastrar(tutor);
        int id = idPorEmail(tutor.getEmail());

        TutorDTO encontrado = dao.buscarPorId(id);

        assertNotNull(encontrado);
        assertEquals(id, encontrado.getIdTutor());
        assertEquals(tutor.getEmail(), encontrado.getEmail());

        TestDatabaseUtil.excluirTutorCompleto(id);
    }

    @Test
    public void deveRetornarNullAoBuscarTutorInexistente() {
        TutorDAO dao = new TutorDAO();

        assertNull(dao.buscarPorId(-999999));
    }

    @Test
    public void deveRealizarLoginDoTutor() {
        TutorDAO dao = new TutorDAO();
        TutorDTO tutor = novoTutor();

        dao.cadastrar(tutor);
        int id = idPorEmail(tutor.getEmail());

        TutorDTO login = dao.buscarLogin(
                tutor.getEmail(), tutor.getSenha());

        assertNotNull(login);
        assertEquals(id, login.getIdTutor());

        TestDatabaseUtil.excluirTutorCompleto(id);
    }

    @Test
    public void naoDeveRealizarLoginComSenhaIncorreta() {
        TutorDAO dao = new TutorDAO();
        TutorDTO tutor = novoTutor();

        dao.cadastrar(tutor);
        int id = idPorEmail(tutor.getEmail());

        assertNull(dao.buscarLogin(tutor.getEmail(), "senha_errada"));

        TestDatabaseUtil.excluirTutorCompleto(id);
    }

    @Test
    public void deveExcluirTutor() {
        TutorDAO dao = new TutorDAO();
        TutorDTO tutor = novoTutor();

        dao.cadastrar(tutor);
        int id = idPorEmail(tutor.getEmail());

        dao.excluir(id);

        assertNull(dao.buscarPorId(id));
    }
}
