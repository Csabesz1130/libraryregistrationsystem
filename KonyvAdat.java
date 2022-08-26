package konyvtar;

import java.util.ArrayList;
import java.util.List;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

@SuppressWarnings("serial")
public class KonyvAdat extends AbstractTableModel {
	private final List<Konyvek> mindenkonyv;
	/*
	 * Konstruktor paraméter nélkül
	 */
	public KonyvAdat() {
		this.mindenkonyv = new ArrayList<>();
	}
	/*
	 * Konstruktor paraméterrel
	 */
	public KonyvAdat(List<Konyvek> osszkonyvek) {
		// TODO Auto-generated constructor stub
		this.mindenkonyv = osszkonyvek;
	}
	/*
	 * Visszaadja, hogy melyik sorban vagyunk.
	 */
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.mindenkonyv.size();
	}
	/*
	 * Az oszlopot adja vissza, ol. melyikre kattintottunk.
	 */
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 7;
	}
	/*
	 * Beépített setValueAt függvény felüldefiniálása, amelyben az egyes 
	 * adattagok értékeinek beállítása történik a Konyvek osztályra nézve.
	 * Követi a konstruktor paramétereinek, valamint a JPanel-ben történõ adatok megadása 
	 * során megkövetelt adatbeviteli sorrendet.
	 */
	@Override
	public void setValueAt(Object o, int i, int i1)
	{
		Konyvek kk = mindenkonyv.get(i);
			switch(i1) { 
				case 0:
					String szerzo = (String)o;
					if(!szerzo.equals("")) {
						kk.setKonyvSzerzo(szerzo);
					}
					break;
				case 1:
					String cime = (String)o;
					if(!cime.equals("")) {
						kk.setKonyvCime(cime);
					}
					break;
				case 2:
					int ido =(Integer)o;
					if(ido > 0) {
						kk.setMegjelenes_eve(ido);
					}
					break;
				case 3:
					kk.setMufaj(Mufaj.ertek((String)o, "HU"));
					break;
				case 4:
					String nyelv = (String)o;
					if(!nyelv.equals("")) {
						kk.setKonyvNyelv(nyelv.toLowerCase());
					}
					break;
				case 5:
					KolcsonzesKezelo.kolcsStatuszValt(kk, o);
					fireTableDataChanged();
					break;
				case 6:
					if(kk.isKolcsstatusz()==true) {
						KolcsonzesKezelo.kolcsonzoValt(kk, o);
						fireTableDataChanged();
					}
					break;
			}
			/*mindenkonyv.set(i, kk);
			this.fireTableRowsUpdated(i, i);*/
		
	}
	/*
	 * Visszaadja, hogy milyen érték van egy adott oszlop-sor interszekciójában.
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Konyvek konyvem = mindenkonyv.get(rowIndex);
		switch(columnIndex) {
			case 0: return konyvem.getKonyvSzerzo();
			case 1: return konyvem.getKonyvCime();
			case 2: return konyvem.getMegjelenes_eve();
			case 3: return konyvem.getMufaj().getLocN();
			case 4: return konyvem.getKonyvNyelv();
			case 5: return konyvem.isKolcsstatusz();
			default: 
				Kolcsonzo kolcsonzo = konyvem.getKikolcsonzi();
				if(kolcsonzo!=null) {
					return kolcsonzo.getKolnev();
				}
				return null;
			
		}
	}
	/*
	 * Oszlop neve
	 */
	@Override
	public String getColumnName(int idx)
	{
		switch(idx)
		{
			case 0: return "Szerzõ";
			case 1: return "Címe";
			case 2: return "Megjelenésének éve";
			case 3: return "Mûfaj";
			case 4: return "Nyelv";
			case 5: return "Állapot";
			default: return "Kölcsönzõ neve";
		}
	}
    
	/*
	 * Könyv hozzáadása
	 */
	public void addKonyv(Konyvek konyv) {
		mindenkonyv.add(konyv);
		fireTableDataChanged();
	}
	/*
	 * Könyv eltávolítása
	 */
	public void deleteKonyv(Konyvek konyv) {
		mindenkonyv.remove(konyv);
		fireTableDataChanged();
	}
	/*
	 * Milyen osztályú, típusú adat van az egyes oszlopokban.
	 */
	@Override
	public Class<?> getColumnClass(int i)
	{
		
		switch(i)
		{
			case 2:
				return Integer.class;
			case 5:
				return Boolean.class;
			default:
				return String.class;
		}
	}
	/*
	 * A változtatások érzékeléséért felelõs az egyes listákat illetõen a táblában.
	 */
	public void addTableModelListener(TableModelListener tl) {
		super.addTableModelListener(tl); 
	}
	/*
	 * Minden oszlop módosítható.
	 */
	public boolean isCellEditable(int i, int i1)
	{
		boolean[] b={true,true,true,true,true,true,true};
		return (i1<getColumnCount() && i1>=0)?b[i1]:false;
	}
	/*
	 * Kölcsönzöttek kilistázásához szükséges RowSorter
	 */
	public RowSorter<KonyvAdat> csakKolcsonzottek() {
		RowFilter<KonyvAdat, Integer> filter = new RowFilter<>() {
			@Override
			public boolean include(Entry<? extends KonyvAdat, ? extends Integer> entry) {
				Konyvek akonyv = mindenkonyv.get(entry.getIdentifier());
				return akonyv.getKikolcsonzi() != null;	
			}
		};
		TableRowSorter<KonyvAdat> valogat = new TableRowSorter<>(this);
		valogat.setRowFilter(filter);
		return valogat;
	}

}
