package Model;

public class dino {
    // Atributos conectando com as colunas das tabelas do banco
    public int id;
    public String nome;
    public String especie;
    public String dieta;
    public int idadeEstimada;
    public int idadeAtual;
    public String status;

    // Construtor com id para update
    public dino(int id, String nome, String especie, String dieta, int idadeEstimada, int idadeAtual, String status) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.dieta = dieta;
        this.idadeEstimada = idadeEstimada;
        this.idadeAtual = idadeAtual;
        this.status = status;
    }

    //Construtor para criar um objeto
    public dino(String nome, String especie, String dieta, int idadeEstimada, int idadeAtual, String status) {
        this.nome = nome;
        this.especie = especie;
        this.dieta = dieta;
        this.idadeEstimada = idadeEstimada;
        this.idadeAtual = idadeAtual;
        this.status = status;
    }

    //Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getDieta() {
        return dieta;
    }

    public void setDieta(String dieta) {
        this.dieta = dieta;
    }

    public int getIdadeEstimada() {
        return idadeEstimada;
    }

    public void setIdadeEstimada(int idadeEstimada) {
        this.idadeEstimada = idadeEstimada;
    }

    public int getIdadeAtual() {
        return idadeAtual;
    }

    public void setIdadeAtual(int idadeAtual) {
        this.idadeAtual = idadeAtual;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
