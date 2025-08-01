package Controller;

import Model.Aluno;
import Model.Dao.EmprestimoDAO;
import Model.Emprestimo;

import java.util.List;

public class EmprestimoController {
    private EmprestimoDAO emprestimoDAO;

    public EmprestimoController() {
        this.emprestimoDAO = new EmprestimoDAO();
    }

    public void cadastrarEmprestimo(String data_emprestimo, String data_devolucao) throws Exception {

        if (data_emprestimo == null || data_emprestimo.trim().isEmpty()) {
            throw new Exception("A data de emprestimo é obrigatório.");
        }
        if (data_devolucao == null || data_devolucao.trim().isEmpty()) {
            throw new Exception("A data de devolucao é obrigatória.");
        }
        Emprestimo emprestimo = new Emprestimo(data_emprestimo,data_emprestimo);
        emprestimoDAO.inserir(emprestimo);
        }
    public List<Emprestimo> listarTodosEmprestimos() {
        return emprestimoDAO.listarTodosEmprestimos();
    }

    public void removerEmprestimo(int id_emprestimo) {
        emprestimoDAO.remover(id_emprestimo);
    }

    public List<Emprestimo> buscarEmprestimoPorNome(String data_emprestimo) {
        return emprestimoDAO.buscarPorNome(data_emprestimo);
    }

    public Emprestimo buscarEmprestimoPorId(int id) {
        return emprestimoDAO.buscarPorId(id);
    }


    public void atualizarEmprestimo(int idEmp,int idAlu,int idLiv ,String data_emprestimo, String data_devolucao) throws Exception {
        if ( data_emprestimo == null || data_devolucao.trim().isEmpty() ) {
            throw new Exception("Todos os campos do emprestimo são obrigatórios e devem ser válidos.");
        }
        Emprestimo emprestimo = new Emprestimo(idEmp,idAlu,idLiv,data_emprestimo,data_devolucao);
        emprestimoDAO.atualizarEmprestimo(emprestimo);
    }

    }
