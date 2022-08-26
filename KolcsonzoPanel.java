package konyvtar;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.text.ParseException;
import java.awt.event.FocusEvent;
import javax.swing.text.MaskFormatter;
import java.awt.*;

class KolcsonzoPanel extends JPanel {
	/*
	 * Kölcsönzõ táblázat Panelének adattagjai, ahol az egyes adatok megjelennek.
	 */
	private final JTextField nev;
	private final JTextField telszam;
	private final JFormattedTextField szulIdo;
	private final JPanel foPanel;
	/*
	 * Konstruktor, létrehozódnak és beállítódnak a táblázat objektumai.
	 * Születési idõ helyes megadása érdekében wlõre beállított formátumban
	 * kell megadni a dátumot.
	 */
	KolcsonzoPanel() {
		// TODO Auto-generated constructor stub
		
		this.foPanel = new JPanel(new BorderLayout(6, 6)); 
		JPanel cimkek = new JPanel(new GridLayout(0, 1, 2, 2)); 
		cimkek.add(new JLabel("Név", SwingConstants.RIGHT));
		cimkek.add(new JLabel("Születési ideje", SwingConstants.RIGHT));
		cimkek.add(new JLabel("Elérhetõsége", SwingConstants.RIGHT));
		this.foPanel.add(cimkek, BorderLayout.WEST);
		
		JPanel bevitel = new JPanel(new GridLayout(0, 1, 2, 2));
		this.nev = new JTextField(13);
		this.szulIdo = new JFormattedTextField();
		try {
			MaskFormatter datumM = new MaskFormatter("####-##-##");
			datumM.install(szulIdo);
		} catch(ParseException pex) {
			JOptionPane.showMessageDialog(null, "Nem megfelelõ a dátum alakja. Helyes alak: év-hh-nn","Nem megfelelõ alak." ,JOptionPane.WARNING_MESSAGE);
		}
		this.szulIdo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				szulIdo.setCaretPosition(0);
			}
		});
		this.telszam = new JTextField(13);
		this.nev.setMargin(new Insets(0,4,0,5)); 
		this.szulIdo.setMargin(new Insets(0,4,0,5)); 
		this.telszam.setMargin(new Insets(0,4,0,5)); 
		bevitel.add(telszam);
		bevitel.add(szulIdo);
		bevitel.add(nev);
		this.foPanel.add(bevitel, BorderLayout.CENTER); 
	}
	
	public JPanel getFoPanel() {
		return foPanel;
	}

	/**
	 * @return the nev
	 */
	public String getNevErtek() {
		return nev.getText();
	}

	public void setNevErtek(String nev) {
		this.nev.setText(nev);;
	}
	/**
	 * @return the telszam
	 */
	public String getTelszamErtek() {
		return telszam.getText();
	}
	
	public void setTelszamErtek(String telszam) {
		this.telszam.setText(telszam);
	}
	
	public String getSzulIdoErtek() {
		return szulIdo.getText();
	}
	
	public void setSzulIdoErtek(String szulIdo) {
		this.szulIdo.setText(szulIdo);
	}
	
}
