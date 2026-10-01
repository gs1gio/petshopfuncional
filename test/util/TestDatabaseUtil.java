package util;

import conexao.ConexaoSQLite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestDatabaseUtil {

    public static void criarEstrutura() {
        conexao.CriarTabelas.main(new String[0]);
    }

    public static int buscarId(String sql, String valor) {
        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, valor);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new AssertionError("Erro ao buscar ID no banco: " + e.getMessage(), e);
        }

        return -1;
    }

    public static void executar(String sql, int id) {
        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new AssertionError("Erro ao limpar banco: " + e.getMessage(), e);
        }
    }

    public static void excluirTutorCompleto(int idTutor) {
        try (Connection conexao = ConexaoSQLite.conectar()) {

            try (PreparedStatement stmt = conexao.prepareStatement(
                    "DELETE FROM Agendamento WHERE id_Tutor_Agendamento = ?")) {
                stmt.setInt(1, idTutor);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conexao.prepareStatement(
                    "DELETE FROM Pet WHERE id_Tutor_Pet = ?")) {
                stmt.setInt(1, idTutor);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conexao.prepareStatement(
                    "DELETE FROM Tutor WHERE id_Tutor = ?")) {
                stmt.setInt(1, idTutor);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new AssertionError("Erro ao limpar tutor: " + e.getMessage(), e);
        }
    }

    public static void excluirFuncionarioCompleto(int idFuncionario) {
        try (Connection conexao = ConexaoSQLite.conectar()) {

            try (PreparedStatement stmt = conexao.prepareStatement(
                    "DELETE FROM Agendamento WHERE id_Funcionario_Agendamento = ?")) {
                stmt.setInt(1, idFuncionario);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conexao.prepareStatement(
                    "DELETE FROM Funcionario WHERE id_Funcionario = ?")) {
                stmt.setInt(1, idFuncionario);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new AssertionError("Erro ao limpar funcionario: " + e.getMessage(), e);
        }
    }

    public static void excluirPet(int idPet) {
        executar("DELETE FROM Agendamento WHERE id_Pet_Agendamento = ?", idPet);
        executar("DELETE FROM Pet WHERE id_Pet = ?", idPet);
    }

    public static void excluirServico(int idServico) {
        executar("DELETE FROM Agendamento WHERE id_Servico_Agendamento = ?", idServico);
        executar("DELETE FROM Servico WHERE id_Servico = ?", idServico);
    }

    public static void excluirAgendamento(int idAgendamento) {
        executar("DELETE FROM Agendamento WHERE id_Agendamento = ?", idAgendamento);
    }
    public static int buscarIdAgendamento(int idTutor, String data, String hora) {
        String sql = "SELECT id_Agendamento FROM Agendamento "
                + "WHERE id_Tutor_Agendamento = ? "
                + "AND data_Agendamento = ? "
                + "AND hora_Agendamento = ? "
                + "ORDER BY id_Agendamento DESC LIMIT 1";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idTutor);
            stmt.setString(2, data);
            stmt.setString(3, hora);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new AssertionError("Erro ao buscar agendamento: " + e.getMessage(), e);
        }

        return -1;
    }

}
