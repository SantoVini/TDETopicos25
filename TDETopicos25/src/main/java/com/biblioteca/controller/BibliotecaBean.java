package com.biblioteca.controller;

import com.biblioteca.entity.Autor;
import com.biblioteca.entity.Emprestimo;
import com.biblioteca.entity.Livro;
import com.biblioteca.service.BibliotecaService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class BibliotecaBean implements Serializable {

    // Variáveis privadas
    @Inject
    private BibliotecaService bibliotecaService;

    private List<Autor> autores;
    private List<Livro> livros;
    private List<Emprestimo> emprestimos;

    private Long totalLivros;
    private Long livrosDisponiveis;
    private Long emprestimosAtivosCount;
    private Long totalAutores;

    // Inicializa os dados, pós construção da tela
    @PostConstruct
    public void init() {
        carregarDados();
        carregarEstatisticas();
    }

    // Métodos privados

    private void carregarDados() {
        try {
            autores = bibliotecaService.listarTodosAutoresComSeusLivros();
            livros = bibliotecaService.listarTodosLivros();
            emprestimos = bibliotecaService.listarTodosEmprestimos();
        } catch (Exception e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }
    }

    private void carregarEstatisticas() {
        try {
            totalLivros = bibliotecaService.contarTotalLivros();
            livrosDisponiveis = bibliotecaService.contarLivrosDisponiveis();
            emprestimosAtivosCount = bibliotecaService.contarEmprestimosAtivos();
            totalAutores = bibliotecaService.contarTotalAutores();
        } catch (Exception e) {
            System.out.println("Erro ao carregar estisticas: " + e.getMessage());
        }
    }

    // Métodos públicos

    public List<Autor> getAutores() {
        return autores;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public Long getTotalLivros() {
        return totalLivros;
    }

    public Long getLivrosDisponiveis() {
        return livrosDisponiveis;
    }

    public Long getEmprestimosAtivosCount() {
        return emprestimosAtivosCount;
    }

    public Long getTotalAutores() {
        return totalAutores;
    }

    public void recarregarDados() {
        init();
    }

}