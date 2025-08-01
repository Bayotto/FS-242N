package Controller;

import Model.Aluno;
import Model.Dao.AlunoDAO;

import java.util.List;

public class AlunoController {
    private AlunoDAO alunoDAO;

    public AlunoController(){
        this.alunoDAO = new AlunoDAO();
    }


    public void cadastrarAluno(String nome, String idade, String telefone) throws Exception {


        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("O nome do aluno é obrigatório.");
        }

        if (idade == null || idade.trim().isEmpty()) {
            throw new Exception("idade é obrigatória");
        }
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new Exception("Telefone é obrigatório");
        }


            Aluno aluno = new Aluno( nome.trim(), idade, telefone.trim());
            alunoDAO.inserir(aluno);

}
    public List<Aluno> listarTodosAlunos() {
        return alunoDAO.listarTodosAlunos();
    }

    public void removerAluno(int id) {
        alunoDAO.remover(id);
    }

    public Aluno buscarAlunoPorId(int id) {
        return alunoDAO.buscarPorId(id);
    }

    public List<Aluno> buscarAlunoPorNome(String nome) {
        return alunoDAO.buscarPorNome(nome);
    }

    public void atualizarAlunos(int id, String nome, String idade, String telefone) throws Exception {
        if (nome == null || nome.trim().isEmpty() || idade == null || telefone.trim().isEmpty() ) {
            throw new Exception("Todos os campos do aluno são obrigatórios e devem ser válidos.");
        }
        Aluno aluno = new Aluno(id, nome,idade, telefone);
        alunoDAO.atualizarAluno(aluno);
    }
}

