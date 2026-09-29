package repositorio;

import model.HistoricoPreco;
import java.util.List;

public interface IHistoricoPrecoRepository {

    void salvar(HistoricoPreco historicoPreco);
    List<HistoricoPreco> buscarPorProdutoId(Long produtoId);
    List<HistoricoPreco> obterTodos();
}
