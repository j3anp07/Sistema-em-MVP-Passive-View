package service;

import exception.RegraNegocioException;
import model.Categoria;
import model.HistoricoPreco;
import model.Produto;
import repositorio.ICategoriaRepository;
import repositorio.IHistoricoPrecoRepository;
import repositorio.IProdutoRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CalculoPrecoService {

    private static final long INTERVALO_MINIMO_DIAS = 10;
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final IProdutoRepository produtoRepository;
    private final ICategoriaRepository categoriaRepository;
    private final IHistoricoPrecoRepository historicoPrecoRepository;

    public CalculoPrecoService(IProdutoRepository produtoRepository, ICategoriaRepository categoriaRepository, IHistoricoPrecoRepository historicoPrecoRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.historicoPrecoRepository = historicoPrecoRepository;
    }

    public List<HistoricoPreco> calcular() {
        return calcular(LocalDate.now());
    }

    public List<HistoricoPreco> calcular(LocalDate dataCalculo) {
        if (dataCalculo == null) {
            throw new RegraNegocioException("A data do cálculo é obrigatória.");
        }

        validarIntervalo(dataCalculo);

        List<HistoricoPreco> historicos = gerarHistoricos(dataCalculo);
        historicos.forEach(historicoPrecoRepository::salvar);
        return historicos;
    }

    public double calcularPrecoVenda(double precoCusto, double percentualLucro) {

        BigDecimal fator = BigDecimal.ONE.add(BigDecimal.valueOf(percentualLucro).movePointLeft(2));

        return BigDecimal.valueOf(precoCusto).multiply(fator).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private void validarIntervalo(LocalDate dataCalculo) {
        Optional<LocalDate> ultimoCalculo = buscarDataUltimoCalculo();
        if (ultimoCalculo.isEmpty()) {
            return;
        }

        LocalDate proximaDataPermitida = ultimoCalculo.get().plusDays(INTERVALO_MINIMO_DIAS);
        if (dataCalculo.isBefore(proximaDataPermitida)) {
            throw new RegraNegocioException("O novo cálculo ainda não pode ser realizado. Último cálculo em " + ultimoCalculo.get().format(FORMATO_DATA) + "; próximo permitido a partir de " + proximaDataPermitida.format(FORMATO_DATA) + ".");
        }
    }

    private Optional<LocalDate> buscarDataUltimoCalculo() {
        return historicoPrecoRepository.obterTodos().stream().map(HistoricoPreco::getData).max(Comparator.naturalOrder());
    }

    private List<HistoricoPreco> gerarHistoricos(LocalDate dataCalculo) {
        List<HistoricoPreco> historicos = new ArrayList<>();

        for (Produto produto : produtoRepository.obterTodos()) {
            double percentual = obterPercentualVigente(produto);
            double precoVenda = calcularPrecoVenda(produto.getPrecoCusto(), percentual);
            historicos.add(new HistoricoPreco(dataCalculo, percentual, precoVenda, produto));
        }

        return historicos;
    }

    private double obterPercentualVigente(Produto produto) {
        if (produto == null) {
            throw new RegraNegocioException("O produto não pode ser nulo.");
        }

        if (produto.getCategoria() == null) {
            throw new RegraNegocioException("O produto \"" + produto.getNome() + "\" não possui categoria associada.");
        }

        long idCategoria = produto.getCategoria().getId();
        List<Categoria> resultado = categoriaRepository.buscar(idCategoria);

        if (resultado != null) {
            for (Categoria c : resultado) {
                if (c.getId() == idCategoria) {
                    return c.getPercentualLucro();
                }
            }
        }

        throw new RegraNegocioException("Categoria do produto \"" + produto.getNome() + "\" não encontrada.");
    }
}
