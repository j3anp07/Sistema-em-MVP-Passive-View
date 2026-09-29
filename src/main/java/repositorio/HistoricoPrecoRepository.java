package repositorio;

import model.HistoricoPreco;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HistoricoPrecoRepository implements IHistoricoPrecoRepository {

    private final List<HistoricoPreco> historicos = new ArrayList<>();

    @Override
    public void salvar(HistoricoPreco historicoPreco) {
        if (historicoPreco == null) {
            throw new IllegalArgumentException("O histórico de preço não pode ser nulo.");
        }

        historicos.add(historicoPreco);
    }

    @Override
    public List<HistoricoPreco> buscarPorProdutoId(Long produtoId) {

        if (produtoId == null) {
            throw new IllegalArgumentException("O ID não pode ser nulo");
        }

        List<HistoricoPreco> resultado = new ArrayList<>();

        for (HistoricoPreco historico : historicos) {
            if (historico.getProduto() != null) {
                if (historico.getProduto().getId() != null) {
                    if (historico.getProduto().getId().equals(produtoId)) {
                        resultado.add(historico);
                    }
                }
            }
        }

        Collections.sort(resultado, new Comparator<HistoricoPreco>() {
            @Override
            public int compare(HistoricoPreco h1, HistoricoPreco h2) {
                return h2.getData().compareTo(h1.getData());
            }
        });

        return resultado;
    }

    @Override
    public List<HistoricoPreco> obterTodos() {
        return new ArrayList<>(historicos);
    }

}