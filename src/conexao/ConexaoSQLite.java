package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoSQLite {

    private static final String URL = "jdbc:sqlite:petshop.db";

    public static Connection conectar() {
        Connection conexao = null;

        try {
            conexao = DriverManager.getConnection(URL);
            System.out.println("Conexao  realizada com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao conectar");
            System.out.println(e.getMessage());
        }

        return conexao;
    }
}