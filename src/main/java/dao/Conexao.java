/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author laispaivaportela
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection conectar() {
        Connection connection = null;
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);

            String url = "jdbc:mysql://localhost:3306/flashcards";
            String user = "root";
            String senha = "me877757xl25M3355348S151.05458E@";

            connection = DriverManager.getConnection(url, user, senha);

            if (connection != null) {
                System.out.println("Status: Conectado!");
            } else {
                System.out.println("Status: Não conectado!");
            }
            return connection;
        } catch (ClassNotFoundException e) {
            System.out.println("O driver não foi encontrado.");
            e.printStackTrace(); //ajuda a depurar
            return null;
        } catch (SQLException e) {
            System.out.println("Não foi possivel conectar...");
            e.printStackTrace(); //mostra o erro exato
            return null;
        }
    }
}
