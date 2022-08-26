package konyvtar;
/*
 * Olyan enumerációs típus, amely a könyek lehetséges mûfajait tárolja, késõbb ,,String-esítõdik".
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
	 * A magyar nyelvû (String típusú) megfelelõi az egyes mûfajoknak, a megadás sorrendje tükörképe az angol-magyar fordításnak.
	 */
	public String getLocN(String loc) {
		if(loc.equals("HU")) {
			String[] forditott = {"tudományos", "kicsiknek", "fantázia", "kortárs", "romantikus", "rejtély", "mûvészet", "egészség", "kaland", "humor", "minden más"};
			return forditott[this.ordinal()];
		}
		return this.name();
	}
	/*
	 * Magyar nyelvû könyvtípusokhoz való getter függvény.
	 */
	public String getLocN() {
		return getLocN("HU");
	} 
	/*
	 * Mûfaj értékét adja majd meg a könyveknek, átkonvetálva az enumerációs típus értékét magyar nyelvû String-é (a felhasználó
	 * által értelmezhetõ karakterlánccá).
	 */
	public static Mufaj ertek(String locName, String frLoc) {
		if(frLoc.equals("HU")) {
			switch(locName) {
				case "tudományos":
					return SCIENCE;
				case "kicsiknek":
					return CHILDREN;
				case "fantázia":
					return FANTASY;
				case "kortárs":
					return CONTEMPORARY;
				case "romantikus":
					return ROMANCE;
				case "rejtély":
					return MYSTERY;
				case "mûvészet":
					return ART;
				case "egészség":
					return HEALTH;
				case "kaland":
					return ADVENTURE;
				case "humor":
					return HUMOR;
				case "minden más":
					return MISCELLANEOUS;
				default:
					return null;
			}
		}
		return null;
	}
}
