package DAO;

import DTO.PetDTO;
import conexao.ConexaoSQLite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PetDAO {

    public void cadastrar(PetDTO pet) {

        String sql = "INSERT INTO Pet "
                + "(id_Tutor_Pet, nome_Pet, especie_Pet, porte_Pet) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, pet.getTutorId());
            stmt.setString(2, pet.getNome());
            stmt.setString(3, pet.getEspecie());
            stmt.setString(4, pet.getPorte());

            stmt.executeUpdate();

            System.out.println("Pet cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar pet!");
            System.out.println(e.getMessage());
        }
    }

    public void alterar(PetDTO pet) {

        String sql = "UPDATE Pet SET "
                + "id_Tutor_Pet = ?, "
                + "nome_Pet = ?, "
                + "especie_Pet = ?, "
                + "porte_Pet = ? "
                + "WHERE id_Pet = ?";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, pet.getTutorId());
            stmt.setString(2, pet.getNome());
            stmt.setString(3, pet.getEspecie());
            stmt.setString(4, pet.getPorte());
            stmt.setInt(5, pet.getIdPet());

            stmt.executeUpdate();

            System.out.println("Pet alterado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao alterar pet!");
            System.out.println(e.getMessage());
        }
    }

    public void excluir(int idPet) {

        String sql = "DELETE FROM Pet WHERE id_Pet = ?";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idPet);

            stmt.executeUpdate();

            System.out.println("Pet excluido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao excluir pet!");
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<PetDTO> listar() {

        ArrayList<PetDTO> lista = new ArrayList<>();

        String sql = "SELECT * FROM Pet";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                PetDTO pet = new PetDTO();

                pet.setIdPet(rs.getInt("id_Pet"));
                pet.setTutorId(rs.getInt("id_Tutor_Pet"));
                pet.setNome(rs.getString("nome_Pet"));
                pet.setEspecie(rs.getString("especie_Pet"));
                pet.setPorte(rs.getString("porte_Pet"));

                lista.add(pet);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar pets!");
            System.out.println(e.getMessage());
        }

        return lista;
    }

    public PetDTO buscarPorId(int idPet) {

        String sql = "SELECT * FROM Pet WHERE id_Pet = ?";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idPet);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                PetDTO pet = new PetDTO();

                pet.setIdPet(rs.getInt("id_Pet"));
                pet.setTutorId(rs.getInt("id_Tutor_Pet"));
                pet.setNome(rs.getString("nome_Pet"));
                pet.setEspecie(rs.getString("especie_Pet"));
                pet.setPorte(rs.getString("porte_Pet"));

                return pet;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar pet!");
            System.out.println(e.getMessage());
        }

        return null;
    }
    
    
    public ArrayList<PetDTO> listarPorTutor(int idTutor) {

    ArrayList<PetDTO> lista = new ArrayList<>();

    String sql = "SELECT * FROM Pet WHERE id_Tutor_Pet = ?";

    try (Connection conexao = ConexaoSQLite.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setInt(1, idTutor);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            PetDTO pet = new PetDTO();

            pet.setIdPet(rs.getInt("id_Pet"));
            pet.setTutorId(rs.getInt("id_Tutor_Pet"));
            pet.setNome(rs.getString("nome_Pet"));
            pet.setEspecie(rs.getString("especie_Pet"));
            pet.setPorte(rs.getString("porte_Pet"));

            lista.add(pet);
        }

    } catch (SQLException e) {

        System.out.println("Erro ao listar pets do tutor!");
        System.out.println(e.getMessage());
    }

    return lista;
}
}