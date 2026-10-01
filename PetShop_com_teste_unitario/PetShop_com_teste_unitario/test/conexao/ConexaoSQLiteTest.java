package conexao;

import java.sql.Connection;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConexaoSQLiteTest {

    @Test
    public void deveConectarAoSQLite() throws Exception {
        Connection conexao = ConexaoSQLite.conectar();

        assertNotNull("A conexão SQLite não deveria ser null.", conexao);
        assertFalse("A conexão deveria estar aberta.", conexao.isClosed());

        conexao.close();
    }

    @Test
    public void conexaoDeveSerValida() throws Exception {
        Connection conexao = ConexaoSQLite.conectar();

        assertNotNull(conexao);
        assertTrue(conexao.isValid(2));

        conexao.close();
    }
}
