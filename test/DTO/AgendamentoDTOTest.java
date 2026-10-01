package DTO;

import org.junit.Test;
import static org.junit.Assert.*;

public class AgendamentoDTOTest {

    @Test
    public void deveCriarDTOComConstrutorCompleto() {
        AgendamentoDTO dto = new AgendamentoDTO(
                1, 2, 3, 4, 5, "28/09/2026", "10:30", "CONFIRMADO");

        assertEquals(1, dto.getIdAgendamento());
        assertEquals(2, dto.getTutorId());
        assertEquals(3, dto.getPetId());
        assertEquals(4, dto.getServicoId());
        assertEquals(5, dto.getFuncionarioId());
        assertEquals("28/09/2026", dto.getData());
        assertEquals("10:30", dto.getHora());
        assertEquals("CONFIRMADO", dto.getStatus());
    }

    @Test
    public void deveAlterarTodosOsCampos() {
        AgendamentoDTO dto = new AgendamentoDTO();

        dto.setIdAgendamento(10);
        dto.setTutorId(20);
        dto.setPetId(30);
        dto.setServicoId(40);
        dto.setFuncionarioId(50);
        dto.setData("29/09/2026");
        dto.setHora("14:00");
        dto.setStatus("CANCELADO");

        assertEquals(10, dto.getIdAgendamento());
        assertEquals(20, dto.getTutorId());
        assertEquals(30, dto.getPetId());
        assertEquals(40, dto.getServicoId());
        assertEquals(50, dto.getFuncionarioId());
        assertEquals("29/09/2026", dto.getData());
        assertEquals("14:00", dto.getHora());
        assertEquals("CANCELADO", dto.getStatus());
    }
}
