package View;

import Controller.DinossauroController;
import Model.Dino;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate; // caso queria usar data
import java.time.format.DateTimeParseException;

public class CadastroDinossauroForm extends JInternalFrame{

    private DinossauroController controller;
    private JTextField txtId, txtNome, txtEspecie, txtDieta, txtIdadeEstimada, txtIdade, txtStatus;
    private JButton btnSalvar, btnBuscar;
    private Integer dinossauroIdParaEdicao;

    public CadastroDinossauroForm(DinossauroController controller, Integer dinossauroId) {
        super("Cadastro de Dinossauro", true, true, true, true); // Título, redimensionável, fechável, maximizável, iconificável
        this.controller = controller;
        this.dinossauroIdParaEdicao = dinossauroId;

        setSize(500, 350); // Tamanho da janela interna
        setLayout(new GridBagLayout()); // Layout para organizar os componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 40, 5, 40); // Espaçamento entre os componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; // Preenche o espaço horizontalmente

        int row = 0; // Contador de linhas para o layout

        // Campo ID
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row;
        txtId = new JTextField(10);
        txtId.setEditable(false); // ID não pode ser editado diretamente, apenas buscado
        add(txtId, gbc);
        gbc.gridx = 2; gbc.gridy = row;
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarDinossauro()); // Adiciona ação ao botão Buscar
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

        // Campo Espécie
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Espécie:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        txtEspecie = new JTextField(20);
        add(txtEspecie, gbc);
        row++;

        // Campo Dieta
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Dieta:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        txtDieta = new JTextField(20);
        add(txtDieta, gbc);
        row++;

        // Campo Idade Estimada
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Idade Estimada:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        txtIdadeEstimada = new JTextField(20);
        add(txtIdadeEstimada, gbc);
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

        // Campo Status
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        txtStatus = new JTextField(20); // Preenche com a data atual por padrão
        add(txtStatus, gbc);
        row++;

        // Botão Salvar
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 3; // Ocupa 3 colunas
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarDinossauro()); // Adiciona ação ao botão Salvar
        add(btnSalvar, gbc);

        // Se um ID foi passado, carrega o dinossauro para edição
        if (dinossauroIdParaEdicao != null) {
            carregarDinossauroParaEdicao(dinossauroIdParaEdicao);
            txtId.setText(String.valueOf(dinossauroIdParaEdicao));
            btnBuscar.setEnabled(false); // Desabilita o botão buscar se já está editando
        }
    }

    private void buscarDinossauro() {
        String idStr = JOptionPane.showInputDialog(this, "Digite o ID do dinossauro para buscar:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                carregarDinossauroParaEdicao(id);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido. Por favor, digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void carregarDinossauroParaEdicao(int id) {
        try {
            Dino dinossauro = controller.buscarDinossauroPorId(id);
            if (dinossauro != null) {
                txtId.setText(String.valueOf(dinossauro.getId()));
                txtNome.setText(dinossauro.getNome());
                txtEspecie.setText(dinossauro.getEspecie());
                txtDieta.setText(dinossauro.getDieta());
                txtIdadeEstimada.setText(dinossauro.getIdadeEstimada());
                txtIdade.setText(dinossauro.getIdadeAtual());
                txtStatus.setText(dinossauro.getStatus());
                dinossauroIdParaEdicao = dinossauro.getId(); // Define o ID para indicar que é uma edição
            } else {
                JOptionPane.showMessageDialog(this, "Dinossauro com ID " + id + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                limparCampos(); // Limpa os campos se não encontrar
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar dinossauro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarDinossauro() {
        try {
            String nome = txtNome.getText().trim();
            String especie = txtEspecie.getText().trim();
            String dieta = txtDieta.getText().trim();
            String idadeEstimada = txtIdadeEstimada.getText().trim();
            String idade = txtIdade.getText().trim();
            String status = txtStatus.getText().trim();

            if (dinossauroIdParaEdicao == null) { // Se dinossauroIdParaEdicao é null, é um novo cadastro
                controller.cadastrarDinossauro(nome, especie, dieta, idadeEstimada, idade, status);
                JOptionPane.showMessageDialog(this, "Dinossauro cadastrado com sucesso!");
            } else { // Caso contrário, é uma atualização
                controller.atualizarDinossauro(dinossauroIdParaEdicao, nome, especie, dieta, idadeEstimada, idade, status);
                JOptionPane.showMessageDialog(this, "Dinossauro atualizado com sucesso!");
            }
            this.dispose(); // Fecha a janela após a operação bem-sucedida
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar dinossauro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtEspecie.setText("");
        txtDieta.setText("");
        txtIdadeEstimada.setText("");
        txtIdade.setText("");
        txtStatus.setText("");
        dinossauroIdParaEdicao = null; // Reseta para modo de novo cadastro
        btnBuscar.setEnabled(true); // Habilita o botão buscar novamente
    }
}
