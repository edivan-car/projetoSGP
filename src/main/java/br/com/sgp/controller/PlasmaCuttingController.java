package br.com.sgp.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;

import br.com.sgp.view.sector.form.PlasmaCuttingForm;

public class PlasmaCuttingController {

	private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private final PlasmaCuttingForm form;

	public PlasmaCuttingController(PlasmaCuttingForm form) {
		this.form = form;
		initActions();
	}

	private void initActions() {
		
		for (JButton btn : form.getRackButtons()) {
	        configurarRack(btn);
	    }

	    configurarRack(form.getBtnFixedSupport());
	    configurarRack(form.getBtnMobileSupport());
	    
	    form.addCleanRackListener(e -> {
	        form.clearRackField();
	    });
	    
	    form.getBtnGenerate().addActionListener(e -> {           // <- incluir (bloco inteiro)
	        LocalDate date = LocalDate.now();

	        if (form.getChkPreviousDay().isSelected()) {
	            date = date.minusDays(1);
	        }

	        form.getTxtDate().setText(date.format(FORMAT));
	        form.getTxtShift().requestFocusInWindow(); 
	    }); 
	}
	
	private void configurarRack(JButton button) {
        button.addActionListener(e -> {
            form.getTxtRack().setText(button.getText());
        });
    }
}