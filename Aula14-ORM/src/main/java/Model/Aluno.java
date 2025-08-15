package Model;

import jakarta.persistence.*;

@Entity
@Table(name="aluno")


public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // para ids tipo serial
    private int id_aluno;

    @Column(name="nome", nullable = false)
    private String nome;

    @Column(name="idade", nullable = false)
    private String idade;

    @Column(name="telefone",nullable = false)
    private String telefone;

    public int getId_aluno() {
        return id_aluno;
    }public void setId_aluno(int id_aluno) {
        this.id_aluno = id_aluno;
    }

    public String getNome() {
        return nome;
    }public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdade() {
        return idade;
    }public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getTelefone() {
        return telefone;
    }public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}