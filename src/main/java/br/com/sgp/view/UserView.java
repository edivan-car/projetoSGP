package br.com.sgp.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import br.com.sgp.view.util.AppColors;

public class UserView extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	
	private JTable table;
    private DefaultTableModel model;

    private JButton btnNew;
    private JButton btnEdit;
    private JButton btnRefresh;
    private JButton btnClose;
    private JButton btnResetPassword;

    public UserView() {
        super("Usuários", true, true, true, true);

        setSize(680, 400);
        setLayout(new BorderLayout());
        setLocation(50, 50);

        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        btnEdit.setEnabled(false);
        
        table.getSelectionModel().addListSelectionListener(e -> {
        	boolean selected = table.getSelectedRow() != -1;
            btnEdit.setEnabled(selected);
            btnResetPassword.setEnabled(selected);
        });
    }

    private JScrollPane createTablePanel() {

        model = new DefaultTableModel(
                new Object[]{"ID", "Usuário", "Nome", "Perfil", "Setor"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // ID
        table.getColumnModel().getColumn(0).setMinWidth(50);
        table.getColumnModel().getColumn(0).setMaxWidth(70);

        // Usuário
        table.getColumnModel().getColumn(1).setPreferredWidth(120);

        // Nome
        table.getColumnModel().getColumn(2).setPreferredWidth(250);

        // Perfil
        table.getColumnModel().getColumn(3).setPreferredWidth(100);

        // Setor
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        
        table.setRowHeight(24);
        table.setSelectionBackground(new Color(220, 231, 253));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(230, 230, 230));

        table.getTableHeader().setBackground(AppColors.ACCENT);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(table.getTableHeader().getFont().deriveFont(Font.BOLD));
        table.getTableHeader().setOpaque(true); 

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(8, 8, 4, 8));
        return scroll;
    }

    private JPanel createButtonPanel() {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 6));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 8, 8, 8));

        btnNew = new JButton("Novo");
        AppColors.style(btnNew, AppColors.ACCENT);
        
        btnEdit = new JButton("Editar");
        AppColors.style(btnEdit, AppColors.ACCENT);
        
        btnRefresh = new JButton("Atualizar");
        AppColors.style(btnRefresh, AppColors.NEUTRAL);
        
        btnClose = new JButton("Fechar");
        AppColors.style(btnClose, AppColors.NEUTRAL);

        btnClose.addActionListener(e -> {
            int opt = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente fechar?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION
            );
            if (opt == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        btnResetPassword = new JButton("Resetar Senha");
        AppColors.style(btnResetPassword, AppColors.WARNING);
        btnResetPassword.setEnabled(false);
        
        panel.add(btnNew);
        panel.add(btnEdit);
        panel.add(btnResetPassword);
        panel.add(btnRefresh);
        panel.add(btnClose);

        return panel;
    }

    public JTable getTable() { return table; }
    public DefaultTableModel getModel() { return model; }
    public JButton getBtnNew() { return btnNew; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnRefresh() { return btnRefresh; }
    public JButton getBtnResetPassword() { return btnResetPassword; }
}
