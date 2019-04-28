package gui;

import java.awt.EventQueue;

public class ColtExpress {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			Modele modele = new Modele();
			Vue vue = new Vue(modele);
		    });
	}
}
