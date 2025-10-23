package com.biblioteca.repository;

import com.biblioteca.entity.Emprestimo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class EmprestimoRepository {

    @PersistenceContext
    @Inject
    private EntityManager em;

    public List<Emprestimo> findAll() {
        List<Emprestimo> emprestimos = em.createQuery("select e from Emprestimo e LEFT JOIN FETCH e.livro LEFT JOIN FETCH e.livro.autor", Emprestimo.class).getResultList();
        return !emprestimos.isEmpty() ? emprestimos : null;
    }

    public List<Emprestimo> findAtivos() {
        List<Emprestimo> emprestimos = em.createQuery("SELECT e FROM Emprestimo e LEFT JOIN FETCH e.livro WHERE e.dataDevolucao IS NULL", Emprestimo.class).getResultList();
        return !emprestimos.isEmpty() ? emprestimos : null;
    }

    public Long count() {
        return em.createQuery("select count(e) from Emprestimo e", Long.class).getSingleResult();
    }

    public Long countAtivos() {
        return em.createQuery("SELECT COUNT(e) FROM Emprestimo e WHERE e.dataDevolucao IS NULL", Long.class).getSingleResult();
    }
}
