package br.com.alura.screenmatch.controller;

import br.com.alura.screenmatch.dto.SerieDto;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.SerieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
@RequestMapping("/series")
@RestController
public class SerieController {


    @Autowired
    private SerieServices servico;

    @GetMapping
    public List<SerieDto> retornarInicio(){
        return servico.obterTodasAsSeries();
    }
    @GetMapping("/top5")
    public List<SerieDto> obterTop5 (){
        return servico.obterTop5();
    }

    @GetMapping("/lancamentos")
    public List<SerieDto> obterLancamentos(){
       return servico.obterLancamentos();
    }
}
