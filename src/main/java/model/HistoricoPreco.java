package model;

import java.time.LocalDate;

public class HistoricoPreco {

    private final LocalDate data;
    private final double percentualLucro;
    private final Double precoVenda;
    private final Produto produto;

    public HistoricoPreco(LocalDate data, double percentualLucro, Double precoVenda, Produto produto) {
        this.data = data;
        this.percentualLucro = percentualLucro;
        this.precoVenda = precoVenda;
        this.produto = produto;
    }

    public LocalDate getData() {
        return data;
    }

    public double getPercentualLucro() {
        return percentualLucro;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public Produto getProduto() {
        return produto;
    }

    @Override
    public String toString() {
        return "Data: " + data + " - Percentual: " + percentualLucro + " - Preço de venda: " + precoVenda
                + " - Produto: " + (produto != null ? produto.getNome() : "-");
    }

}
