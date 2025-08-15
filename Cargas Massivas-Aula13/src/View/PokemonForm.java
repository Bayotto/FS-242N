package View;

import Controller.PokemonController;
import Model.Pokemon;

import javax.swing.*;
import java.awt.*;

public class PokemonForm extends JInternalFrame {

    private PokemonController controller;
    private JTextField txtId, txtNome, txtTipoPrimario, txtTipoSecundario, txtNivel, txtHpMaximo;
    private JButton btnSalvar, btnBuscar;
    private Integer pokemonIdParaEdicao;

    public PokemonForm(PokemonController controller, Integer pokemonId) {
        super("Cadastro de Pokémon", true, true, true, true);
        this.controller = controller;
        this.pokemonIdParaEdicao = pokemonId;

        setSize(600, 400);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 45, 5, 45);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Campo ID
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row;
        txtId = new JTextField(10);
        txtId.setEditable(false);
        add(txtId, gbc);
        gbc.gridx = 2; gbc.gridy = row;
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarPokemon());
        add(btnBuscar, gbc);
        row++;

        // Nome
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 2;
        txtNome = new JTextField(25);
        add(txtNome, gbc);
        row++;

        // Tipo Primário
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Tipo Primário:"), gbc);
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 2;
        txtTipoPrimario = new JTextField(25);
        add(txtTipoPrimario, gbc);
        row++;

        // Tipo Secundário
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Tipo Secundário:"), gbc);
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 2;
        txtTipoSecundario = new JTextField(25);
        add(txtTipoSecundario, gbc);
        row++;

        // Nível
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Nível (1-100):"), gbc);
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 2;
        txtNivel = new JTextField(25);
        add(txtNivel, gbc);
        row++;

        // HP Máximo
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("HP Máximo (>0):"), gbc);
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 2;
        txtHpMaximo = new JTextField(25);
        add(txtHpMaximo, gbc);
        row++;

        // Botão Salvar
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 3;
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarPokemon());
        add(btnSalvar, gbc);

        if (pokemonIdParaEdicao != null) {
            carregarPokemonParaEdicao(pokemonIdParaEdicao);
            txtId.setText(String.valueOf(pokemonIdParaEdicao));
            btnBuscar.setEnabled(false);
        }
    }

    private void buscarPokemon() {
        String idStr = JOptionPane.showInputDialog(this, "Digite o ID do Pokémon para buscar:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                carregarPokemonParaEdicao(id);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido. Por favor, digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void carregarPokemonParaEdicao(int id) {
        try {
            Pokemon pokemon = controller.buscarPokemonPorId(id);
            if (pokemon != null) {
                txtId.setText(String.valueOf(pokemon.getId()));
                txtNome.setText(pokemon.getNome());
                txtTipoPrimario.setText(pokemon.getTipoPrimario());
                txtTipoSecundario.setText(pokemon.getTipoSecundario());
                txtNivel.setText(String.valueOf(pokemon.getNivel()));
                txtHpMaximo.setText(String.valueOf(pokemon.getHpMaximo()));
                pokemonIdParaEdicao = pokemon.getId();
            } else {
                JOptionPane.showMessageDialog(this, "Pokémon com ID " + id + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                limparCampos();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar Pokémon: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarPokemon() {
        try {
            String nome = txtNome.getText().trim();
            String tipoPrimario = txtTipoPrimario.getText().trim();
            String tipoSecundario = txtTipoSecundario.getText().trim();
            int nivel = Integer.parseInt(txtNivel.getText().trim());
            int hpMaximo = Integer.parseInt(txtHpMaximo.getText().trim());

            // Garante que tipoSecundario seja null se estiver vazio
            if (tipoSecundario.isEmpty()) {
                tipoSecundario = null;
            }

            if (pokemonIdParaEdicao == null) {
                controller.cadastrarPokemon(nome, tipoPrimario, tipoSecundario, nivel, hpMaximo);
                JOptionPane.showMessageDialog(this, "Pokémon cadastrado com sucesso!");
            } else {
                controller.atualizarPokemon(pokemonIdParaEdicao, nome, tipoPrimario, tipoSecundario, nivel, hpMaximo);
                JOptionPane.showMessageDialog(this, "Pokémon atualizado com sucesso!");
            }
            this.dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nível ou HP Máximo inválido. Por favor, insira valores numéricos válidos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) { // Captura exceções do Model (validações de Nível/HP)
            JOptionPane.showMessageDialog(this, "Erro de validação (Model): " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) { // Captura exceções do Controller
            JOptionPane.showMessageDialog(this, "Erro ao salvar Pokémon: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtTipoPrimario.setText("");
        txtTipoSecundario.setText("");
        txtNivel.setText("");
        txtHpMaximo.setText("");
        pokemonIdParaEdicao = null;
        btnBuscar.setEnabled(true);
    }
}