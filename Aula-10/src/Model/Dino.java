package Model;

public class Dino {
    public int id;
    public String nome;
    public String especie;
    public String dieta;
    public  String idadeEstimada;
    public  String idadeAtual;
    public String status;

    // Construtor com id para update
    public Dino(int id, String nome, String especie, String dieta, String idadeEstimada, String  idadeAtual, String status) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.dieta = dieta;
        this.idadeEstimada = idadeEstimada;
        this.idadeAtual = idadeAtual;
        this.status = status;
    }

    //Construtor para criar um objeto
    public Dino(String nome, String especie, String dieta, String idadeEstimada, String idadeAtual, String status) {
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

    public String getNome() {
        return nome;
    }


    public String getEspecie() {
        return especie;
    }

    public String getDieta() {
        return dieta;
    }

    public String getIdadeEstimada() {
        return idadeEstimada;
    }

    public String getIdadeAtual() {
        return idadeAtual;
    }

    public String getStatus() {
        return status;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public void setDieta(String dieta) {
        this.dieta = dieta;
    }

    public void setIdadeEstimada(String idadeEstimada) {
        this.idadeEstimada = idadeEstimada;
    }

    public void setIdadeAtual(String idadeAtual) {
        this.idadeAtual = idadeAtual;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
