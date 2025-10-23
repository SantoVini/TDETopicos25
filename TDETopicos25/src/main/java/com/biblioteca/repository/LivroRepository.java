package com.biblioteca.repository;

import com.biblioteca.entity.Autor;
import com.biblioteca.entity.Livro;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class LivroRepository {

    @PersistenceContext
    @Inject
    private EntityManager em;

    public List<Livro> findAll() {
        List<Livro> livros = em.createQuery("select l from Livro l left join fetch l.autor", Livro.class).getResultList();
        return !livros.isEmpty() ? livros : null;
    }

    public Long count() {
        return em.createQuery("select count(l) from Livro l", Long.class).getSingleResult();
    }

    public Long countByDisponivel(Boolean disponivel) {
        return em.createQuery("select count(l) from Livro l where l.disponivel = :disponivel", Long.class)
                .setParameter("disponivel", disponivel)
                .getSingleResult();
    }
}
