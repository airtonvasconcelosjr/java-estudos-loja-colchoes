package com.colchoesapi.model;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "imagens_colchao")
public class ImagemColchao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    private String tipo;
    
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] dados;
    
    @ManyToOne
    @JoinColumn(name = "colchao_id")
    @JsonBackReference
    private Colchao colchao;
    
    public ImagemColchao() {
    }
    
    public ImagemColchao(String nome, String tipo, byte[] dados) {
        this.nome = nome;
        this.tipo = tipo;
        this.dados = dados;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public byte[] getDados() {
        return dados;
    }

    public void setDados(byte[] dados) {
        this.dados = dados;
    }

    public Colchao getColchao() {
        return colchao;
    }

    public void setColchao(Colchao colchao) {
        this.colchao = colchao;
    }
}