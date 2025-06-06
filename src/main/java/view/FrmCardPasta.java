/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import dao.CardsDAO;
import dao.PastaDAO;
import java.awt.Cursor;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javax.swing.border.EmptyBorder;
import model.Card;
import model.Pasta;
/**
 *
 * @author laispaivaportela
 */
public class FrmCardPasta extends javax.swing.JFrame {

    /**
     * Creates new form FrmCards
     */
    
    //construtor
    public FrmCardPasta() {
        initComponents();
        atualizaPastas();
        customizeUI();
    }
    //atualiza as pastas no combo
    public void atualizaPastas(){
       cmbPastas.removeAllItems(); // limpa o combo, se necessário

        for (String nome : PastaDAO.obterNomesDasPastas()) {
            cmbPastas.addItem(nome);
        }

        cmbPastas.setSelectedItem("Geral"); // garante que "Geral" fique selecionado 
    }
       //customização do frame
      private void customizeUI() {
          
        //configuração do frame
        getContentPane().setBackground(new java.awt.Color(245, 247, 250));
        setTitle("tabela de cards por pasta");
        
        //estilização dos botoes
        styleButton(btnOk, new java.awt.Color(100, 149, 237)); //esqueci
        styleButton(btnExportar, new java.awt.Color(60, 179, 113)); //verde
        
        
        //melhorar o titulo
        jLabel1.setForeground(new java.awt.Color(50, 50, 50));
    }
    
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
        
        //efeito hover moderno
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

    
//emite um arquivo excel com os dados da tabela atual
public void exportarTabelaParaExcel() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Salvar como");
    fileChooser.setSelectedFile(new File("cards.xlsx"));

    int escolha = fileChooser.showSaveDialog(this);
    if (escolha != JFileChooser.APPROVE_OPTION) {
        return;
    }

    File arquivo = fileChooser.getSelectedFile();

    try (Workbook workbook = new XSSFWorkbook()) {
        Sheet sheet = workbook.createSheet("Cards");

        TableModel model = jTableCards.getModel();

        //cria o cabeçalho
        Row header = sheet.createRow(0);
        for (int i = 0; i < model.getColumnCount(); i++) {
            header.createCell(i).setCellValue(model.getColumnName(i));
        }

        //insere os dados
        for (int row = 0; row < model.getRowCount(); row++) {
            Row excelRow = sheet.createRow(row + 1);
            for (int col = 0; col < model.getColumnCount(); col++) {
                Object valor = model.getValueAt(row, col);
                excelRow.createCell(col).setCellValue(valor == null ? "" : valor.toString());
            }
        }

        //salva o arquivo
        try (FileOutputStream out = new FileOutputStream(arquivo)) {
            workbook.write(out);
        }

        JOptionPane.showMessageDialog(this, "Arquivo Excel salvo com sucesso!");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao salvar o arquivo: " + e.getMessage());
        e.printStackTrace();
    }
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCards = new javax.swing.JTable();
        btnExportar = new javax.swing.JButton();
        cmbPastas = new javax.swing.JComboBox<>();
        btnOk = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jTableCards.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 12)); // NOI18N
        jTableCards.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableCards);

        btnExportar.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 14)); // NOI18N
        btnExportar.setText("exportar pro excel");
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        cmbPastas.setFont(new java.awt.Font("Microsoft JhengHei UI Light", 0, 14)); // NOI18N
        cmbPastas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnOk.setFont(new java.awt.Font("Microsoft JhengHei UI Light", 0, 14)); // NOI18N
        btnOk.setText("ok");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 28)); // NOI18N
        jLabel1.setText("Tabela Card-Pasta");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(103, 103, 103)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1066, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cmbPastas, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(38, 38, 38)
                        .addComponent(btnOk)))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnOk, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                        .addComponent(cmbPastas))
                    .addComponent(jLabel1))
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(83, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
       
    }//GEN-LAST:event_formWindowActivated

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        exportarTabelaParaExcel(); //exporta a tabela 
    }//GEN-LAST:event_btnExportarActionPerformed

    //exibe a tabela
    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        String nomePasta = (String) cmbPastas.getSelectedItem();
        Pasta pasta = PastaDAO.buscarPorNome(nomePasta);
        DefaultTableModel model = null;

        try {
            List<Card> cards = CardsDAO.cardsNaPasta(pasta); 
            model = CardsDAO.cardPastaTable(cards);          
        } catch (SQLException ex) {
            Logger.getLogger(FrmCardPasta.class.getName()).log(Level.SEVERE, null, ex);
        }

        jTableCards.setModel(model);
        
    }//GEN-LAST:event_btnOkActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnOk;
    private javax.swing.JComboBox<String> cmbPastas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableCards;
    // End of variables declaration//GEN-END:variables
}
