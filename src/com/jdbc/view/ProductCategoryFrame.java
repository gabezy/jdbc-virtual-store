package com.jdbc.view;

import com.jdbc.controller.CategoryController;
import com.jdbc.controller.ProductController;
import com.jdbc.factory.CategoryControllerFactory;
import com.jdbc.factory.ProductControllerFactory;
import com.jdbc.model.Category;
import com.jdbc.model.Product;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class ProductCategoryFrame  extends JFrame{
    private static final long serialVersionUID = 1L;

    private JLabel labelName, labelDescription, labelcategory;
    private JTextField textName, textDescription;
    private JComboBox<Category> comboCategory;
    private JButton botaoSalvar, botaoEditar, botaoLimpar, botarApagar;
    private JTable tabela;
    private DefaultTableModel modelo;
    private ProductController productController;
    private CategoryController categoryController;

    public ProductCategoryFrame() throws SQLException {
        super("CRUD Produtos");
        Container container = getContentPane();
        setLayout(null);

        this.categoryController = new CategoryControllerFactory().getController();
        this.productController = new ProductControllerFactory().getController();

        labelName = new JLabel("Nome do Produto");
        labelDescription = new JLabel("Descrição do Produto");
        labelcategory = new JLabel("Categoria do Produto");

        labelName.setBounds(10, 10, 240, 15);
        labelDescription.setBounds(10, 50, 240, 15);
        labelcategory.setBounds(10, 90, 240, 15);

        labelName.setForeground(Color.BLACK);
        labelDescription.setForeground(Color.BLACK);
        labelcategory.setForeground(Color.BLACK);

        container.add(labelName);
        container.add(labelDescription);
        container.add(labelcategory);

        textName = new JTextField();
        textDescription = new JTextField();
        comboCategory = new JComboBox<Category>();

        comboCategory.addItem(new Category(0, "Selecione"));
        List<Category> categories = this.listarCategoria();
        for (Category category : categories) {
            comboCategory.addItem(category);
        }

        textName.setBounds(10, 25, 265, 20);
        textDescription.setBounds(10, 65, 265, 20);
        comboCategory.setBounds(10, 105, 265, 20);

        container.add(textName);
        container.add(textDescription);
        container.add(comboCategory);

        botaoSalvar = new JButton("Salvar");
        botaoLimpar = new JButton("Limpar");

        botaoSalvar.setBounds(10, 145, 80, 20);
        botaoLimpar.setBounds(100, 145, 80, 20);

        container.add(botaoSalvar);
        container.add(botaoLimpar);

        String[] columnNames = {"Identificador do Produto", "Nome do Produto", "Descrição do Produto"};

        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(columnNames);
        tabela = new JTable(modelo);

        preencherTabela();

        tabela.setBounds(10, 185, 760, 300);
        container.add(tabela);

        botarApagar = new JButton("Excluir");
        botaoEditar = new JButton("Alterar");

        botarApagar.setBounds(10, 500, 80, 20);
        botaoEditar.setBounds(100, 500, 80, 20);

        container.add(botarApagar);
        container.add(botaoEditar);


        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);


        botaoSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvar();
                limparTabela();
                preencherTabela();
            }
        });

        botaoLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpar();
            }
        });

        botarApagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletar();
                limparTabela();
                preencherTabela();
            }
        });

        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterar();
                limparTabela();
                preencherTabela();
            }
        });
    }

    private void limparTabela() {
        modelo.getDataVector().clear();
    }

    private void alterar() {
        Object objetoDaLinha = (Object) modelo.getValueAt(tabela.getSelectedRow(), tabela.getSelectedColumn());
        if (objetoDaLinha instanceof Integer) {
            Integer id = (Integer) objetoDaLinha;
            String nome = (String) modelo.getValueAt(tabela.getSelectedRow(), 1);
            String descricao = (String) modelo.getValueAt(tabela.getSelectedRow(), 2);
            this.productController.update(nome, descricao, id);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecionar o ID");
        }
    }

    private void deletar() {
        Object objetoDaLinha = (Object) modelo.getValueAt(tabela.getSelectedRow(), tabela.getSelectedColumn());
        if (objetoDaLinha instanceof Integer) {
            Integer id = (Integer) objetoDaLinha;
            this.productController.delete(id);
            modelo.removeRow(tabela.getSelectedRow());
            JOptionPane.showMessageDialog(this, "Item excluído com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecionar o ID");
        }
    }

    private void preencherTabela() {
        List<Product> produtos = listarProduto();
        try {
            for (Product produto : produtos) {
                modelo.addRow(new Object[] { produto.getId(), produto.getName(), produto.getDescription() });
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private List<Category> listarCategoria()  {
        return this.categoryController.list();
    }

    private void salvar() {
        if (!textName.getText().equals("") && !textDescription.getText().equals("")) {
            Product produto = new Product(textName.getText(), textDescription.getText());
            Category categoria = (Category) comboCategory.getSelectedItem();
            produto.setCategoryId(categoria.getId());
            this.productController.save(produto);
            JOptionPane.showMessageDialog(this, "Salvo com sucesso!");
            this.limpar();
        } else {
            JOptionPane.showMessageDialog(this, "Nome e Descri��o devem ser informados.");
        }
    }

    private List<Product> listarProduto() {
        return this.productController.list();
    }

    private void limpar() {
        this.textName.setText("");
        this.textDescription.setText("");
        this.comboCategory.setSelectedIndex(0);
    }

}
