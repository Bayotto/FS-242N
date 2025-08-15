package Controller;

import Model.Pokemon;
import org.hibernate.Transaction;
import org.hibernate.Session;
import util.HibernateUtil;

import java.sql.SQLException;
import java.util.List;

public class PokemonController {
    public void cadastrarPokemon(Pokemon pokemon) throws Exception {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            //Validações de négocio(nome,tipo,etc.)
            if (pokemon.getNome() == null || pokemon.getNome().trim().isEmpty()) {
            }

            if (pokemon.getNome() == null || pokemon.getNome().trim().isEmpty()) {
                throw new Exception("Nome do Pokémon é obrigatório.");
            }
            if (pokemon.getTipoPrimario() == null || pokemon.getTipoPrimario().trim().isEmpty()) {
                throw new Exception("Tipo primário é obrigatório.");
            }
            if (pokemon.getTipoPrimario().equalsIgnoreCase(pokemon.getTipoSecundario())) {
                throw new Exception("Tipo secundário não pode ser igual ao primário.");
            }
            if (pokemon.getNivel() < 0 || pokemon.getNivel() > 100) {
                throw new Exception("Nível inválido.");
            }
            if (pokemon.getHpMaximo() <= 0 || pokemon.getHpMaximo() > 300) {
                throw new Exception("HP inválido.");


            }
            session.persist(pokemon);//Salva o objeto no banco
            transaction.commit();

        }
    }

    public void atualizarPokemon(int id, String nome, String tipoPrimario, String tipoSecundario, int nivel, int hpMaximo) throws Exception {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            //Validações de négocio(nome,tipo,etc.)

            Pokemon pokemon = new Pokemon(id, nome, tipoPrimario, tipoSecundario, nivel, hpMaximo);

            session.persist(pokemon);//Salva o objeto no banco
            transaction.commit();
        }

//    public List<Pokemon> listarTodosPokemons() {
//        return HibernateUtil.listarTodos();
//    }
//
//    public Pokemon buscarPokemonPorId(int id) {
//        return pokemonDAO.buscarPorId(id);
//    }
//
//    public void removerPokemon(int id) throws Exception {
//        // --- EXERCÍCIO: Adicionar validações aqui! ---
//        try {
//            pokemonDAO.remover(id);
//        } catch (SQLException e) {
//            throw new Exception("Erro ao remover Pokémon: " + e.getMessage());
//        }
//    }
//
//    public List<Pokemon> buscarPokemonPorNome(String nome) {
//        // --- EXERCÍCIO: Adicionar validações aqui! ---
//        return pokemonDAO.buscarPorNome(nome);
//    }
//

    }
}