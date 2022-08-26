package konyvtar;

import java.time.LocalDate;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class Kolcsonzo implements Serializable {

	private static final long serialVersionUID = 1L;
	/*
	 * Kölcsönzõ telefonszáma
	 */
	private String koltelszam;
	
	/*
	 * Kölcsönzõ telefonszáma 
	 */
	private String kolnev;
	
	/*
	 * Könytári kölcsönzési érvényessége mikortól.
	 */

	private final LocalDate tagsagKezdete;
	
	/*
	 * Mikor született. 
	 */
	
	private LocalDate szulIdo;
	
	/*
	 * Aktuálisan kölcsönzött könyvek listája. 
	 */
	private final List<Konyvek> kolcsonzottKonyvek;
	
	public Kolcsonzo(String telszam, String neve, LocalDate szulIdo) {
		this.kolnev=neve;
		this.koltelszam=telszam;
		this.szulIdo=szulIdo;
		this.kolcsonzottKonyvek = new ArrayList<>();
		this.tagsagKezdete=LocalDate.now();
	}

	/**
	 * @return the koltelszam
	 */
	public String getKoltelszam() {
		return koltelszam;
	}

	/**
	 * @param koltelszam the koltelszam to set
	 */
	public void setKoltelszam(String koltelszam) {
		this.koltelszam = koltelszam;
	}

	/**
	 * @return the kolnev
	 */
	public String getKolnev() {
		return kolnev;
	}

	/**
	 * @param kolnev the kolnev to set
	 */
	public void setKolnev(String kolnev) {
		this.kolnev = kolnev;
	}

	/**
	 * @return the tagsagKezdete
	 */
	public LocalDate getTagsagKezdete() {
		return tagsagKezdete;
	}
	
	public LocalDate getSzulIdo() {
		return szulIdo;
		
	}
	
	public void setSzulIdo(LocalDate szulIdo) {
		this.szulIdo = szulIdo;
	}

	/**
	 * @return the kolcsonzottKonyvek
	 */
	public List<Konyvek> getKolcsonzottKonyvek() {
		return kolcsonzottKonyvek;
	}
	
	public String toString() {
		return kolnev + " ;" + szulIdo + "; " + koltelszam + " ; " + kolcsonzottKonyvek.size() + "könyve van";
	}
	
}
