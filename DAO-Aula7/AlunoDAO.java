package Model.Dao;

import Conexao.ConexaoPostgresDB;
import Model.Aluno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AlunoDAO {
    public void inserir(Aluno aluno) {
        String sql = "INSERT INTO aluno (nome,idade,telefone) VALUES (?,?,?)";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getIdade());
            stmt.setString(3, aluno.getTelefone());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    aluno.setId(rs.getInt(1));
                }
            }
            System.out.println("Aluno Inserido: " + aluno.getNome());
        } catch (SQLException e) {
            System.err.println("Erro ao inserir Aluno: " + e.getMessage());
            // Exemplo de tratamento para nome/idades duplicados se houver UNIQUE constraint
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                System.err.println("Erro: Alunos com nomes semelhantes já cadastrado.");
            }
        }
    }

    public List<Aluno> listarTodosAlunos() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM aluno ORDER BY id_aluno";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Aluno aluno = new Aluno(
                        rs.getInt("id_aluno"),
                        rs.getString("nome"),
                        rs.getString("idade"),
                        rs.getString("telefone")
                );

                alunos.add(aluno);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar alunos: " + e.getMessage());
        }
        return alunos;
    }

    public Aluno buscarPorId(int idAluno) {
        String sql = "SELECT id_aluno,nome,idade,telefone FROM aluno WHERE id_aluno = ? ";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Aluno(
                            rs.getInt("id_aluno"),
                            rs.getString("nome"),
                            rs.getString("idade"),
                            rs.getString("telefone")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar aluno por ID: " + e.getMessage());
        }
        return null;
    }
    public List<Aluno> buscarPorNome(String nomeBusca) {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT id_aluno,nome,idade,telefone FROM aluno WHERE nome ILIKE ? ORDER BY nome";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nomeBusca + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Aluno aluno = new Aluno(
                            rs.getInt("id_aluno"),
                            rs.getString("nome"),
                            rs.getString("idade"),
                            rs.getString("telefone")
                    );
                   alunos.add(aluno);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar alunos por nome: " + e.getMessage());
        }
        return alunos;
    }

    public void remover(int idAluno) {
        String sql = "DELETE FROM aluno WHERE id_aluno = ?";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Aluno removido com ID: " + idAluno);
            } else {
                System.out.println("Nenhum aluno encontrado para remoção com ID: " + idAluno);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao remover aluno: " + e.getMessage());
        }
    }


    public void setAluno(Aluno aluno) {
        String sql = "INSERT INTO aluno (nome,idade,telefone) VALUES (?,?,?)";
        Connection conexao = null;
        PreparedStatement stmt = null;

        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1, aluno.getNome());
                stmt.setString(2, aluno.getIdade());
                stmt.setString(3, aluno.getTelefone());
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Aluno" + aluno.getNome() + "Inserido no BD com sucesso.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir aluno no PostgresSQL:" + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conexao != null) ConexaoPostgresDB.fecharConexao(conexao);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos após inserção:" + e.getMessage());

            }
        }
    }

    public List<Aluno> getAluno() { // Retorna uma lista de Alunos
        String sql = "SELECT id_aluno,nome,idade,telefone FROM aluno ORDER BY id_aluno";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Aluno> alunos = new ArrayList<>(); // Lista para armazenar os objetos ALuno
        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                rs = stmt.executeQuery();

                System.out.println("\n--- Alunos Cadastrados no BD ---");
                boolean encontrouAluno = false;
                while (rs.next()) {
                    int id = rs.getInt("id_aluno");
                    String nome = rs.getString("nome");
                    String idade = rs.getString("idade");
                    String telefone = rs.getString("telefone");
                    System.out.println("Id: " + id + ", Nome: " + nome + ", Idade: " + idade + ", Telefone: " + telefone);

                    Aluno aluno = new Aluno(id, nome, idade, telefone); // Usa o construtor completo
                    alunos.add(aluno); // Adiciona o objeto á lista
                }
                if (!encontrouAluno) {
                    System.out.println("Nenhum aluno encontrado.");
                }
                System.out.println("------------------------------\n");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar alunos no DB:" + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexao != null) ConexaoPostgresDB.fecharConexao(conexao);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos após consulta" + e.getMessage());
            }
        }
        return alunos;
    }

    public static void atualizarAluno(Aluno aluno) {
        String sql = "UPDATE aluno set nome = ?, idade = ?, telefone = ? WHERE id_aluno = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1, aluno.getNome());
                stmt.setString(2, aluno.getIdade());
                stmt.setString(3, aluno.getTelefone());
                stmt.setInt(4, aluno.getId());
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Aluno com ID" + aluno.getId() + "atualizado com sucesso!");
                } else {
                    System.out.println("Nenhum aluno encontrado com ID" + aluno.getId() + "para atualização");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar aluno no PostgresSQL:" + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conexao != null) ConexaoPostgresDB.fecharConexao(conexao);

            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos após atualização" + e.getMessage());
            }
        }
    }

    public void removeAluno(int idAluno) {
        String sql = " DELETE FROM aluno WHERE id_aluno = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;

        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, idAluno); // O ID do aluno que queremos remover
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Aluno com iD" + idAluno + " removido com sucesso!");
                } else {
                    System.out.println("Nenhum aluno encontrado com ID" + idAluno + "para remoção");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao remover aluno no PostgresSQL:" + e.getMessage());
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


