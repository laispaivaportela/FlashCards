/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author laispaivaportela
 */
import static main.Main.controle;
import javax.swing.JOptionPane;
import view.FrmMenu;

public class Sistema {

    FrmMenu menu = new FrmMenu();

    public void iniciaSistema() {
        menu.setVisible(true);
    }

    //escolhe qual tela abrir (baseado no menubar da classe view de sistema universidade do controller)
    public void executarAcao(int acao) {
    switch (acao) {
        case 0 -> System.exit(0);
        case 1 -> controle.abreTelaCard();
        case 2 -> controle.abreTelaCadastro();
        case 3 -> controle.abreTelaCards(); 
        case 4 -> controle.abreTelaGerenciamento();
        default -> JOptionPane.showMessageDialog(null, "Ação inválida.");
        }
    }
}
