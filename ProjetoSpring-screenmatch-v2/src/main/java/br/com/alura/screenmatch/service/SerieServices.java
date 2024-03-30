package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.dto.SerieDto;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieServices {


    public List<SerieDto> obterTop5(){
        return converteDados(repository.findTop5ByOrderByAvaliacaoDesc());


    }

    @Autowired
    private SerieRepository repository;

    public List<SerieDto> obterTodasAsSeries(){
     return converteDados(repository.findAll());


    }
    private List<SerieDto> converteDados(List<Serie> series){
        return series.stream()
                .map(s-> new SerieDto(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getDataLancamento(), s.getAtores(), s.getSinopse(), s.getPoster(), s.getGenero()))
                .collect(Collectors.toList());
        }

    public List<SerieDto> obterLancamentos() {
        return converteDados(repository.lancamentos());
     }

    public SerieDto obterPorID(Long id) {
        Optional<Serie> serie =  repository.findById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return  new SerieDto(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getDataLancamento(), s.getAtores(), s.getSinopse(), s.getPoster(), s.getGenero());
        }
         return null;
    }
}
