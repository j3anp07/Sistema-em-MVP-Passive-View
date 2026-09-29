/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

/**
 *
 * @author paulo.campos
 */

import model.HistoricoPreco;
import model.Produto;
import repositorio.IHistoricoPrecoRepository;
import view.HistoricoPrecoView;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author paulo.campos
 */
public class HistoricoPrecoPresenter {

    private HistoricoPrecoView view;
    private Produto produto;
    private IHistoricoPrecoRepository historicoPrecoRepository;
    private MainPresenter mainPresenter;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public HistoricoPrecoPresenter(MainPresenter mainPresenter, Produto produto, IHistoricoPrecoRepository historicoPrecoRepository) {
        this.mainPresenter = mainPresenter;
        this.produto = produto;
        this.historicoPrecoRepository = historicoPrecoRepository;
        this.view = new HistoricoPrecoView();
        configuraView();
    }

    private void configuraView() {
        view.setVisible(false);

        // Identificadores superiores do produto
        view.getTxtNomeProduto().setText(produto.getNome());
        view.getTxtNomeProduto().setEditable(false);
        
        String nomeCat = produto.getCategoria() != null ? produto.getCategoria().getNome() : "-";
        view.getTxtCategoria().setText(nomeCat);
        view.getTxtCategoria().setEditable(false);

        configurarTabela();
        carregarHistorico();

        // Botão Fechar
        view.getBtnFechar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    fechar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        view.setVisible(true);
    }

    private void configurarTabela() {
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Data", "Percentual de lucro (%)", "Preço de venda"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        view.getTblHistoricoPreco().setModel(model);
    }

    private void carregarHistorico() {
        DefaultTableModel model = (DefaultTableModel) view.getTblHistoricoPreco().getModel();
        model.setRowCount(0);

        // O repositório já retorna ordenado do mais recente para o mais antigo (Requisito 9)
        List<HistoricoPreco> historicos = historicoPrecoRepository.buscarPorProdutoId(produto.getId());

        for (HistoricoPreco h : historicos) {
            String dataFormatada = h.getData() != null ? h.getData().format(formatter) : "-";
            
            model.addRow(new Object[]{
                    dataFormatada,
                    String.format("%.2f", h.getPercentualLucro()),
                    String.format("%.2f", h.getPrecoVenda())
            });
        }
    }

    private void fechar() {
        view.dispose();
    }
}