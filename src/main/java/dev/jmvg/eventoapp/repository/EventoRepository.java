package dev.jmvg.eventoapp.repository;

import dev.jmvg.eventoapp.model.Evento;
import org.springframework.data.repository.CrudRepository;

 public interface EventoRepository extends CrudRepository<Evento, Long> {

  Evento findByCodigo(long codigo);
}
