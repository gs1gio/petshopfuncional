package DAO;

import DTO.TutorDTO;
import conexao.ConexaoSQLite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TutorDAO {

    public void cadastrar(TutorDTO tutor) {

        String sql = "INSERT INTO Tutor "
                + "(nome_Tutor, cpf_Tutor, telefone_Tutor, email_Tutor, senha_Tutor) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, tutor.getNome());
            stmt.setString(2, tutor.getCpf());
            stmt.setString(3, tutor.getTelefone());
            stmt.setString(4, tutor.getEmail());
            stmt.setString(5, tutor.getSenha());

            stmt.executeUpdate();

            System.out.println("Tutor cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar tutor!");
            System.out.println(e.getMessage());
        }
    }

    public void alterar(TutorDTO tutor) {

        String sql = "UPDATE Tutor SET "
                + "nome_Tutor = ?, "
                + "cpf_Tutor = ?, "
                + "telefone_Tutor = ?, "
                + "email_Tutor = ?, "
                + "senha_Tutor = ? "
                + "WHERE id_Tutor = ?";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, tutor.getNome());
            stmt.setString(2, tutor.getCpf());
            stmt.setString(3, tutor.getTelefone());
            stmt.setString(4, tutor.getEmail());
            stmt.setString(5, tutor.getSenha());
            stmt.setInt(6, tutor.getIdTutor());

            stmt.executeUpdate();

            System.out.println("Tutor alterado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao alterar tutor!");
            System.out.println(e.getMessage());
        }
    }

    public void excluir(int idTutor) {

        String sql = "DELETE FROM Tutor WHERE id_Tutor = ?";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idTutor);

            stmt.executeUpdate();

            System.out.println("Tutor excluido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao excluir tutor!");
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<TutorDTO> listar() {

        ArrayList<TutorDTO> lista = new ArrayList<>();

        String sql = "SELECT * FROM Tutor";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                TutorDTO tutor = new TutorDTO();

                tutor.setIdTutor(rs.getInt("id_Tutor"));
                tutor.setNome(rs.getString("nome_Tutor"));
                tutor.setCpf(rs.getString("cpf_Tutor"));
                tutor.setTelefone(rs.getString("telefone_Tutor"));
                tutor.setEmail(rs.getString("email_Tutor"));
                tutor.setSenha(rs.getString("senha_Tutor"));

                lista.add(tutor);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar tutores!");
            System.out.println(e.getMessage());
        }

        return lista;
    }

    public TutorDTO buscarPorId(int idTutor) {

        String sql = "SELECT * FROM Tutor WHERE id_Tutor = ?";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idTutor);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                TutorDTO tutor = new TutorDTO();

                tutor.setIdTutor(rs.getInt("id_Tutor"));
                tutor.setNome(rs.getString("nome_Tutor"));
                tutor.setCpf(rs.getString("cpf_Tutor"));
                tutor.setTelefone(rs.getString("telefone_Tutor"));
                tutor.setEmail(rs.getString("email_Tutor"));
                tutor.setSenha(rs.getString("senha_Tutor"));

                return tutor;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar tutor!");
            System.out.println(e.getMessage());
        }

        return null;
    }
    
    public TutorDTO buscarLogin(String email, String senha) {

    String sql = "SELECT * FROM Tutor "
            + "WHERE email_Tutor = ? "
            + "AND senha_Tutor = ?";

    try (Connection conexao = ConexaoSQLite.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setString(1, email);
        stmt.setString(2, senha);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            TutorDTO tutor = new TutorDTO();

            tutor.setIdTutor(rs.getInt("id_Tutor"));
            tutor.setNome(rs.getString("nome_Tutor"));
            tutor.setCpf(rs.getString("cpf_Tutor"));
            tutor.setTelefone(rs.getString("telefone_Tutor"));
            tutor.setEmail(rs.getString("email_Tutor"));
            tutor.setSenha(rs.getString("senha_Tutor"));

            return tutor;
        }

    } catch (SQLException e) {

        System.out.println("Erro ao realizar login do tutor!");
        System.out.println(e.getMessage());
    }

    return null;
}
    
}