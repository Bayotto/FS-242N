package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Dino {
    private static final String URL = "jdbc:postgresql://localhost:5432/JurassicWorld";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "root";

    public static Connection conectar() {
        Connection conexao = null;//Inicializa a conexao como nulaa
        try {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.err.println("Conexao com o db Completa");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o bd: " + e.getMessage());
        }
        return conexao;// retorna a conexao, pode ser nula em caso de erro
    }

    public static void fecharConexao(Connection conexao) {
        if (conexao != null) {
            //Verifica se a conexao nao e nula antes de tentar fechar:
            try {
                conexao.close();
                System.err.println("Conexao com o db encerrado ");
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static void setDino(int id_dinossauro, String nome, String especie, String dieta, String idadeEstimada, int idadeDino, String statusCercado) {
        String sql = "INSERT INTO dino (id_dinossauro, nome, especie, dieta, idadeEstimada, idadeDino, statusCercado) VALUES (?,?,?,?,?,?,?)";

        Connection conexao = null;
        PreparedStatement stmt = null;


        try {
            conexao = conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, id_dinossauro);
                stmt.setString(2, nome);
                stmt.setString(3, especie);
                stmt.setString(4, dieta);
                stmt.setString(5, idadeEstimada);
                stmt.setInt(6, idadeDino);
                stmt.setString(7, statusCercado);
                int linhasAfetadas = stmt.executeUpdate();//executa o INSERT
                if (linhasAfetadas > 0) {
                    System.out.println("dino: " + nome + " inserido no BD com sucesso!");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro inesperado ao inserir dino no postgress " + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conexao != null) {
                    fecharConexao(conexao);
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos apos insercao: " + e.getMessage());
            }
        }
    }

    public static void getDino() {
        String sql = "SELECT id_dinossauro,nome,especie,dieta,idadeEstimada,idadeDino, statusCercado from dino ORDER BY id_dinossauro";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;//Objeto para aramazenar os resultados da consulta

        try {
            conexao = conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                rs = stmt.executeQuery();
                System.out.println("\n ---DINOS CADASTRADOS NO BD ---");
                boolean encontrouAluno = false;
                while (rs.next()) {
                    encontrouAluno = true;
                    int id_dinossauro = rs.getInt("id_dinossauro");
                    String nome = rs.getString("nome");
                    String especie = rs.getString("especie");
                    String dieta = rs.getString("dieta");
                    String idadeEstimada = rs.getString("idadeEstimada");
                    int idadeDino = rs.getInt("idadeDino");
                    String statusCercado = rs.getString("statusCercado");
                    System.out.println("Id: " + id_dinossauro + ", Nome: " + nome + ", especie: " + especie + ", dieta: " + dieta + ", idadeEstimada: " + idadeEstimada + ", idadeDino:" + idadeDino + ", statusCercado: " + statusCercado);
                }
                if (!encontrouAluno) {
                    System.out.println("Nenhum dino encontrado.");
                }
                System.out.println("-------------------------------------\n");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conexao != null) {
                    fecharConexao(conexao);
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos apos consulta: " + e.getMessage());
            }
        }
    }

    public static void deleteDino(int id_dinossauro){
        String sql="DELETE FROM dino where id_dinossauro=?";
        Connection conexao=null;
        PreparedStatement stmt=null;

        try{
            conexao=conectar();
            if(conexao!=null){
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1,id_dinossauro);

                int linhasAfetadas=stmt.executeUpdate();
                if(linhasAfetadas>0){
                    System.out.println("dino com ID " + id_dinossauro + " removido com sucesso!");
                }else{
                    System.out.println("Nenhum dino encotrado com ID "+id_dinossauro+" para remoção.");
                }
            }
        } catch(SQLException e){
            System.err.println("Erro ao remover dino no PostgreSQL: "+e.getMessage());
        }finally {
            try{
                if(stmt!=null) stmt.close();
                if(conexao!=null)fecharConexao(conexao);
            }catch(SQLException e){
                System.err.println("Erro ao fechar recursos após atualização: "+e.getMessage());
            }
        }
    }
    public static void atualizarDino(int id_dinossauro, String novoNomeDino,String novaEspecieDino,String novaDietaDino ){
        String sql = "UPDATE dino set nome = ?, especie = ?, dieta = ? WHERE idDino = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = conectar();
            if(conexao != null){
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1,id_dinossauro);
                stmt.setString(2,novoNomeDino);
                stmt.setString(3,novaEspecieDino);
                stmt.setString(4,novaDietaDino);
                int linhasAfetadas = stmt.executeUpdate();
                if(linhasAfetadas > 0){
                    System.out.println("DINO com ID" + id_dinossauro + "atualizado com sucesso!");
                }else {
                    System.out.println("Nenhum dinosssauro encontrado com ID" + id_dinossauro + "para atualização");
                }
            }
        }catch (SQLException e){
            System.err.println("Erro ao atualizar dinossauro no PostgresSQL:" + e.getMessage());
        }finally {
            try{
                if(stmt != null) stmt.close();
                if(conexao != null) fecharConexao(conexao);

            }catch(SQLException e){
                System.err.println("Erro ao fechar recursos após atualização" + e.getMessage());
            }
        }
    }



    public static void main(String[] args){
        Connection testeConexao = ConexaoPostgresDB.conectar();
        System.out.println("\n--- Realizando Inserções");
        //setDino(1,"T-Rex","Tyrannosaurus rex","Carnívoro","  66 a 68 milhões de anos" ,30 ,"Seguro");
        //setDino(2,"Carnotauro","Carnotaurus sastrei","Carnívoro","72 a 69 milhões de anos",27,"Em laboratorio");
        System.out.println("\n--- Realizando Consulta---");
        getDino();

        System.out.println("\n--- Atualizando---");
        //atualizarDino(2, "Brontossauro", "Brontosaurus excelsus", "Herbivoro");

        System.out.println("\n--- Removendo---");
        deleteDino(1);
        if(testeConexao != null){
            ConexaoPostgresDB.fecharConexao(testeConexao);

        }

    }

}
