package service;

import exception.RegraNegocioException;
import model.Categoria;
import model.Produto;
import repositorio.ICategoriaRepository;
import repositorio.IProdutoRepository;

import java.util.List;

public class ProdutoService {

    private final IProdutoRepository produtoRepository;
    private final ICategoriaRepository categoriaRepository;

    public ProdutoService(IProdutoRepository produtoRepository, ICategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Produto cadastrar(String nome, Double precoCusto, Categoria categoria) {
        validarNome(nome);
        validarPrecoCusto(precoCusto);
        Categoria categoriaExistente = obterCategoriaExistente(categoria);

        Produto novoProduto = new Produto(nome.trim(), precoCusto, categoriaExistente);
        produtoRepository.salvar(novoProduto);

        return novoProduto;
    }

    public Produto atualizar(Long id, String nome, Double precoCusto, Categoria categoria) {
        if (id == null || id <= 0) {
            throw new RegraNegocioException("Produto não encontrado.");
        }

        Produto produto = buscarProdutoPorId(id);

        validarNome(nome);
        validarPrecoCusto(precoCusto);
        Categoria categoriaExistente = obterCategoriaExistente(categoria);

        produto.setNome(nome.trim());
        produto.setPrecoCusto(precoCusto);
        produto.setCategoria(categoriaExistente);

        produtoRepository.salvar(produto);

        return produto;
    }

    private Produto buscarProdutoPorId(Long id) {
        List<Produto> resultado = produtoRepository.buscarPorId(id);

        if (resultado != null) {
            for (Produto p : resultado) {
                if (p.getId() != null && p.getId().equals(id)) {
                    return p;
                }
            }
        }

        throw new RegraNegocioException("Produto não encontrado.");
    }

    private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new RegraNegocioException("O nome do produto é obrigatório.");
        }
    }

    private void validarPrecoCusto(Double precoCusto) {
        if (precoCusto == null) {
            throw new RegraNegocioException("O preço de custo é obrigatório.");
        }
        if (!Double.isFinite(precoCusto) || precoCusto <= 0) {
            throw new RegraNegocioException("O preço de custo deve ser maior que zero.");
        }
    }

    private Categoria obterCategoriaExistente(Categoria categoria) {
        if (categoria == null) {
            throw new RegraNegocioException("A categoria do produto é obrigatória.");
        }

        long idCategoria = categoria.getId();
        List<Categoria> resultado = categoriaRepository.buscar(idCategoria);

        if (resultado != null) {
            for (Categoria c : resultado) {
                if (c.getId() == idCategoria) {
                    return c;
                }
            }
        }

        throw new RegraNegocioException("A categoria informada não existe.");
    }
}