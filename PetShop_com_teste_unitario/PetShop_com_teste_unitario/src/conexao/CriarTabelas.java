
//arquivo temporario, so serve pra criar as tabelas. 
//depois crie o banco e as tabelas no sqlite visual

package conexao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class CriarTabelas {

    public static void main(String[] args) {

        Connection conexao = ConexaoSQLite.conectar();

        if (conexao == null) {
            System.out.println("Nao foi possivel conectar ao banco.");
            return;
        }

        try {

            Statement stmt = conexao.createStatement();

            // TABELA TUTOR
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Tutor (" +
                "id_Tutor INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome_Tutor TEXT NOT NULL, " +
                "cpf_Tutor TEXT NOT NULL, " +
                "telefone_Tutor TEXT NOT NULL, " +
                "email_Tutor TEXT NOT NULL, " +
                "senha_Tutor TEXT NOT NULL)"
            );

            // TABELA PET
           stmt.executeUpdate(
    "CREATE TABLE IF NOT EXISTS Pet (" +
    "id_Pet INTEGER PRIMARY KEY AUTOINCREMENT, " +
    "id_Tutor_Pet INTEGER NOT NULL, " +
    "nome_Pet TEXT NOT NULL, " +
    "especie_Pet TEXT NOT NULL, " +
    "porte_Pet TEXT NOT NULL, " +
    "FOREIGN KEY (id_Tutor_Pet) REFERENCES Tutor(id_Tutor))"
);

            // TABELA SERVICO
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Servico (" +
                "id_Servico INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome_Servico TEXT NOT NULL, " +
                "preco_Servico REAL NOT NULL, " +
                "ativo_Servico INTEGER NOT NULL DEFAULT 1)"
            );

            // TABELA FUNCIONARIO
            stmt.executeUpdate(
    "CREATE TABLE IF NOT EXISTS Funcionario (" +
    "id_Funcionario INTEGER PRIMARY KEY AUTOINCREMENT, " +
    "nome_Funcionario TEXT NOT NULL, " +
    "email_Funcionario TEXT NOT NULL, " +
    "senha_Funcionario TEXT NOT NULL)"
);

            // TABELA AGENDAMENTO
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Agendamento (" +
                "id_Agendamento INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_Tutor_Agendamento INTEGER NOT NULL, " +
                "id_Pet_Agendamento INTEGER NOT NULL, " +
                "id_Servico_Agendamento INTEGER NOT NULL, " +
                "id_Funcionario_Agendamento INTEGER NOT NULL, " +
                "data_Agendamento TEXT NOT NULL, " +
                "hora_Agendamento TEXT NOT NULL, " +
                "status_Agendamento TEXT NOT NULL, " +
                "FOREIGN KEY (id_Tutor_Agendamento) REFERENCES Tutor(id_Tutor), " +
                "FOREIGN KEY (id_Pet_Agendamento) REFERENCES Pet(id_Pet), " +
                "FOREIGN KEY (id_Servico_Agendamento) REFERENCES Servico(id_Servico), " +
                "FOREIGN KEY (id_Funcionario_Agendamento) REFERENCES Funcionario(id_Funcionario))"
            );

            
            
 //adiciona um tutor inicial
//email: tutor@petshop.com
//Senha: 123456
            
            stmt.executeUpdate(
    "INSERT INTO Tutor "
    + "(nome_Tutor, cpf_Tutor, telefone_Tutor, email_Tutor, senha_Tutor) "
    + "SELECT 'Tutor Teste', '00000000000', '51999999999', "
    + "'tutor@petshop.com', '123456' "
    + "WHERE NOT EXISTS "
    + "(SELECT 1 FROM Tutor WHERE email_Tutor = 'tutor@petshop.com')"
);
            
         
            
            
//adiciona um funcionario inicial. 
//Email: atendente@petshop.com
//Senha: 123456
stmt.executeUpdate(
    "INSERT INTO Funcionario " +
    "(nome_Funcionario, email_Funcionario, senha_Funcionario) " +
    "SELECT 'Atendente Principal', " +
    "'atendente@petshop.com', " +
    "'123456' " +
    "WHERE NOT EXISTS (" +
    "SELECT 1 FROM Funcionario " +
    "WHERE email_Funcionario = 'atendente@petshop.com')"
);
            
            
            
            System.out.println("Tabelas criadas com sucesso!");

            stmt.close();
            conexao.close();

        } catch (SQLException e) {

            System.out.println("Erro ao criar as tabelas!");
            System.out.println(e.getMessage());
        }
    }
}




