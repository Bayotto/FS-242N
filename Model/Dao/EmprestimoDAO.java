package Model.Dao;

import Conexao.ConexaoPostgresDB;
import Model.Aluno;
import Model.Emprestimo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {


    public static void inserir(Emprestimo emprestimo) {
        String sql = "INSERT INTO emprestimo (id_aluno,id_livro,data_emprestimo,data_devolucao) VALUES (?,?,?,?)";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1,emprestimo.getId_aluno());
            stmt.setInt(2,emprestimo.getId_livro());
            stmt.setString(3, emprestimo.getData_emprestimo());
            stmt.setString(4, emprestimo.getData_devolucao());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    emprestimo.setId_emprestimo(rs.getInt(1));
                }
            }
            System.out.println("Emprestimo Inserido: " + emprestimo.getId_emprestimo());
        } catch (SQLException e) {
            System.err.println("Erro ao inserir Emprestimo: " + e.getMessage());
            // Exemplo de tratamento para nome/idades duplicados se houver UNIQUE constraint
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                System.err.println("Erro: Emprestimos com ID semelhantes já cadastrado.");
            }
        }
    }

    public List<Emprestimo> listarTodosEmprestimos() {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT id_emprestimo,id_aluno,id_livro,data_emprestimo,data_devolucao FROM emprestimo ORDER BY id_emprestimo";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Emprestimo emprestimo = new Emprestimo(
                        rs.getInt("id_emprestimo"),
                        rs.getInt("id_aluno"),
                        rs.getInt("id_livro"),
                        rs.getString("data_emprestimo"),
                        rs.getString("data_devolucao")
                );

                emprestimos.add(emprestimo);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar emprestimos: " + e.getMessage());
        }
        return emprestimos;
    }

    public Emprestimo buscarPorId(int idEmprestimo) {
        String sql = "SELECT id_aluno,id_livro,data_emprestimo,data_devolucao FROM emprestimo WHERE id_emprestimo = ? ";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEmprestimo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Emprestimo(
                            rs.getInt("id_aluno"),
                            rs.getInt("id_livro"),
                            rs.getString("data_emprestimo"),
                            rs.getString("data_devolucao")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar Emprestimo por ID: " + e.getMessage());
        }
        return null;
    }

    public List<Emprestimo> buscarPorNome(String nomeBusca) {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimo, aluno WHERE aluno.id_aluno = emprestimo.id_aluno AND aluno.nome = ?";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,  nomeBusca);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Emprestimo emprestimo = new Emprestimo(
                            rs.getInt("id_emprestimo"),
                            rs.getInt("id_aluno"),
                            rs.getInt("id_livro"),
                            rs.getString("data_emprestimo"),
                            rs.getString("data_devolucao")

                    );
                    emprestimos.add(emprestimo);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar alunos por nome: " + e.getMessage());
        }
        return emprestimos;
    }

    public void remover(int idEmprestimo) {
        String sql = "DELETE FROM emprestimo WHERE id_emprestimo = ?";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEmprestimo);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Emprestimo removido com ID: " + idEmprestimo);
            } else {
                System.out.println("Nenhum emprestimo encontrado para remoção com ID: " + idEmprestimo);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao remover emprestimo: " + e.getMessage());
        }
    }


    public void setEmprestimo(Emprestimo emprestimo) {
        String sql = "INSERT INTO emprestimo (id_aluno,id_livro,data_emprestimo,data_devolucao) VALUES (?,?,?,?)";
        Connection conexao = null;
        PreparedStatement stmt = null;

        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, emprestimo.getId_aluno());
                stmt.setInt(2, emprestimo.getId_livro());
                stmt.setString(3, emprestimo.getData_emprestimo());
                stmt.setString(4, emprestimo.getData_devolucao());
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Emprestimo" + emprestimo.getId_emprestimo() + "Inserido no BD com sucesso.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir emprestimo no PostgresSQL:" + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conexao != null) ConexaoPostgresDB.fecharConexao(conexao);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos após inserção:" + e.getMessage());

            }
        }
    }

    public List<Emprestimo> getEmprestimo() { // Retorna uma lista de Emprestimos
        String sql = "SELECT id_emprestimo,id_aluno,id_livro,data_emprestimo,data_devolucao FROM emprestimo ORDER BY id_emprestimo";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Emprestimo> emprestimos = new ArrayList<>(); // Lista para armazenar os objetos Emprestimo
        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                rs = stmt.executeQuery();

                System.out.println("\n--- Emprestimos Cadastrados no BD ---");
                boolean encontrouEmprestimo = false;
                while (rs.next()) {
                    int id_emprestimo = rs.getInt("id_emprestimo");
                    int id_aluno = rs.getInt("id_aluno");
                    int id_livro = rs.getInt("id_livro");
                    String data_emprestimo = rs.getString("data_emprestimo");
                    String data_devolucao = rs.getString("data_devolucao");
                    System.out.println("Id do Emprestimo: " + id_emprestimo + ", ID do Aluno: " + id_aluno + ", ID do Livro: " + id_livro + ", Data de Emprestimo: " + data_emprestimo + ", Data de Devolucao:" + data_devolucao);

                    Emprestimo emprestimo = new Emprestimo(id_emprestimo, id_aluno, id_livro, data_emprestimo, data_devolucao); // Usa o construtor completo
                    emprestimos.add(emprestimo); // Adiciona o objeto á lista
                }
                if (!encontrouEmprestimo) {
                    System.out.println("Nenhum emprestimo encontrado.");
                }
                System.out.println("------------------------------\n");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar emprestimo no DB:" + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexao != null) ConexaoPostgresDB.fecharConexao(conexao);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos após consulta" + e.getMessage());
            }
        }
        return emprestimos;
    }

    public static void atualizarEmprestimo(Emprestimo emprestimo) {
        String sql = "UPDATE emprestimo set id_aluno = ?, id_livro = ? WHERE id_emprestimo = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, emprestimo.getId_emprestimo());
                stmt.setInt(2, emprestimo.getId_aluno());
                stmt.setInt(3, emprestimo.getId_livro());
                stmt.setString(4, emprestimo.getData_emprestimo());
                stmt.setString(5, emprestimo.getData_devolucao());
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Emprestimo com ID" + emprestimo.getId_emprestimo() + "atualizado com sucesso!");
                } else {
                    System.out.println("Nenhum emprestimo encontrado com ID" + emprestimo.getId_emprestimo() + "para atualização");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar emprestimo no PostgresSQL:" + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conexao != null) ConexaoPostgresDB.fecharConexao(conexao);

            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos após atualização" + e.getMessage());
            }
        }
    }

    public void removeEmprestimo(int id_emprestimo) {
        String sql = " DELETE FROM emprestimo WHERE id_emprestimo = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;

        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, id_emprestimo); // O ID do emprestimo que queremos remover
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Emprestimo com iD" + id_emprestimo + " removido com sucesso!");
                } else {
                    System.out.println("Nenhum emprestimo encontrado com ID" + id_emprestimo + "para remoção");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao remover o emprestimo no PostgresSQL:" + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conexao != null) ConexaoPostgresDB.fecharConexao(conexao);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos após remoção:" + e.getMessage());

            }
        }
    }
}
