package DTO;

import org.junit.Test;
import static org.junit.Assert.*;

public class ServicoDTOTest {

    @Test
    public void deveCriarDTOComConstrutorCompleto() {
        ServicoDTO dto =
                new ServicoDTO(1, "Banho", 50.0, true);

        assertEquals(1, dto.getIdServico());
        assertEquals("Banho", dto.getNome());
        assertEquals(50.0, dto.getPreco(), 0.001);
        assertTrue(dto.isAtivo());
    }

    @Test
    public void deveAlterarTodosOsCampos() {
        ServicoDTO dto = new ServicoDTO();

        dto.setIdServico(2);
        dto.setNome("Tosa");
        dto.setPreco(75.50);
        dto.setAtivo(false);

        assertEquals(2, dto.getIdServico());
        assertEquals("Tosa", dto.getNome());
        assertEquals(75.50, dto.getPreco(), 0.001);
        assertFalse(dto.isAtivo());
    }
}
