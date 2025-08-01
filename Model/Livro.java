package Model;

public class Livro {

    private int id_livro;
    private String titulo;
    private String autor;
    private String genero;
    private String isbm;

    public Livro(int id_livro, String titulo, String autor, String genero, String isbm) {
        this.id_livro = id_livro;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.isbm = isbm;
    }
    public Livro(String titulo, String autor, String genero, String isbm) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.isbm = isbm;
    }

    public int getId_livro() {
        return id_livro;
    }

    public void setId_livro(int id_livro) {
        this.id_livro = id_livro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getIsbm() {
        return isbm;
    }

    public void setIsbm(String isbm) {
        this.isbm = isbm;
    }


}
