package DAO;

import DTO.PetDTO;
import DTO.TutorDTO;
import org.junit.BeforeClass;
import org.junit.Test;
import util.TestDatabaseUtil;

import static org.junit.Assert.*;

public class PetDAOTest {

    @BeforeClass
    public static void prepararBanco() {
        TestDatabaseUtil.criarEstrutura();
    }

    private TutorDTO criarTutor() {
        String sufixo = String.valueOf(System.nanoTime());

        TutorDTO tutor = new TutorDTO();
        tutor.setNome("Tutor Pet " + sufixo);
        tutor.setCpf("CPF" + sufixo);
        tutor.setTelefone("519" + sufixo);
        tutor.setEmail("tutor.pet." + sufixo + "@teste.com");
        tutor.setSenha("123456");

        TutorDAO tutorDAO = new TutorDAO();
        tutorDAO.cadastrar(tutor);

        tutor.setIdTutor(TestDatabaseUtil.buscarId(
                "SELECT id_Tutor FROM Tutor WHERE email_Tutor = ?",
                tutor.getEmail()));

        return tutor;
    }

    private PetDTO novoPet(int idTutor) {
        String sufixo = String.valueOf(System.nanoTime());

        PetDTO pet = new PetDTO();
        pet.setTutorId(idTutor);
        pet.setNome("Pet " + sufixo);
        pet.setEspecie("Cachorro");
        pet.setPorte("Grande");
        return pet;
    }

    private int idPorNome(String nome) {
        return TestDatabaseUtil.buscarId(
                "SELECT id_Pet FROM Pet WHERE nome_Pet = ?", nome);
    }

    @Test
    public void deveCadastrarPet() {
        TutorDTO tutor = criarTutor();
        PetDAO dao = new PetDAO();
        PetDTO pet = novoPet(tutor.getIdTutor());

        dao.cadastrar(pet);
        int id = idPorNome(pet.getNome());

        PetDTO salvo = dao.buscarPorId(id);

        assertNotNull(salvo);
        assertEquals(tutor.getIdTutor(), salvo.getTutorId());
        assertEquals(pet.getNome(), salvo.getNome());

        TestDatabaseUtil.excluirPet(id);
        TestDatabaseUtil.excluirTutorCompleto(tutor.getIdTutor());
    }

    @Test
    public void deveAlterarPet() {
        TutorDTO tutor = criarTutor();
        PetDAO dao = new PetDAO();
        PetDTO pet = novoPet(tutor.getIdTutor());

        dao.cadastrar(pet);
        int id = idPorNome(pet.getNome());

        pet.setIdPet(id);
        pet.setNome("Pet Alterado");
        pet.setPorte("Pequeno");

        dao.alterar(pet);

        PetDTO alterado = dao.buscarPorId(id);

        assertEquals("Pet Alterado", alterado.getNome());
        assertEquals("Pequeno", alterado.getPorte());

        TestDatabaseUtil.excluirPet(id);
        TestDatabaseUtil.excluirTutorCompleto(tutor.getIdTutor());
    }

    @Test
    public void deveListarPets() {
        TutorDTO tutor = criarTutor();
        PetDAO dao = new PetDAO();
        PetDTO pet = novoPet(tutor.getIdTutor());

        dao.cadastrar(pet);
        int id = idPorNome(pet.getNome());

        boolean encontrou = false;

        for (PetDTO item : dao.listar()) {
            if (item.getIdPet() == id) {
                encontrou = true;
                break;
            }
        }

        assertTrue(encontrou);

        TestDatabaseUtil.excluirPet(id);
        TestDatabaseUtil.excluirTutorCompleto(tutor.getIdTutor());
    }

    @Test
    public void deveBuscarPetPorId() {
        TutorDTO tutor = criarTutor();
        PetDAO dao = new PetDAO();
        PetDTO pet = novoPet(tutor.getIdTutor());

        dao.cadastrar(pet);
        int id = idPorNome(pet.getNome());

        PetDTO encontrado = dao.buscarPorId(id);

        assertNotNull(encontrado);
        assertEquals(id, encontrado.getIdPet());

        TestDatabaseUtil.excluirPet(id);
        TestDatabaseUtil.excluirTutorCompleto(tutor.getIdTutor());
    }

    @Test
    public void deveRetornarNullAoBuscarPetInexistente() {
        assertNull(new PetDAO().buscarPorId(-999999));
    }

    @Test
    public void deveListarPetsPorTutor() {
        TutorDTO tutor = criarTutor();
        PetDAO dao = new PetDAO();
        PetDTO pet = novoPet(tutor.getIdTutor());

        dao.cadastrar(pet);
        int id = idPorNome(pet.getNome());

        boolean encontrou = false;

        for (PetDTO item : dao.listarPorTutor(tutor.getIdTutor())) {
            if (item.getIdPet() == id) {
                encontrou = true;
                break;
            }
        }

        assertTrue(encontrou);

        TestDatabaseUtil.excluirPet(id);
        TestDatabaseUtil.excluirTutorCompleto(tutor.getIdTutor());
    }

    @Test
    public void deveExcluirPet() {
        TutorDTO tutor = criarTutor();
        PetDAO dao = new PetDAO();
        PetDTO pet = novoPet(tutor.getIdTutor());

        dao.cadastrar(pet);
        int id = idPorNome(pet.getNome());

        dao.excluir(id);

        assertNull(dao.buscarPorId(id));

        TestDatabaseUtil.excluirTutorCompleto(tutor.getIdTutor());
    }
}
