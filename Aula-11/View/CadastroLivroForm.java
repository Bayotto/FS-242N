package View;

import Controller.LivroController;
import Model.Livro;

import javax.swing.*;
import java.awt.*;

public class CadastroLivroForm extends JInternalFrame {
    private LivroController controller;
    private JTextField txtId, txtTitulo, txtAutor, txtGenero, txtISBM;
    private JButton btnSalvar, btnBuscar;
    private Integer livroIdParaEdicao;

    public CadastroLivroForm(LivroController controller, Integer livroId) {
        super("Cadastro de Livro", true, true, true, true); // Título, redimensionável, fechável, maximizável, iconificável
        this.controller = controller;
        this.livroIdParaEdicao = livroId;

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
        btnBuscar.addActionListener(e -> buscarLivro()); // Adiciona ação ao botão Buscar
        add(btnBuscar, gbc);
        row++;

        // Campo Titulo
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Titulo:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2; // Ocupa 2 colunas
        txtTitulo = new JTextField(20);
        add(txtTitulo, gbc);
        row++;

        // Campo Autor
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Autor:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        txtAutor = new JTextField(20);
        add(txtAutor, gbc);
        row++;

        // Campo Genero
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Genero:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        txtGenero = new JTextField(20);
        add(txtGenero, gbc);
        row++;

        // Campo ISBM
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("ISBM:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        txtISBM = new JTextField(20);
        add(txtISBM, gbc);
        row++;

        // Botão Salvar
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 3; // Ocupa 3 colunas
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarLivro()); // Adiciona ação ao botão Salvar
        add(btnSalvar, gbc);

        // Se um ID foi passado, carrega o Livro para edição
        if (livroIdParaEdicao != null) {
            carregarLivroParaEdicao(livroIdParaEdicao);
            txtId.setText(String.valueOf(livroIdParaEdicao));
            btnBuscar.setEnabled(false); // Desabilita o botão buscar se já está editando
        }
    }

    private void buscarLivro() {
        String idStr = JOptionPane.showInputDialog(this, "Digite o ID do Livro para buscar:");
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
            Livro livro = controller.buscarLivroPorId(id);
            if (livro != null) {
                txtId.setText(String.valueOf(livro.getId_livro()));
                txtTitulo.setText(livro.getTitulo());
                txtAutor.setText(livro.getAutor());
                txtGenero.setText(livro.getGenero());
                txtISBM.setText(livro.getIsbm());
                livroIdParaEdicao = livro.getId_livro(); // Define o ID para indicar que é uma edição
            } else {
                JOptionPane.showMessageDialog(this, "Livro com ID " + id + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                limparCampos(); // Limpa os campos se não encontrar
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar livro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarLivro() {
        try {
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            String genero = txtGenero.getText().trim();
            String ISBM = txtISBM.getText().trim();

            if (livroIdParaEdicao == null) { // Se LivroIdParaEdicao é null, é um novo cadastro
                controller.cadastrarLivro(titulo,autor,genero,ISBM);
                JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso!");
            } else { // Caso contrário, é uma atualização
                controller.atualizarLivros(livroIdParaEdicao,titulo,autor,genero,ISBM );
                JOptionPane.showMessageDialog(this, "Livro atualizado com sucesso!");
            }
            this.dispose(); // Fecha a janela após a operação bem-sucedida
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar livro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void limparCampos() {
        txtId.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtGenero.setText("");
        txtISBM.setText("");
        livroIdParaEdicao = null; // Reseta para modo de novo cadastro
        btnBuscar.setEnabled(true); // Habilita o botão buscar novamente
    }
}
