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
	 * Az adatok, ObjectOutputStream használatával
	 * történõ, paraméterként megadott helyre történõ lementése.
	 */
	public void mentes(String szerializaloUtvonal) {
		try {
			ObjectOutputStream ops = new ObjectOutputStream(new FileOutputStream(szerializaloUtvonal));
			ops.writeObject(this);
			ops.close();
		} catch(IOException ioex) {
			JOptionPane.showMessageDialog(null, "Sikertelen a könyvek mentése, hiba történt:" + ioex.getMessage(), "Probléma adódott.", JOptionPane.ERROR_MESSAGE);
		}
	}
	//Mentés az elõzõ függvény segítségével
	public void mentes() {
		mentes(this.szerializaloUtvonal);
	}
	//Mentés mint funkcióhoz szükséges függvény, dokumentációban kifejtve a funkció mûködése.
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
			int valasz = JOptionPane.showConfirmDialog(null, "Ugyanilyen nevû fájlt találtam. Létrehoz egy újat?", "Átnevezés", JOptionPane.YES_NO_OPTION);
			if(valasz==JOptionPane.YES_OPTION) {
				this.szerializaloUtvonal=szerializaloUtvonal;
				this.mentes();
			}
		}
	}
	/*
	 * Tranziens, tehát nem sorosított változók inicializálása egy paraméterként
	 * megadott elérési út használatával.
	 */
	public void inicializalTValtozok(String szerializaloUt) {
		this.konyvekadatai = new KonyvAdat(this.konyvek);
		this.kolcsonzokadatai = new KolcsonzoAdat(this.kolcsonzok);
		this.szerializaloUtvonal = szerializaloUt;
	}
	//Adatok fájlból türténõ beolvasása, figyelve a lehetséges hibákra és azokat lekezelve, jelezve a felhasználónak.
	public static Konyvtar fajlbolBeolvas(Konyvtar konyvtar) {
		try {
			String utvonal = konyvtar.szerializaloUtvonal;
			ObjectInputStream konyvtarBeolvasIS = new ObjectInputStream(new FileInputStream(utvonal));
			konyvtar = (Konyvtar)konyvtarBeolvasIS.readObject();
			konyvtarBeolvasIS.close();
			konyvtar.inicializalTValtozok(utvonal);
		} catch(StreamCorruptedException stCE) {
			JOptionPane.showMessageDialog(null, "Sérült a forrás" + "Hiba részletei:" + stCE.getMessage(), "Nem sikerült megtalálni a forrást.", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		catch(FileNotFoundException fajlEx) {
			JOptionPane.showMessageDialog(null, "A fájlt nem sikerült megtalálni." + "Az Import funkcióval próbálkozhat egy másik fájlnál. Üzenet:" + fajlEx.getMessage(), "Nem sikerült megtalálni a forrást.", JOptionPane.WARNING_MESSAGE);
			konyvtar = new Konyvtar();
			konyvtar.inicializalTValtozok("konyvtar.libdat");
		}
		catch(Exception exc) {
			JOptionPane.showMessageDialog(null, "Hiba az adatok beolvasását követõen." + "Hiba részletei" + exc.getMessage(), "Nem sikerült megtalálni a forrást.", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		return konyvtar;
	}
	//Keresés megvalósítása, amivel lehet szerzõben és címben keresni vagy csak cím alapján.
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
	//Kiköllcsönzött könyvek megjelenítése
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
	//Könyv eltávolítása a könyvtárból
	public void eltavolit(Konyvek konyv) {
		if(konyv == null) {
			return;
		}
		if(konyv.getKikolcsonzi() != null) {
			konyv.getKikolcsonzi().getKolcsonzottKonyvek().remove(konyv);
		}
		this.konyvekadatai.deleteKonyv(konyv);
	}
	//Kölcsönzõ felvétele a könyvtárba.
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
	//Adott kölcsönzõ egyes adattagjainak módosítása
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
	//Kölcsönzõ törlése
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
