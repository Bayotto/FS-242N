package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoPark {
    private static final String URL = "jdbc:postgresql://localhost:5432/JurassicWorld";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    public static Connection conectar() {
        Connection conexao = null;
        try {
            conexao = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco: " + e.getMessage());
        }
        return conexao;
    }

    public static void fecharConexao(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
                System.out.println("Conexão com o banco de dados fechada!");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão com o banco: " + e.getMessage());
            }
        }
    }
}
