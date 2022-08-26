package konyvtar;

import javax.swing.*;
import java.awt.*;

public class KonyvPanel extends JPanel {
	/*
	 * A k�nyvek t�bl�zat�nak alpajaiul szolg�l� objektumok, ahol az adotok megjelennek
	 * 
	 */
	private final JTextField szerzo;
	private final JTextField cime;
	private final JTextField nyelve;
	private final JTextField megjelenes_id;
	private final JPanel foPanel;
	private final JComboBox<String> mufaj;
	private final JRadioButton pipa;
	/*
	 * Konstruktor. Oszlopok neveinek l�trehoz�sa, az adatok cell�inak (JTextField,...)
	 * l�trehoz�sa, hozz�ad�sa a f�panelhez, t�rbeli orient�ci� be�ll�t�sa.
	 */
	KonyvPanel() {
		this.foPanel = new JPanel(new BorderLayout(5, 5)); 
		JPanel cimkek = new JPanel(new GridLayout(0, 1, 2, 2));
		cimkek.add(new JLabel("Szerz�", SwingConstants.RIGHT));
		cimkek.add(new JLabel("C�m",SwingConstants.RIGHT));
		cimkek.add(new JLabel("Megjelen�s �ve",SwingConstants.RIGHT));
		cimkek.add(new JLabel("M�faj",SwingConstants.RIGHT));
		cimkek.add(new JLabel("Nyelve",SwingConstants.RIGHT));
		cimkek.add(new JLabel("K�lcs�nz�si st�tusz",SwingConstants.RIGHT));
		foPanel.add(cimkek, BorderLayout.WEST);
		JPanel felvetel = new JPanel(new GridLayout(0, 1, 2, 2)); 
		this.szerzo = new JTextField(23);
		this.cime= new JTextField(23);
		this.megjelenes_id = new JTextField(23);
		this.mufaj = new JComboBox<>();
		for(Mufaj muf : Mufaj.values()) {
			mufaj.addItem(muf.getLocN());
		}
		this.nyelve = new JTextField("magyarul",23);
		
		ButtonGroup kolcsGombok = new ButtonGroup();
		this.pipa = new JRadioButton("Igen");
		JRadioButton nem = new JRadioButton("Nem", true);
		JPanel gombok = new JPanel();
		gombok.setLayout(new BoxLayout(gombok, BoxLayout.X_AXIS)); 
		gombok.add(pipa);
		gombok.add(nem);
		kolcsGombok.add(pipa);
		kolcsGombok.add(nem);
		felvetel.add(szerzo);
		felvetel.add(cime);
		felvetel.add(megjelenes_id);
		felvetel.add(mufaj);
		felvetel.add(nyelve);
		felvetel.add(gombok, BorderLayout.WEST); 
		foPanel.add(felvetel, BorderLayout.CENTER); 
	}

	/**
	 * @return the szerzo
	 */
	public String getSzerzo() {
		return szerzo.getText();
	}

	/**
	 * @return the cime
	 */
	public String getCime() {
		return cime.getText();
	}

	/**
	 * @return the nyelve
	 */
	public String getNyelve() {
		return nyelve.getText();
	}

	/**
	 * @return the megjelenes_id
	 */
	public String getMegjelenes_id() {
		return megjelenes_id.getText();
	}

	/**
	 * @return the foPanel
	 */
	public JPanel getFoPanel() {
		return foPanel;
	}

	/**
	 * @return the mufaj
	 */
	public String getMufaj() {
		return (String)mufaj.getSelectedItem();
	}

	/**
	 * @return the pipa
	 */
	public boolean getPipa() {
		return pipa.isSelected();
	}
	
	
}
