package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.*;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.channels.Pipe;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO =  "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=bd8febfa" ;
    private ConsumoApi consumo = new ConsumoApi();
    private    ConverteDados conversor = new ConverteDados();
    private List<Serie> series = new ArrayList<>();
    private SerieRepository  repositorio;
    private Optional<Serie> serieBusca;


    public Principal(SerieRepository repositorio){
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;

        while (opcao != 0) {
            var menu = """
                    1 - Buscar Séries
                    2 - Buscar Episórios
                    3 - Mostrar series Listadas
                    4 - Buscar serie por titulo
                    5 - Buscar serie por ator
                    6 - top 5 series
                    7 - Buscar serie por genero
                    8 - Filtrar series por temporada e avaliacao
                    9 - Buscar ep por nome/trecho
                    10 - top 5 episodios

                    0 - Sair
                    """;


            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();

                case 2:
                    buscarEpisodioPorSerie();

                case 3:
                    listarSeriesBuscadas();
                    break;

                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriePorAtor();
                    break;
                case 6:
                    top5Series();
                    break;
                case 7:
                    buscaPorGenero();
                    break;
                case 8:
                    seriesPorTemporadaEAValiacao();
                    break;
                case 9:
                    buscarEpTrecho();
                    break;
                case 10:
                    topEpSerie();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção Invalida");

            }
        }
    }



    private void buscarSerieWeb(){
            DadosSerie dados = getDadosSerie();
            Serie serie = new Serie(dados);
            repositorio.save(serie);
            //dadosSeries.add(dados);

            System.out.println(dados);
        }


        private DadosSerie getDadosSerie() {
            System.out.println("Digite o nome da serie para busca  ");
            var nomeSerie = leitura.nextLine();
            var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
            DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
            return dados;
        }

        private void buscarEpisodioPorSerie(){
            listarSeriesBuscadas();
            System.out.println("Escolha uma serie pelo nome:");
            var nomeSerie = leitura.next();

            Optional<Serie> serie = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

            if(serie.isPresent()){
                var  serieEncontrada = serie.get();
                List<DadosTemporada> temporadas = new ArrayList<>();

                for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                    var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                    DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                    temporadas.add(dadosTemporada);
                }
                temporadas.forEach(System.out::println);

                List<Episodios> episodios = temporadas.stream()
                        .flatMap(d -> d.episodios().stream()
                                .map(e -> new Episodios(d.numero(), e)))
                        .collect(Collectors.toList());
                serieEncontrada.setEpisodios(episodios);
                repositorio.save(serieEncontrada);
            } else{
                System.out.println("serie não encontrada");
                }
        }

        private void listarSeriesBuscadas(){
        series = repositorio.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
        }
        private void buscarSeriePorTitulo() {
            System.out.println("escolha uma serie pelo titulo");
            var nomeSerie = leitura.next();
            serieBusca = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

            if(serieBusca.isPresent()){
                System.out.println("dados da serie ;" + serieBusca.get());
            }else {
                System.out.println("serie não encontrada");
            }
        }

    private void buscarSeriePorAtor(){
        System.out.println("digite o nome do ator ");
        var nomeAtor = leitura.nextLine();
        System.out.println("avaliação apartir de qual valor");
        var avaliacao = leitura.nextDouble();

        List<Serie> seriesEncontradas = repositorio.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacao);
        System.out.println("Series em que  " + nomeAtor + " trabalhou"  );
        seriesEncontradas.forEach(s ->
                System.out.println(s.getTitulo() + " avaliação " + s.getAvaliacao()));

    }

        private void top5Series(){
        List<Serie> seriesTop =  repositorio.findTop5ByOrderByAvaliacaoDesc();
        seriesTop.forEach(s ->
                System.out.println(s.getTitulo() + " avaliação " + s.getAvaliacao()));
        }

        private void buscaPorGenero(){
            System.out.println("Qual genero deseja buscar");
            var nomeGenero = leitura.next();

            Categoria categoria = Categoria.fromPortugues(nomeGenero);
            List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
            System.out.println("Series De " + nomeGenero);
            seriesPorCategoria.forEach(System.out::println);

        }
        public void seriesPorTemporadaEAValiacao(){
            System.out.println("Filtrar series até quantas tempodaras? ");
            var totalTemporadas = leitura.nextInt();
            leitura.nextLine();
            System.out.println("Qual a avaliação para ser exibida ");
            var avaliacao = leitura.nextInt();
            leitura.nextLine();
            System.out.println("**** séries filtradas ****");
            List<Serie> filtroSeries = repositorio.seriesPorTemporadaEAvaliacao(totalTemporadas, avaliacao);
            filtroSeries.forEach(s ->
                    System.out.println(s.getTitulo() + " - avaliação  " + s.getAvaliacao()));

        }

        private void buscarEpTrecho(){
            System.out.println("qual o nome do ep ou trecho");
            var trecho = leitura.nextLine();
            List<Episodios> epEcontrados = repositorio.buscarEpTrecho(trecho);
            epEcontrados.forEach(System.out::println);
        }

        public void topEpSerie(){
            buscarSeriePorTitulo();
            if(serieBusca.isPresent()){
              Serie serie =  serieBusca.get();
              List<Episodios> topEps = repositorio.topEpSeries(serie);

              topEps.forEach(e ->
                      System.out.printf("Série: %s Temporada %s - Episódio %s - %s Avaliação %s\n",
                              e.getSerie().getTitulo(), e.getTemporada(),
                              e.getNumero(),e.getTitulo(), e.getAvaliacao() ));
            }
        }

}


