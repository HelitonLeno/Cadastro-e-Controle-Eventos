package com.app.app.repository;

import com.app.app.model.Convidado;
import com.app.app.model.Evento;
import org.springframework.data.repository.CrudRepository;

public interface ConvidadoRepository extends CrudRepository<Convidado, String> {
    Iterable<Convidado> findByEvento(Evento evento);
    Convidado findByRg(String rg);

}
