package DAO;

import DTO.ServicoDTO;
import conexao.ConexaoSQLite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServicoDAO {

    public void cadastrar(ServicoDTO servico) {

        String sql = "INSERT INTO Servico "
                + "(nome_Servico, preco_Servico, ativo_Servico) "
                + "VALUES (?, ?, ?)";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, servico.getNome());
            stmt.setDouble(2, servico.getPreco());
            stmt.setBoolean(3, servico.isAtivo());

            stmt.executeUpdate();

            System.out.println("Servico cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar servico!");
            System.out.println(e.getMessage());
        }
    }

    public void alterar(ServicoDTO servico) {

        String sql = "UPDATE Servico SET "
                + "nome_Servico = ?, "
                + "preco_Servico = ?, "
                + "ativo_Servico = ? "
                + "WHERE id_Servico = ?";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, servico.getNome());
            stmt.setDouble(2, servico.getPreco());
            stmt.setBoolean(3, servico.isAtivo());
            stmt.setInt(4, servico.getIdServico());

            stmt.executeUpdate();

            System.out.println("Servico alterado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao alterar servico!");
            System.out.println(e.getMessage());
        }
    }

    public void excluir(int idServico) {

        String sql = "DELETE FROM Servico WHERE id_Servico = ?";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idServico);

            stmt.executeUpdate();

            System.out.println("Servico excluido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao excluir servico!");
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<ServicoDTO> listar() {

        ArrayList<ServicoDTO> lista = new ArrayList<>();

        String sql = "SELECT * FROM Servico";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                ServicoDTO servico = new ServicoDTO();

                servico.setIdServico(
                        rs.getInt("id_Servico"));

                servico.setNome(
                        rs.getString("nome_Servico"));

                servico.setPreco(
                        rs.getDouble("preco_Servico"));

                servico.setAtivo(
                        rs.getBoolean("ativo_Servico"));

                lista.add(servico);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar servicos!");
            System.out.println(e.getMessage());
        }

        return lista;
    }

    public ServicoDTO buscarPorId(int idServico) {

        String sql = "SELECT * FROM Servico WHERE id_Servico = ?";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idServico);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                ServicoDTO servico = new ServicoDTO();

                servico.setIdServico(
                        rs.getInt("id_Servico"));

                servico.setNome(
                        rs.getString("nome_Servico"));

                servico.setPreco(
                        rs.getDouble("preco_Servico"));

                servico.setAtivo(
                        rs.getBoolean("ativo_Servico"));

                return servico;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar servico!");
            System.out.println(e.getMessage());
        }

        return null;
    }
}