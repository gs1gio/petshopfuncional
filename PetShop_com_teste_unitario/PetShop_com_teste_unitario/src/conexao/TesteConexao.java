//arquivo temporario para testar a conexao
package conexao;

import java.sql.Connection;

public class TesteConexao {

    public static void main(String[] args) {

        Connection conexao = ConexaoSQLite.conectar();

        if (conexao != null) {
            System.out.println("BANCO CONECTADO!");
        } else {
            System.out.println("BANCO NAO CONECTADO!");
        }
    }
}