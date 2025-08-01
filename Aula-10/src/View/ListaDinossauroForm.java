package View;

import Controller.DinossauroController;
import Model.Dino;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;


public class ListaDinossauroForm extends JInternalFrame {

    private DinossauroController controller;
    private JTable tabelaDinossauros;
    private DefaultTableModel tableModel;
    private JButton btnAtualizar, btnRemover, btnBuscar;
    private JTextField txtBuscaNome;

    public ListaDinossauroForm(DinossauroController controller) { // Altere o tipo do parâmetro
        super("Lista de Dinossauros", true, true, true, true);
        this.controller = controller; // Atribui o novo controller

        setSize(850, 500);
        setLayout(new BorderLayout());

        String[] colunas = {"ID", "Nome", "Espécie", "Peso (kg)", "Idade Estimada (m)", "Idade Atual", "Status Cercado"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaDinossauros = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaDinossauros);
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

        btnAtualizar.addActionListener(e -> carregarDinossaurosNaTabela());
        btnRemover.addActionListener(e -> removerDinossauroSelecionado());
        btnBuscar.addActionListener(e -> buscarDinossaurosPorNome());

    }

    private void carregarDinossaurosNaTabela() {
        tableModel.setRowCount(0); // Limpa as linhas existentes na tabela
        List<Dino> listDino = controller.listarTodosDinossauros(); // Busca todos os dinossauros
        for (Dino dino : listDino) {
            // Adiciona cada dinossauro como uma nova linha na tabela
            tableModel.addRow(new Object[]{
                    dino.getId(),
                    dino.getNome(),
                    dino.getEspecie(),
                    dino.getDieta(),
                    dino.getIdadeEstimada(),
                    dino.getIdadeAtual(),
                    dino.getStatus()
            });
        }
    }


    private void removerDinossauroSelecionado() {
        int selectedRow = tabelaDinossauros.getSelectedRow(); // Obtém a linha selecionada
        if (selectedRow >= 0) { // Verifica se alguma linha foi selecionada
            int idDinossauro = (int) tableModel.getValueAt(selectedRow, 0); // Obtém o ID da célula da tabela
    // Confirmação antes de remover
    int confirm = JOptionPane.showConfirmDialog(this,
            "Tem certeza que deseja remover o dinossauro ID: " + idDinossauro + "?",
            "Confirmar Remoção", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
        try {
            controller.removerDinossauro(idDinossauro); // Chama o controller para remover
            JOptionPane.showMessageDialog(this, "Dinossauro removido com sucesso!");
            carregarDinossaurosNaTabela(); // Atualiza a tabela após a remoção
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao remover dinossauro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
} else {
        JOptionPane.showMessageDialog(this, "Selecione um dinossauro para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
                }


    private void buscarDinossaurosPorNome() {
        String nomeBusca = txtBuscaNome.getText().trim(); // Obtém o texto do campo de busca
        tableModel.setRowCount(0); // Limpa a tabela

        List<Dino> dinossauros;
        if (nomeBusca.isEmpty()) {
            // Se o campo de busca estiver vazio, lista todos
            dinossauros = controller.listarTodosDinossauros();
        } else {
            // Caso contrário, busca por nome
            dinossauros = controller.buscarDinossauroPorNome(nomeBusca);
        }

        if (dinossauros.isEmpty() && !nomeBusca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum dinossauro encontrado com o nome: '" + nomeBusca + "'", "Busca", JOptionPane.INFORMATION_MESSAGE);
        }

        for (Dino dino : dinossauros) {
            tableModel.addRow(new Object[]{
                    dino.getId(),
                    dino.getNome(),
                    dino.getEspecie(),
                    dino.getDieta(),
                    dino.getIdadeEstimada(),
                    dino.getIdadeAtual(),
                    dino.getStatus()
            });
        }
    }
}
