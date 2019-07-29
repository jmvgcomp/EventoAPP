package dev.jmvg.eventoapp.repository;

import dev.jmvg.eventoapp.model.Convidados;
import dev.jmvg.eventoapp.model.Evento;
import org.springframework.data.repository.CrudRepository;

public interface ConvidadoRepository extends CrudRepository<Convidados, Long> {
    Iterable<Convidados> findByEvento(Evento evento);
    Convidados findById(long id);
}
