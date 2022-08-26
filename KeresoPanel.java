package konyvtar;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.JTextField;

@SuppressWarnings("serial")
class KeresoPanel extends JTextField {
	//Az egyes adattagok, kit�lt� sz�veg �s a k�nyvek list�ja
	
	private String helyettesString;
	private List<Konyvek> konyvek;
	
	//Konstruktor
	KeresoPanel(List<Konyvek> konyvek) {
		// TODO Auto-generated constructor stub
		this.konyvek = konyvek;
		this.helyettesString = konyvek.size() + " db k�nyv k�z�tti keres�s";
		this.setText(helyettesString);
		this.addFocusListener(new KonyvKeresFocusListener());
	}
	//Nyilv�ntartott k�nyvek setter f�ggv�nye
	public void setKonyvek(List<Konyvek> konyvek) {
		this.konyvek = konyvek;
		this.update();
	}
	//Friss�ti majd a program keres� panel�ben l�v� sz�veget.
	public void update() {
		this.helyettesString = konyvek.size() + " db k�nyv k�z�tti keres�s";
		this.setText(helyettesString);
	}
	//Helyettes�t�/kit�lt� sz�veg a keres� panelben
	public String getHelyettesitoString() {
		return helyettesString;
	}
	//Keres� mez�ben l�v� kit�lt� sz�veg helyett elhelyezi a beg�pelt sz�veget, friss�ti a benne l�v� sz�veget.
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
