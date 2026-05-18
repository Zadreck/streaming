package joserojasb.streaming.controllers;

import joserojasb.streaming.models.Episodio;
import joserojasb.streaming.models.Serie;
import joserojasb.streaming.services.StreamingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final StreamingService service;

    public ApiController(StreamingService service) {
        this.service = service;
    }

    // ── GET /api/series?genero=&plataforma=
    @GetMapping("/series")
    public List<Serie> listarSeries(
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) String plataforma) {
        return service.filtrarSeries(genero, plataforma);
    }

    // ── GET /api/series/{id}
    @GetMapping("/series/{id}")
    public ResponseEntity<Serie> obtenerSerie(@PathVariable Long id) {
        return service.buscarSerie(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ── POST /api/series
    @PostMapping("/series")
    public Serie crearSerie(@RequestBody Serie serie) {
        return service.guardarSerie(serie);
    }

    // ── PUT /api/series/{id}
    @PutMapping("/series/{id}")
    public ResponseEntity<Serie> actualizarSerie(@PathVariable Long id, @RequestBody Serie datos) {
        return service.buscarSerie(id).map(s -> {
            s.setTitulo(datos.getTitulo());
            s.setGenero(datos.getGenero());
            s.setPlataforma(datos.getPlataforma());
            s.setAnoEstreno(datos.getAnoEstreno());
            return ResponseEntity.ok(service.guardarSerie(s));
        }).orElse(ResponseEntity.notFound().build());
    }

    // ── DELETE /api/series/{id}
    @DeleteMapping("/series/{id}")
    public ResponseEntity<Void> eliminarSerie(@PathVariable Long id) {
        if (service.buscarSerie(id).isEmpty()) return ResponseEntity.notFound().build();
        service.eliminarSerie(id);
        return ResponseEntity.noContent().build();
    }

    // ─────────────────────────────────────────────────────────

    // ── GET /api/episodios?serieId=&temporada=&desde=&hasta=
    @GetMapping("/episodios")
    public List<Episodio> listarEpisodios(
            @RequestParam(required = false) Long serieId,
            @RequestParam(required = false) Integer temporada,
            @RequestParam(required = false) LocalDate desde,
            @RequestParam(required = false) LocalDate hasta) {

        if (serieId != null && temporada != null)
            return service.episodiosPorTemporada(serieId, temporada);
        if (serieId != null)
            return service.episodiosDeSerie(serieId);
        if (desde != null && hasta != null)
            return service.episodiosEntreFechas(desde, hasta);
        return service.listarEpisodios();
    }

    // ── GET /api/episodios/{id}
    @GetMapping("/episodios/{id}")
    public ResponseEntity<Episodio> obtenerEpisodio(@PathVariable Long id) {
        return service.buscarEpisodio(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ── POST /api/episodios
    @PostMapping("/episodios")
    public ResponseEntity<Episodio> crearEpisodio(@RequestBody Episodio episodio) {
        if (episodio.getSerie() == null || episodio.getSerie().getId() == null)
            return ResponseEntity.badRequest().build();
        return service.buscarSerie(episodio.getSerie().getId()).map(s -> {
            episodio.setSerie(s);
            return ResponseEntity.ok(service.guardarEpisodio(episodio));
        }).orElse(ResponseEntity.notFound().build());
    }

    // ── PUT /api/episodios/{id}
    @PutMapping("/episodios/{id}")
    public ResponseEntity<Episodio> actualizarEpisodio(@PathVariable Long id, @RequestBody Episodio datos) {
        return service.buscarEpisodio(id).map(e -> {
            e.setNumero(datos.getNumero());
            e.setTemporada(datos.getTemporada());
            e.setTitulo(datos.getTitulo());
            e.setDuracionMin(datos.getDuracionMin());
            e.setFechaEmision(datos.getFechaEmision());
            return ResponseEntity.ok(service.guardarEpisodio(e));
        }).orElse(ResponseEntity.notFound().build());
    }

    // ── DELETE /api/episodios/{id}
    @DeleteMapping("/episodios/{id}")
    public ResponseEntity<Void> eliminarEpisodio(@PathVariable Long id) {
        if (service.buscarEpisodio(id).isEmpty()) return ResponseEntity.notFound().build();
        service.eliminarEpisodio(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/series/{id}/temporada/{t}/duracion")
    public ResponseEntity<Integer> duracionTemporada(@PathVariable Long id, @PathVariable int t) {
    return service.buscarSerie(id)
        .map(s -> ResponseEntity.ok(service.duracionTotalTemporada(id, t)))
        .orElse(ResponseEntity.notFound().build());
}
}