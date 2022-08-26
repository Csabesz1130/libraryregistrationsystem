package konyvtar;
/*
 * Olyan enumer�ci�s t�pus, amely a k�nyek lehets�ges m�fajait t�rolja, k�s�bb ,,String-es�t�dik".
 */
public enum Mufaj {
	SCIENCE,
	CHILDREN,
	FANTASY,
	CONTEMPORARY,
	ROMANCE,
	MYSTERY,
	ART,
	HEALTH,
	ADVENTURE,
	HUMOR,
	MISCELLANEOUS;
	/*
	 * A magyar nyelv� (String t�pus�) megfelel�i az egyes m�fajoknak, a megad�s sorrendje t�k�rk�pe az angol-magyar ford�t�snak.
	 */
	public String getLocN(String loc) {
		if(loc.equals("HU")) {
			String[] forditott = {"tudom�nyos", "kicsiknek", "fant�zia", "kort�rs", "romantikus", "rejt�ly", "m�v�szet", "eg�szs�g", "kaland", "humor", "minden m�s"};
			return forditott[this.ordinal()];
		}
		return this.name();
	}
	/*
	 * Magyar nyelv� k�nyvt�pusokhoz val� getter f�ggv�ny.
	 */
	public String getLocN() {
		return getLocN("HU");
	} 
	/*
	 * M�faj �rt�k�t adja majd meg a k�nyveknek, �tkonvet�lva az enumer�ci�s t�pus �rt�k�t magyar nyelv� String-� (a felhaszn�l�
	 * �ltal �rtelmezhet� karakterl�ncc�).
	 */
	public static Mufaj ertek(String locName, String frLoc) {
		if(frLoc.equals("HU")) {
			switch(locName) {
				case "tudom�nyos":
					return SCIENCE;
				case "kicsiknek":
					return CHILDREN;
				case "fant�zia":
					return FANTASY;
				case "kort�rs":
					return CONTEMPORARY;
				case "romantikus":
					return ROMANCE;
				case "rejt�ly":
					return MYSTERY;
				case "m�v�szet":
					return ART;
				case "eg�szs�g":
					return HEALTH;
				case "kaland":
					return ADVENTURE;
				case "humor":
					return HUMOR;
				case "minden m�s":
					return MISCELLANEOUS;
				default:
					return null;
			}
		}
		return null;
	}
}
