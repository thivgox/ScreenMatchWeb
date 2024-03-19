package br.com.alura.screenmatch.model;

public enum Categoria {
    ACAO("Action", "Ação"),
    ROMANCE("Romance", "Comédia"),
    DRAMA("Drama","Drama"),
    COMEDIA("Comedy", "Comédia"),
    CRIME("Crime", "Crime");
    private String categoriaOmdb;

    private  String categoriaPt;
    Categoria(String categoriaOmdb, String categoriaPt){
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaPt = categoriaPt;
    }

    public static Categoria fromString(String text){
        for (Categoria categoria : Categoria.values()){
            if(categoria.categoriaOmdb.equalsIgnoreCase(text)| categoria.categoriaOmdb.equalsIgnoreCase(null)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada");
    }
    public static Categoria fromPortugues(String text){
        for (Categoria categoria : Categoria.values()){
            if(categoria.categoriaPt.equalsIgnoreCase(text)| categoria.categoriaPt.equalsIgnoreCase(null)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada");
    }
}
