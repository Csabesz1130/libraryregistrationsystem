package konyvtar;

import java.time.LocalDate;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class Kolcsonzo implements Serializable {

	private static final long serialVersionUID = 1L;
	/*
	 * K�lcs�nz� telefonsz�ma
	 */
	private String koltelszam;
	
	/*
	 * K�lcs�nz� telefonsz�ma 
	 */
	private String kolnev;
	
	/*
	 * K�nyt�ri k�lcs�nz�si �rv�nyess�ge mikort�l.
	 */

	private final LocalDate tagsagKezdete;
	
	/*
	 * Mikor sz�letett. 
	 */
	
	private LocalDate szulIdo;
	
	/*
	 * Aktu�lisan k�lcs�nz�tt k�nyvek list�ja. 
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
		return kolnev + " ;" + szulIdo + "; " + koltelszam + " ; " + kolcsonzottKonyvek.size() + "k�nyve van";
	}
	
}
