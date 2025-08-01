import Controller.DinossauroController;
import View.CadastroDinossauroForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Model.Dino;
import View.CadastroDinossauroForm;
import View.ListaDinossauroForm;

public class Main extends JFrame{
    private JDesktopPane desktopPane;
    private DinossauroController dinossauroController; // Nova instância do DinossauroController

    public Main() {
        super("Sistema de Gerenciamento de Dinossauros");
        this.dinossauroController = new DinossauroController(); // Instancia o controller de dinossauros

        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);

        createMenuBar();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // --- Menu Dinossauros (usará dinossauroController) ---
        JMenu menuDinossauros = new JMenu("Dinossauros");
        JMenuItem itemCadastrarDinossauro = new JMenuItem("Cadastrar Dinossauro");
        JMenuItem itemListarDinossauros = new JMenuItem("Listar Dinossauros");

        itemCadastrarDinossauro.addActionListener(e -> openDinossauroForm(null));
        itemListarDinossauros.addActionListener(e -> openListaDinossaurosPanel());

        menuDinossauros.add(itemCadastrarDinossauro);
        menuDinossauros.add(itemListarDinossauros);

        menuBar.add(menuDinossauros);

        // --- Menu Sair (Existente) ---
        JMenu menuSair = new JMenu("Sair");
        JMenuItem itemSair = new JMenuItem("Sair do Sistema");
        itemSair.addActionListener(e -> System.exit(0));

        menuSair.add(itemSair);
        menuBar.add(menuSair);

        setJMenuBar(menuBar);
    }

    private void openDinossauroForm(Integer idDinossauro) {
        CadastroDinossauroForm dinossauroForm = new CadastroDinossauroForm(dinossauroController, idDinossauro); // Passa o dinossauroController
        desktopPane.add(dinossauroForm);
        dinossauroForm.setVisible(true);
        dinossauroForm.toFront();
    }


    private void openListaDinossaurosPanel() {
        ListaDinossauroForm listaDinossauros = new ListaDinossauroForm(dinossauroController); // Passa o dinossauroController
        desktopPane.add(listaDinossauros);
        listaDinossauros.setVisible(true);
        listaDinossauros.toFront();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }

}