package br.com.sgp.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.SwingUtilities;

import br.com.sgp.service.ThermalCuttingRefGenerator;
import br.com.sgp.view.sector.dialog.ThermalCuttingInfoDialog;
import br.com.sgp.view.sector.form.ThermalCuttingForm;

public class ThermalCuttingController {

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final ThermalCuttingForm form;

    public ThermalCuttingController(ThermalCuttingForm form) {
        this.form = form;
        initActions();
    }

    private void initActions() {

        form.getBtnInfo().addActionListener(e -> {

            ThermalCuttingInfoDialog dialog =
                new ThermalCuttingInfoDialog(
                    SwingUtilities.getWindowAncestor(form)
                );

            dialog.setVisible(true);

            if (dialog.isConfirmed()) {

                String linha = dialog.getLinhaMontagem();
                LocalDate dataReceb = dialog.getDataRecebimento();
                LocalDate dataProg = LocalDate.parse(
                        dialog.getDataProgramacao(),
                        FORMAT
                );

                String ref = ThermalCuttingRefGenerator.gerar(
                        dataProg,
                        dataReceb,
                        linha
                );

                form.setLinhaMontagem(linha);
                form.setTextDataPlano(dataProg);
                form.setTextDataRecebimento(dataReceb);
                form.setProgCorte(ref);
            }
        });
    }
}