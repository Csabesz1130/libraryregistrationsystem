package konyvtar;

import javax.swing.table.TableRowSorter;
import java.io.*;
import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@SuppressWarnings("serial")
public class Konyvtar implements Serializable {
	
	private final List<Konyvek> konyvek;
	private final List<Kolcsonzo> kolcsonzok;
	private transient String szerializaloUtvonal;
	private transient KonyvAdat konyvekadatai;
	private transient KolcsonzoAdat kolcsonzokadatai;
	
	public Konyvtar() {
		// TODO Auto-generated constructor stub
		this.konyvek = new ArrayList<>();
		this.kolcsonzok = new ArrayList<>();
	}

	/**
	 * @return the konyvek
	 */
	public List<Konyvek> getKonyvek() {
		return konyvek;
	}

	/**
	 * @return the kolcsonzok
	 */
	public List<Kolcsonzo> getKolcsonzok() {
		return kolcsonzok;
	}

	/**
	 * @return the szerializaloUtvonal
	 */
	public String getSzerializaloUtvonal() {
		return szerializaloUtvonal;
	}

	/**
	 * @param szerializaloUtvonal the szerializaloUtvonal to set
	 */
	public void setSzerializaloUtvonal(String szerializaloUtvonal) {
		this.szerializaloUtvonal = szerializaloUtvonal;
	}

	/**
	 * @return the konvekadatai
	 */
	public KonyvAdat getKonyvekadatai() {
		return konyvekadatai;
	}

