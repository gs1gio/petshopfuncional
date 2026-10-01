package DTO;

public class FuncionarioDTO {

    private int idFuncionario;
    private String nome;
    private String email;
    private String senha;

    public FuncionarioDTO() {
    }

    public FuncionarioDTO(int idFuncionario, String nome, String email, String senha) {
        this.idFuncionario = idFuncionario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}