package DTO;

import org.junit.Test;
import static org.junit.Assert.*;

public class TutorDTOTest {

    @Test
    public void deveCriarDTOComConstrutorCompleto() {
        TutorDTO dto = new TutorDTO(
                1, "Joao", "11111111111",
                "51999999999", "joao@test.com", "123456");

        assertEquals(1, dto.getIdTutor());
        assertEquals("Joao", dto.getNome());
        assertEquals("11111111111", dto.getCpf());
        assertEquals("51999999999", dto.getTelefone());
        assertEquals("joao@test.com", dto.getEmail());
        assertEquals("123456", dto.getSenha());
    }

    @Test
    public void deveAlterarTodosOsCampos() {
        TutorDTO dto = new TutorDTO();

        dto.setIdTutor(2);
        dto.setNome("Maria");
        dto.setCpf("22222222222");
        dto.setTelefone("51888888888");
        dto.setEmail("maria@test.com");
        dto.setSenha("654321");

        assertEquals(2, dto.getIdTutor());
        assertEquals("Maria", dto.getNome());
        assertEquals("22222222222", dto.getCpf());
        assertEquals("51888888888", dto.getTelefone());
        assertEquals("maria@test.com", dto.getEmail());
        assertEquals("654321", dto.getSenha());
    }
}
