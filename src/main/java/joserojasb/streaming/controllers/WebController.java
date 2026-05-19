package joserojasb.streaming.controllers;

import joserojasb.streaming.models.Episodio;
import joserojasb.streaming.models.Serie;
import joserojasb.streaming.services.StreamingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web")
public class WebController {

    private final StreamingService service;

    public WebController(StreamingService service) {
        this.service = service;
    }

    // ── SERIES ──────────────────────────────────────────────

    @GetMapping("/series")
    public String listarSeries(
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) String plataforma,
            Model model) {
        model.addAttribute("series", service.filtrarSeries(genero, plataforma));
        model.addAttribute("genero", genero);
        model.addAttribute("plataforma", plataforma);
        return "series/lista";
    }

    @GetMapping("/series/nueva")
    public String formNuevaSerie(Model model) {
        model.addAttribute("serie", new Serie());
        return "series/nueva";
    }

    @PostMapping("/series/nueva")
    public String guardarNuevaSerie(@ModelAttribute Serie serie) {
        service.guardarSerie(serie);
        return "redirect:/web/series";
    }

    @GetMapping("/series/editar/{id}")
    public String formEditarSerie(@PathVariable Long id, Model model) {
        service.buscarSerie(id).ifPresent(s -> model.addAttribute("serie", s));
        return "series/editar";
    }

    @PostMapping("/series/editar/{id}")
    public String guardarEdicionSerie(@PathVariable Long id, @ModelAttribute Serie datos) {
        service.buscarSerie(id).ifPresent(s -> {
            s.setTitulo(datos.getTitulo());
            s.setGenero(datos.getGenero());
            s.setPlataforma(datos.getPlataforma());
            s.setAnoEstreno(datos.getAnoEstreno());
            service.guardarSerie(s);
        });
        return "redirect:/web/series";
    }

    @PostMapping("/series/borrar/{id}")
    public String eliminarSerie(@PathVariable Long id) {
        service.eliminarSerie(id);
        return "redirect:/web/series";
    }

    @GetMapping("/series/{id}")
    public String detalleSerie(@PathVariable Long id, Model model) {
        service.buscarSerie(id).ifPresent(s -> {
            model.addAttribute("serie", s);
            model.addAttribute("episodios", service.episodiosDeSerie(id));
        });
        return "series/detalle";
    }

    // ── EPISODIOS ────────────────────────────────────────────

@GetMapping("/episodios")
public String listarEpisodios(@RequestParam(required = false) Long serieId, Model model) {
    model.addAttribute("episodios", serieId != null 
        ? service.episodiosDeSerie(serieId) 
        : service.listarEpisodios());
    model.addAttribute("series", service.listarSeries());
    return "episodios/lista";
    }

    @GetMapping("/episodios/nuevo")
    public String formNuevoEpisodio(Model model) {
        model.addAttribute("episodio", new Episodio());
        model.addAttribute("series", service.listarSeries());
        return "episodios/nuevo";
    }

    @PostMapping("/episodios/nuevo")
    public String guardarNuevoEpisodio(@ModelAttribute Episodio episodio,
                                       @RequestParam Long serieId) {
        service.buscarSerie(serieId).ifPresent(s -> {
            episodio.setSerie(s);
            service.guardarEpisodio(episodio);
        });
        return "redirect:/web/episodios";
    }

    @GetMapping("/episodios/editar/{id}")
    public String formEditarEpisodio(@PathVariable Long id, Model model) {
        service.buscarEpisodio(id).ifPresent(e -> model.addAttribute("episodio", e));
        model.addAttribute("series", service.listarSeries());
        return "episodios/editar";
    }

    @PostMapping("/episodios/editar/{id}")
    public String guardarEdicionEpisodio(@PathVariable Long id,
                                          @ModelAttribute Episodio datos,
                                          @RequestParam Long serieId) {
        service.buscarEpisodio(id).ifPresent(e -> {
            e.setNumero(datos.getNumero());
            e.setTemporada(datos.getTemporada());
            e.setTitulo(datos.getTitulo());
            e.setDuracionMin(datos.getDuracionMin());
            e.setFechaEmision(datos.getFechaEmision());
            service.buscarSerie(serieId).ifPresent(e::setSerie);
            service.guardarEpisodio(e);
        });
        return "redirect:/web/episodios";
    }

    @PostMapping("/episodios/borrar/{id}")
    public String eliminarEpisodio(@PathVariable Long id) {
        service.eliminarEpisodio(id);
        return "redirect:/web/episodios";
    }

    @GetMapping("/")
    public String inicio() {
    return "redirect:/web/series";
    }
}