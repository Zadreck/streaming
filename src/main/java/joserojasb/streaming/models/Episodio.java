package joserojasb.streaming.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "episodios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "serie")
@EqualsAndHashCode(exclude = "serie")
public class Episodio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numero;
    private int temporada;
    private String titulo;
    private int duracionMin;
    private LocalDate fechaEmision;

    @ManyToOne
    @JoinColumn(name = "serie_id", nullable = false)
    private Serie serie;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public int getTemporada() { return temporada; }
    public void setTemporada(int temporada) { this.temporada = temporada; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public int getDuracionMin() { return duracionMin; }
    public void setDuracionMin(int duracionMin) { this.duracionMin = duracionMin; }

    public LocalDate getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }

    public Serie getSerie() { return serie; }
    public void setSerie(Serie serie) { this.serie = serie; }
}