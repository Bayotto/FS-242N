package View;

import Controller.AlunoController;
import Model.Aluno;

import javax.swing.*;
import java.awt.*;

public class CadastroAlunoForm extends JInternalFrame {
    private AlunoController controller;
    private JTextField txtId, txtNome, txtIdade, txtTelefone;
    private JButton btnSalvar, btnBuscar;
    private Integer alunoIdParaEdicao;

    public CadastroAlunoForm(AlunoController controller, Integer alunoId) {
        super("Cadastro de Aluno", true, true, true, true); // Título, redimensionável, fechável, maximizável, iconificável
        this.controller = controller;
        this.alunoIdParaEdicao = alunoId;

        setSize(500, 350); // Tamanho da janela interna
        setLayout(new GridBagLayout()); // Layout para organizar os componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 40, 5, 40); // Espaçamento entre os componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; // Preenche o espaço horizontalmente

        int row = 0; // Contador de linhas para o layout

        // Campo ID
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        txtId = new JTextField(10);
        txtId.setEditable(false); // ID não pode ser editado diretamente, apenas buscado
        add(txtId, gbc);
        gbc.gridx = 2;
        gbc.gridy = row;
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarAluno()); // Adiciona ação ao botão Buscar
        add(btnBuscar, gbc);
        row++;

        // Campo Nome
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2; // Ocupa 2 colunas
        txtNome = new JTextField(20);
        add(txtNome, gbc);
        row++;

        // Campo Idade
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Idade:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        txtIdade = new JTextField(20); // Preenche com a data atual por padrão
        add(txtIdade, gbc);
        row++;

        // Campo Telefone
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        txtTelefone = new JTextField(20); // Preenche com a data atual por padrão
        add(txtTelefone, gbc);
        row++;

        // Botão Salvar
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 3; // Ocupa 3 colunas
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarAluno()); // Adiciona ação ao botão Salvar
        add(btnSalvar, gbc);

        // Se um ID foi passado, carrega o Aluno para edição
        if (alunoIdParaEdicao != null) {
            carregarAlunoParaEdicao(alunoIdParaEdicao);
            txtId.setText(String.valueOf(alunoIdParaEdicao));
            btnBuscar.setEnabled(false); // Desabilita o botão buscar se já está editando
        }
    }

    private void buscarAluno() {
        String idStr = JOptionPane.showInputDialog(this, "Digite o ID do Aluno para buscar:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                carregarAlunoParaEdicao(id);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido. Por favor, digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void carregarAlunoParaEdicao(int id) {
        try {
            Aluno aluno = controller.buscarAlunoPorId(id);
            if (aluno != null) {
                txtId.setText(String.valueOf(aluno.getId()));
                txtNome.setText(aluno.getNome());
                txtIdade.setText(aluno.getIdade());
                txtTelefone.setText(aluno.getTelefone());
                alunoIdParaEdicao = aluno.getId(); // Define o ID para indicar que é uma edição
            } else {
                JOptionPane.showMessageDialog(this, "Aluno com ID " + id + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                limparCampos(); // Limpa os campos se não encontrar
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar aluno: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarAluno() {
        try {
            String nome = txtNome.getText().trim();
            String idade = txtIdade.getText().trim();
            String telefone = txtTelefone.getText().trim();

            if (alunoIdParaEdicao == null) { // Se alunoIdParaEdicao é null, é um novo cadastro
                controller.cadastrarAluno(nome, idade, telefone);
                JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!");
            } else { // Caso contrário, é uma atualização
                controller.atualizarAlunos(alunoIdParaEdicao, nome, idade, telefone);
                JOptionPane.showMessageDialog(this, "Aluno atualizado com sucesso!");
            }
            this.dispose(); // Fecha a janela após a operação bem-sucedida
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar Aluno: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtIdade.setText("");
        txtTelefone.setText("");

        alunoIdParaEdicao = null; // Reseta para modo de novo cadastro
        btnBuscar.setEnabled(true); // Habilita o botão buscar novamente
    }

}
