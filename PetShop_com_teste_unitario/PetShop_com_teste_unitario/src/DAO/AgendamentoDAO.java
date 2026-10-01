package DAO;

import DTO.AgendamentoDTO;
import conexao.ConexaoSQLite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AgendamentoDAO {

    public void cadastrar(AgendamentoDTO agendamento) {

        String sql = "INSERT INTO Agendamento "
                + "(id_Tutor_Agendamento, id_Pet_Agendamento, "
                + "id_Servico_Agendamento, id_Funcionario_Agendamento, "
                + "data_Agendamento, hora_Agendamento, status_Agendamento) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, agendamento.getTutorId());
            stmt.setInt(2, agendamento.getPetId());
            stmt.setInt(3, agendamento.getServicoId());
            stmt.setInt(4, agendamento.getFuncionarioId());
            stmt.setString(5, agendamento.getData());
            stmt.setString(6, agendamento.getHora());
            stmt.setString(7, agendamento.getStatus());

            stmt.executeUpdate();

            System.out.println("Agendamento cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar agendamento!");
            System.out.println(e.getMessage());
        }
    }

    public void alterar(AgendamentoDTO agendamento) {

        String sql = "UPDATE Agendamento SET "
                + "id_Tutor_Agendamento = ?, "
                + "id_Pet_Agendamento = ?, "
                + "id_Servico_Agendamento = ?, "
                + "id_Funcionario_Agendamento = ?, "
                + "data_Agendamento = ?, "
                + "hora_Agendamento = ?, "
                + "status_Agendamento = ? "
                + "WHERE id_Agendamento = ?";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, agendamento.getTutorId());
            stmt.setInt(2, agendamento.getPetId());
            stmt.setInt(3, agendamento.getServicoId());
            stmt.setInt(4, agendamento.getFuncionarioId());
            stmt.setString(5, agendamento.getData());
            stmt.setString(6, agendamento.getHora());
            stmt.setString(7, agendamento.getStatus());
            stmt.setInt(8, agendamento.getIdAgendamento());

            stmt.executeUpdate();

            System.out.println("Agendamento alterado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao alterar agendamento!");
            System.out.println(e.getMessage());
        }
    }

    public void excluir(int idAgendamento) {

        String sql = "DELETE FROM Agendamento WHERE id_Agendamento = ?";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idAgendamento);

            stmt.executeUpdate();

            System.out.println("Agendamento excluido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao excluir agendamento!");
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<AgendamentoDTO> listar() {

        ArrayList<AgendamentoDTO> lista = new ArrayList<>();

        String sql = "SELECT * FROM Agendamento";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                AgendamentoDTO agendamento = new AgendamentoDTO();

                agendamento.setIdAgendamento(
                        rs.getInt("id_Agendamento"));

                agendamento.setTutorId(
                        rs.getInt("id_Tutor_Agendamento"));

                agendamento.setPetId(
                        rs.getInt("id_Pet_Agendamento"));

                agendamento.setServicoId(
                        rs.getInt("id_Servico_Agendamento"));

                agendamento.setFuncionarioId(
                        rs.getInt("id_Funcionario_Agendamento"));

                agendamento.setData(
                        rs.getString("data_Agendamento"));

                agendamento.setHora(
                        rs.getString("hora_Agendamento"));

                agendamento.setStatus(
                        rs.getString("status_Agendamento"));

                lista.add(agendamento);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar agendamentos!");
            System.out.println(e.getMessage());
        }

        return lista;
    }

    public AgendamentoDTO buscarPorId(int idAgendamento) {

        String sql = "SELECT * FROM Agendamento "
                + "WHERE id_Agendamento = ?";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idAgendamento);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                AgendamentoDTO agendamento = new AgendamentoDTO();

                agendamento.setIdAgendamento(
                        rs.getInt("id_Agendamento"));

                agendamento.setTutorId(
                        rs.getInt("id_Tutor_Agendamento"));

                agendamento.setPetId(
                        rs.getInt("id_Pet_Agendamento"));

                agendamento.setServicoId(
                        rs.getInt("id_Servico_Agendamento"));

                agendamento.setFuncionarioId(
                        rs.getInt("id_Funcionario_Agendamento"));

                agendamento.setData(
                        rs.getString("data_Agendamento"));

                agendamento.setHora(
                        rs.getString("hora_Agendamento"));

                agendamento.setStatus(
                        rs.getString("status_Agendamento"));

                return agendamento;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar agendamento!");
            System.out.println(e.getMessage());
        }

        return null;
    }
    
    
    public boolean horarioOcupado(String data, String hora) {

    String sql = "SELECT COUNT(*) FROM Agendamento "
            + "WHERE data_Agendamento = ? "
            + "AND hora_Agendamento = ? "
            + "AND status_Agendamento = 'CONFIRMADO'";

    try (Connection conexao = ConexaoSQLite.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setString(1, data);
        stmt.setString(2, hora);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt(1) > 0;
        }

    } catch (SQLException e) {

        System.out.println("Erro ao verificar horario!");
        System.out.println(e.getMessage());
    }

    return false;
}
    
    
    
    public ArrayList<AgendamentoDTO> listarPorTutor(int idTutor) {

    ArrayList<AgendamentoDTO> lista =
            new ArrayList<>();

    String sql =
            "SELECT * FROM Agendamento "
            + "WHERE id_Tutor_Agendamento = ?";

    try (Connection conexao = ConexaoSQLite.conectar();
         PreparedStatement stmt =
                 conexao.prepareStatement(sql)) {

        stmt.setInt(1, idTutor);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            AgendamentoDTO agendamento =
                    new AgendamentoDTO();

            agendamento.setIdAgendamento(
                    rs.getInt("id_Agendamento")
            );

            agendamento.setTutorId(
                    rs.getInt("id_Tutor_Agendamento")
            );

            agendamento.setPetId(
                    rs.getInt("id_Pet_Agendamento")
            );

            agendamento.setServicoId(
                    rs.getInt("id_Servico_Agendamento")
            );

            agendamento.setFuncionarioId(
                    rs.getInt("id_Funcionario_Agendamento")
            );

            agendamento.setData(
                    rs.getString("data_Agendamento")
            );

            agendamento.setHora(
                    rs.getString("hora_Agendamento")
            );

            agendamento.setStatus(
                    rs.getString("status_Agendamento")
            );

            lista.add(agendamento);
        }

    } catch (SQLException e) {

        System.out.println(
                "Erro ao listar agendamentos do tutor!"
        );

        System.out.println(e.getMessage());
    }

    return lista;
}
}