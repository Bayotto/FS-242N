import Controller.PokemonController;
import Model.Aluno;
import Model.Pokemon;
import View.ListaPokemonsPanel;
import View.PokemonForm;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.swing.*;

public class MainApp extends JFrame{

    private JDesktopPane desktopPane;
    private PokemonController pokemonController;

    public MainApp() {
        super("Sistema de Gerenciamento de Pokémons");
        this.pokemonController = new PokemonController();

        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);

        createMenuBar();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Menu Pokémons
        JMenu menuPokemons = new JMenu("Pokémons");
        JMenuItem itemCadastrarPokemon = new JMenuItem("Cadastrar Pokémon");
        JMenuItem itemListarPokemons = new JMenuItem("Listar Pokémons");

        itemCadastrarPokemon.addActionListener(e -> openPokemonForm(null));
        itemListarPokemons.addActionListener(e -> openListaPokemonsPanel());

        menuPokemons.add(itemCadastrarPokemon);
        menuPokemons.add(itemListarPokemons);

        menuBar.add(menuPokemons);

        // Menu Sair
        JMenu menuSair = new JMenu("Sair");
        JMenuItem itemSair = new JMenuItem("Sair do Sistema");
        itemSair.addActionListener(e -> System.exit(0));

        menuSair.add(itemSair);
        menuBar.add(menuSair);

        setJMenuBar(menuBar);
    }

    private void openPokemonForm(Integer idPokemon) {
        PokemonForm pokemonForm = new PokemonForm(pokemonController, idPokemon);
        desktopPane.add(pokemonForm);
        pokemonForm.setVisible(true);
        pokemonForm.toFront();
    }

    private void openListaPokemonsPanel() {
        ListaPokemonsPanel listaPokemons = new ListaPokemonsPanel(pokemonController);
        desktopPane.add(listaPokemons);
        listaPokemons.setVisible(true);
        listaPokemons.toFront();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainApp().setVisible(true);
        });
    }
//    public static void main(String[] args){
//
//        Configuration config = new Configuration();
//        config.configure("hibernatePokemon.cfg.xml");
//        config.addAnnotatedClass(Pokemon.class);
//
//        SessionFactory sessionFactory = config.buildSessionFactory();
//        Session session = sessionFactory.openSession();
//
//        session.beginTransaction();
//
//        Pokemon pokemon = new Pokemon("Gardevoir","Psíquico","Fada",70,125);
//
//
//        session.save(pokemon);
//        session.getTransaction().commit();
//
//        session.close();
//        sessionFactory.close();
//    }

















    }
//        Configuration config = new Configuration();
//        config.configure("hibernatePokemon.cfg.xml");
//        config.addAnnotatedClass(Pokemon.class);
//
//        SessionFactory sessionFactory = config.buildSessionFactory();
//        Session session = sessionFactory.openSession();
//
//        session.beginTransaction();
//
//        Aluno aluno = new Aluno();
//        aluno.setNome("Jorge");
//        aluno.setIdade("30");
//        aluno.setTelefone("40028922");
//
//        session.save("aluno");
//        session.getTransaction().commit();
//
//        session.close();
//        sessionFactory.close();
//    }
//  }
