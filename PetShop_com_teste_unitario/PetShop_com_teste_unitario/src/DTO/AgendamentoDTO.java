package DTO;

public class AgendamentoDTO {

    private int idAgendamento;
    private int tutorId;
    private int petId;
    private int servicoId;
    private int funcionarioId;
    private String data;
    private String hora;
    private String status;

    public AgendamentoDTO() {
    }

    public AgendamentoDTO(int idAgendamento, int tutorId, int petId,
            int servicoId, int funcionarioId, String data,
            String hora, String status) {

        this.idAgendamento = idAgendamento;
        this.tutorId = tutorId;
        this.petId = petId;
        this.servicoId = servicoId;
        this.funcionarioId = funcionarioId;
        this.data = data;
        this.hora = hora;
        this.status = status;
    }

    public int getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(int idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getServicoId() {
        return servicoId;
    }

    public void setServicoId(int servicoId) {
        this.servicoId = servicoId;
    }

    public int getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(int funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}