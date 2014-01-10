package Action;

import map.Map;
import objets.Objet;

public class SupprimerObjet extends Action{
	private Objet objet;
	
	public SupprimerObjet(Map m,Objet o){
		super(m);
		this.objet = o;
	}
	@Override
	public boolean action(String s) {
		this.map.supprimerObjet(this.objet);
		return false;
	}

}
