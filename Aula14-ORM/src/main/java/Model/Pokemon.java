package Model;
import jakarta.persistence.*;

@Entity
@Table(name="pokemons")
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_pokemon",nullable = false)
    private int id_pokemon;

    @Column(name="nome", nullable = false)
    private String nome;

    @Column(name="tipo_primario", nullable = false)
    private String tipoPrimario;

    @Column(name="tipo_secundario",nullable = true)
    private String tipoSecundario;

    @Column(name="nivel",nullable = false)
    private int nivel;

    @Column(name="hp_maximo",nullable = false)
    private int hpMaximo;

    public Pokemon(int id_pokemon, String nome, String tipoPrimario, String tipoSecundario, int nivel, int hpMaximo) {
        this.id_pokemon = id_pokemon;
        this.nome = nome;
        this.tipoPrimario = tipoPrimario;
        this.tipoSecundario = tipoSecundario;
        this.nivel = nivel;
        this.hpMaximo = hpMaximo;
    }
    public Pokemon( String nome, String tipoPrimario, String tipoSecundario, int nivel, int hpMaximo) {
        this.nome = nome;
        this.tipoPrimario = tipoPrimario;
        this.tipoSecundario = tipoSecundario;
        this.nivel = nivel;
        this.hpMaximo = hpMaximo;
    }

    public int getId_pokemon() {
        return id_pokemon;
    }

    public void setId_pokemon(int id_pokemon) {
        this.id_pokemon = id_pokemon;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoPrimario() {
        return tipoPrimario;
    }

    public void setTipoPrimario(String tipoPrimario) {
        this.tipoPrimario = tipoPrimario;
    }

    public String getTipoSecundario() {
        return tipoSecundario;
    }

    public void setTipoSecundario(String tipoSecundario) {
        this.tipoSecundario = tipoSecundario;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getHpMaximo() {
        return hpMaximo;
    }

    public void setHpMaximo(int hpMaximo) {
        this.hpMaximo = hpMaximo;
    }


}
