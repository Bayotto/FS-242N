package Dao;

import Conexao.ConexaoPostgresDB;
import Model.Aluno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AlunoDAO {
    public void setAluno(Aluno aluno) {
        String sql = "INSERT INTO aluno (nome,idade,telefone) VALUES (?,?,?)";
        Connection conexao = null;
        PreparedStatement stmt = null;

        try {
            conexao = ConexaoPostgresDB.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1, aluno.getNome());
                stmt.setInt(2, aluno.getIdade());
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
                    int idade = rs.getInt("idade");
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
    public void atualizarAluno(Aluno aluno){
        String sql = "UPDATE aluno set nome = ?, idade = ?, telefone = ? WHERE id_aluno = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = ConexaoPostgresDB.conectar();
            if(conexao != null){
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1,aluno.getNome());
                stmt.setInt(2,aluno.getIdade());
                stmt.setString(3,aluno.getTelefone());
                stmt.setInt(4,aluno.getId());
                int linhasAfetadas = stmt.executeUpdate();
                if(linhasAfetadas > 0){
                    System.out.println("Aluno com ID" + aluno.getId() + "atualizado com sucesso!");
                }else {
                    System.out.println("Nenhum aluno encontrado com ID" + aluno.getId() + "para atualização");
                }
            }
        }catch (SQLException e){
            System.err.println("Erro ao atualizar aluno no PostgresSQL:" + e.getMessage());
        }finally {
            try{
                if(stmt != null) stmt.close();
                if(conexao != null) ConexaoPostgresDB.fecharConexao(conexao);

            }catch(SQLException e){
                System.err.println("Erro ao fechar recursos após atualização" + e.getMessage());
            }
        }
    }
    public  void removeAluno(int idAluno){
        String sql = " DELETE FROM aluno WHERE id_aluno = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;

        try{
            conexao = ConexaoPostgresDB.conectar();
            if(conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, idAluno); // O ID do aluno que queremos remover
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Aluno com iD" + idAluno + " removido com sucesso!");
                }else{
                    System.out.println("Nenhum aluno encontrado com ID" + idAluno + "para remoção");
                }
            }
        }catch(SQLException e){
            System.err.println("Erro ao remover aluno no PostgresSQL:" + e.getMessage());
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


