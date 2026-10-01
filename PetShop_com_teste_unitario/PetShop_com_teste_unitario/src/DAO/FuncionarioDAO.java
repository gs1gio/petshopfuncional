package DAO;

import DTO.FuncionarioDTO;
import conexao.ConexaoSQLite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FuncionarioDAO {

    public void cadastrar(FuncionarioDTO funcionario) {

        String sql = "INSERT INTO Funcionario "
                + "(nome_Funcionario, email_Funcionario, senha_Funcionario) "
                + "VALUES (?, ?, ?)";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getEmail());
            stmt.setString(3, funcionario.getSenha());

            stmt.executeUpdate();

            System.out.println("Funcionario cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar funcionario!");
            System.out.println(e.getMessage());
        }
    }

    public void alterar(FuncionarioDTO funcionario) {

        String sql = "UPDATE Funcionario SET "
                + "nome_Funcionario = ?, "
                + "email_Funcionario = ?, "
                + "senha_Funcionario = ? "
                + "WHERE id_Funcionario = ?";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getEmail());
            stmt.setString(3, funcionario.getSenha());
            stmt.setInt(4, funcionario.getIdFuncionario());

            stmt.executeUpdate();

            System.out.println("Funcionario alterado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao alterar funcionario!");
            System.out.println(e.getMessage());
        }
    }

    public void excluir(int idFuncionario) {

        String sql = "DELETE FROM Funcionario WHERE id_Funcionario = ?";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idFuncionario);

            stmt.executeUpdate();

            System.out.println("Funcionario excluido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao excluir funcionario!");
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<FuncionarioDTO> listar() {

        ArrayList<FuncionarioDTO> lista = new ArrayList<>();

        String sql = "SELECT * FROM Funcionario";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                FuncionarioDTO funcionario = new FuncionarioDTO();

                funcionario.setIdFuncionario(
                        rs.getInt("id_Funcionario"));

                funcionario.setNome(
                        rs.getString("nome_Funcionario"));

                funcionario.setEmail(
                        rs.getString("email_Funcionario"));

                funcionario.setSenha(
                        rs.getString("senha_Funcionario"));

                lista.add(funcionario);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar funcionarios!");
            System.out.println(e.getMessage());
        }

        return lista;
    }

    public FuncionarioDTO buscarPorId(int idFuncionario) {

        String sql = "SELECT * FROM Funcionario WHERE id_Funcionario = ?";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idFuncionario);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                FuncionarioDTO funcionario = new FuncionarioDTO();

                funcionario.setIdFuncionario(
                        rs.getInt("id_Funcionario"));

                funcionario.setNome(
                        rs.getString("nome_Funcionario"));

                funcionario.setEmail(
                        rs.getString("email_Funcionario"));

                funcionario.setSenha(
                        rs.getString("senha_Funcionario"));

                return funcionario;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar funcionario!");
            System.out.println(e.getMessage());
        }

        return null;
    }

    public FuncionarioDTO buscarLogin(String email, String senha) {

        String sql = "SELECT * FROM Funcionario "
                + "WHERE email_Funcionario = ? "
                + "AND senha_Funcionario = ?";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                FuncionarioDTO funcionario = new FuncionarioDTO();

                funcionario.setIdFuncionario(
                        rs.getInt("id_Funcionario"));

                funcionario.setNome(
                        rs.getString("nome_Funcionario"));

                funcionario.setEmail(
                        rs.getString("email_Funcionario"));

                funcionario.setSenha(
                        rs.getString("senha_Funcionario"));

                return funcionario;
            }

        } catch (SQLException e) {

            System.out.println("Erro ao realizar login do funcionario!");
            System.out.println(e.getMessage());
        }

        return null;
    }
}