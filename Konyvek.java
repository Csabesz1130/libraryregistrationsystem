package konyvtar;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Konyvek implements Serializable {
	/*
	 * Adattagok
	 */
	private String cime;

    private String szerzo;
    
    private int megjelenes_eve;

    private boolean kolcsstatusz;
    
    //private String mufaja;
    private Mufaj mufaj;
    
    private String nyelv;

    private Kolcsonzo kikolcsonzi;
    
    //private int kiadas;

    /*
     * K�nyv c�m�t adja vissza
     */
	public String getKonyvCime() {
		return cime;
	}
	/*
     * K�nyv c�m�t �ll�tja be.
     */
	public void setKonyvCime(String cime) {
		this.cime = cime;
	}
	/*
     * Alkot�t adja vissza
     */
	public String getKonyvSzerzo() {
		return szerzo;
	}
	/*
     * Alkot�t �ll�tja be.
     */
	public void setKonyvSzerzo(String szerzo) {
		this.szerzo = szerzo;
	}
	/*
     * K�lcs�nz�si st�tuszt adja meg
     */
	public boolean isKolcsstatusz() {
		return kolcsstatusz;
	}
	/*
     * K�lcs�nz�si st. �ll�tja be.
     */
	public void setKolcsstatusz(boolean kolcsstatusz) {
		this.kolcsstatusz = kolcsstatusz;
	}
	/*
	public String getKonyvMufaja() {
		return mufaja;
	}

	public void setKonyvMufaja(String mufaja) {
		this.mufaja = mufaja;
	}
	*/
	/*
	 * K�nyv nyelve
	 */
	public String getKonyvNyelv() {
		return nyelv;
	}
	/*
	 * K�nyv nyelve visszaad.
	 */
	public void setKonyvNyelv(String nyelv) {
		this.nyelv = nyelv;
	}
	/*
	 * Megjelenes eve viszaad
	 */
	public int getMegjelenes_eve() {
		return megjelenes_eve;
	}
	/*
	 * Megjelen�s �ve be�ll�t
	 */
	public void setMegjelenes_eve(int megjelenes_eve) {
		this.megjelenes_eve = megjelenes_eve;
	}
	/*
	 * K�nyvek konstruktora
	 */
	public Konyvek(String cime, String szerzo, int megj_eve, Mufaj mufaj, String lang, boolean kolcsonozheto) {
		this.cime = cime;
		this.szerzo=szerzo;
		this.megjelenes_eve=megj_eve;
		this.nyelv=lang;
		this.mufaj = mufaj;
		this.kolcsstatusz = kolcsonozheto;
	}

	/**
	 * @return the kikolcsonzi
	 */
	public Kolcsonzo getKikolcsonzi() {
		return kikolcsonzi;
	}

	/**
	 * @param kikolcsonzi the kikolcsonzi to set
	 */
	public void setKikolcsonzi(Kolcsonzo kikolcsonzi) {
		if(this.isKolcsstatusz()) {
			this.kikolcsonzi = kikolcsonzi;
		}
	}

	/**
	 * @return the mufaj
	 */
	public Mufaj getMufaj() {
		return mufaj;
	}

	/**
	 * @param mufaj the mufaj to set
	 */
	public void setMufaj(Mufaj mufaj) {
		this.mufaj = mufaj;
	}


}
