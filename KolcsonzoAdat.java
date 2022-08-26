package konyvtar;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import javax.swing.*;
import java.util.List;

@SuppressWarnings("serial")
public class KolcsonzoAdat extends AbstractTableModel {
	/*
	 * Kölcsönzõket tároló Combobox, legördülõ menüben lehet megadni ki a kölcsönzõ.
	 */
	private final JComboBox<Kolcsonzo> kolcsonzokBox;

	/*
	 * Kölcsönzõket tároló lista
	 */
	private final List<Kolcsonzo> tagjai;
	/*
	 * Konstruktor
	 */
	public KolcsonzoAdat(List<Kolcsonzo> kolcsonzok) {
		this.kolcsonzokBox = new JComboBox<>();
		this.tagjai = kolcsonzok;
		inicializalBox();
	}
	/*
	 * Sorok száma
	 */
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return tagjai.size();
	}
	/*
	 * Oszlopok száma
	 */
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 4;
	}
	/*
	 * Visszaadja, hogy az adott sor, adott oszlopának találkozásánál miylen értéket kell majd az adott cellában visszaadni,
	 * megjeleníteni.
	 * 
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		Kolcsonzo kolcsonzo = tagjai.get(rowIndex);
		switch(columnIndex) {
			case 0:
				return kolcsonzo.getKolnev();
			case 1:
				return kolcsonzo.getSzulIdo();
			case 2:
				return kolcsonzo.getKoltelszam();
			default:
				return kolcsonzo.getTagsagKezdete();
		}
	}
	/*
	 * A ComboBox értékeit, a kölcsönzõket fogja inicializálni.
	 */
	private void inicializalBox() {
		for(Kolcsonzo kolcsonzo : this.tagjai) {
			this.kolcsonzokBox.addItem(kolcsonzo);
		}
	}
	/*
	 * Oszlop neve
	 */
	@Override
    public String getColumnName(int column) {
		if(column==0) {
			return "Név";
		}
		else if(column==1) {
			return "Születés ideje";
		}
		else if(column==2) {
			return "Elérhetõség";
		}
		else {
			return "Kölcsönzési érvényessége";
		}
	}
	/*
	 * Az oslopban tárolt értékek típusa, osztálya.
	 */
	@Override
    public Class<?> getColumnClass(int columnIndex) {
		if(columnIndex==1||columnIndex==3) {
			return LocalDate.class;
		}
		return String.class;
	}
	/*
	 * Az adatok értékeinek beállítására szolgál
	 */
	@Override
    public void setValueAt(Object something, int rowIndex, int columnIndex) {
		if(columnIndex==0) {
			String neve = (String)something;
			if(!neve.equals("")) {
				tagjai.get(rowIndex).setKolnev(neve);
			}
		}
		else if(columnIndex==2) {
			String telszam = (String)something;
			if(!telszam.equals("")) {
				tagjai.get(rowIndex).setKoltelszam(telszam);
			}
		}
	}
	/*
	 * Kölcsönzõ hozzáadása
	 */
	public void hozzaad(Kolcsonzo kolcsonzo) {
		tagjai.add(kolcsonzo);
		this.kolcsonzokBox.addItem(kolcsonzo);
		fireTableDataChanged();
	}
	/*
	 * Kölcsönzõ törlése
	 */
	public void torol(Kolcsonzo kolcsonzo) {
		this.kolcsonzokBox.removeItem(kolcsonzo);
		this.tagjai.remove(kolcsonzo);
		fireTableDataChanged();
	}
	/*
	 * Kölcsönzõk legördülõ listájának visszaadása 6. oszlopban.
	 */
	public JComboBox<Kolcsonzo> getKolcsonzoBox() {
		return kolcsonzokBox;
	}
	/*
	 * Nem lesznek módosíthatóak az egyes cellák a Kölcsönzõk táblában.
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
}
