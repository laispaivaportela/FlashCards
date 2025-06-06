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
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import model.Pasta;

public class PastaDAO {

    private static final List<Pasta> pastas = new ArrayList<>(); //lista que armazena as pastas

    public static void inserirPasta(String nomePasta, String descricao) { //insere uma nova pasta ao banco de dados e atualiza a lista local
        int id = gerarNovoId();
        int quantidadeCards = 0; // inicialmente zero

        String sql = "INSERT INTO pastas (idPasta, nomePasta, descricao, quantidadeCards) VALUES (?, ?, ?, ?)";
        try (
            Connection connection = Conexao.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, id);
            statement.setString(2, nomePasta);
            statement.setString(3, descricao);
            statement.setInt(4, quantidadeCards);
            statement.executeUpdate();

            listarPastas(); // atualiza a lista local
        } catch (SQLException ex) {
            Logger.getLogger(PastaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void atualizarPasta(int idPasta, String nomePasta, String descricao, int quantidadeCards) throws SQLException { //atualiza os dadosd e uma pasta no banco de dados
        String sql = "UPDATE pastas SET nomePasta = ?, descricao = ?, quantidadeCards = ? WHERE idPasta = ?";
        try (
            Connection connection = Conexao.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, nomePasta);
            statement.setString(2, descricao);
            statement.setInt(3, quantidadeCards);
            statement.setInt(4, idPasta);

            statement.executeUpdate();
            listarPastas(); // atualiza a lista
        } catch (SQLException ex) {
            Logger.getLogger(PastaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int gerarNovoId() { //gera id para pasta
        int maiorId = 0;
        List<Pasta> lista = listaPastas();
        for (Pasta pasta : lista) {
            if (pasta.getIdPasta() > maiorId) {
                maiorId = pasta.getIdPasta();
            }
        }
        return maiorId + 1;
    }

    public static List<Pasta> listaPastas() { //devolve a lista de pastas atualizada
        String sql = "SELECT * FROM pastas";
        pastas.clear();

        try (
            Connection connection = Conexao.conectar();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
        ) {
            while (resultSet.next()) {
                int idPasta = resultSet.getInt("idPasta");
                String nomePasta = resultSet.getString("nomePasta");
                String descricao = resultSet.getString("descricao");
                int quantidadeCards = resultSet.getInt("quantidadeCards");
                Pasta pasta = new Pasta();
                pasta.setIdPasta(idPasta);
                pasta.setNomePasta(nomePasta);
                pasta.setDescricao(descricao);
                pastas.add(pasta);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar pastas: " + e.getMessage());
        }

        return pastas;
    }

    public static Pasta buscarPorId(int idPesquisado) { //procura pasta a partir do seu id
        Pasta pasta = new Pasta();
        try (
            Connection connection = Conexao.conectar();
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM pastas WHERE idPasta = " + idPesquisado);
        ) {
            if (res.next()) {
                pasta.setIdPasta(res.getInt("idPasta"));
                pasta.setNomePasta(res.getString("nomePasta"));
                pasta.setDescricao(res.getString("descricao"));
                return pasta;
            }
        } catch (SQLException erro) {
            System.out.println("Erro ao buscar pasta por ID: " + erro.getMessage());
        }
        return null;
    }
    
   public static Pasta buscarPorNome(String nomePesquisado) { //procura pasta a partir do seu nome
    String sql = "SELECT * FROM pastas WHERE nomePasta = ?";
    try (
        Connection connection = Conexao.conectar();
        PreparedStatement stmt = connection.prepareStatement(sql);
    ) {
        stmt.setString(1, nomePesquisado);
        ResultSet res = stmt.executeQuery();

        if (res.next()) {
            Pasta pasta = new Pasta();
            pasta.setIdPasta(res.getInt("idPasta"));
            pasta.setNomePasta(res.getString("nomePasta"));
            pasta.setDescricao(res.getString("descricao"));
            return pasta;
        }
    } catch (SQLException erro) {
        System.out.println("Erro ao buscar pasta por nome: " + erro.getMessage());
    }
    return null;
}

    
    public static ResultSet listarPastas() { //lista as pastas atualizadas através do result set
        atualizarTodasQuantidadesDeCards(); //atualiza a quantidade de cards antes de montar a tabela 
        String sql = "SELECT * FROM pastas";
        try {
            Connection connection = Conexao.conectar();
            PreparedStatement stmt = connection.prepareStatement(sql);
            return stmt.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static DefaultTableModel montarTabela() throws SQLException { //monta tabela com todas as pastas
        ResultSet rs = listarPastas();
        DefaultTableModel model = new DefaultTableModel();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        // adiciona os nomes das colunas
        for (int column = 1; column <= columnCount; column++) {
            model.addColumn(metaData.getColumnName(column));
        }
        // adiciona as linhas
        while (rs.next()) {
            Object[] row = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                row[i] = rs.getObject(i + 1);
            }
            model.addRow(row);
        }
        return model;
    }
    
    public static List<String> obterNomesDasPastas() { //lista com os nomes das pastas para os combobox
    List<String> nomes = new ArrayList<>();
    //nomes.add("Geral"); // opção padrão

    String sql = "SELECT nomePasta FROM pastas";
    try (
        Connection connection = Conexao.conectar();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
    ) {
        while (resultSet.next()) {
            nomes.add(resultSet.getString("nomePasta"));
        }
    } catch (SQLException e) {
        System.out.println("Erro ao buscar nomes das pastas: " + e.getMessage());
    }

    return nomes;
}
    
    
public static void incrementarQuantidadeCards(int idPasta) { //incrementa a quantidade de cards de uma pasta específica em +1 (quando um novo elemento for adicionado)
    String sql = "UPDATE pastas SET quantidadeCards = quantidadeCards + 1 WHERE idPasta = ?";
    try (
        Connection connection = Conexao.conectar();
        PreparedStatement statement = connection.prepareStatement(sql);
    ) {
        statement.setInt(1, idPasta);
        statement.executeUpdate();
        listarPastas(); // Atualiza lista local
    } catch (SQLException ex) {
        Logger.getLogger(PastaDAO.class.getName()).log(Level.SEVERE, "Erro ao incrementar quantidade de cards", ex);
    }
}

    public static void atualizarQuantidadeCardsPorPasta(int idPasta) {  //atualiza a quantidade de cards de uma pasta específica (quando acontecer qualquer variação na quantidade de cards)
        String countSql = "SELECT COUNT(*) FROM cards WHERE idPasta = ?";
        String updateSql = "UPDATE pastas SET quantidadeCards = ? WHERE idPasta = ?";

        try (
            Connection connection = Conexao.conectar();
            PreparedStatement countStmt = connection.prepareStatement(countSql);
            PreparedStatement updateStmt = connection.prepareStatement(updateSql);
        ) {
            countStmt.setInt(1, idPasta);
            ResultSet rs = countStmt.executeQuery();
            if (rs.next()) {
                int quantidade = rs.getInt(1);
                updateStmt.setInt(1, quantidade);
                updateStmt.setInt(2, idPasta);
                updateStmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PastaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void atualizarTodasQuantidadesDeCards() {   //atualiza a quantidade de cards para todas as pastas
        String sql = "SELECT idPasta FROM pastas";
        try (
            Connection connection = Conexao.conectar();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        ) {
            while (rs.next()) {
                int idPasta = rs.getInt("idPasta");
                atualizarQuantidadeCardsPorPasta(idPasta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PastaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

}

