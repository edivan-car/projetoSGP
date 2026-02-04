package br.com.sgp.view.util;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GridBagHelper {

    public GridBagConstraints c(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    public GridBagConstraints span(GridBagConstraints gbc, int width) {
        gbc.gridwidth = width;
        return gbc;
    }

    public GridBagConstraints weight(GridBagConstraints gbc, double x, double y) {
        gbc.weightx = x;
        gbc.weighty = y;
        return gbc;
    }

    public GridBagConstraints fill(GridBagConstraints gbc, int fill) {
        gbc.fill = fill;
        return gbc;
    }
}
