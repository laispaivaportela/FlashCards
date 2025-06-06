/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import javax.swing.JOptionPane;
import dao.CardsDAO;
import view.FrmCard;
import view.FrmCriaCard;
import view.FrmCards;
import view.FrmGerenciaCard;
import java.sql.SQLException;
import model.Card;
import dao.PastaDAO;
import javax.swing.table.DefaultTableModel;
import model.Pasta;

/**
 *
 * @author laispaivaportela
 */
public class ControleCards {
    public static final CardsDAO cardsBanco = new CardsDAO();
    public static final FrmCard telaCard = new FrmCard();
    public static final FrmCriaCard cadastro = new FrmCriaCard();
    public static final FrmCards telaCards = new FrmCards();
    public static final FrmGerenciaCard telaGerenciamento = new FrmGerenciaCard();
    public static final PastaDAO pastaBanco = new PastaDAO();

   
    public void abreTelaCard(){
        geraGrupo();
        telaCard.setVisible(true);
        atualizaLista();
    }
    public void abreTelaCadastro(){
        cadastro.setVisible(true);
    }
    public void abreTelaCards(){
        atualizaLista();
        telaCards.setVisible(true);
    }
    public void abreTelaGerenciamento(){
        telaGerenciamento.setVisible(true);
    }
    
    //atualiza a lista assim que o programa começa
    public void atualizaLista(){
      cardsBanco.listaCards();
    }
    
     public void geraGrupo(){ //cria o grupo de cards
      cardsBanco.idOrganiza();
    }
    
    public Card cardPesquisado(int id){
        Card cardPesquisado = cardsBanco.buscarPorId(id);
        return cardPesquisado;
    }
    
    public String frenteCard(int id){
        Card card = cardsBanco.buscarPorId(id);
        String frente = card.getFrente();
        return frente;
    }
    
     public String versoCard(int id){
        Card card = cardsBanco.buscarPorId(id);
        String verso = card.getVerso();
        return verso;
    }
    
     //manda o card aleatorio
     public void pastaCardAleatorio(Pasta pasta){
        Card cardAleatorio = cardsBanco.cardAleatorioPasta(pasta);
        if(cardAleatorio == null){
             JOptionPane.showMessageDialog(null, "parabenxxxx, cabo do grupo de cards! arrasouuu ;)");
             cardsBanco.pastaIdOrganiza(pasta);
        }
        else{
        int id = cardAleatorio.getId();
        String frente = cardAleatorio.getFrente();
        telaCard.setFrente(frente);
        String verso = cardAleatorio.getVerso();
        telaCard.setVerso(verso);
        }
    }
     
     //manda o card aleatorio
     public void cardAleatorio(){
        Card cardAleatorio = cardsBanco.cardAleatorio();
        if(cardAleatorio == null){
             JOptionPane.showMessageDialog(null, "parabenxxxx, cabo do grupo de cards! arrasouuu ;)");
             cardsBanco.idOrganiza();
        }
        else{
        int id = cardAleatorio.getId();
        String frente = cardAleatorio.getFrente();
        telaCard.setFrente(frente);
        String verso = cardAleatorio.getVerso();
        telaCard.setVerso(verso);
        }
    }
     
    
    public void adicionaCard(String frente, String verso, Pasta pasta){
       cardsBanco.inserirCard(frente, verso, pasta);
    }
    
    public void gerenciaCard(int id, String frente, String verso, Pasta pasta) { //cadastra aluno a partir da entrada da classe view para cadastrar alunos
         cardsBanco.atualizaCard(id, frente, verso, pasta);
    }
    
    public static DefaultTableModel tabelaCards() throws SQLException {
    DefaultTableModel model = cardsBanco.montarTabela();
    return model;
    }
    
    public static DefaultTableModel tabelaPastas() throws SQLException {
    DefaultTableModel model = pastaBanco.montarTabela();
    return model;
    }
}
