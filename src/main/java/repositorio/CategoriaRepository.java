package repositorio;

import model.Categoria;

import java.util.*;

public class CategoriaRepository implements ICategoriaRepository {

    private ArrayList<Categoria> categorias;
    private long sequenciaId = 1L;

    public CategoriaRepository() {
        this.categorias = new ArrayList<>();
    }

    @Override
    public void salvar(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("A Categoria não pode ser nula");
        }

        if (categoria.getId() == null || categoria.getId() == 0) {
            categoria.setId(sequenciaId++);
            categorias.add(categoria);
        } else {
            int indice = -1;
            for (int i = 0; i < categorias.size(); i++) {
                if (Objects.equals(categorias.get(i).getId(), categoria.getId())) {
                    indice = i;
                    break;
                }
            }

            if (indice != -1) {
                categorias.set(indice, categoria);
            } else {
                throw new IllegalArgumentException("Categoria não encontrada para atualização com o ID: " + categoria.getId());
            }
        }
    }

    @Override
    public List<Categoria> buscar(Long categoriaID) {
        if (categoriaID == null) {
            throw new IllegalArgumentException("O ID não pode ser nulo");
        }

        if (categoriaID <= 0) {
            throw new IllegalArgumentException("O ID deve ser maior que zero");
        }

        ArrayList<Categoria> resultado = new ArrayList<>();

        for (Categoria categoria : categorias) {
            if (Objects.equals(categoria.getId(), categoriaID)) {
                resultado.add(categoria);
            }
        }

        return resultado;
    }

    @Override
    public List<Categoria> listar() {
        return new ArrayList<>(categorias);
    }

    @Override
    public void excluir(long id) {
        try {
            verificarExistencia(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        Iterator<Categoria> categoriaIterator = categorias.iterator();

        while (categoriaIterator.hasNext()) {
            Categoria categoria = categoriaIterator.next();
            if (categoria.getId() == id) {
                categoriaIterator.remove();
            }
        }
    }

    private void verificarExistencia(long id) throws Exception {
        for (Categoria categoria : categorias) {
            if (categoria.getId() == id) {
                return;
            }
        }

        throw new Exception("Categoria não encontrada para o id: " + id);
    }
}