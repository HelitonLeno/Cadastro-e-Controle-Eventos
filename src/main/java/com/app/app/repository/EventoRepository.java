package com.app.app.repository;

import com.app.app.model.Evento;
import org.springframework.data.repository.CrudRepository;

public interface EventoRepository extends CrudRepository<Evento, String> {
    Evento findById(long id);
}
