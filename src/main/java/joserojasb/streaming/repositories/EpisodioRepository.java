package joserojasb.streaming.repositories;

import joserojasb.streaming.models.Episodio;
import org.springframework.data.repository.CrudRepository;
import java.time.LocalDate;
import java.util.List;

public interface EpisodioRepository extends CrudRepository<Episodio, Long> {
    List<Episodio> findBySerieId(Long serieId);
    List<Episodio> findBySerieIdAndTemporada(Long serieId, int temporada);
    List<Episodio> findByFechaEmisionBetween(LocalDate desde, LocalDate hasta);
}