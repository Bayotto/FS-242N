package View;

import Controller.EmprestimoController;
import Model.Dao.AlunoDAO;
import Model.Dao.LivroDAO;
import Model.Emprestimo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class ListaEmprestimoForm extends JInternalFrame {
    private EmprestimoController controller;
    private JTable tabelaEmprestimos;
    private DefaultTableModel tableModel;
    private JButton btnAtualizar, btnRemover, btnBuscar;
    private JTextField txtBuscaNome;

    public ListaEmprestimoForm(EmprestimoController controller) { // Altere o tipo do parâmetro
        super("Lista de Emprestimo", true, true, true, true);
        this.controller = controller; // Atribui o novo controller

        setSize(850, 500);
        setLayout(new BorderLayout());

        String[] colunas = {"ID do Emprestimo","Nome do Aluno","Nome do Livro", "Data de Emprestimo", "Data de Devolucao"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaEmprestimos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaEmprestimos);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelAcoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBuscaNome = new JTextField(20);
        btnBuscar = new JButton("Buscar por Nome");
        btnAtualizar = new JButton("Atualizar Tabela");
        btnRemover = new JButton("Remover Selecionado");

        panelAcoes.add(new JLabel("Nome:"));
        panelAcoes.add(txtBuscaNome);
        panelAcoes.add(btnBuscar);
        panelAcoes.add(btnAtualizar);
        panelAcoes.add(btnRemover);
        add(panelAcoes, BorderLayout.NORTH);

        btnAtualizar.addActionListener(e -> carregarEmprestimoNaTabela());
        btnRemover.addActionListener(e -> removerEmprestimoSelecionado());
        btnBuscar.addActionListener(e -> buscarEmprestimosPorNome());

    }


    private void carregarEmprestimoNaTabela() {
        tableModel.setRowCount(0); // Limpa as linhas existentes na tabela
        LivroDAO livro = new LivroDAO();
        java.util.List<Emprestimo> emprestimos = controller.listarTodosEmprestimos(); // Busca todos os empresimos
        for (Emprestimo emprestimo : emprestimos) {
            // Adiciona cada emprestimo como uma nova linha na tabela
            tableModel.addRow(new Object[]{
                    emprestimo.getId_emprestimo(),
                    emprestimo.getId_aluno(),
                    livro.buscarPorId(emprestimo.getId_livro()).getTitulo(),
                    emprestimo.getData_emprestimo(),
                    emprestimo.getData_devolucao()
            });
        }
    }


    private void removerEmprestimoSelecionado() {
        int selectedRow = tabelaEmprestimos.getSelectedRow(); // Obtém a linha selecionada
        if (selectedRow >= 0) { // Verifica se alguma linha foi selecionada
            int id_Emprestimo = (int) tableModel.getValueAt(selectedRow, 0); // Obtém o ID da célula da tabela

            // Confirmação antes de remover
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja remover o livro ID: " + id_Emprestimo + "?",
                    "Confirmar Remoção", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    controller.removerEmprestimo(id_Emprestimo); // Chama o controller para remover
                    JOptionPane.showMessageDialog(this, "Emprestimo removido com sucesso!");
                    carregarEmprestimoNaTabela(); // Atualiza a tabela após a remoção
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao remover emprestimo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um emprestimo para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void buscarEmprestimosPorNome() {
        String nomeBusca = txtBuscaNome.getText().trim(); // Obtém o texto do campo de busca
        tableModel.setRowCount(0); // Limpa a tabela
        LivroDAO livro = new LivroDAO();
        AlunoDAO aluno = new AlunoDAO();

        List<Emprestimo> emprestimos;
        if (nomeBusca.isEmpty()) {
            // Se o campo de busca estiver vazio, lista todos
            emprestimos = controller.listarTodosEmprestimos();
        } else {
            // Caso contrário, busca por nome
            emprestimos = controller.buscarEmprestimoPorNome(nomeBusca);
        }

        if (emprestimos.isEmpty() && !nomeBusca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum emprestimo encontrado com o nome: '" + nomeBusca + "'", "Busca", JOptionPane.INFORMATION_MESSAGE);
        }

        System.out.println(livro.buscarPorId(emprestimos.getFirst().getId_livro()).getTitulo());

        for (Emprestimo emprestimo : emprestimos) {
            tableModel.addRow(new Object[]{
                    emprestimo.getId_emprestimo(),
                    aluno.buscarPorId(emprestimo.getId_aluno()).getNome(),
                    livro.buscarPorId(emprestimo.getId_livro()).getTitulo(),
                    emprestimo.getData_emprestimo(),
                    emprestimo.getData_devolucao()
            });
        }
    }
}
