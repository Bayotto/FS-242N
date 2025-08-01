package Dao;

import Conexao.ConexaoPark;
import Model.dino;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Conexao.ConexaoPark.conectar;
import static Conexao.ConexaoPark.fecharConexao;

public class dinoDAO {
    //Metodo que cria um objeto da classe Dino com seus respectivos atributos
    public static void setDinossauro(dino dinossauro) {
        String sql = "INSERT INTO dinossauros (nome,especie,dieta,idade_estimada_anos,idade_do_dinossauro,status_cercado) VALUES (?,?,?,?,?,?)";
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1, dinossauro.getNome());
                stmt.setString(2, dinossauro.getEspecie());
                stmt.setString(3, dinossauro.getDieta());
                stmt.setInt(4, dinossauro.getIdadeEstimada());
                stmt.setInt(5, dinossauro.getIdadeAtual());
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
                if (conexao != null) fecharConexao(conexao);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos após inserção " + e.getMessage());
            }
        }
    }

    public static List<dino> getDinossauro() {
        String sql = "SELECT * FROM dinossauros ORDER BY id_dinossauro";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<dino> Dinossauros = new ArrayList<>();

        try {
            conexao = conectar();
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
                    int idadeEstimada = rs.getInt("idade_estimada_anos");
                    int idade = rs.getInt("idade_do_dinossauro");
                    String status = rs.getString("status_cercado");
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
                if (conexao != null) fecharConexao(conexao);
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos após consulta: " + e.getMessage());
            }
        }
        return Dinossauros;
    }

    public static List<dino> getDieta(String dieta) {
        String sql = "SELECT * FROM dinossauros WHERE dieta = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<dino> Dinossauros = new ArrayList<>();

        try {
            conexao = conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1, dieta);
                rs = stmt.executeQuery();
                System.out.println("\n--- Dinossauro(s) Cadastrados no BD ---");
                boolean encontrouDinossauro = false;
                while (rs.next()) {
                    encontrouDinossauro = true;
                    int id = rs.getInt("id_dinossauro");
                    String nome = rs.getString("nome");
                    String especie = rs.getString("especie");
                    int idadeEstimada = rs.getInt("idade_estimada_anos");
                    int idade = rs.getInt("idade_do_dinossauro");
                    String status = rs.getString("status_cercado");
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
                if (conexao != null) fecharConexao(conexao);
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos após consulta: " + e.getMessage());
            }
        }
        return Dinossauros;
    }

    public static List<dino> getStatus(String status) {
        String sql = "SELECT * FROM dinossauros WHERE status_cercado = ? ORDER BY nome";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<dino> Dinossauros = new ArrayList<>();

        try {
            conexao = conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1, status);
                rs = stmt.executeQuery();
                System.out.println("\n--- Dinossauro(s) Cadastrados no BD ---");
                boolean encontrouDinossauro = false;
                while (rs.next()) {
                    encontrouDinossauro = true;
                    int id = rs.getInt("id_dinossauro");
                    String nome = rs.getString("nome");
                    String especie = rs.getString("especie");
                    int idadeEstimada = rs.getInt("idade_estimada_anos");
                    int idade = rs.getInt("idade_do_dinossauro");
                    String dieta = rs.getString("dieta");
                    String statusBanco = rs.getString("status_cercado");
                    System.out.println("\nID: " + id + "\n Nome: " + nome + "\n Especie: " + especie + "\n Dieta: " + dieta + "\n Idade Estimada: " + idadeEstimada + "M" + "\n Idade Atual: " + idade + "\n Status: " + statusBanco);
                    System.out.println("\n----------------------------------");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar dinossauros: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexao != null) fecharConexao(conexao);
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos após consulta: " + e.getMessage());
            }
        }
        return Dinossauros;
    }

    public static void updateDinossauro(dino dino) {
        String sql = "UPDATE dinossauros SET nome = ?, especie = ?, dieta = ?, idade_estimada_anos = ?, idade_do_dinossauro = ?, status_cercado = ? WHERE id_dinossauro = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;

        System.out.println("--- UPDATING ENTITIES ---");
        try {
            conexao = ConexaoPark.conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1, dino.getNome());
                stmt.setString(2, dino.getEspecie());
                stmt.setString(3, dino.getDieta());
                stmt.setInt(4, dino.getIdadeEstimada());
                stmt.setInt(5, dino.getIdadeAtual());
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
                if (conexao != null) fecharConexao(conexao);
            } catch (SQLException e) {
                System.out.println("Erro ao consultar banco: " + e.getMessage());
            }
        }
    }

    public static void removerDinossauro(dino dinossauro, int id) {
        String sql = "DELETE FROM dinossauros WHERE id_dinossauro = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        System.out.println("--- Aniquilando Dinossauro(s) ---");
        try {
            conexao = conectar();
            if (conexao != null) {
                stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, id);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Dinossauro: ");
                    System.out.println("\n Nome: " + dinossauro.getNome() + "\n Especie: " + dinossauro.getEspecie() + "\n Dieta: " + dinossauro.getDieta() + "\n Idade Estimada: " + dinossauro.getIdadeEstimada() + "M" + "\n Idade Atual: " + dinossauro.getIdadeAtual() + "\n Status: " + dinossauro.getStatus());
                    System.out.println("\nAniquilado com sucesso!");
                } else {
                    System.out.println("Nenhum Dinossauro encontrado com ID " + dinossauro.getId());
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir o Dinossauro: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conexao != null) fecharConexao(conexao);
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexao: " + e.getMessage());
            }
        }
    }
}
