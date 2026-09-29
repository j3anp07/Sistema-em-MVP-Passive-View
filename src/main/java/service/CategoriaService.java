package service;

import exception.RegraNegocioException;
import model.Categoria;
import repositorio.ICategoriaRepository;
import repositorio.IProdutoRepository;

import java.util.List;

public class CategoriaService {

    private final ICategoriaRepository categoriaRepository;
    private final IProdutoRepository produtoRepository;

    public CategoriaService(ICategoriaRepository categoriaRepository, IProdutoRepository produtoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
    }

    public Categoria cadastrar(String nome, Double percentualLucro) {
        validarNome(nome, null);
        validarPercentualLucro(percentualLucro);

        Categoria categoria = new Categoria(nome.trim(), percentualLucro);
        categoriaRepository.salvar(categoria);
        return categoria;
    }

    public Categoria atualizar(long id, String nome, Double percentualLucro) {
        Categoria categoria = buscarExistente(id);

        validarNome(nome, id);
        validarPercentualLucro(percentualLucro);

        categoria.setNome(nome.trim());
        categoria.setPercentualLucro(percentualLucro);
        categoriaRepository.salvar(categoria);
        return categoria;
    }

    public void excluir(long id) {
        Categoria categoria = buscarExistente(id);

        if (produtoRepository.existePorCategoriaId(categoria.getId())) {
            throw new RegraNegocioException(
                    "A categoria \"" + categoria.getNome() + "\" não pode ser excluída, pois existem produtos associados a ela.");
        }

        categoriaRepository.excluir(id);
    }

    public List<Categoria> listar() {
        return categoriaRepository.listar();
    }

    private Categoria buscarExistente(long id) {
        if (id <= 0) {
            throw new RegraNegocioException("Categoria não encontrada.");
        }

        List<Categoria> resultado = categoriaRepository.buscar(id);

        if (resultado != null) {
            for (Categoria c : resultado) {
                if (c.getId() == id) {
                    return c;
                }
            }
        }

        throw new RegraNegocioException("Categoria não encontrada.");
    }

    private void validarNome(String nome, Long idIgnorado) {

        if (nome == null || nome.trim().isEmpty()) {
            throw new RegraNegocioException("O nome da categoria é obrigatório.");
        }

        String nomeNormalizado = nome.trim();
        List<Categoria> categoriasCadastradas = categoriaRepository.listar();

        if (categoriasCadastradas != null) {
            for (Categoria c : categoriasCadastradas) {

                boolean deveIgnorar = false;
                if (idIgnorado != null) {
                    if (c.getId() == idIgnorado) {
                        deveIgnorar = true;
                    }
                }

                if (!deveIgnorar) {
                    if (c.getNome() != null && c.getNome().trim().equalsIgnoreCase(nomeNormalizado)) {
                        throw new RegraNegocioException("Já existe uma categoria com o nome \"" + nomeNormalizado + "\".");
                    }
                }
            }
        }
    }

    private void validarPercentualLucro(Double percentualLucro) {
        if (percentualLucro == null) {
            throw new RegraNegocioException("O percentual de lucro é obrigatório.");
        }
        if (!Double.isFinite(percentualLucro) || percentualLucro < 0) {
            throw new RegraNegocioException("O percentual de lucro deve ser maior ou igual a zero.");
        }
    }
}