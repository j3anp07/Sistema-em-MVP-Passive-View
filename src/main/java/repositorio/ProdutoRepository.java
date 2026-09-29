package repositorio;

import model.Produto;

import java.util.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProdutoRepository implements IProdutoRepository {

    private final List<Produto> produtos = new ArrayList<>();

    private long sequenciaId = 1L;

    @Override
    public void salvar(Produto produto) {

        if(produto == null){
            throw new IllegalArgumentException("O Produto não pode ser nulo");
        }

        if (produto.getId() == null) {
            produto.setId(sequenciaId++);
            produtos.add(produto);
        }

        else {
            int indice = -1;
            for (int i = 0; i < produtos.size(); i++) {
                if (Objects.equals(produtos.get(i).getId(), produto.getId())) {
                    indice = i;
                    break;
                }
            }

            if (indice != -1) {
                produtos.set(indice, produto);
            } else {
                throw new IllegalArgumentException("Produto não encontrado para atualização com o ID: " + produto.getId());
            }
        }


    }

    @Override
    public List<Produto> buscarPorId(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("O ID não pode ser nulo");
        }

        if(id <= 0){
            throw new IllegalArgumentException("O ID deve ser maior que zero");
        }

        ArrayList<Produto> resultado = new ArrayList<>();

        for(Produto produto : produtos){
            if(produto.getId() == id){
                resultado.add(produto);
            }
        }

        return resultado;
    }

    @Override
    public List<Produto> obterTodos() {
        return Collections.unmodifiableList(new ArrayList<>(produtos));
    }

    @Override
    public List<Produto> buscarPorNome(String termo) {
        if (termo == null || termo.isBlank()) {
            return obterTodos();
        }

        String termoNormalizado = termo.trim().toLowerCase();
        return produtos.stream()
                .filter(p -> p.getNome() != null && p.getNome().toLowerCase().contains(termoNormalizado))
                .toList();
    }

    @Override
    public List<Produto> buscarPorCategoria(String nomeCategoria) {
        if (nomeCategoria == null || nomeCategoria.isBlank()) {
            return obterTodos();
        }

        String termoNormalizado = nomeCategoria.trim().toLowerCase();
        return produtos.stream()
                .filter(p -> p.getCategoria() != null
                        && p.getCategoria().getNome() != null
                        && p.getCategoria().getNome().toLowerCase().contains(termoNormalizado))
                .toList();
    }

    @Override
    public boolean existePorCategoriaId(Long categoriaId) {
        if (categoriaId == null) {
            return false;
        }
        return produtos.stream()
                .anyMatch(p -> p.getCategoria() != null && Objects.equals(p.getCategoria().getId(), categoriaId));
    }

    public void atualizar(Produto produtoExistente) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean existePorCodigo(String codigo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
