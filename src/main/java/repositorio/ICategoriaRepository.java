package repositorio;

import model.Categoria;

import java.util.List;
import java.util.Optional;

public interface ICategoriaRepository {

    void salvar(Categoria categoria);
    List<Categoria> buscar(Long categoriaID);
    List<Categoria> listar();
    void excluir(long id);

}
