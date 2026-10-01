package DAO;

import DTO.AgendamentoDTO;
import DTO.FuncionarioDTO;
import DTO.PetDTO;
import DTO.ServicoDTO;
import DTO.TutorDTO;
import org.junit.BeforeClass;
import org.junit.Test;
import util.TestDatabaseUtil;

import static org.junit.Assert.*;

public class AgendamentoDAOTest {

    @BeforeClass
    public static void prepararBanco() {
        TestDatabaseUtil.criarEstrutura();
    }

    private static class Cenario {
        TutorDTO tutor;
        PetDTO pet;
        ServicoDTO servico;
        FuncionarioDTO funcionario;
        int idPet;
        int idServico;
        int idFuncionario;
    }

    private Cenario criarCenario() {
        String sufixo = String.valueOf(System.nanoTime());

        Cenario c = new Cenario();

        c.tutor = new TutorDTO();
        c.tutor.setNome("Tutor Ag " + sufixo);
        c.tutor.setCpf("CPF" + sufixo);
        c.tutor.setTelefone("519" + sufixo);
        c.tutor.setEmail("tutor.ag." + sufixo + "@teste.com");
        c.tutor.setSenha("123456");

        TutorDAO tutorDAO = new TutorDAO();
        tutorDAO.cadastrar(c.tutor);
        c.tutor.setIdTutor(TestDatabaseUtil.buscarId(
                "SELECT id_Tutor FROM Tutor WHERE email_Tutor = ?",
                c.tutor.getEmail()));

        c.pet = new PetDTO();
        c.pet.setTutorId(c.tutor.getIdTutor());
        c.pet.setNome("Pet Ag " + sufixo);
        c.pet.setEspecie("Cachorro");
        c.pet.setPorte("Medio");

        PetDAO petDAO = new PetDAO();
        petDAO.cadastrar(c.pet);
        c.idPet = TestDatabaseUtil.buscarId(
                "SELECT id_Pet FROM Pet WHERE nome_Pet = ?",
                c.pet.getNome());

        c.servico = new ServicoDTO();
        c.servico.setNome("Servico Ag " + sufixo);
        c.servico.setPreco(60.0);
        c.servico.setAtivo(true);

        ServicoDAO servicoDAO = new ServicoDAO();
        servicoDAO.cadastrar(c.servico);
        c.idServico = TestDatabaseUtil.buscarId(
                "SELECT id_Servico FROM Servico WHERE nome_Servico = ?",
                c.servico.getNome());

        c.funcionario = new FuncionarioDTO();
        c.funcionario.setNome("Funcionario Ag " + sufixo);
        c.funcionario.setEmail("funcionario.ag." + sufixo + "@teste.com");
        c.funcionario.setSenha("123456");

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        funcionarioDAO.cadastrar(c.funcionario);
        c.idFuncionario = TestDatabaseUtil.buscarId(
                "SELECT id_Funcionario FROM Funcionario "
                + "WHERE email_Funcionario = ?",
                c.funcionario.getEmail());

        return c;
    }

    private AgendamentoDTO novoAgendamento(
            Cenario c, String data, String hora) {

        AgendamentoDTO agendamento = new AgendamentoDTO();
        agendamento.setTutorId(c.tutor.getIdTutor());
        agendamento.setPetId(c.idPet);
        agendamento.setServicoId(c.idServico);
        agendamento.setFuncionarioId(c.idFuncionario);
        agendamento.setData(data);
        agendamento.setHora(hora);
        agendamento.setStatus("CONFIRMADO");

        return agendamento;
    }

    private int idAgendamento(Cenario c, String data, String hora) {
        return TestDatabaseUtil.buscarIdAgendamento(
                c.tutor.getIdTutor(), data, hora);
    }

    private void limpar(Cenario c, int idAgendamento) {
        if (idAgendamento > 0) {
            TestDatabaseUtil.excluirAgendamento(idAgendamento);
        }

        TestDatabaseUtil.excluirPet(c.idPet);
        TestDatabaseUtil.excluirServico(c.idServico);
        TestDatabaseUtil.excluirFuncionarioCompleto(c.idFuncionario);
        TestDatabaseUtil.excluirTutorCompleto(c.tutor.getIdTutor());
    }

