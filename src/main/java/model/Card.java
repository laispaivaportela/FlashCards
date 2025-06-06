/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author laispaivaportela
 */
public class Card {
    //variáveis de instância
    private int id;
    private String frente;
    private String verso;
    private Pasta pasta;

    //construtores
    public Card() {
    }

    public Card(int id, String frente, String verso, Pasta pasta) {
        this.id = id;
        this.frente = frente;
        this.verso = verso;
        this.pasta = pasta;
    }

    //getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrente() {
        return frente;
    }

    public void setFrente(String frente) {
        this.frente = frente;
    }

    public String getVerso() {
        return verso;
    }

    public void setVerso(String verso) {
        this.verso = verso;
    }

    public Pasta getPasta() {
        return pasta;
    }

    public void setPasta(Pasta pasta) {
        this.pasta = pasta;
    }
    
    
}
