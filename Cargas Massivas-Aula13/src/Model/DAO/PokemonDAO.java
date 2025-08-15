package Model.DAO;

import Model.Pokemon;
import Conexao.ConexaoPostgresDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PokemonDAO {

    public void inserir(Pokemon pokemon) throws SQLException {
        String sql = "INSERT INTO pokemons (nome, tipo_primario, tipo_secundario, nivel, hp_maximo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, pokemon.getNome());
            stmt.setString(2, pokemon.getTipoPrimario());
            stmt.setString(3, pokemon.getTipoSecundario());
            stmt.setInt(4, pokemon.getNivel());
            stmt.setInt(5, pokemon.getHpMaximo());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pokemon.setId(rs.getInt(1));
                }
            }
        }
    }

    public void atualizar(Pokemon pokemon) throws SQLException {
        String sql = "UPDATE pokemons SET nome = ?, tipo_primario = ?, tipo_secundario = ?, nivel = ?, hp_maximo = ? WHERE id_pokemon = ?";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pokemon.getNome());
            stmt.setString(2, pokemon.getTipoPrimario());
            stmt.setString(3, pokemon.getTipoSecundario());
            stmt.setInt(4, pokemon.getNivel());
            stmt.setInt(5, pokemon.getHpMaximo());
            stmt.setInt(6, pokemon.getId());
            stmt.executeUpdate();
        }
    }

    public void remover(int id) throws SQLException {
        String sql = "DELETE FROM pokemons WHERE id_pokemon = ?";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Pokemon buscarPorId(int id) {
        String sql = "SELECT * FROM pokemons WHERE id_pokemon = ?";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Pokemon(
                            rs.getInt("id_pokemon"),
                            rs.getString("nome"),
                            rs.getString("tipo_primario"),
                            rs.getString("tipo_secundario"),
                            rs.getInt("nivel"),
                            rs.getInt("hp_maximo")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar Pokémon por ID: " + e.getMessage());
        }
        return null;
    }

    public List<Pokemon> listarTodos() {
        List<Pokemon> pokemons = new ArrayList<>();
        String sql = "SELECT * FROM pokemons ORDER BY nome";
        try (Connection conn = ConexaoPostgresDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                pokemons.add(new Pokemon(
                        rs.getInt("id_pokemon"),
                        rs.getString("nome"),
                        rs.getString("tipo_primario"),
                        rs.getString("tipo_secundario"),
                        rs.getInt("nivel"),
                        rs.getInt("hp_maximo")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar todos os Pokémons: " + e.getMessage());
        }
        return pokemons;
    }

    public List<Pokemon> buscarPorNome(String nomeBusca) {
        List<Pokemon> pokemons = new ArrayList<>();
        String sql = "SELECT * FROM pokemons WHERE LOWER(nome) LIKE LOWER(?) ORDER BY nome";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nomeBusca + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pokemons.add(new Pokemon(
                            rs.getInt("id_pokemon"),
                            rs.getString("nome"),
                            rs.getString("tipo_primario"),
                            rs.getString("tipo_secundario"),
                            rs.getInt("nivel"),
                            rs.getInt("hp_maximo")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar Pokémon por nome: " + e.getMessage());
        }
        return pokemons;
    }

    public boolean pokemonJaExiste(String nome) throws SQLException {
        String sql = "SELECT COUNT(*) FROM pokemons WHERE LOWER(nome) = LOWER(?)";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public void inserirListaPokemons(List<Pokemon> listaPokemons) throws SQLException {
        String sql = "INSERT INTO pokemons (nome, tipo_primario, tipo_secundario, nivel, hp_maximo) VALUES (?, ?, ?, ?, ?)";
        Connection conn = ConexaoPostgresDB.conectar();
        conn.setAutoCommit(false);
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        final int Batch_size = 1000;
        for (int i = 0; i < listaPokemons.size(); i++) {
            Pokemon pokemao = listaPokemons.get(i);
            stmt.setString(1, pokemao.getNome());
            stmt.setString(2, pokemao.getTipoPrimario());
            stmt.setString(3, pokemao.getTipoSecundario());
            stmt.setInt(4, pokemao.getNivel());
            stmt.setInt(5, pokemao.getHpMaximo());
            stmt.addBatch();
            if ((i + 1) == listaPokemons.size() || (i + 1) % Batch_size == 0) {
                stmt.executeBatch();
                stmt.clearBatch();
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pokemao.setId(rs.getInt(1));
                }
            }
        }
        conn.commit();
        conn.setAutoCommit(true);
    }
}