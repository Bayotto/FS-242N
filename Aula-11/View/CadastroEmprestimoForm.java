package View;



import Controller.EmprestimoController;
import Model.Dao.EmprestimoDAO;
import Model.Emprestimo;

import javax.swing.*;
import java.awt.*;

public class CadastroEmprestimoForm extends JInternalFrame {
    private EmprestimoController controller;
    private JTextField txtId_Emprestimo, txtId_Aluno, txtId_Livro, txtData_Emprestimo,txtData_Devolucao;
    private JButton btnSalvar, btnBuscar;
    private Integer emprestimoIdParaEdicao;

    public CadastroEmprestimoForm(EmprestimoController controller, Integer emprestimoId) {
        super("Cadastro de Emprestimo", true, true, true, true); // Título, redimensionável, fechável, maximizável, iconificável
        this.controller = controller;
        this.emprestimoIdParaEdicao = emprestimoId;

        setSize(500, 350); // Tamanho da janela interna
        setLayout(new GridBagLayout()); // Layout para organizar os componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 40, 5, 40); // Espaçamento entre os componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; // Preenche o espaço horizontalmente

        int row = 0; // Contador de linhas para o layout

        // Campo ID do Emprestimo
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("ID do Emprestimo:"), gbc);
        gbc.gridx = 1; gbc.gridy = row;
        txtId_Emprestimo = new JTextField(10);
        txtId_Emprestimo.setEditable(false); // ID não pode ser editado diretamente, apenas buscado
        add(txtId_Emprestimo, gbc);
        gbc.gridx = 2; gbc.gridy = row;
        row++;


        // Campo ID do Aluno
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("ID do Aluno:"), gbc);
        gbc.gridx = 1; gbc.gridy = row;
        txtId_Aluno = new JTextField(10);
        txtId_Aluno.setEditable(true); // ID não pode ser editado diretamente, apenas buscado
        add(txtId_Aluno, gbc);
        gbc.gridx = 2; gbc.gridy = row;
        row++;


        // Campo ID do livro
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("ID do Livro:"), gbc);
        gbc.gridx = 1; gbc.gridy = row;
        txtId_Livro = new JTextField(10);
        txtId_Livro.setEditable(true); // ID não pode ser editado diretamente, apenas buscado
        add(txtId_Livro, gbc);
        gbc.gridx = 2; gbc.gridy = row;
        row++;


        // Campo data_emprestimo
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Data do Emprestimo:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2; // Ocupa 2 colunas
        txtData_Emprestimo = new JTextField(20);
        add(txtData_Emprestimo, gbc);
        row++;

        // Campo data_devolucao
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Data da Devolucao:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        txtData_Devolucao = new JTextField(20);
        add(txtData_Devolucao, gbc);
        row++;


        // Botão Salvar
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 3; // Ocupa 3 colunas
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarEmprestimo()); // Adiciona ação ao botão Salvar
        add(btnSalvar, gbc);

        // Se um ID foi passado, carrega o emprestimo para edição
        if (emprestimoIdParaEdicao != null) {
            carregarLivroParaEdicao(emprestimoIdParaEdicao);
            txtId_Emprestimo.setText(String.valueOf(emprestimoIdParaEdicao));
            btnBuscar.setEnabled(false); // Desabilita o botão buscar se já está editando
        }
    }

    private void buscarEmprestimo() {
        String idStr = JOptionPane.showInputDialog(this, "Digite o ID do Emprestimo para buscar:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                carregarLivroParaEdicao(id);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido. Por favor, digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void carregarLivroParaEdicao(int id) {
        try {
            Emprestimo emprestimo = controller.buscarEmprestimoPorId(id);
            if (emprestimo != null) {
                txtId_Emprestimo.setText(String.valueOf(emprestimo.getId_emprestimo()));
                txtId_Aluno.setText(String.valueOf(emprestimo.getId_aluno()));
                txtId_Livro.setText(String.valueOf(emprestimo.getId_livro()));
                txtData_Emprestimo.setText(emprestimo.getData_emprestimo());
                txtData_Devolucao.setText(emprestimo.getData_devolucao());
                emprestimoIdParaEdicao = emprestimo.getId_emprestimo(); // Define o ID para indicar que é uma edição
            } else {
                JOptionPane.showMessageDialog(this, "Livro com ID " + id + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                limparCampos(); // Limpa os campos se não encontrar
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar livro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarEmprestimo() {
        try {
            String data_emprestimo = txtData_Emprestimo.getText().trim();
            String data_devolucao = txtData_Devolucao.getText().trim();
            String id_aluno  = txtId_Aluno.getText().trim();
            String id_livro = txtId_Livro.getText().trim();

            Emprestimo emprestimo= new Emprestimo(Integer.parseInt(id_aluno),Integer.parseInt(id_livro),data_emprestimo,data_devolucao);
            EmprestimoDAO.inserir(emprestimo);


            if (emprestimoIdParaEdicao == null) { // Se emprestimoIdParaEdicao é null, é um novo cadastro
                controller.cadastrarEmprestimo(data_emprestimo,data_devolucao);
                JOptionPane.showMessageDialog(this, "Emprestimo cadastrado com sucesso!");
            } else { // Caso contrário, é uma atualização
                controller.atualizarEmprestimo(emprestimoIdParaEdicao,Integer.parseInt(id_aluno),Integer.parseInt(id_livro),data_emprestimo,data_devolucao );
                JOptionPane.showMessageDialog(this, "Emprestimo atualizado com sucesso!");
            }
            this.dispose(); // Fecha a janela após a operação bem-sucedida
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar emprestimo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void limparCampos() {
        txtId_Emprestimo.setText("");
        txtId_Aluno.setText("");
        txtId_Livro.setText("");
        txtData_Emprestimo.setText("");
        txtData_Devolucao.setText("");
        emprestimoIdParaEdicao = null; // Reseta para modo de novo cadastro
        btnBuscar.setEnabled(true); // Habilita o botão buscar novamente
    }
}
