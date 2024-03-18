package br.com.alura.screenmatch.model;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title")String titulo,
                         @JsonAlias("totalSeasons")int totalTemporadas,
                         @JsonAlias("imdbRating")String avaliacao,
                         @JsonAlias("Released")String dataLancamento,
                         @JsonAlias("Actors")String atores,
                         @JsonAlias("Plot")String sinopse,
                         @JsonAlias("Poster")String poster,
                         @JsonAlias("Genre")String genero) {

}