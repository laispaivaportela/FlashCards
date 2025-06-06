/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;
import controller.ControleCards;
import controller.Sistema;
/**
 *
 * @author laispaivaportela
 */
//objetivo: criar uma aplicação para imprimir, armazenar, e alternar flashcards
public class Main {
    public static final ControleCards controle = new ControleCards();
    public static final Sistema aplicacao = new Sistema();
    
    public static void main(String[] args) {
        aplicacao.iniciaSistema();
        //System.out.println("Hello World!");
    }
}