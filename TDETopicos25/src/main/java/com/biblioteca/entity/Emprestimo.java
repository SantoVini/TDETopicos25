package com.biblioteca.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "emprestimos")
public class Emprestimo {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nomeUsuario;

    @Column(nullable = false, length = 150)
    private String emailUsuario;

    @Column(nullable = false)
    private LocalDate dataEmprestimo;

    @Column(nullable = false)
    private LocalDate dataDevolucaoPrevista;

    @Column(nullable = true)
    private LocalDate dataDevolucao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    // Construtores
    public Emprestimo() {}

    public Emprestimo(String nomeUsuario, String emailUsuario, Livro livro) {
        this.nomeUsuario = nomeUsuario;
        this.emailUsuario = emailUsuario;
        this.livro = livro;
    }

    // Getters e Setters
    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    // Método de negócio
    public Boolean isAtivo() {
        return this.dataDevolucao == null;
    }
}
