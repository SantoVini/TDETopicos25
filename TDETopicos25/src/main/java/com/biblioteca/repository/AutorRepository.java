package com.biblioteca.repository;

import com.biblioteca.entity.Autor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class AutorRepository {

    @PersistenceContext
    @Inject
    private EntityManager em;

    public List<Autor> findAll() {
        List<Autor> autores = em.createQuery("select a from Autor a", Autor.class).getResultList();
        return !autores.isEmpty() ? autores : null;
    }

    // Método criado para poder ser transacional a chamada do autores.livros.size() na view
    public List<Autor> findAllComLivros() {
        List<Autor> autores = em.createQuery("SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.livros", Autor.class).getResultList();
        return !autores.isEmpty() ? autores : null;
    }

    public Long count () {
        return em.createQuery("select count(a) from Autor a", Long.class).getSingleResult();
    }
}
