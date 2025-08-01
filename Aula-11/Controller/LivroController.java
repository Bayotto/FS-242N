package Controller;

import Model.Aluno;
import Model.Dao.EmprestimoDAO;
import Model.Dao.LivroDAO;
import Model.Emprestimo;
import Model.Livro;

import java.util.List;

public class LivroController {

    private LivroDAO livroDAO;

    public LivroController() {
        this.livroDAO = new LivroDAO();
    }

    public void cadastrarLivro(String titulo, String autor, String genero, String isbm) throws Exception {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new Exception("O titulo é obrigatório.");
        }
        if (autor == null || autor.trim().isEmpty()) {
            throw new Exception("O autor é obrigatório.");
        }
        if (genero == null || genero.trim().isEmpty()) {
            throw new Exception("O genero é obrigatório.");
        }
        if (isbm == null || isbm.trim().isEmpty()) {
            throw new Exception("O ISBM é obrigatório.");
        }
        Livro livro = new Livro(titulo,autor,genero,isbm);
       livroDAO.inserir(livro);
    }

    public List<Livro> listarTodosLivros() {
        return livroDAO.listarTodosLivros();
    }

    public void removerLivro(int id_livro) {
        livroDAO.remover(id_livro);
    }

    public List<Livro> buscarLivroPorNome(String titulo) {
        return livroDAO.buscarPorTitulo(titulo);
    }

    public Livro buscarLivroPorId(int id) {
        return livroDAO.buscarPorId(id);
    }

    public void atualizarLivros(int id, String titulo, String autor, String genero, String isbm) throws Exception {
        if (titulo == null || autor.trim().isEmpty() || genero == null || isbm.trim().isEmpty() ) {
            throw new Exception("Todos os campos do livro são obrigatórios e devem ser válidos.");
        }
        Livro livro = new Livro(id, titulo,autor,genero,isbm);
        livroDAO.atualizarLivro(livro);
    }
}
