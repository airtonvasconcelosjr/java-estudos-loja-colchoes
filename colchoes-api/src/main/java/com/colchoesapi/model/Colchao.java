package com.colchoesapi.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "colchoes")
public class Colchao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modelo;
    private String marca;
    private Double preco;
    
    @OneToMany(mappedBy = "colchao", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ImagemColchao> imagens = new ArrayList<>();

    public Colchao() {
    }

    public Colchao(String modelo, String marca, Double preco) {
        this.modelo = modelo;
        this.marca = marca;
        this.preco = preco;
    }

    // Métodos para gerenciar a relação bidirecional
    public void adicionarImagem(ImagemColchao imagem) {
        imagens.add(imagem);
        imagem.setColchao(this);
    }
    
    public void removerImagem(ImagemColchao imagem) {
        imagens.remove(imagem);
        imagem.setColchao(null);
    }

    // Getters e Setters originais
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
    
    // Getter e Setter para imagens
    public List<ImagemColchao> getImagens() {
        return imagens;
    }

    public void setImagens(List<ImagemColchao> imagens) {
        this.imagens = imagens;
    }
}