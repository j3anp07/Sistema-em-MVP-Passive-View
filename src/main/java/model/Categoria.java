package model;

public class Categoria {

    private static long contador = 0;
    private long id;
    private String nome;
    private double percentualLucro;

    public Categoria(String nome, double percentualLucro) {
        contador++;
        this.nome = nome;
        this.percentualLucro = percentualLucro;

    }


    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPercentualLucro() {
        return percentualLucro;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPercentualLucro(double percentualLucro) {
        this.percentualLucro = percentualLucro;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ID: "+id+" - Categoria: "+nome+" - "+percentualLucro;
    }

}
