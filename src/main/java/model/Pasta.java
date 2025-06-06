/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author laispaivaportela
 */
public class Pasta {
    //variáveis de instância
    private int idPasta;
    private String nomePasta;
    private String descricao;

    //construtor
    public Pasta() {
    }

    

    //getters e setters
    public int getIdPasta() {
        return idPasta;
    }

    public void setIdPasta(int idPasta) {
        this.idPasta = idPasta;
    }

    public String getNomePasta() {
        return nomePasta;
    }

    public void setNomePasta(String nomePasta) {
        this.nomePasta = nomePasta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    //hashcode para evitar erros
@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Pasta pasta = (Pasta) obj;
    return idPasta == pasta.idPasta;
}

@Override
public int hashCode() {
    return Objects.hash(idPasta);
}

    
    
}
