package joserojasb.streaming.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "series")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "episodios")
@EqualsAndHashCode(exclude = "episodios")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String genero;
    private String plataforma;
    private int anoEstreno;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Episodio> episodios = new ArrayList<>();

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getPlataforma() { return plataforma; }
    public void setPlataforma(String plataforma) { this.plataforma = plataforma; }

    public int getAnoEstreno() { return anoEstreno; }
    public void setAnoEstreno(int anoEstreno) { this.anoEstreno = anoEstreno; }

    public List<Episodio> getEpisodios() { return episodios; }
    public void setEpisodios(List<Episodio> episodios) { this.episodios = episodios; }
}