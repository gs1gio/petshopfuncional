package DTO;

import org.junit.Test;
import static org.junit.Assert.*;

public class PetDTOTest {

    @Test
    public void deveAlterarTodosOsCampos() {
        PetDTO dto = new PetDTO();

        dto.setIdPet(1);
        dto.setTutorId(2);
        dto.setNome("Rex");
        dto.setEspecie("Cachorro");
        dto.setPorte("Grande");

        assertEquals(1, dto.getIdPet());
        assertEquals(2, dto.getTutorId());
        assertEquals("Rex", dto.getNome());
        assertEquals("Cachorro", dto.getEspecie());
        assertEquals("Grande", dto.getPorte());
    }
}