    @Test
    public void deveCadastrarAgendamento() {
        Cenario c = criarCenario();
        String data = "29/09/2026";
        String hora = "10:11";

        AgendamentoDAO dao = new AgendamentoDAO();
        dao.cadastrar(novoAgendamento(c, data, hora));

        int id = idAgendamento(c, data, hora);
        AgendamentoDTO salvo = dao.buscarPorId(id);

        assertNotNull(salvo);
        assertEquals(c.tutor.getIdTutor(), salvo.getTutorId());
        assertEquals(c.idPet, salvo.getPetId());
        assertEquals("CONFIRMADO", salvo.getStatus());

        limpar(c, id);
    }

    @Test
    public void deveAlterarAgendamento() {
        Cenario c = criarCenario();
        String data = "29/09/2026";
        String hora = "10:12";

        AgendamentoDAO dao = new AgendamentoDAO();
        dao.cadastrar(novoAgendamento(c, data, hora));

        int id = idAgendamento(c, data, hora);
        AgendamentoDTO agendamento = dao.buscarPorId(id);

        agendamento.setHora("11:12");
        agendamento.setStatus("CANCELADO");

        dao.alterar(agendamento);

        AgendamentoDTO alterado = dao.buscarPorId(id);

        assertEquals("11:12", alterado.getHora());
        assertEquals("CANCELADO", alterado.getStatus());

        limpar(c, id);
    }

    @Test
    public void deveListarAgendamentos() {
        Cenario c = criarCenario();
        String data = "29/09/2026";
        String hora = "10:13";

        AgendamentoDAO dao = new AgendamentoDAO();
        dao.cadastrar(novoAgendamento(c, data, hora));

        int id = idAgendamento(c, data, hora);

        boolean encontrou = false;

        for (AgendamentoDTO item : dao.listar()) {
            if (item.getIdAgendamento() == id) {
                encontrou = true;
                break;
            }
        }

        assertTrue(encontrou);

        limpar(c, id);
    }

    @Test
    public void deveBuscarAgendamentoPorId() {
        Cenario c = criarCenario();
        String data = "29/09/2026";
        String hora = "10:14";

        AgendamentoDAO dao = new AgendamentoDAO();
        dao.cadastrar(novoAgendamento(c, data, hora));

        int id = idAgendamento(c, data, hora);
        AgendamentoDTO encontrado = dao.buscarPorId(id);

        assertNotNull(encontrado);
        assertEquals(id, encontrado.getIdAgendamento());

        limpar(c, id);
    }

    @Test
    public void deveRetornarNullAoBuscarAgendamentoInexistente() {
        assertNull(new AgendamentoDAO().buscarPorId(-999999));
    }

    @Test
    public void deveIdentificarHorarioOcupado() {
        Cenario c = criarCenario();
        String data = "29/09/2026";
        String hora = "10:15";

        AgendamentoDAO dao = new AgendamentoDAO();
        dao.cadastrar(novoAgendamento(c, data, hora));

        assertTrue(dao.horarioOcupado(data, hora));
        assertFalse(dao.horarioOcupado(data, "10:16"));

        int id = idAgendamento(c, data, hora);
        limpar(c, id);
    }

    @Test
    public void deveIgnorarHorarioComAgendamentoCancelado() {
        Cenario c = criarCenario();
        String data = "29/09/2026";
        String hora = "10:16";

        AgendamentoDAO dao = new AgendamentoDAO();
        AgendamentoDTO agendamento = novoAgendamento(c, data, hora);
        agendamento.setStatus("CANCELADO");

        dao.cadastrar(agendamento);

        assertFalse(dao.horarioOcupado(data, hora));

        int id = idAgendamento(c, data, hora);
        limpar(c, id);
    }

    @Test
    public void deveListarAgendamentosPorTutor() {
        Cenario c = criarCenario();
        String data = "29/09/2026";
        String hora = "10:17";

        AgendamentoDAO dao = new AgendamentoDAO();
        dao.cadastrar(novoAgendamento(c, data, hora));

        int id = idAgendamento(c, data, hora);

        boolean encontrou = false;

        for (AgendamentoDTO item :
                dao.listarPorTutor(c.tutor.getIdTutor())) {

            if (item.getIdAgendamento() == id) {
                encontrou = true;
                break;
            }
        }

        assertTrue(encontrou);

        limpar(c, id);
    }

    @Test
    public void deveExcluirAgendamento() {
        Cenario c = criarCenario();
        String data = "29/09/2026";
        String hora = "10:18";

        AgendamentoDAO dao = new AgendamentoDAO();
        dao.cadastrar(novoAgendamento(c, data, hora));

        int id = idAgendamento(c, data, hora);

        dao.excluir(id);

        assertNull(dao.buscarPorId(id));

        limpar(c, -1);
    }
}