	/**
	 * @return the kolcsonzokadatai
	 */
	public KolcsonzoAdat getKolcsonzokadatai() {
		return kolcsonzokadatai;
	}
	/*
	 * Az adatok, ObjectOutputStream haszn�lat�val
	 * t�rt�n�, param�terk�nt megadott helyre t�rt�n� lement�se.
	 */
	public void mentes(String szerializaloUtvonal) {
		try {
			ObjectOutputStream ops = new ObjectOutputStream(new FileOutputStream(szerializaloUtvonal));
			ops.writeObject(this);
			ops.close();
		} catch(IOException ioex) {
			JOptionPane.showMessageDialog(null, "Sikertelen a k�nyvek ment�se, hiba t�rt�nt:" + ioex.getMessage(), "Probl�ma ad�dott.", JOptionPane.ERROR_MESSAGE);
		}
	}
	//Ment�s az el�z� f�ggv�ny seg�ts�g�vel
	public void mentes() {
		mentes(this.szerializaloUtvonal);
	}
	//Ment�s mint funkci�hoz sz�ks�ges f�ggv�ny, dokument�ci�ban kifejtve a funkci� m�k�d�se.
	public void mentesMasKepp(String szerializaloUtvonal) {
		int kiterj = szerializaloUtvonal.indexOf(".libdat");
		if(kiterj > 0) {
			String kiter = szerializaloUtvonal.substring(kiterj);
			if(kiter.equals(".libdat")==false) { 
				kiterj = -1;
			}
		}
		szerializaloUtvonal = (kiterj==1) ? szerializaloUtvonal + ".libdat" : szerializaloUtvonal;
		if(new File(szerializaloUtvonal).exists()==false) {
			this.szerializaloUtvonal = szerializaloUtvonal;
			this.mentes();
		}
		else {
			int valasz = JOptionPane.showConfirmDialog(null, "Ugyanilyen nev� f�jlt tal�ltam. L�trehoz egy �jat?", "�tnevez�s", JOptionPane.YES_NO_OPTION);
			if(valasz==JOptionPane.YES_OPTION) {
				this.szerializaloUtvonal=szerializaloUtvonal;
				this.mentes();
			}
		}
	}
	/*
	 * Tranziens, teh�t nem soros�tott v�ltoz�k inicializ�l�sa egy param�terk�nt
	 * megadott el�r�si �t haszn�lat�val.
	 */
	public void inicializalTValtozok(String szerializaloUt) {
		this.konyvekadatai = new KonyvAdat(this.konyvek);
		this.kolcsonzokadatai = new KolcsonzoAdat(this.kolcsonzok);
		this.szerializaloUtvonal = szerializaloUt;
	}
	//Adatok f�jlb�l t�rt�n� beolvas�sa, figyelve a lehets�ges hib�kra �s azokat lekezelve, jelezve a felhaszn�l�nak.
	public static Konyvtar fajlbolBeolvas(Konyvtar konyvtar) {
		try {
			String utvonal = konyvtar.szerializaloUtvonal;
			ObjectInputStream konyvtarBeolvasIS = new ObjectInputStream(new FileInputStream(utvonal));
			konyvtar = (Konyvtar)konyvtarBeolvasIS.readObject();
			konyvtarBeolvasIS.close();
			konyvtar.inicializalTValtozok(utvonal);
		} catch(StreamCorruptedException stCE) {
			JOptionPane.showMessageDialog(null, "S�r�lt a forr�s" + "Hiba r�szletei:" + stCE.getMessage(), "Nem siker�lt megtal�lni a forr�st.", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		catch(FileNotFoundException fajlEx) {
			JOptionPane.showMessageDialog(null, "A f�jlt nem siker�lt megtal�lni." + "Az Import funkci�val pr�b�lkozhat egy m�sik f�jln�l. �zenet:" + fajlEx.getMessage(), "Nem siker�lt megtal�lni a forr�st.", JOptionPane.WARNING_MESSAGE);
			konyvtar = new Konyvtar();
			konyvtar.inicializalTValtozok("konyvtar.libdat");
		}
		catch(Exception exc) {
			JOptionPane.showMessageDialog(null, "Hiba az adatok beolvas�s�t k�vet�en." + "Hiba r�szletei" + exc.getMessage(), "Nem siker�lt megtal�lni a forr�st.", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		return konyvtar;
	}
	//Keres�s megval�s�t�sa, amivel lehet szerz�ben �s c�mben keresni vagy csak c�m alapj�n.
	public RowSorter<KonyvAdat> kereses(String mit, boolean szerzobenIs) {
		RowFilter<KonyvAdat, Integer> konyvFilter;
		if(szerzobenIs==false) {
			konyvFilter = new RowFilter<>() {
				@Override
				public boolean include(Entry<? extends KonyvAdat, ? extends Integer> entry) {
					// TODO Auto-generated method stub
					Konyvek akonyv = konyvek.get(entry.getIdentifier());
					return akonyv.getKonyvCime().contains(mit);
				}
			};
		}
		else {
			konyvFilter = new RowFilter<>() {

				@Override
				public boolean include(Entry<? extends KonyvAdat, ? extends Integer> entry) {
					// TODO Auto-generated method stub
					Konyvek akonyv = konyvek.get(entry.getIdentifier());
					return akonyv.getKonyvCime().contains(mit) || akonyv.getKonyvSzerzo().contains(mit);
				}
				
			};
		}
		TableRowSorter<KonyvAdat> valogat = new TableRowSorter<>(konyvekadatai);
		valogat.setRowFilter(konyvFilter);
		return valogat;
	}
	//Kik�llcs�nz�tt k�nyvek megjelen�t�se
	public RowSorter<KonyvAdat> kolcsonzottekFilter() {
		return konyvekadatai.csakKolcsonzottek();
	}
	
	public boolean addKonyv(String szerzoje, String cime, String megj_ideje, Mufaj mufa, String nyelv, boolean kolcsonozheto) {
		if(!szerzoje.equals("") && !cime.equals("") && !megj_ideje.equals("") && !nyelv.equals("")) {
			Konyvek konyv = new Konyvek(cime, szerzoje, Integer.parseInt(megj_ideje), mufa, nyelv, kolcsonozheto);
			this.konyvekadatai.addKonyv(konyv);
			return true;
		}
		return false;
	}
	//K�nyv elt�vol�t�sa a k�nyvt�rb�l
	public void eltavolit(Konyvek konyv) {
		if(konyv == null) {
			return;
		}
		if(konyv.getKikolcsonzi() != null) {
			konyv.getKikolcsonzi().getKolcsonzottKonyvek().remove(konyv);
		}
		this.konyvekadatai.deleteKonyv(konyv);
	}
	//K�lcs�nz� felv�tele a k�nyvt�rba.
	public boolean addKolcsonzo(String neve, String szulIdo, String telszam) {
		if(!neve.equals("") && !szulIdo.equals("") && !telszam.equals("")) {
			DateTimeFormatter datumFormatum = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate szuletesiIdo = LocalDate.parse(szulIdo, datumFormatum);
			Kolcsonzo kolcsonzo = new Kolcsonzo(neve, telszam, szuletesiIdo);
			this.kolcsonzokadatai.hozzaad(kolcsonzo);
			return true;
		}
		return false;
	}
	//Adott k�lcs�nz� egyes adattagjainak m�dos�t�sa
	public boolean modositKolcsonzo(Kolcsonzo kolcsonzo, String neve, String szulIdo, String telszam) {
		if(!neve.equals("") && !szulIdo.equals("") && !telszam.equals("")) {
			kolcsonzo.setKolnev(neve);
			DateTimeFormatter datumFormatum = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			kolcsonzo.setSzulIdo(LocalDate.parse(szulIdo, datumFormatum));
			kolcsonzo.setKoltelszam(telszam);
			this.kolcsonzokadatai.fireTableDataChanged();
			return true;
		}
		return false;
	}
	//K�lcs�nz� t�rl�se
	public void eltavolit(Kolcsonzo kolcsonzo) {
		if(kolcsonzo == null) {
			return;
		}
		List<Konyvek> konyvek = kolcsonzo.getKolcsonzottKonyvek();
		for(Konyvek konyv : konyvek) {
			konyv.setKikolcsonzi(null);
		}
		this.kolcsonzokadatai.torol(kolcsonzo);
	}
}
