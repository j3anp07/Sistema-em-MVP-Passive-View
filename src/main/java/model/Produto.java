package model;

public class Produto {

    private Long id;
    private String nome;
    private Double precoCusto;
    private Categoria categoria;

    public Produto(String nome, Double precoCusto, Categoria categoria) {
        this.nome = nome;
        this.precoCusto = precoCusto;
        this.categoria = categoria;
    }

    public Produto(Long id, String nome, Double precoCusto, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.precoCusto = precoCusto;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(Double precoCusto) {
        this.precoCusto = precoCusto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        String nomeCategoria = (categoria != null) ? categoria.getNome() : "-";
        return "ID: " + id + " - Produto: " + nome + " - Custo: " + precoCusto + " - Categoria: " + nomeCategoria;
    }

}