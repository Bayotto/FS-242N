import Conexao.ConexaoPostgresDB;
import Controller.AlunoController;
import Controller.EmprestimoController;
import Controller.LivroController;

import Model.Aluno;
import Model.Livro;
import Model.Emprestimo;
import View.*;


import javax.swing.*;
import java.sql.Connection;
import java.util.List;


public class Main extends JFrame {
    private JDesktopPane desktopPane;
    private AlunoController alunoController; // Nova instância do DinossauroController
    private EmprestimoController emprestimoController;
    private LivroController livroController;


    public Main() {
        super("Sistema de Gerenciamento de Aluno");
        this.alunoController = new AlunoController(); // Instancia o controller de aluno
        this.emprestimoController = new EmprestimoController();
        this.livroController = new LivroController();

        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);

        createMenuBar();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // --- Menu aluno (usará alunos) ---
        JMenu menuAlunos = new JMenu("Alunos");

        // --- Menu Emprestimo (usará Emprestimos) ---
        JMenu menuEmprestimos = new JMenu("Emprestimo");

        // --- Menu Livro (usará Livros) ---
        JMenu menuLivros = new JMenu("Livro");

        // Cadastros
        JMenuItem itemCadastrarALunos = new JMenuItem("Cadastrar Alunos");
        JMenuItem itemCadastrarEmrpestimos = new JMenuItem("Cadastrar Emprestimos");
        JMenuItem itemCadastrarLivros = new JMenuItem("Cadastrar Livros");


        // Listas
        JMenuItem itemListarAlunos = new JMenuItem("Listar Alunos");
        JMenuItem itemListarEmprestimos = new JMenuItem("Listar Emprestimos");
        JMenuItem itemListarLivros = new JMenuItem("Listar Livros");



        itemCadastrarALunos.addActionListener(e -> openAlunoForm(null));
        itemListarAlunos.addActionListener(e -> openListaAlunosForm());

        itemCadastrarEmrpestimos.addActionListener(e -> openEmprestimoForm(null));
        itemListarEmprestimos.addActionListener(e -> openListaEmprestimosForm());

        itemCadastrarLivros.addActionListener(e -> openLivroForm(null));
        itemListarLivros.addActionListener(e -> openListaLivrosForm());



        menuAlunos.add(itemCadastrarALunos);
        menuAlunos.add(itemListarAlunos);

        menuEmprestimos.add(itemCadastrarEmrpestimos);
        menuEmprestimos.add(itemListarEmprestimos);


        menuLivros.add(itemCadastrarLivros);
        menuLivros.add(itemListarLivros);







        menuBar.add(menuAlunos);
        menuBar.add(menuEmprestimos);
        menuBar.add(menuLivros);


        // --- Menu Sair (Existente) ---
        JMenu menuSair = new JMenu("Sair");
        JMenuItem itemSair = new JMenuItem("Sair do Sistema");
        itemSair.addActionListener(e -> System.exit(0));

        menuSair.add(itemSair);
        menuBar.add(menuSair);

        setJMenuBar(menuBar);
    }

    private void openAlunoForm(Integer id_aluno) {
        CadastroAlunoForm alunoForm = new CadastroAlunoForm(alunoController, id_aluno); // Passa o alunoController
        desktopPane.add(alunoForm);
       alunoForm.setVisible(true);
        alunoForm.toFront();
    }

    private void openEmprestimoForm(Integer id_emprestimo) {
        CadastroEmprestimoForm emprestimoForm = new CadastroEmprestimoForm(emprestimoController, id_emprestimo); // Passa o EmprestimoController
        desktopPane.add(emprestimoForm);
        emprestimoForm.setVisible(true);
        emprestimoForm.toFront();
    }

    private void openLivroForm(Integer id_livro) {
        CadastroLivroForm livroForm = new CadastroLivroForm(livroController, id_livro); // Passa o livroController
        desktopPane.add(livroForm);
        livroForm.setVisible(true);
        livroForm.toFront();
    }





    private void openListaAlunosForm() {
        ListaAlunoForm listaAlunos = new ListaAlunoForm(alunoController); // Passa o alunoController
        desktopPane.add(listaAlunos);
        listaAlunos.setVisible(true);
        listaAlunos.toFront();
    }


    private void openListaEmprestimosForm() {
        ListaEmprestimoForm listaEmprestimos = new ListaEmprestimoForm(emprestimoController); // Passa o emprestimoController
        desktopPane.add(listaEmprestimos);
        listaEmprestimos.setVisible(true);
        listaEmprestimos.toFront();
    }


    private void openListaLivrosForm() {
        ListaLivroForm listaLivros = new ListaLivroForm(livroController); // Passa o alunoController
        desktopPane.add(listaLivros);
        listaLivros.setVisible(true);
        listaLivros.toFront();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}





