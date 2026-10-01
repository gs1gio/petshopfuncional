package DTO;

public class ServicoDTO {

    private int idServico;
    private String nome;
    private double preco;
    private boolean ativo;

    public ServicoDTO() {
    }

    public ServicoDTO(int idServico, String nome, double preco, boolean ativo) {
        this.idServico = idServico;
        this.nome = nome;
        this.preco = preco;
        this.ativo = ativo;
    }

    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}