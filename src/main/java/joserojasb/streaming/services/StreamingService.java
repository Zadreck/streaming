package joserojasb.streaming.services;

import joserojasb.streaming.models.Episodio;
import joserojasb.streaming.models.Serie;
import joserojasb.streaming.repositories.EpisodioRepository;
import joserojasb.streaming.repositories.SerieRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StreamingService {

    private final SerieRepository serieRepo;
    private final EpisodioRepository episodioRepo;

    public StreamingService(SerieRepository serieRepo, EpisodioRepository episodioRepo) {
        this.serieRepo = serieRepo;
        this.episodioRepo = episodioRepo;
    }

    // ─── SERIES ───────────────────────────────────────────────
    public List<Serie> listarSeries() {
        return (List<Serie>) serieRepo.findAll();
    }

    public List<Serie> filtrarSeries(String genero, String plataforma) {
        if (genero != null && !genero.isBlank()) return serieRepo.findByGeneroIgnoreCase(genero);
        if (plataforma != null && !plataforma.isBlank()) return serieRepo.findByPlataformaIgnoreCase(plataforma);
        return listarSeries();
    }

    public Optional<Serie> buscarSerie(Long id) {
        return serieRepo.findById(id);
    }

    public Serie guardarSerie(Serie serie) {
        return serieRepo.save(serie);
    }

    public void eliminarSerie(Long id) {
        serieRepo.deleteById(id);
    }

    // ─── EPISODIOS ────────────────────────────────────────────
    public List<Episodio> listarEpisodios() {
        return (List<Episodio>) episodioRepo.findAll();
    }

    public List<Episodio> episodiosDeSerie(Long serieId) {
        return episodioRepo.findBySerieId(serieId);
    }

    public List<Episodio> episodiosPorTemporada(Long serieId, int temporada) {
        return episodioRepo.findBySerieIdAndTemporada(serieId, temporada);
    }

    public List<Episodio> episodiosEntreFechas(LocalDate desde, LocalDate hasta) {
        return episodioRepo.findByFechaEmisionBetween(desde, hasta);
    }

    public int duracionTotalTemporada(Long serieId, int temporada) {
        return episodiosPorTemporada(serieId, temporada)
                .stream().mapToInt(Episodio::getDuracionMin).sum();
    }

    public Optional<Episodio> buscarEpisodio(Long id) {
        return episodioRepo.findById(id);
    }

    public Episodio guardarEpisodio(Episodio episodio) {
        return episodioRepo.save(episodio);
    }

    public void eliminarEpisodio(Long id) {
        episodioRepo.deleteById(id);
    }
}