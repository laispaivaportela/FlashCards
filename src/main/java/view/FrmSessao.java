/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;


import dao.PastaDAO;
import java.awt.Cursor;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import view.FrmCard;
/**
 *
 * @author laispaivaportela
 */

import model.Pasta;
public class FrmSessao extends javax.swing.JFrame {
    FrmCard flashcard = new FrmCard();


    
    

    /**
     * Creates new form FrmCards
     */
    
    //construtor
    public FrmSessao() {
        initComponents();
        atualizaPastas();
        customizeUI();
    }
    //atualiza o combobox
    public void atualizaPastas(){
        
       cmbPastas.removeAllItems(); //limpa o combo, se necessário

        for (String nome : PastaDAO.obterNomesDasPastas()) {
            cmbPastas.addItem(nome);
        }

        cmbPastas.setSelectedItem("Geral"); // garante que "Geral" fique selecionado 
    }
    
    //estiliza o código
     private void customizeUI() {
        //configuração do frame
        getContentPane().setBackground(new java.awt.Color(245, 247, 250));
        setTitle("sessão de estudo");
        
        //estilização dos botões
        styleButton(btnGo, new java.awt.Color(255, 165, 0)); // Laranja
        
        
        //melhorar o título
        jLabel1.setForeground(new java.awt.Color(50, 50, 50));
    }
    
     //estiliza botão
    private void styleButton(JButton button, java.awt.Color color) {
        button.setBackground(color);
        button.setForeground(java.awt.Color.WHITE);
        button.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 1),
            new EmptyBorder(10, 25, 10, 25)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        //efeito hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(color.brighter().brighter(), 2),
                    new EmptyBorder(10, 25, 10, 25)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(color.darker(), 1),
                    new EmptyBorder(10, 25, 10, 25)
                ));
            }
        });
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbPastas = new javax.swing.JComboBox<>();
        btnGo = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        cmbPastas.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 18)); // NOI18N
        cmbPastas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnGo.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 14)); // NOI18N
        btnGo.setText("vamo laaaa");
        btnGo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Microsoft JhengHei UI Light", 0, 30)); // NOI18N
        jLabel1.setText("Escolha a pasta a qual você deseja estudar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(cmbPastas, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btnGo, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbPastas, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(161, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
       
    }//GEN-LAST:event_formWindowActivated
//inicia sessão de estudos com cards da pasta específica escolhida 
    private void btnGoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoActionPerformed
        String nomePasta = (String) cmbPastas.getSelectedItem();
        Pasta pasta = PastaDAO.buscarPorNome(nomePasta);
        try {
            flashcard.comeca(pasta);
        } catch (SQLException ex) {
            Logger.getLogger(FrmSessao.class.getName()).log(Level.SEVERE, null, ex);
        }
        flashcard.setVisible(true);
        
    }//GEN-LAST:event_btnGoActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGo;
    private javax.swing.JComboBox<String> cmbPastas;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
