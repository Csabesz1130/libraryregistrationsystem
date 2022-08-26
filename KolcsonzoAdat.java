package konyvtar;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import javax.swing.*;
import java.util.List;

@SuppressWarnings("serial")
public class KolcsonzoAdat extends AbstractTableModel {
	/*
	 * K�lcs�nz�ket t�rol� Combobox, leg�rd�l� men�ben lehet megadni ki a k�lcs�nz�.
	 */
	private final JComboBox<Kolcsonzo> kolcsonzokBox;

	/*
	 * K�lcs�nz�ket t�rol� lista
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
	 * Sorok sz�ma
	 */
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return tagjai.size();
	}
	/*
	 * Oszlopok sz�ma
	 */
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 4;
	}
	/*
	 * Visszaadja, hogy az adott sor, adott oszlop�nak tal�lkoz�s�n�l miylen �rt�ket kell majd az adott cell�ban visszaadni,
	 * megjelen�teni.
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
	 * A ComboBox �rt�keit, a k�lcs�nz�ket fogja inicializ�lni.
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
			return "N�v";
		}
		else if(column==1) {
			return "Sz�let�s ideje";
		}
		else if(column==2) {
			return "El�rhet�s�g";
		}
		else {
			return "K�lcs�nz�si �rv�nyess�ge";
		}
	}
	/*
	 * Az oslopban t�rolt �rt�kek t�pusa, oszt�lya.
	 */
	@Override
    public Class<?> getColumnClass(int columnIndex) {
		if(columnIndex==1||columnIndex==3) {
			return LocalDate.class;
		}
		return String.class;
	}
	/*
	 * Az adatok �rt�keinek be�ll�t�s�ra szolg�l
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
	 * K�lcs�nz� hozz�ad�sa
	 */
	public void hozzaad(Kolcsonzo kolcsonzo) {
		tagjai.add(kolcsonzo);
		this.kolcsonzokBox.addItem(kolcsonzo);
		fireTableDataChanged();
	}
	/*
	 * K�lcs�nz� t�rl�se
	 */
	public void torol(Kolcsonzo kolcsonzo) {
		this.kolcsonzokBox.removeItem(kolcsonzo);
		this.tagjai.remove(kolcsonzo);
		fireTableDataChanged();
	}
	/*
	 * K�lcs�nz�k leg�rd�l� list�j�nak visszaad�sa 6. oszlopban.
	 */
	public JComboBox<Kolcsonzo> getKolcsonzoBox() {
		return kolcsonzokBox;
	}
	/*
	 * Nem lesznek m�dos�that�ak az egyes cell�k a K�lcs�nz�k t�bl�ban.
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
}
