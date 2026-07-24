package br.com.sgp.view.resource;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import br.com.sgp.view.util.AppColors;

public class ResourceView extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private JTextField txtSearch;
    private JTable table;
    private DefaultTableModel model;

    private JButton btnNew;
    private JButton btnEdit;
    private JButton btnToggleActive;
    private JButton btnRefresh;
    private JButton btnClose;

    public ResourceView() {
        super(
                "Cadastro de equipamentos e recursos",
                true,
                true,
                true,
                true);

        setSize(950, 500);
        setLayout(new BorderLayout());

        add(createSearchPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        updateSelectionButtons();

        table.getSelectionModel()
                .addListSelectionListener(
                        event -> updateSelectionButtons());
    }

    private JPanel createSearchPanel() {
        JPanel panel =
                new JPanel(new FlowLayout(
                        FlowLayout.LEFT,
                        8,
                        8));

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        4,
                        8,
                        0,
                        8));

        panel.add(new JLabel("Pesquisar:"));

        txtSearch = new JTextField(30);
        txtSearch.setToolTipText(
                "Pesquise pelo código ou descrição");

        panel.add(txtSearch);

        return panel;
    }

    private JScrollPane createTablePanel() {
        model = new DefaultTableModel(
                new Object[] {
                        "ID",
                        "Código",
                        "Descrição",
                        "Categoria",
                        "Tipo",
                        "Unidade",
                        "Situação"
                },
                0) {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(
                    int row,
                    int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(24);
        table.setSelectionBackground(
                new Color(220, 231, 253));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(
                new Color(230, 230, 230));

        table.getTableHeader().setBackground(
                AppColors.ACCENT);
        table.getTableHeader().setForeground(
                Color.WHITE);
        table.getTableHeader().setFont(
                table.getTableHeader()
                        .getFont()
                        .deriveFont(Font.BOLD));
        table.getTableHeader().setOpaque(true);

        configureColumns();

        JScrollPane scrollPane =
                new JScrollPane(table);

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder(
                        4,
                        8,
                        4,
                        8));

        return scrollPane;
    }

    private void configureColumns() {
        table.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(45);

        table.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(110);

        table.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(320);

        table.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(110);

        table.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(110);

        table.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(70);

        table.getColumnModel()
                .getColumn(6)
                .setPreferredWidth(75);
    }

    private JPanel createButtonPanel() {
        JPanel panel =
                new JPanel(new FlowLayout(
                        FlowLayout.RIGHT,
                        8,
                        6));

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        8,
                        8,
                        8));

        btnNew = new JButton("Novo");
        AppColors.style(
                btnNew,
                AppColors.SUCCESS);

        btnEdit = new JButton("Editar");
        AppColors.style(
                btnEdit,
                AppColors.ACCENT);

        btnToggleActive =
                new JButton("Ativar/Inativar");
        AppColors.style(
                btnToggleActive,
                AppColors.WARNING);

        btnRefresh = new JButton("Atualizar");
        AppColors.style(
                btnRefresh,
                AppColors.NEUTRAL);

        btnClose = new JButton("Fechar");
        AppColors.style(
                btnClose,
                AppColors.NEUTRAL);

        btnClose.addActionListener(
                event -> closeView());

        panel.add(btnNew);
        panel.add(btnEdit);
        panel.add(btnToggleActive);
        panel.add(btnRefresh);
        panel.add(btnClose);

        return panel;
    }

    private void updateSelectionButtons() {
        boolean hasSelection =
                table.getSelectedRow() >= 0;

        btnEdit.setEnabled(hasSelection);
        btnToggleActive.setEnabled(hasSelection);
    }

    private void closeView() {
        int option =
                JOptionPane.showConfirmDialog(
                        this,
                        "Deseja fechar a tela de recursos?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    public JTextField getTxtSearch() {
        return txtSearch;
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public JButton getBtnNew() {
        return btnNew;
    }

    public JButton getBtnEdit() {
        return btnEdit;
    }

    public JButton getBtnToggleActive() {
        return btnToggleActive;
    }

    public JButton getBtnRefresh() {
        return btnRefresh;
    }
}