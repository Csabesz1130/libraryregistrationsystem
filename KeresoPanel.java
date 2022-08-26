package konyvtar;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.JTextField;

@SuppressWarnings("serial")
class KeresoPanel extends JTextField {
	//Az egyes adattagok, kitöltõ szöveg és a könyvek listája
	
	private String helyettesString;
	private List<Konyvek> konyvek;
	
	//Konstruktor
	KeresoPanel(List<Konyvek> konyvek) {
		// TODO Auto-generated constructor stub
		this.konyvek = konyvek;
		this.helyettesString = konyvek.size() + " db könyv közötti keresés";
		this.setText(helyettesString);
		this.addFocusListener(new KonyvKeresFocusListener());
	}
	//Nyilvántartott könyvek setter függvénye
	public void setKonyvek(List<Konyvek> konyvek) {
		this.konyvek = konyvek;
		this.update();
	}
	//Frissíti majd a program keresõ panelében lévõ szöveget.
	public void update() {
		this.helyettesString = konyvek.size() + " db könyv közötti keresés";
		this.setText(helyettesString);
	}
	//Helyettesítõ/kitöltõ szöveg a keresõ panelben
	public String getHelyettesitoString() {
		return helyettesString;
	}
	//Keresõ mezõben lévõ kitöltõ szöveg helyett elhelyezi a begépelt szöveget, frissíti a benne lévõ szöveget.
	private class KonyvKeresFocusListener implements FocusListener {
		@Override
        public void focusGained(FocusEvent e) {
			if(KeresoPanel.this.getText().equals(helyettesString)) {
				KeresoPanel.this.setText("");
			}
		}
		@Override
        public void focusLost(FocusEvent e) {
			if(KeresoPanel.this.getText().equals(helyettesString)) {
				KeresoPanel.this.update();
			}
		}
	}
	
	
}
