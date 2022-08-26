package konyvtar;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import junit.framework.Assert;

class KonyvtarTest {
	
	Konyvtar konyvtar;
	
	@SuppressWarnings("deprecation")
	@Test
	void beolvas() {
		this.konyvtar = new Konyvtar();
		this.konyvtar.inicializalTValtozok("konyvtar.libdat");
		this.konyvtar = Konyvtar.fajlbolBeolvas(konyvtar);
		Assert.assertEquals(3, konyvtar.getKonyvek().size());
		Assert.assertEquals("Balogh Árpád", konyvtar.getKonyvek().get(0).getKonyvSzerzo());
	}

	@Test
	void beviszadat() {
		this.konyvtar = new Konyvtar();
		this.konyvtar.inicializalTValtozok("konyvtar.libdat");
		this.konyvtar = Konyvtar.fajlbolBeolvas(konyvtar);
		this.konyvtar.addKolcsonzo("Hunor Monor", "1999-03-09", "6306541347");
		this.konyvtar.mentes();
		this.konyvtar = new Konyvtar();
		this.konyvtar.inicializalTValtozok("konyvtar.libdat");
		this.konyvtar = Konyvtar.fajlbolBeolvas(konyvtar);
		assertEquals("1999-03-09", konyvtar.getKolcsonzok().get(4).getSzulIdo().toString());
	}
}
