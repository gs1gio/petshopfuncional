package DTO;

import org.junit.Test;
import static org.junit.Assert.*;

public class FuncionarioDTOTest {

    @Test
    public void deveCriarDTOComConstrutorCompleto() {
        FuncionarioDTO dto =
                new FuncionarioDTO(1, "Atendente", "atendente@test.com", "123");

        assertEquals(1, dto.getIdFuncionario());
        assertEquals("Atendente", dto.getNome());
        assertEquals("atendente@test.com", dto.getEmail());
        assertEquals("123", dto.getSenha());
    }

    @Test
    public void deveAlterarTodosOsCampos() {
        FuncionarioDTO dto = new FuncionarioDTO();

        dto.setIdFuncionario(2);
        dto.setNome("Maria");
        dto.setEmail("maria@test.com");
        dto.setSenha("456");

        assertEquals(2, dto.getIdFuncionario());
        assertEquals("Maria", dto.getNome());
        assertEquals("maria@test.com", dto.getEmail());
        assertEquals("456", dto.getSenha());
    }
}
