package br.com.alura.screenmatch.dto;

import br.com.alura.screenmatch.model.Categoria;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record SerieDto(Long id,
                        String titulo,
                                Integer totalTemporadas,
                                Double avaliacao,
                                String dataLancamento,
                                String atores,
                                String sinopse,
                                String poster,
                                Categoria genero) {
}
