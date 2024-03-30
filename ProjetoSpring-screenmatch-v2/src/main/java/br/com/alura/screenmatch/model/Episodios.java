package br.com.alura.screenmatch.model;
import jakarta.persistence.*;
import java.time.DateTimeException;
import java.time.LocalDate;


@Entity
@Table(name="episodios")

public class Episodios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer temporadas;
    private String titulo;
    private Integer numero;
    private Double avaliacao;
    private LocalDate dataLancamento;


    public Episodios(Integer numeroTemporada, DadosEps dadosEpisodios){
        this.temporadas = numeroTemporada;
        this.titulo = dadosEpisodios.titulo();
        this.numero = dadosEpisodios.numero();

        try {
            this.avaliacao = Double.valueOf(dadosEpisodios.avaliacao());

        }catch (NumberFormatException ex){
            this.avaliacao = 0.0;
        }
        try {
            this.dataLancamento = LocalDate.parse(dadosEpisodios.dataLancamento());

        }catch (DateTimeException dt){
            this.dataLancamento = null;
        }

    }

    @ManyToOne
    private Serie serie;

    public Episodios() {}


    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Integer getTemporada() {
        return temporadas;
    }

    public void setTemporada(Integer temporada) {
        this.temporadas = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @Override
    public String toString() {
        return  "temporadas=" + temporadas +
                ", titulo='" + titulo + '\'' +
                ", numero=" + numero +
                ", avaliacao=" + avaliacao +
                ", dataLancamento=" + dataLancamento;
    }

    public Integer getNumeroEpisodio() {
        return numero;
    };
}
