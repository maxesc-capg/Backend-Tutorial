package com.ccsw.tutorial.author;

import com.ccsw.tutorial.author.model.Author;
import com.ccsw.tutorial.author.model.AuthorDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {

    /**
     * Método para recuperar todas las {@link Author}
     *
     * @return {@link List} de {@link Author}
     */
    List<Author> findAll();

    /**
     * Método para crear o actualizar una {@link Author}
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, AuthorDto dto);

    /**
     * Método para borrar una {@link Author}
     *
     * @param id PK de la entidad
     */
    void delete(Long id) throws Exception;

}

