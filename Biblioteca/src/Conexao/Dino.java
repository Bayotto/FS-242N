package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Dino {
    private static final String URL = "jdbc:postgresql://localhost:5432/dino";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "root";



    public static Connection conectar(){

        Connection conexao = null;

        try{
            conexao = DriverManager.getConnection(URL,USUARIO,SENHA);
            System.out.println("Conexão com o banco de dados estabelecida com sucesso");
        }catch(SQLException error){
            System.err.println("Erro ao conectar ao bd:" + error.getMessage());

        } return conexao;
    }
    public static void fecharConexao(Connection conexao){
        if(conexao != null){
            try{
                conexao.close();
                System.out.println("Conexao com bd fechada!");

            }catch (SQLException e){
                System.err.println("Erro ao fechar a conexão com bd:" + e.getMessage());
            }

        }
    }

    public static void setDino(int idDino, String nome, String especie, String dieta, int idadeEstimada, int idadeDino, String statusCercado){
        String sql = "INSERT INTO aluno (idDino,nome,especie,dieta,idadeEstimada,idadeDino,statusCercado) VALUES (?,?,?,?,?,?,?)";
        Connection conexao = null;
        PreparedStatement stmt = null;

        try{
            conexao = conectar();
            if(conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1,idDino);
                stmt.setString(2, nome);
                stmt.setString(3, especie);
                stmt.setString(4, dieta);
                stmt.setInt(5,idadeEstimada);
                stmt.setInt(6,idadeDino);
                stmt.setString(7,statusCercado);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Dinossauro" + nome + "Inserido no BD com sucesso.");
                }
            }
        }catch(SQLException e){
            System.err.println("Erro ao inserir aluno no PostgresSQL:" + e.getMessage());
        }finally {
            try{
                if(stmt != null) stmt.close();
                if(conexao != null) fecharConexao(conexao);
            }catch (SQLException e){
                System.err.println("Erro ao fechar recursos após inserção:" + e.getMessage());

            }
        }
    }

    public static void getAluno(){
        String sql = "SELECT idDino,nome,especie,dieta,idadeEstimada,idadeDino,statusCercado FROM dino ORDER BY idDino";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            conexao = conectar();
            if(conexao != null) {
                stmt = conexao.prepareStatement(sql);
                rs = stmt.executeQuery();
                System.out.println("\n--- Dinossauros Cadastrados no BD ---");
                boolean encontrouAluno = false;
                while (rs.next()) {
                    int idDino = rs.getInt("idDino");
                    String nome = rs.getString("nome");
                    String especie = rs.getString("especie");
                    String dieta = rs.getString("dieta");
                    int idadeEstimada = rs.getInt("idadeEstimada");
                    int idadeDino = rs.getInt("idadeDino");
                    String statusCercado = rs.getString("statusCercado");
                    System.out.println("ID do DINO:" + idDino + ", Nome:" + nome + ", Especie:" + especie + ",Dieta:" + dieta + "Idade Estimada do Dino:" + idadeEstimada + "Idade do Dino:" + idadeDino + "Status Cercado:" + statusCercado);
                }
                if(!encontrouAluno){
                    System.out.println("Nenhum aluno encontrado.");
                }
                System.out.println("------------------------------\n");
            }
        }catch(SQLException e){System.err.println("Erro ao consultar alunos no DB:" + e.getMessage());
        }finally {
            try{
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(conexao != null) fecharConexao(conexao);
            }catch (SQLException e){System.err.println("Erro ao fechar recursos após consulta" + e.getMessage());
            }
        }
    }
    public static void atualizarDino(int idDino, String novoNomeDino,int novaEspecieDino,String novaDietaDino ){
        String sql = "UPDATE dino set nome = ?, especie = ?, dieta = ? WHERE idDino = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = conectar();
            if(conexao != null){
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1,);
                stmt.setInt(2,novaIdade);
                stmt.setString(3,novoTelefone);
                stmt.setInt(4,idAluno);
                int linhasAfetadas = stmt.executeUpdate();
                if(linhasAfetadas > 0){
                    System.out.println("Aluno com ID" + idAluno + "atualizado com sucesso!");
                }else {
                    System.out.println("Nenhum aluno encontrado com ID" + idAluno + "para atualização");
                }
            }
        }catch (SQLException e){
            System.err.println("Erro ao atualizar aluno no PostgresSQL:" + e.getMessage());
        }finally {
            try{
                if(stmt != null) stmt.close();
                if(conexao != null) fecharConexao(conexao);

            }catch(SQLException e){
                System.err.println("Erro ao fechar recursos após atualização" + e.getMessage());
            }
        }
    }

    public static void removeAluno(int idAluno){
        String sql = " DELETE FROM aluno WHERE id_aluno = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;

        try{
            conexao = conectar();
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
                if(conexao != null) fecharConexao(conexao);
            }catch (SQLException e){
                System.err.println("Erro ao fechar recursos após remoção:" + e.getMessage());

            }
        }
    }



    public static void main(String[] args){
        Connection testeConexao = ConexaoPostgresDB.conectar();
        System.out.println("\n--- Realizando Inserções");
        setAluno("Larissa",29,"40028922");
        setAluno("Gabs",19,"09349094");
        System.out.println("\n--- Realizando Consulta---");
        getAluno();

        System.out.println("\n--- Atualizando---");
        atualizarAluno(3,"Jorge",29,"5140028922");

        System.out.println("\n--- Removendo---");
        removeAluno(4);
        removeAluno(5);
        removeAluno(6);
        removeAluno(7);
        removeAluno(8);
        removeAluno(9);
        removeAluno(10);
        removeAluno(11);
        removeAluno(12);
        if(testeConexao != null){
            ConexaoPostgresDB.fecharConexao(testeConexao);

        }

    }

}
