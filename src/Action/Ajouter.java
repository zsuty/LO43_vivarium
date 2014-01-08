package Action;

import map.Map;
import objets.Objet;

public class Ajouter extends Action{

	private Objet objet;
	
	public Ajouter(Map map,Objet o){
		super(map);
		this.objet = o;
	}
	@Override
	public boolean action(String s) {
		if(this.map.ajouterObjet(this.objet)){
			s = new String("erreur loes de l'ajout d'objet");
		}
		return false;
	}

}
