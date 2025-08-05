package Controller;

import Model.Dao.PokemonDao;
import Model.Pokemon;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PokemonController {
    private PokemonDao pokemonDAO;

    public PokemonController() {
        this.pokemonDAO = new PokemonDao();
    }

    public void cadastrarPokemon(String nome, String tipoPrimario, String tipoSecundario, int nivel, int hpMaximo) throws Exception {
        // --- EXERCÍCIO: Adicionar validações aqui! ---
        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("O nome do Pokemon é obrigatório.");
        }
        if (tipoPrimario == null || tipoPrimario.trim().isEmpty()) {
            throw new Exception("O tipo Primario do pokemon é obrigatório.");
        }
        if (nivel < 0 || nivel > 100) {
            throw new Exception("O nivel é obrigatorio");
        }
        if (hpMaximo < 0) {
            throw new Exception("O hp é obrigatorio e deve ser maior que zero");
        }

        List<Pokemon> pokes = buscarPokemonPorNome(nome);

        for (Pokemon poke : pokes) {
            if (poke.getNome().equals(nome)) {
                throw new Exception("O nome é identico e nn pode ser repetido!");
            }
        }

        if (tipoPrimario.equals(tipoSecundario)) {
            throw new Exception("O tipo Primario não pode ser igual ao Secundario");
        }


        // Exemplo de chamada do Model (já validado):
        Pokemon pokemon = new Pokemon(nome, tipoPrimario, tipoSecundario, nivel, hpMaximo);
        try {
            pokemonDAO.inserir(pokemon);
        } catch (SQLException e) {
            throw new Exception("Erro ao cadastrar Pokémon no banco de dados: " + e.getMessage());
        }
    }

    public void atualizarPokemon(int id, String nome, String tipoPrimario, String tipoSecundario, int nivel, int hpMaximo) throws Exception {
        // --- EXERCÍCIO: Adicionar validações aqui! ---
        System.out.println(buscarPokemonPorId(id));
        if (buscarPokemonPorId(id) == null) {
            throw new Exception("O Pokemon não foi encontrado para atualização.");
        }

        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("O nome do Pokemon é obrigatório.");
        }
        if (tipoPrimario == null || tipoPrimario.trim().isEmpty()) {
            throw new Exception("O tipo do pokemon é obrigatório.");
        }
        // Exemplo de chamada do Model (já validado):
        Pokemon pokemon = new Pokemon(id, nome, tipoPrimario, tipoSecundario, nivel, hpMaximo);
        try {
            pokemonDAO.atualizar(pokemon);
        } catch (SQLException e) {
            throw new Exception("Erro ao atualizar Pokémon no banco de dados: " + e.getMessage());
        }
    }

    public List<Pokemon> listarTodosPokemons() {
        return pokemonDAO.listarTodos();
    }

    public Pokemon buscarPokemonPorId(int id) {
        return pokemonDAO.buscarPorId(id);
    }

    public void removerPokemon(int id) throws Exception {
        // --- EXERCÍCIO: Adicionar validações aqui! ---
        System.out.println(buscarPokemonPorId(90));
        if (buscarPokemonPorId(90) == null) {
            throw new Exception("O Pokemon não foi encontrado para remoção");
        }
        try {
            pokemonDAO.remover(id);
        } catch (SQLException e) {
            throw new Exception("Erro ao remover Pokémon: " + e.getMessage());
        }
    }

    public List<Pokemon> buscarPokemonPorNome(String nome) {
        // --- EXERCÍCIO: Adicionar validações aqui! ---
        return pokemonDAO.buscarPorNome(nome);
    }
}
