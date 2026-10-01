package DAO;

import DTO.ServicoDTO;
import org.junit.BeforeClass;
import org.junit.Test;
import util.TestDatabaseUtil;

import static org.junit.Assert.*;

public class ServicoDAOTest {

    @BeforeClass
    public static void prepararBanco() {
        TestDatabaseUtil.criarEstrutura();
    }

    private ServicoDTO novoServico() {
        String sufixo = String.valueOf(System.nanoTime());

        ServicoDTO servico = new ServicoDTO();
        servico.setNome("Servico " + sufixo);
        servico.setPreco(50.75);
        servico.setAtivo(true);
        return servico;
    }

    private int idPorNome(String nome) {
        return TestDatabaseUtil.buscarId(
                "SELECT id_Servico FROM Servico WHERE nome_Servico = ?",
                nome);
    }

    @Test
    public void deveCadastrarServico() {
        ServicoDAO dao = new ServicoDAO();
        ServicoDTO servico = novoServico();

        dao.cadastrar(servico);
        int id = idPorNome(servico.getNome());

        ServicoDTO salvo = dao.buscarPorId(id);

        assertNotNull(salvo);
        assertEquals(servico.getNome(), salvo.getNome());
        assertEquals(servico.getPreco(), salvo.getPreco(), 0.001);
        assertTrue(salvo.isAtivo());

        TestDatabaseUtil.excluirServico(id);
    }

    @Test
    public void deveAlterarServico() {
        ServicoDAO dao = new ServicoDAO();
        ServicoDTO servico = novoServico();

        dao.cadastrar(servico);
        int id = idPorNome(servico.getNome());

        servico.setIdServico(id);
        servico.setNome("Servico Alterado");
        servico.setPreco(99.90);
        servico.setAtivo(false);

        dao.alterar(servico);

        ServicoDTO alterado = dao.buscarPorId(id);

        assertEquals("Servico Alterado", alterado.getNome());
        assertEquals(99.90, alterado.getPreco(), 0.001);
        assertFalse(alterado.isAtivo());

        TestDatabaseUtil.excluirServico(id);
    }

    @Test
    public void deveListarServicos() {
        ServicoDAO dao = new ServicoDAO();
        ServicoDTO servico = novoServico();

        dao.cadastrar(servico);
        int id = idPorNome(servico.getNome());

        boolean encontrou = false;

        for (ServicoDTO item : dao.listar()) {
            if (item.getIdServico() == id) {
                encontrou = true;
                break;
            }
        }

        assertTrue(encontrou);

        TestDatabaseUtil.excluirServico(id);
    }

    @Test
    public void deveBuscarServicoPorId() {
        ServicoDAO dao = new ServicoDAO();
        ServicoDTO servico = novoServico();

        dao.cadastrar(servico);
        int id = idPorNome(servico.getNome());

        ServicoDTO encontrado = dao.buscarPorId(id);

        assertNotNull(encontrado);
        assertEquals(id, encontrado.getIdServico());

        TestDatabaseUtil.excluirServico(id);
    }

    @Test
    public void deveRetornarNullAoBuscarServicoInexistente() {
        assertNull(new ServicoDAO().buscarPorId(-999999));
    }

    @Test
    public void deveExcluirServico() {
        ServicoDAO dao = new ServicoDAO();
        ServicoDTO servico = novoServico();

        dao.cadastrar(servico);
        int id = idPorNome(servico.getNome());

        dao.excluir(id);

        assertNull(dao.buscarPorId(id));
    }
}
