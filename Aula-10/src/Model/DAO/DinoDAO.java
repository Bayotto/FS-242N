package Model.DAO;

import Conexao.ConexaoPost;
import Model.Dino;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DinoDAO {

    public void inserir(Dino dinossauro) {
        String sql = "INSERT INTO dino (nome, especie, dieta, idadeEstimada, idadeDino, statusCercado) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoPost.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, dinossauro.getNome());
            stmt.setString(2, dinossauro.getEspecie());
            stmt.setString(3, dinossauro.getDieta());
            stmt.setString(4, dinossauro.getIdadeEstimada());
            stmt.setString(5, dinossauro.getIdadeAtual());
            stmt.setString(6, dinossauro.getStatus());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    dinossauro.setId(rs.getInt(1));
                }
            }
            System.out.println("Dinossauro inserido: " + dinossauro.getNome());
        } catch (SQLException e) {
            System.err.println("Erro ao inserir dinossauro: " + e.getMessage());
            // Exemplo de tratamento para nome/espécie duplicados se houver UNIQUE constraint
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                System.err.println("Erro: Dinossauro com nome e espécie semelhantes já cadastrado.");
            }
        }
    }

    public List<Dino> listarTodos() {
        List<Dino> dinossauros = new ArrayList<>();
        String sql = "SELECT id_dinossauro, nome, especie, dieta, idadeEstimada, idadeDino, statusCercado FROM dino ORDER BY nome";
        try (Connection conn = ConexaoPost.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Dino dinossauro = new Dino(
                        rs.getInt("id_dinossauro"),
                        rs.getString("nome"),
                        rs.getString("especie"),
                        rs.getString("dieta"),
                        rs.getString("idadeEstimada"),
                        rs.getString("idadeDino"),
                        rs.getString("statusCercado")
                );
                dinossauros.add(dinossauro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar dinossauros: " + e.getMessage());
        }
        return dinossauros;
    }

    public Dino buscarPorId(int idDinossauro) {
        String sql = "SELECT id_dinossauro, nome, especie, dieta, idadeEstimada, idadeDino, statusCercado FROM dinossauro WHERE id_dinossauro = ?";
        try (Connection conn = ConexaoPost.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idDinossauro);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Dino(
                            rs.getInt("id_dinossauro"),
                            rs.getString("nome"),
                            rs.getString("especie"),
                            rs.getString("dieta"),
                            rs.getString("idadeEstimada"),
                            rs.getString("idadeDino"),
                            rs.getString("statusCercado")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar dinossauro por ID: " + e.getMessage());
        }
        return null;
    }

    public static void setDinossauro(Dino dinossauro) {
        String sql = "INSERT INTO dino (nome,especie,dieta,idadeEstimada,idadeDino,statusCercado) VALUES (?,?,?,?,?,?)";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = ConexaoPost.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1, dinossauro.getNome());
                stmt.setString(2, dinossauro.getEspecie());
                stmt.setString(3, dinossauro.getDieta());
                stmt.setString(4, dinossauro.getIdadeEstimada());
                stmt.setString(5, dinossauro.getIdadeAtual());
                stmt.setString(6, dinossauro.getStatus());
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("\nDinossauro " + dinossauro.getNome() + " inserido no BD com sucesso!");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir dinossauro no PostgreSQL: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conexao != null) ConexaoPost.fecharConexao(conexao);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos após inserção " + e.getMessage());
            }
        }
    }

    public static List<Dino> getDinossauro() {
        String sql = "SELECT * FROM dino ORDER BY id_dinossauro";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Dino> Dinossauros = new ArrayList<>();

        try {
            conexao = ConexaoPost.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                rs = stmt.executeQuery();
                System.out.println("\n--- Dinossauro(s) Cadastrados no BD ---");
                boolean encontrouDinossauro = false;
                while (rs.next()) {
                    encontrouDinossauro = true;
                    int id = rs.getInt("id_dinossauro");
                    String nome = rs.getString("nome");
                    String especie = rs.getString("especie");
                    String dieta = rs.getString("dieta");
                    int idadeEstimada = rs.getInt("idadeEstimada");
                    int idade = rs.getInt("idadeDino");
                    String status = rs.getString("statusCercado");
                    System.out.println("\nID: " + id + "\n Nome: " + nome + "\n Especie: " + especie + "\n Dieta: " + dieta + "\n Idade Estimada: " + idadeEstimada + "M" + "\n Idade Atual: " + idade + "\n Status: " + status);
                    System.out.println("\n----------------------------------");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar dinossauros: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                //if (conexao != null) fecharConexao(conexao);
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos após consulta: " + e.getMessage());
            }
        }
        return Dinossauros;
    }

    public static void updateDinossauro(Dino dino) {
        String sql = "UPDATE dino SET nome = ?, especie = ?, dieta = ?, idadeEstimada = ?, idadeDino = ?, statusCercado = ? WHERE id_dinossauro = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;

        System.out.println("--- UPDATING ENTITIES ---");
        try {
            //conexao = ConexaoPark.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1, dino.getNome());
                stmt.setString(2, dino.getEspecie());
                stmt.setString(3, dino.getDieta());
                stmt.setString(4, dino.getIdadeEstimada());
                stmt.setString(5, dino.getIdadeAtual());
                stmt.setString(6, dino.getStatus());
                stmt.setInt(7, dino.getId());
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("\nDinossauro com ID:" + dino.getId() + " atualizado com sucesso.");
                } else {
                    System.out.println("\nNenhuma entidade encontrada com ID" + dino.getId() + " para atualizar.");
                }

            }
        } catch (SQLException e) {
            System.out.println("\nErro ao atualizar entidade no postgreSQL: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conexao != null) ConexaoPost.fecharConexao(conexao);
            } catch (SQLException e) {
                System.out.println("Erro ao consultar banco: " + e.getMessage());
            }
        }
    }

    public List<Dino> buscarPorNome(String nomeBusca) {
        List<Dino> dinossauros = new ArrayList<>();
        String sql = "SELECT id_dinossauro, nome, especie, dieta, idadeEstimada, idadeDino, statusCercado FROM dinossauro WHERE nome ILIKE ? ORDER BY nome";
        try (Connection conn = ConexaoPost.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nomeBusca + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Dino dinossauro = new Dino(
                            rs.getInt("id_dinossauro"),
                            rs.getString("nome"),
                            rs.getString("especie"),
                            rs.getString("dieta"),
                            rs.getString("idadeEstimada"),
                            rs.getString("idadeDino"),
                            rs.getString("statusCercado")
                    );
                    dinossauros.add(dinossauro);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar dinossauros por nome: " + e.getMessage());
        }
        return dinossauros;
    }

    public void remover(int idDinossauro) {
        String sql = "DELETE FROM dinossauros WHERE id_dinossauro = ?";
        try (Connection conn = ConexaoPost.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idDinossauro);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Dinossauro removido com ID: " + idDinossauro);
            } else {
                System.out.println("Nenhum dinossauro encontrado para remoção com ID: " + idDinossauro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao remover dinossauro: " + e.getMessage());
        }
    }
}
