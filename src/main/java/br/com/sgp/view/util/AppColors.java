package br.com.sgp.view.util;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.JButton;

/**
 * Paleta de cores e utilitário de estilo para as telas de apontamento.
 * Cada cor tem um significado funcional, não é só decoração:
 * ACCENT identifica estrutura/identidade, as demais indicam o tipo de ação do botão.
 */
public final class AppColors {

    public static final Color ACCENT   = new Color(0x2F, 0x6F, 0xED); // azul  - identidade / Info
    public static final Color SUCCESS  = new Color(0x16, 0xA3, 0x4A); // verde - ação de confirmação (Registrar)
    public static final Color WARNING  = new Color(0xF9, 0x73, 0x16); // laranja - ação de reset (Limpar)
    public static final Color NEUTRAL  = new Color(0x8A, 0x94, 0xA6); // cinza-azulado - ação secundária
    public static final Color FIELD_BG = new Color(245, 245, 245);    // fundo de campo somente leitura

    private AppColors() {
    }

    /** Aplica o estilo colorido padrão a um botão de ação. */
    public static void style(JButton button, Color background) {
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setMargin(new Insets(6, 16, 6, 16));
    }
}