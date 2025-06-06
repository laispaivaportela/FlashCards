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
import model.Card;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import model.Pasta;

public class CardsDAO { 

    private static final List<Card> cards = new ArrayList<>();
    private static final List<Integer> idRepetidos = new ArrayList<>();
    private static final List<Integer> idLista = new ArrayList<>();

    //adiciona card à tabela e à alguma pasta 
    public static void inserirCard(String frente, String verso, Pasta pasta) { 
        String nomePasta = pasta.getNomePasta();
        int id = gerarNovoId();
        String sql = "INSERT INTO cards (id, frente, verso, pasta) VALUES (?, ?, ?, ?)";
        try (
                Connection connection = Conexao.conectar(); PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, id);
            statement.setString(2, frente);
            statement.setString(3, verso);
            statement.setString(4, nomePasta);
            statement.executeUpdate();

            listaCards(); //atualiza a lista

        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     //atualiza o card da tabela
    public static void atualizaCard(int id, String frente, String verso, Pasta pasta) {
        String nomePasta = pasta.getNomePasta();
        String sql = "UPDATE cards SET frente = ?, verso = ?, pasta = ? WHERE id = ?";
        try (
                Connection connection = Conexao.conectar(); PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setString(1, frente);
            statement.setString(2, verso);
            statement.setString(3, nomePasta); 
            statement.setInt(4, id);        
            statement.executeUpdate();

            listaCards(); // atualiza a lista
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //gera um novo id para um card novo
    public static int gerarNovoId() {
        int maiorId = 0;
        List<Card> listaCards = listaCards();
        for (Card card : listaCards) {
            if (card.getId() > maiorId) {
                maiorId = card.getId();
            }
        }
        return maiorId + 1;
    }

    //lista com todos os cards
    public static List<Card> listaCards() { //pega todos os cards que tão no banco de dados e armazena na lista
        String sql = "SELECT * FROM cards";
        cards.clear(); 
        
        try (Connection connection = Conexao.conectar(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String frente = resultSet.getString("frente");
                String verso = resultSet.getString("verso");
                String nomePasta = resultSet.getString("pasta");
                Pasta pasta = PastaDAO.buscarPorNome(nomePasta);
                cards.add(new Card(id, frente, verso, pasta));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar elementos: " + e.getMessage());
        }

        return cards;
    }

    //metodo para devolver o card a partir  do id
    public static Card buscarPorId(int idPesquisado) {
        Connection connection = Conexao.conectar();
        Card card = new Card();
        card.setId(idPesquisado);
        try {
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM cards WHERE id = " + idPesquisado);
            if (res.next()) {
                card.setId(res.getInt("id"));
                card.setFrente(res.getString("frente"));
                card.setVerso(res.getString("verso"));
                return card;
            } else {
                return null;
            }
        } catch (SQLException erro) {
            System.out.println("Erro:" + erro);
        }
        return null;
    }

    //método para organizar os ids
    public static void idOrganiza() {
        idLista.clear();
        for (Card card : cards) {
            idLista.add(card.getId());
        }
    }

    //devolve um id escolhido aleatoriamente
    public static int idAleatorio() {
        //escolhe um id aleatório
        Random random = new Random();
        int idAleatorio = -1;
        int tamanho = idLista.size();
        if (tamanho <= 0) {
            return -1;
        } else {
            int indice = random.nextInt(idLista.size());
            idAleatorio = idLista.get(indice);
            idLista.remove(indice);
            return idAleatorio;
        }
    }
    //devolve um card aleatório
    public static Card cardAleatorio() {
        int idAleatorio = idAleatorio();
        if (idAleatorio < 0) {
            return null;
        } else {
            Card cardAleatorio = buscarPorId(idAleatorio);
            return cardAleatorio;
        }
    }

    //lista cards com resultset
    public static ResultSet listarCards() {
        String sql = "SELECT * FROM cards";
        try {
            Connection connection = Conexao.conectar();
            PreparedStatement stmt = connection.prepareStatement(sql);
            return stmt.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    //monta uma tabela a partir do retorno do resultset
    public static DefaultTableModel montarTabela() throws SQLException {
        ResultSet rs = listarCards();
        DefaultTableModel model = new DefaultTableModel();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        //adiciona os nomes das colunas
        for (int column = 1; column <= columnCount; column++) {
            model.addColumn(metaData.getColumnName(column));
        }
        //adiciona as linhas
        while (rs.next()) {
            Object[] row = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                row[i] = rs.getObject(i + 1);
            }
            model.addRow(row);
        }

        return model;
    }
    
    //verifica e adiciona à uma lista cards que estão numa pasta específica
    public static List<Card> cardsNaPasta(Pasta pasta) throws SQLException {
    List<Card> cardPasta = new ArrayList<>();
    List<Card> listaCards = listaCards();
    for(Card card : listaCards){
        if(card.getPasta().equals(pasta)){
            cardPasta.add(card);
        }
    }
    return cardPasta;
}
    
//emite model para table de cards por pasta
 public static DefaultTableModel cardPastaTable(List<Card> cardPasta) {
    String[] colunas = {"Id", "Frente", "Verso", "Pasta"};
    DefaultTableModel model = new DefaultTableModel(colunas, 0); // 0 = número de linhas inicial

    for (Card card : cardPasta) {
        Object[] row = {
            card.getId(),
            card.getFrente(),
            card.getVerso(),
            card.getPasta().getNomePasta()
        };
        model.addRow(row);
    }

    return model;
}
 //organiza pasta com ids dos cards
 public static List <Integer>  pastaIdOrganiza(Pasta pasta) {
     listaCards(); //atualiza a lista
      List <Integer> pastaIdLista = new ArrayList<>();
        pastaIdLista.clear();
        for (Card card : cards) {
            if(card.getPasta().equals(pasta)){
            pastaIdLista.add(card.getId());
            }
        }
        return pastaIdLista;
    }

    //escolhe id aleatório a partir da lista de ids na pasta
    public static int pastaIdAleatorio(Pasta pasta) {
        List <Integer> pastaIdLista = pastaIdOrganiza(pasta);
        //escolhe um id aleatório
        Random random = new Random();
        int idAleatorio = -1;
        int tamanho = pastaIdLista.size();
        if (tamanho <= 0) {
            return -1;
        } else {
            int indice = random.nextInt(pastaIdLista.size());
            idAleatorio = pastaIdLista.get(indice);
            pastaIdLista.remove(indice);
            return idAleatorio;
        }
    }
        //devolve um card escolhido aleatoriamente
        public static Card cardAleatorioPasta(Pasta pasta) {
        int idAleatorio = pastaIdAleatorio(pasta);
        if (idAleatorio < 0) {
            return null;
        } else {
            Card cardAleatorio = buscarPorId(idAleatorio);
            return cardAleatorio;
        }
    }
    }

