/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

/**
 *
 * @author laispaivaportela
 */
import dao.PastaDAO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import static main.Main.controle;
import model.Pasta;

public class FrmCriaCard extends javax.swing.JFrame {

    private static final int MAX_CHARS = 250; //define o tamanho máximo da  entrada (250 caracteres)
    private JLabel lblCounterFrente; //label para fazer a contagem de caracteres do txt pra frente
    private JLabel lblCounterVerso; //label para fazer a contagem de caracteres do txt pro verso

    /**
     * Creates new form FrmCriaCard
     */
    
    //construtor
    public FrmCriaCard() {
        initComponents();
        initCustomComponents();
        setupCharacterCounters();
        customizeUI();
        atualizaPastas();

    }
    
    public void atualizaPastas(){
       cmbPastas.removeAllItems(); //limpa o combo

        for (String nome : PastaDAO.obterNomesDasPastas()) {
            cmbPastas.addItem(nome);
        }

        cmbPastas.setSelectedItem("Geral"); //garante que "Geral" fique selecionado 
    }
    //método para inicializar componentes de customização
    private void initCustomComponents() {
        lblCounterFrente = new JLabel(MAX_CHARS + "/" + MAX_CHARS);
        lblCounterVerso = new JLabel(MAX_CHARS + "/" + MAX_CHARS);

        lblCounterFrente.setFont(new Font("Consolas", Font.PLAIN, 12));
        lblCounterFrente.setForeground(Color.GRAY);
        lblCounterVerso.setFont(new Font("Consolas", Font.PLAIN, 12));
        lblCounterVerso.setForeground(Color.GRAY);

        //ajusta o layout para adicionar os contadores
        javax.swing.GroupLayout layout = (javax.swing.GroupLayout) getContentPane().getLayout();
        
        //alinha e define os componentes/scrolls
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblCounterFrente))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblCounterVerso)))
                .addGap(0, 414, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99))
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCounterFrente)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCounterVerso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
    }

    //configuram os contadores de caracteres (keys)
    private void setupCharacterCounters() {
        txtFrente.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateCounter(txtFrente, lblCounterFrente);
            }
        });

        txtVerso.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateCounter(txtVerso, lblCounterVerso);
            }
        });
    }

    //atualizam os contadores
    private void updateCounter(JTextPane textPane, JLabel counterLabel) {
        SwingUtilities.invokeLater(() -> {
            int length = textPane.getText().length();
            int remaining = MAX_CHARS - length;
            counterLabel.setText(remaining + "/" + MAX_CHARS);

            if (remaining < 0) {
                String text = textPane.getText();
                textPane.setText(text.substring(0, MAX_CHARS));
                counterLabel.setText("0/" + MAX_CHARS);
                Toolkit.getDefaultToolkit().beep();
            } else if (remaining < 20) {
                counterLabel.setForeground(Color.RED);
            } else if (remaining < 50) {
                counterLabel.setForeground(Color.ORANGE);
            } else {
                counterLabel.setForeground(Color.GRAY);
            }
        });
    }

    //outras customizações (alinhamento e aparência dos texts/btn) 
    private void customizeUI() {
        // Centralizar texto
        centerText(txtFrente);
        centerText(txtVerso);

        //melhora aparência dos botões
        btnSalvar.setBackground(new Color(70, 130, 180));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);

        //melhora aparência das áreas de texto
        txtFrente.setBackground(new Color(255, 255, 245));
        txtVerso.setBackground(new Color(255, 255, 245));
    }

    //centraliza texto
    private void centerText(JTextPane textPane) {
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtFrente = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtVerso = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        cmbPastas = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 28)); // NOI18N
        jLabel2.setText("Frente");

        jLabel3.setFont(new java.awt.Font("Microsoft JhengHei Light", 0, 28)); // NOI18N
        jLabel3.setText("Verso");

        btnSalvar.setFont(new java.awt.Font("Microsoft JhengHei UI Light", 0, 18)); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        txtFrente.setFont(new java.awt.Font("Inter 18pt", 0, 14)); // NOI18N
        jScrollPane1.setViewportView(txtFrente);

        txtVerso.setFont(new java.awt.Font("Inter 18pt", 0, 14)); // NOI18N
        jScrollPane2.setViewportView(txtVerso);

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei Light", 0, 28)); // NOI18N
        jLabel1.setText("Pasta");

        cmbPastas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99))
            .addGroup(layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addGap(12, 12, 12)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(cmbPastas, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 409, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel2)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jLabel3)))
                .addGap(69, 69, 69)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbPastas, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    //método para quando o btn salvar é acionado -> recebe as informações disponibilizadas pelo usuário e pede para adicionar à base de dados
    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        int id = -1;
        String frente = txtFrente.getText();
        String verso = txtVerso.getText();
        String nomePasta = ((String) cmbPastas.getSelectedItem()).trim();
        Pasta pasta = PastaDAO.buscarPorNome(nomePasta);
        id = pasta.getIdPasta();
        PastaDAO.incrementarQuantidadeCards(id);
        
        if (frente.trim().isEmpty() || verso.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, preencha ambos os lados do card!",
                    "Campos vazios",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        
        controle.adicionaCard(frente, verso, pasta);
        txtFrente.setText("");
        txtVerso.setText("");
        lblCounterFrente.setText(MAX_CHARS + "/" + MAX_CHARS);
        lblCounterVerso.setText(MAX_CHARS + "/" + MAX_CHARS);
    
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
       atualizaPastas(); //atualiza o combobox
    }//GEN-LAST:event_formWindowOpened

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        atualizaPastas(); //atualiza o combobox
    }//GEN-LAST:event_formWindowActivated

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cmbPastas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane txtFrente;
    private javax.swing.JTextPane txtVerso;
    // End of variables declaration//GEN-END:variables
}
