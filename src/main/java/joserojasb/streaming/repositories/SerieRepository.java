package joserojasb.streaming.repositories;

import joserojasb.streaming.models.Serie;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface SerieRepository extends CrudRepository<Serie, Long> {
    List<Serie> findByGeneroIgnoreCase(String genero);
    List<Serie> findByPlataformaIgnoreCase(String plataforma);
}