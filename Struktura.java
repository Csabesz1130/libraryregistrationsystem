package konyvtar;

import java.util.List;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

@SuppressWarnings("serial")
public class Struktura extends DefaultTreeModel {
	private final List<Kolcsonzo> kolcsonzok;
	
	public Struktura(List<Kolcsonzo> kolcsonzok) {
		super(new DefaultMutableTreeNode(kolcsonzok));
		this.kolcsonzok = kolcsonzok;
		// TODO Auto-generated constructor stub
	}
	@Override
	public Object getRoot() {
		return this.root;
	}
	@Override
	public Object getChild(Object os, int idx) {
		if(os==root) {
			return this.kolcsonzok.get(idx);
		}
		else {
			Kolcsonzo kolcsonzo = ((Kolcsonzo)os);
			Konyvek konyv = kolcsonzo.getKolcsonzottKonyvek().get(idx);
			return konyv.getKonyvSzerzo() + "- " + konyv.getKonyvCime();
		}
	}
	
	@Override
	public int getChildCount(Object os) {
		if(os==root) {
			return this.kolcsonzok.size();
		}
		return ((Kolcsonzo)os).getKolcsonzottKonyvek().size();
	}
	
	@Override
	public boolean isLeaf(Object fej) {
		if(kolcsonzok.contains(fej)) {
			return ((Kolcsonzo)fej).getKolcsonzottKonyvek().size()==0;
		}
		else {
			return fej != root;
		}
	}
}
