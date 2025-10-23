package com.biblioteca.service;

import com.biblioteca.entity.Autor;
import com.biblioteca.entity.Emprestimo;
import com.biblioteca.entity.Livro;
import com.biblioteca.repository.AutorRepository;
import com.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.repository.LivroRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class BibliotecaService {

    // Variáveis
    @Inject
    private AutorRepository autorRepository;

    @Inject
    private LivroRepository livroRepository;

    @Inject
    private EmprestimoRepository emprestimoRepository;

    @Transactional
    public List<Autor> listarTodosAutores() {
        return autorRepository.findAll();
    }

    // Métodos

    // Método criado para poder ser transacional a chamada do autores.livros.size() na view
    @Transactional
    public List<Autor> listarTodosAutoresComSeusLivros() {
        return autorRepository.findAllComLivros();
    }

    @Transactional
    public List<Livro> listarTodosLivros() {
        return livroRepository.findAll();
    }

    @Transactional
    public List<Emprestimo> listarTodosEmprestimos() {
        return emprestimoRepository.findAll();
    }

    @Transactional
    public Long contarTotalLivros() {
        return livroRepository.count();
    }

    @Transactional
    public Long contarLivrosDisponiveis() {
        return livroRepository.countByDisponivel(true);
    }

    @Transactional
    public Long contarEmprestimosAtivos() {
        return emprestimoRepository.countAtivos();
    }

    @Transactional
    public Long contarTotalAutores() {
        return autorRepository.count();
    }
}
