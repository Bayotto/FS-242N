package Model.Dao;

import Conexao.ConexaoPostgresDB;
import Model.Aluno;
import Model.Emprestimo;
import Model.Livro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    public void inserir(Livro livro) {
        String sql = "INSERT INTO livro (titulo,autor,genero,isbm) VALUES (?,?,?,?)";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getGenero());
            stmt.setString(4, livro.getIsbm());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    livro.setId_livro(rs.getInt(1));
                }
            }
            System.out.println("Livro Inserido: " + livro.getTitulo());
        } catch (SQLException e) {
            System.err.println("Erro ao inserir Livro: " + e.getMessage());
            // Exemplo de tratamento para nome/idades duplicados se houver UNIQUE constraint
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                System.err.println("Erro: Livros com nomes semelhantes já cadastrado.");
            }
        }
    }

    public List<Livro> listarTodosLivros() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livro ORDER BY id_livro";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Livro livro = new Livro(
                        rs.getInt("id_livro"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("genero"),
                        rs.getString("isbm")
                );

                livros.add(livro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar alunos: " + e.getMessage());
        }
        return livros;
    }

    public Livro buscarPorId(int idLivro) {
        String sql = "SELECT * FROM livro ORDER BY id_livro = ? ";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idLivro);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Livro(
                            rs.getInt("id_livro"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getString("genero"),
                            rs.getString("isbm")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar livro por ID: " + e.getMessage());
        }
        return null;
    }
    public List<Livro> buscarPorTitulo(String tituloBusca) {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT titulo,autor,genero,isbm FROM livro WHERE titulo ILIKE ? ORDER BY titulo";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + tituloBusca + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Livro livro = new Livro(
                            rs.getInt("id_livro"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getString("genero"),
                            rs.getString("isbm")
                    );
                    livros.add(livro);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar alunos por nome: " + e.getMessage());
        }
        return livros;
    }

    public void remover(int idLivro) {
        String sql = "DELETE FROM id_livro WHERE id_livro = ?";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idLivro);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Livro removido com ID: " + idLivro);
            } else {
                System.out.println("Nenhum livro encontrado para remoção com ID: " + idLivro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao remover livro: " + e.getMessage());
        }
    }

    public void setLivro(Livro livro) {
        String sql = "INSERT INTO livro (id_livro,titulo,autor,genero,isbm) VALUES (?,?,?,?,?)";
        Connection conexao = null;
        PreparedStatement stmt = null;

        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, livro.getId_livro());
                stmt.setString(2, livro.getTitulo());
                stmt.setString(3, livro.getAutor());
                stmt.setString(4, livro.getGenero());
                stmt.setString(5, livro.getIsbm());
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Livro " + livro.getTitulo() + "Inserido no BD com sucesso.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir livro no PostgresSQL:" + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conexao != null) ConexaoPostgresDB.fecharConexao(conexao);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos após inserção:" + e.getMessage());

            }
        }
    }

    public List<Livro> getLivro() { // Retorna uma lista de livro
        String sql = "SELECT id_livro,titulo,autor,genero,isbm FROM livro ORDER BY id_livro";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Livro> livros = new ArrayList<>(); // Lista para armazenar os objetos Livro
        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                rs = stmt.executeQuery();

                System.out.println("\n--- Livros Cadastrados no BD ---");
                boolean encontrouLivro = false;
                while (rs.next()) {
                    int id_livro = rs.getInt("id_livro");
                    String titulo = rs.getString("titulo");
                    String autor = rs.getString("autor");
                    String genero = rs.getString("genero");
                    String isbm = rs.getString("isbm");
                    System.out.println("ID do Livro: " + id_livro +  ", Titulo: " + titulo + ", Autor: " + autor + ", Genero: " + genero  + ", ISBM:" + isbm);

                    Livro livro = new Livro(id_livro,titulo,autor,genero,isbm); // Usa o construtor completo
                    livros.add(livro); // Adiciona o objeto á lista
                }
                if (!encontrouLivro) {
                    System.out.println("Nenhum livro encontrado.");
                }
                System.out.println("------------------------------\n");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar livro no DB:" + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexao != null) ConexaoPostgresDB.fecharConexao(conexao);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos após consulta" + e.getMessage());
            }
        }
        return livros;
    }

    public static void atualizarLivro(Livro livro){
        String sql = "UPDATE livro set  titulo = ?, autor = ?, genero = ?, isbm = ? WHERE id_livro = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = ConexaoPostgresDB.conectar();
            if(conexao != null){
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, livro.getId_livro());
                stmt.setString(2, livro.getTitulo());
                stmt.setString(3, livro.getAutor());
                stmt.setString(4, livro.getGenero());
                stmt.setString(5, livro.getIsbm());
                int linhasAfetadas = stmt.executeUpdate();
                if(linhasAfetadas > 0){
                    System.out.println("Livro com ID" + livro.getId_livro() + "atualizado com sucesso!");
                }else {
                    System.out.println("Nenhum livro encontrado com ID" +  livro.getId_livro() + "para atualização");
                }
            }
        }catch (SQLException e){
            System.err.println("Erro ao atualizar livro no PostgresSQL:" + e.getMessage());
        }finally {
            try{
                if(stmt != null) stmt.close();
                if(conexao != null) ConexaoPostgresDB.fecharConexao(conexao);

            }catch(SQLException e){
                System.err.println("Erro ao fechar recursos após atualização" + e.getMessage());
            }
        }
    }

    public  void removeLivro(int id_Livro){
        String sql = " DELETE FROM livro WHERE id_livro = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;

        try{
            conexao = ConexaoPostgresDB.conectar();
            if(conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, id_Livro); // O ID do livro que queremos remover
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Livro com iD" + id_Livro+ " removido com sucesso!");
                }else{
                    System.out.println("Nenhum livro encontrado com ID" + id_Livro + "para remoção");
                }
            }
        }catch(SQLException e){
            System.err.println("Erro ao remover o livro no PostgresSQL:" + e.getMessage());
        }finally {
            try{
                if(stmt != null) stmt.close();
                if(conexao != null) ConexaoPostgresDB.fecharConexao(conexao);
            }catch (SQLException e){
                System.err.println("Erro ao fechar recursos após remoção:" + e.getMessage());

            }
        }
    }
}
