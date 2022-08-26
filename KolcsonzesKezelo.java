package konyvtar;

import javax.swing.*;

public class KolcsonzesKezelo {
	public static void kolcsStatuszValt(Konyvek konyv, Object something) {
		if(konyv.getKikolcsonzi() != null) {
			int valasz = JOptionPane.showConfirmDialog(null, "Ez a könyv valakinél van." + "Kikölcsönözhetõvé teheti, de akkor a jelenlegi kölcsönzés eltávolítódik.", "Ez a könyv kölcsönzött státuszban van nyilvántartva.", JOptionPane.YES_NO_OPTION);
			if(valasz == JOptionPane.YES_OPTION) {
				konyv.getKikolcsonzi().getKolcsonzottKonyvek().remove(konyv);
				konyv.setKikolcsonzi(null);
			}
			else {
				return;
			}
		}
		konyv.setKolcsstatusz((Boolean)something);
	}
	
	
	public static void kolcsonzoValt(Konyvek konyv, Object something) {
		Kolcsonzo kolcsonzo = (Kolcsonzo)something;
		Kolcsonzo elodje = konyv.getKikolcsonzi();
		if(elodje == kolcsonzo) {
			return;
		}
		if(kolcsonzo!=null) {
			if(!kolcsonzo.getKolcsonzottKonyvek().contains(konyv)) {
				kolcsonzo.getKolcsonzottKonyvek().add(konyv);
			}
		}
		if(elodje!=null) {
			elodje.getKolcsonzottKonyvek().remove(konyv);
		}
		konyv.setKikolcsonzi(kolcsonzo);
	}
	
}
