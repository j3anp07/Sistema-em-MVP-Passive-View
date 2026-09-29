package repositorio;

import model.Categoria;
import model.Produto;

import java.util.List;
import java.util.Optional;

public interface IProdutoRepository {


    void salvar(Produto produto);
    List<Produto> buscarPorId(Long id);
    List<Produto> obterTodos();
    List<Produto> buscarPorNome(String nome);
    List<Produto> buscarPorCategoria(String nomeCategoria);
    boolean existePorCategoriaId(Long categoriaId);


}
