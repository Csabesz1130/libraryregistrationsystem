package konyvtar;

import static org.junit.Assert.assertTrue;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class KonyvtarkezeloT {

	Konyvtar konyvtar;

    @Before
    public void setUp() {
        konyvtar = new Konyvtar();
        konyvtar.inicializalTValtozok("konyvtar.libdat");
    }
    
	@Test
	public void testHozzaadKonyv() {
		konyvtar = new Konyvtar();
        konyvtar.inicializalTValtozok("konyvtar.libdat");
		assertTrue(konyvtar.addKonyv("Winston Churchill", "Kubai szivarom", "1930", Mufaj.MISCELLANEOUS, "magyarul", false));
		Assert.assertEquals(1, konyvtar.getKonyvek().size());
	}
	
	@Test
	public void eltavolitKony() {
		konyvtar = new Konyvtar();
        konyvtar.inicializalTValtozok("konyvtar.libdat");
		konyvtar.addKonyv("Winston Churchill", "Kubai szivarom", "1930", Mufaj.MISCELLANEOUS, "magyarul", false);
		Assert.assertEquals(1, konyvtar.getKonyvek().size());
		konyvtar.eltavolit(konyvtar.getKonyvek().get(0));
		Assert.assertEquals(0, konyvtar.getKonyvek().size());
	}
	
	@Test 
	public void kikolcsonoz() {
		konyvtar = new Konyvtar();
        konyvtar.inicializalTValtozok("konyvtar.libdat");
        Kolcsonzo kolcsonzo = new Kolcsonzo("06603214590", "Patta Nóra", LocalDate.of(2000, 11, 11));
        Assert.assertEquals(0, kolcsonzo.getKolcsonzottKonyvek().size());
        Konyvek konyv = new Konyvek("Winston Churchill", "Kubai szivarom", 1930, Mufaj.MISCELLANEOUS, "magyarul", true);
        this.konyvtar.getKonyvekadatai().addKonyv(konyv);
        this.konyvtar.getKonyvekadatai().setValueAt(kolcsonzo, 0, 6);
        Assert.assertSame(konyv.getKikolcsonzi().getKolnev(), kolcsonzo.getKolnev());
	}
	
	@Test
	public void kolcsonzoEltavolit() {
		konyvtar = new Konyvtar();
        konyvtar.inicializalTValtozok("konyvtar.libdat");
        Kolcsonzo kolcsonzo = new Kolcsonzo("06603214590", "Patta Nóra", LocalDate.of(2000, 11, 11));
        this.konyvtar.addKolcsonzo("Patta Nóra", LocalDate.of(2000, 11, 11).toString(), "06603214590");
        Assert.assertEquals(1, konyvtar.getKolcsonzok().size());
        this.konyvtar.eltavolit(konyvtar.getKolcsonzok().get(0));
        Assert.assertEquals(0, konyvtar.getKolcsonzok().size());
	}
}
